package com.lucab.grimorium.blocks.altar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lucab.grimorium.blocks.ModBlocks;
import com.lucab.grimorium.blocks.pedestal.PedestalBlockEntity;
import com.lucab.grimorium.recipes.ModRecipes;
import com.lucab.grimorium.recipes.altar.AltarRecipe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class AltarBlockEntity extends BlockEntity {
    private ItemStack item = ItemStack.EMPTY;

    private int progress = 0;
    private int maxProgress = 0;

    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ALTAR_BLOCK_ENTITY.get(), pos, state);
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
        setChanged();
        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 3);
        }
    }

    public void removeItem() {
        setItem(ItemStack.EMPTY);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AltarBlockEntity blockEntity) {
        if (level.isClientSide)
            return;

        ItemStack catalyst = blockEntity.getItem();
        if (catalyst.isEmpty())
            return;

        List<ItemStack> inputs = new ArrayList<>();
        List<PedestalBlockEntity> pedestals = new ArrayList<>();

        BlockPos.betweenClosed(pos.offset(-3, 0, -3), pos.offset(3, 0, 3)).forEach(p -> {
            if (level.getBlockEntity(p) instanceof PedestalBlockEntity pedestal && !pedestal.getItem().isEmpty()) {
                inputs.add(pedestal.getItem());
                pedestals.add(pedestal);
            }
        });

        AltarRecipe.AltarRecipeInput input = new AltarRecipe.AltarRecipeInput(catalyst, inputs);

        Optional<RecipeHolder<AltarRecipe>> recipe = level.getRecipeManager().getRecipeFor(ModRecipes.ALTAR_TYPE.get(),
                input, level);

        if (recipe.isPresent()) {
            if (blockEntity.progress == 0)
                level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS);

            if (level instanceof ServerLevel serverLevel) {
                for (PedestalBlockEntity pedestal : pedestals) {
                    BlockPos pedPos = pedestal.getBlockPos();
                    double startX = pedPos.getX() + 0.5;
                    double startY = pedPos.getY() + 1.2;
                    double startZ = pedPos.getZ() + 0.5;
                    double targetX = pos.getX() + 0.5;
                    double targetY = pos.getY() + 1.2;
                    double targetZ = pos.getZ() + 0.5;
                    double dx = targetX - startX;
                    double dy = targetY - startY;
                    double dz = targetZ - startZ;
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                    // Particles to altar
                    serverLevel.sendParticles(
                            ParticleTypes.WITCH,
                            targetX, targetY, targetZ,
                            0,
                            dx / distance, dy / distance, dz / distance,
                            0.2);

                    // Particles to pedestals
                    serverLevel.sendParticles(
                            ParticleTypes.WITCH,
                            startX, startY, startZ,
                            0,
                            dx / distance, dy / distance, dz / distance,
                            0.2);

                    // Particles from pedestals to altar
                    serverLevel.sendParticles(
                            ParticleTypes.ENCHANT,
                            targetX, targetY + 0.5, targetZ,
                            0,
                            startX - targetX, startY - targetY, startZ - targetZ,
                            1.0);
                }
            }

            AltarRecipe r = recipe.get().value();
            blockEntity.maxProgress = r.getProcessTime();
            blockEntity.progress++;
            if (blockEntity.progress >= blockEntity.maxProgress) { // Consume catalyst
                level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS);
                blockEntity.removeItem();

                // Consume inputs
                List<PedestalBlockEntity> remainingPedestals = new ArrayList<>(pedestals);
                for (Ingredient ingredient : r.getInputs()) {
                    for (int i = 0; i < remainingPedestals.size(); i++) {
                        PedestalBlockEntity ped = remainingPedestals.get(i);
                        if (ingredient.test(ped.getItem())) {
                            ped.removeItem();
                            remainingPedestals.remove(i);
                            break;
                        }
                    }
                }

                // Pop result
                ItemStack result = r.assemble(input, level.registryAccess());
                ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5,
                        result);
                level.addFreshEntity(entity);

                blockEntity.progress = 0;
            }
        } else {
            blockEntity.progress = 0;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!item.isEmpty()) {
            tag.put("Item", item.save(registries));
        } else {
            tag.put("Item", new CompoundTag());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Item")) {
            item = ItemStack.parse(registries, tag.getCompound("Item")).orElse(ItemStack.EMPTY);
        } else {
            item = ItemStack.EMPTY;
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}

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
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class AltarBlockEntity extends BlockEntity implements Container {
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private boolean Active = false;

    private int progress = 0;
    private int maxProgress = 0;

    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ALTAR_BLOCK_ENTITY.get(), pos, state);
    }

    public ItemStack getItem() {
        return getItem(0);
    }

    public void setItem(ItemStack item) {
        setItem(0, item);
    }

    public void removeItem() {
        removeItem(0, 64);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.items) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(this.items, slot, amount);
        if (!result.isEmpty()) {
            setChanged();
        }
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.items.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    public boolean toogleActive() {
        this.Active = !this.Active;
        setChanged();
        return this.isActive();
    }

    public void setActive(boolean val) {
        this.Active = val;
        setChanged();
    }

    public boolean isActive() {
        return this.Active;
    }

    @Override
    public void setChanged() {
        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 3);
        }
        super.setChanged();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AltarBlockEntity blockEntity) {
        if (level.isClientSide)
            return;

        if (!blockEntity.isActive()) {
            blockEntity.resetProgress();
            return;
        }

        ItemStack catalyst = blockEntity.getItem();
        if (catalyst.isEmpty()) {
            blockEntity.resetProgress();
            return;
        }

        List<ItemStack> inputs = new ArrayList<>();
        List<PedestalBlockEntity> pedestals = new ArrayList<>();

        pedestals = getPedestals(level, pos);
        if (!pedestals.isEmpty()) {
            pedestals.forEach(ped -> {
                if (!ped.getItem().isEmpty()) {
                    inputs.add(ped.getItem());
                }
            });
        } else {
            blockEntity.resetProgress();
            return;
        }

        AltarRecipe.AltarRecipeInput input = new AltarRecipe.AltarRecipeInput(catalyst, inputs);
        Optional<RecipeHolder<AltarRecipe>> recipe = level.getRecipeManager().getRecipeFor(ModRecipes.ALTAR_TYPE.get(),
                input, level);

        if (recipe.isPresent()) {
            AltarRecipe r = recipe.get().value();

            if (blockEntity.progress == 0)
                level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS);

            List<PedestalBlockEntity> usedPedestals = getUsedPedestals(level, pos, r, pedestals);

            addParticles(level, pos, usedPedestals);

            blockEntity.maxProgress = r.getProcessTime();
            blockEntity.progress++;
            if (blockEntity.progress >= blockEntity.maxProgress) {
                level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS);
                blockEntity.removeItem();

                // Consume inputs
                for (PedestalBlockEntity ped : usedPedestals) {
                    ped.removeItem();
                }

                // Pop result
                ItemStack result = r.assemble(input, level.registryAccess());
                ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, result);
                level.addFreshEntity(entity);

                blockEntity.resetProgress();
            }
        } else {
            blockEntity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.setActive(false);
    }

    private static List<PedestalBlockEntity> getPedestals(Level level, BlockPos pos) {
        List<BlockEntity> blockEntities = new ArrayList<>();
        List<PedestalBlockEntity> pedestals = new ArrayList<>();

        blockEntities.add(level.getBlockEntity(pos.offset(0, 0, 3)));
        blockEntities.add(level.getBlockEntity(pos.offset(0, 0, -3)));
        blockEntities.add(level.getBlockEntity(pos.offset(3, 0, 0)));
        blockEntities.add(level.getBlockEntity(pos.offset(-3, 0, 0)));
        blockEntities.add(level.getBlockEntity(pos.offset(2, 0, 2)));
        blockEntities.add(level.getBlockEntity(pos.offset(-2, 0, -2)));
        blockEntities.add(level.getBlockEntity(pos.offset(2, 0, -2)));
        blockEntities.add(level.getBlockEntity(pos.offset(-2, 0, 2)));

        blockEntities.forEach(block -> {
            if (block instanceof PedestalBlockEntity pedestal)
                pedestals.add(pedestal);
        });

        return pedestals;
    }

    private static List<PedestalBlockEntity> getUsedPedestals(Level level, BlockPos pos, AltarRecipe r,
            List<PedestalBlockEntity> pedestals) {
        List<PedestalBlockEntity> usedPedestals = new ArrayList<>();
        List<PedestalBlockEntity> remainingPedestals = new ArrayList<>(pedestals);
        for (Ingredient ingredient : r.getInputs()) {
            for (int i = 0; i < remainingPedestals.size(); i++) {
                PedestalBlockEntity ped = remainingPedestals.get(i);
                if (ingredient.test(ped.getItem())) {
                    usedPedestals.add(ped);
                    remainingPedestals.remove(i);
                    break;
                }
            }
        }

        return usedPedestals;
    }

    private static void addParticles(Level level, BlockPos pos, List<PedestalBlockEntity> pedestals) {
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
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
        tag.putBoolean("Active", Active);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items.clear();
        ContainerHelper.loadAllItems(tag, this.items, registries);
        if (tag.contains("Item")) {
            ItemStack legacyItem = ItemStack.parse(registries, tag.getCompound("Item")).orElse(ItemStack.EMPTY);
            if (!legacyItem.isEmpty()) {
                setItem(0, legacyItem);
            }
        }
        Active = tag.getBoolean("Active");
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

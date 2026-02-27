package com.lucab.grimorium.blocks.stand;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class StandBlockEntity extends BlockEntity {
    private ItemStack item = ItemStack.EMPTY;

    public StandBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(StandRegister.STAND_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    public void setItem(ItemStack item) {
        ItemStack item_to_place = item.copy();
        item_to_place.setCount(1);
        this.item = item_to_place.copy();
        setChanged();
        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 3);
        }
    }

    public ItemStack getItem() {
        return item.copy();
    }

    public void removeItem() {
        setItem(ItemStack.EMPTY);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!item.isEmpty()) {
            tag.put("Item", item.save(registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Item")) {
            item = ItemStack.parse(registries, tag.getCompound("Item")).orElse(ItemStack.EMPTY);
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

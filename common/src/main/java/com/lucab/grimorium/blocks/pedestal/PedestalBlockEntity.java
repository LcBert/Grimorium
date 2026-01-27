package com.lucab.grimorium.blocks.pedestal;

import org.jetbrains.annotations.Nullable;

import com.lucab.grimorium.blocks.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PedestalBlockEntity extends BlockEntity {
    private ItemStack item = ItemStack.EMPTY;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.PEDESTAL_BLOCK_ENTITY.get(), pos, state);
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

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!item.isEmpty()) {
            tag.put("Item", item.save(registries));
        } else {
            tag.putBoolean("Empty", true);
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

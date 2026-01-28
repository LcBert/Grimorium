package com.lucab.grimorium.blocks.padlock;

import java.util.UUID;

import com.lucab.grimorium.blocks.ModBlocks;
import com.lucab.grimorium.items.PadlockKey;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PadlockBlockEntity extends BlockEntity {
    private UUID code;
    private UUID owner;

    public PadlockBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.PADLOCK_BLOCK_ENTITY.get(), pos, state);
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        setChanged();
    }

    public UUID generateCode() {
        this.code = UUID.randomUUID();
        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 3);
        }
        setChanged();
        return code;
    }

    public UUID getCode(UUID playerUuid) {
        if (owner.equals(playerUuid))
            return code;
        else
            return null;
    }

    public boolean attemptUnlock(ItemStack stack) {
        if (code != null) {
            UUID keyCode = PadlockKey.getCode(stack);
            boolean success = code.equals(keyCode);
            if (success) {
                emitRedstoneSignal();
            }
            return success;
        }
        return false;
    }

    public void emitRedstoneSignal() {
        if (this.level != null && !this.level.isClientSide) {
            BlockState blockState = this.getBlockState();
            if (blockState.hasProperty(PadlockBlock.POWERED) && !blockState.getValue(PadlockBlock.POWERED)) {
                this.level.setBlock(this.worldPosition, blockState.setValue(PadlockBlock.POWERED, true), 3);
                this.level.updateNeighborsAt(this.worldPosition, blockState.getBlock());
                this.level.scheduleTick(this.worldPosition, blockState.getBlock(), 20);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (code != null) {
            tag.putUUID("Code", code);
        }
        if (owner != null) {
            tag.putUUID("Owner", owner);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.hasUUID("Code")) {
            code = tag.getUUID("Code");
        }
        if (tag.hasUUID("Owner")) {
            owner = tag.getUUID("Owner");
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}

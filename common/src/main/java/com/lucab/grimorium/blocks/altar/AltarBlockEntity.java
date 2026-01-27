package com.lucab.grimorium.blocks.altar;

import com.lucab.grimorium.blocks.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AltarBlockEntity extends BlockEntity {
    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ALTAR_BLOCK_ENTITY.get(), pos, state);
    }
}

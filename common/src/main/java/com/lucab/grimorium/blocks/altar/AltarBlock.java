package com.lucab.grimorium.blocks.altar;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;

public class AltarBlock extends BaseEntityBlock {
    public static final MapCodec<AltarBlock> CODEC = simpleCodec(AltarBlock::new);

    public AltarBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> type) {
        return createTickerHelper(type, AltarRegister.ALTAR_BLOCK_ENTITY.get(), AltarBlockEntity::tick);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult) {
        AltarBlockEntity altar = (AltarBlockEntity) level.getBlockEntity(pos);
        ItemStack item = altar.getItem().copy();

        if (stack.getItem().equals(Items.FLINT_AND_STEEL)) {
            if (!item.isEmpty() && !altar.isActive()) {
                altar.setActive(true);
                level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS);
                stack.hurtAndBreak(!player.isCreative() ? 1 : 0, player, EquipmentSlot.MAINHAND);
                return ItemInteractionResult.SUCCESS;
            } else if (item.isEmpty()) {
                altar.setItem(stack.split(1));
                return ItemInteractionResult.SUCCESS;
            }
        } else {
            if (item.isEmpty() && !stack.isEmpty()) {
                altar.setItem(stack.split(1));
                return ItemInteractionResult.SUCCESS;
            } else if (!item.isEmpty()) {
                ItemStack removedItem = altar.removeItem();
                if (!player.getInventory().add(removedItem))
                    player.drop(removedItem, false);
                return ItemInteractionResult.SUCCESS;
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AltarBlockEntity altar) {
                if (altar.getItem() != ItemStack.EMPTY) {
                    BaseEntityBlock.popResourceFromFace(level, pos, Direction.UP, altar.getItem());
                }
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AltarVoxelShape.makeShape();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AltarBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}

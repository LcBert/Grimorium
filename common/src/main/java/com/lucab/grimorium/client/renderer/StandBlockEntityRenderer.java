package com.lucab.grimorium.client.renderer;

import com.lucab.grimorium.blocks.stand.StandBlock;
import com.lucab.grimorium.blocks.stand.StandBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class StandBlockEntityRenderer implements BlockEntityRenderer<StandBlockEntity> {
    public StandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(StandBlockEntity blockEntity, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack itemStack = blockEntity.getItem();
        if (!itemStack.isEmpty()) {
            poseStack.pushPose();
            switch (blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                case Direction.NORTH:
                    poseStack.translate(0.5D, 0.4D, 0.78D);
                    break;
                case Direction.EAST:
                    poseStack.translate(0.22D, 0.4D, 0.5D);
                    break;
                case Direction.SOUTH:
                    poseStack.translate(0.5D, 0.4D, 0.22D);
                    break;
                case Direction.WEST:
                    poseStack.translate(0.78D, 0.4D, 0.5D);
                    break;

                default:
                    break;
            }
            poseStack.scale(0.75F, 0.75F, 0.75F);

            Direction facing = blockEntity.getBlockState().getValue(StandBlock.FACING);
            poseStack.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));

            int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());
            Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.GROUND, lightAbove,
                    packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }
    }
}

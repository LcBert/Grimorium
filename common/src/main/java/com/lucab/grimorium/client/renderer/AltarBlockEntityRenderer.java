package com.lucab.grimorium.client.renderer;

import com.lucab.grimorium.blocks.altar.AltarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class AltarBlockEntityRenderer implements BlockEntityRenderer<AltarBlockEntity> {
    public AltarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(AltarBlockEntity blockEntity, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack itemStack = blockEntity.getItem();
        if (!itemStack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5D, 1.5D, 0.5D);
            poseStack.scale(1.5f, 1.5f, 1.5f);

            long time = blockEntity.getLevel().getGameTime();
            poseStack.mulPose(Axis.YP.rotationDegrees((time + partialTick) * 2)); // Rotate slowly

            int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());
            Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.GROUND, lightAbove,
                    packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }
    }
}

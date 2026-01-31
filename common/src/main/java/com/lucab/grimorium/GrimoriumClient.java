package com.lucab.grimorium;

import com.lucab.grimorium.blocks.ModBlocks;
import com.lucab.grimorium.client.renderer.AltarBlockEntityRenderer;
import com.lucab.grimorium.client.renderer.PedestalBlockEntityRenderer;
import com.lucab.grimorium.client.renderer.StandBlockEntityRenderer;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

public class GrimoriumClient {
    public static void init() {
        BlockEntityRendererRegistry.register(ModBlocks.ALTAR_BLOCK_ENTITY.get(), AltarBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlocks.PEDESTAL_BLOCK_ENTITY.get(), PedestalBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlocks.STAND_BLOCK_ENTITY.get(), StandBlockEntityRenderer::new);

        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.STAND_BLOCK.get());
    }
}

package com.lucab.grimorium;

import com.lucab.grimorium.blocks.ModBlocks;
import com.lucab.grimorium.client.renderer.AltarBlockEntityRenderer;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;

public class GrimoriumClient {
    public static void init() {
        BlockEntityRendererRegistry.register(ModBlocks.ALTAR_BLOCK_ENTITY.get(), AltarBlockEntityRenderer::new);
    }
}

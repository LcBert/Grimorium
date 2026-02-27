package com.lucab.grimorium;

import com.lucab.grimorium.blocks.altar.AltarRegister;
import com.lucab.grimorium.blocks.pedestal.PedestalRegister;
import com.lucab.grimorium.blocks.stand.StandRegister;
import com.lucab.grimorium.client.renderer.AltarBlockEntityRenderer;
import com.lucab.grimorium.client.renderer.PedestalBlockEntityRenderer;
import com.lucab.grimorium.client.renderer.StandBlockEntityRenderer;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

public class GrimoriumClient {
    public static void init() {
        BlockEntityRendererRegistry.register(AltarRegister.ALTAR_BLOCK_ENTITY.get(), AltarBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(PedestalRegister.PEDESTAL_BLOCK_ENTITY.get(),PedestalBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(StandRegister.STAND_BLOCK_ENTITY.get(), StandBlockEntityRenderer::new);

        RenderTypeRegistry.register(RenderType.cutout(), StandRegister.STAND_BLOCK.get());
    }
}

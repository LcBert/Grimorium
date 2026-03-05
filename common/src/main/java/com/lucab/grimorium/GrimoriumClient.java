package com.lucab.grimorium;

import com.lucab.grimorium.blocks.altar.AltarRegister;
import com.lucab.grimorium.blocks.pedestal.PedestalRegister;
import com.lucab.grimorium.blocks.stand.StandRegister;
import com.lucab.grimorium.blocks.ModBlocks;
import com.lucab.grimorium.client.renderer.AltarBlockEntityRenderer;
import com.lucab.grimorium.client.renderer.PedestalBlockEntityRenderer;
import com.lucab.grimorium.client.renderer.StandBlockEntityRenderer;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

public class GrimoriumClient {
    public static void init() {
        BlockEntityRendererRegistry.register(AltarRegister.ALTAR_BLOCK_ENTITY.get(), AltarBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(PedestalRegister.PEDESTAL_BLOCK_ENTITY.get(),
                PedestalBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(StandRegister.STAND_BLOCK_ENTITY.get(), StandBlockEntityRenderer::new);

        RenderTypeRegistry.register(RenderType.cutout(), StandRegister.STAND_BLOCK.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.ARCANE_SAPLING.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.ARCANE_LEAVES.get());

        // Purple color for Arcane Leaves
        ColorHandlerRegistry.registerBlockColors((state, level, pos, tintIndex) -> 0x8A2BE2, ModBlocks.ARCANE_LEAVES);
        ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> 0x8A2BE2, ModBlocks.ARCANE_LEAVES_ITEM);
    }
}

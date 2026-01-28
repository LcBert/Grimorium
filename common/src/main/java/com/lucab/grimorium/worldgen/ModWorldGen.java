package com.lucab.grimorium.worldgen;

import com.lucab.grimorium.Grimorium;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModWorldGen {
    public static void init() {
        System.out.println("Grimorium: Initializing WorldGen");
        BiomeModifications.addProperties((ctx, mutable) -> {
            if (ctx.hasTag(BiomeTags.IS_END)) {
                System.out.println("Grimorium: Adding ore to biome " + ctx.getKey());
                mutable.getGenerationProperties().addFeature(
                        GenerationStep.Decoration.UNDERGROUND_ORES,
                        ResourceKey.create(Registries.PLACED_FEATURE,
                                ResourceLocation.fromNamespaceAndPath(Grimorium.MODID, "end_grimor_ore")));
            }
        });
    }
}

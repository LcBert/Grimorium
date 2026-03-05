package com.lucab.grimorium.fabric;

import com.lucab.grimorium.Grimorium;
import terrablender.api.Regions;
import terrablender.api.Region;
import terrablender.api.RegionType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.resources.ResourceKey;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import terrablender.api.TerraBlenderApi;

public class ModTerraBlender implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        registerRegions();
    }

    public static void registerRegions() {
        Regions.register(
                new ArcaneRegion(ResourceLocation.fromNamespaceAndPath(Grimorium.MODID, "overworld_region"), 10));
    }

    public static class ArcaneRegion extends Region {
        public ArcaneRegion(ResourceLocation name, int weight) {
            super(name, RegionType.OVERWORLD, weight);
        }

        @Override
        public void addBiomes(Registry<Biome> registry,
                Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
            this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
                ResourceKey<Biome> ARCANE_FOREST = ResourceKey.create(net.minecraft.core.registries.Registries.BIOME,
                        ResourceLocation.fromNamespaceAndPath(Grimorium.MODID, "arcane_forest"));
                builder.replaceBiome(net.minecraft.world.level.biome.Biomes.DARK_FOREST, ARCANE_FOREST);
            });
        }
    }
}
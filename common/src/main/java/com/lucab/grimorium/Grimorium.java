package com.lucab.grimorium;

import com.lucab.grimorium.blocks.ModBlocks;
import com.lucab.grimorium.creativeTabs.ModCreativeModeTabs;
import com.lucab.grimorium.items.ModItems;
import com.lucab.grimorium.recipes.ModRecipes;
import com.lucab.grimorium.worldgen.ModWorldGen;

public final class Grimorium {
    public static final String MODID = "grimorium";

    public static void init() {
        ModBlocks.register();
        ModItems.register();
        ModCreativeModeTabs.register();
        ModRecipes.register();
        ModWorldGen.init();
    }
}

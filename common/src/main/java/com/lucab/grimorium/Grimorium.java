package com.lucab.grimorium;

import com.lucab.grimorium.blocks.ModBlocks;
import com.lucab.grimorium.creativeTabs.ModCreativeModeTabs;
import com.lucab.grimorium.items.ModItems;

public final class Grimorium {
    public static final String MODID = "grimorium";

    public static void init() {
        ModItems.register();
        ModBlocks.register();
        ModCreativeModeTabs.register();
    }
}

package com.lucab.grimorium.fabric;

import net.fabricmc.api.ModInitializer;

import com.lucab.grimorium.Grimorium;

public final class GrimoriumFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Grimorium.init();
    }
}

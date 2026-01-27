package com.lucab.grimorium.fabric;

import net.fabricmc.api.ModInitializer;

import com.lucab.grimorium.Grimorium;

public final class GrimoriumFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Grimorium.init();
    }
}

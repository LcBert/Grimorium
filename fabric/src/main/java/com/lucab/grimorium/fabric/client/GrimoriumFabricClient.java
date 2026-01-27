package com.lucab.grimorium.fabric.client;

import com.lucab.grimorium.GrimoriumClient;
import net.fabricmc.api.ClientModInitializer;

public final class GrimoriumFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GrimoriumClient.init();
    }
}

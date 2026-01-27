package com.lucab.grimorium.neoforge;

import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.GrimoriumClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Grimorium.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class GrimoriumNeoForgeClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        GrimoriumClient.init();
    }
}

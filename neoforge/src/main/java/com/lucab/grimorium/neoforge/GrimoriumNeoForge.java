package com.lucab.grimorium.neoforge;

import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.neoforge.loot.ModLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Grimorium.MODID)
public final class GrimoriumNeoForge {
    public GrimoriumNeoForge(IEventBus bus) {
        Grimorium.init();
        ModLootModifiers.register(bus);

        bus.addListener((FMLCommonSetupEvent event) -> {
            event.enqueueWork(() -> {
                ModTerraBlender.registerRegions();
            });
        });
    }
}

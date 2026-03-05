package com.lucab.grimorium.neoforge;

import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.neoforge.loot.ModLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Grimorium.MODID)
public final class GrimoriumNeoForge {
    public GrimoriumNeoForge(IEventBus bus) {
        Grimorium.init();
        ModLootModifiers.register(bus);
    }
}

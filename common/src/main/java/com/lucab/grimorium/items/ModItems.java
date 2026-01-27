package com.lucab.grimorium.items;

import com.lucab.grimorium.Grimorium;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Grimorium.MODID, Registries.ITEM);

    public static final RegistrySupplier<Item> RUBIN = ITEMS.register(
            "rubin",
            () -> new Rubin(new Item.Properties()));

    public static void register() {
        ITEMS.register();
    }
}

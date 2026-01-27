package com.lucab.grimorium.items;

import com.lucab.grimorium.Grimorium;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Grimorium.MODID, Registries.ITEM);

    // Grimor
    public static final RegistrySupplier<Item> RAW_GRIMOR = ITEMS.register(
            "raw_grimor",
            () -> new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> GRIMOR_INGOT = ITEMS.register(
            "grimor_ingot",
            () -> new Item(new Item.Properties()));

    public static void register() {
        ITEMS.register();
    }
}

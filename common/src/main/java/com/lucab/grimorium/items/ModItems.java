package com.lucab.grimorium.items;

import java.util.List;

import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.tool_tiers.ModToolTiers;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SwordItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Grimorium.MODID, Registries.ITEM);

    // Padlock Key
    public static final RegistrySupplier<Item> PADLOCK_KEY = ITEMS.register(
            "padlock_key",
            () -> new PadlockKey(new Item.Properties().stacksTo(1)));

    // Grimor
    public static final RegistrySupplier<Item> GRIMOR_SCRAP = ITEMS.register(
            "grimor_scrap",
            () -> new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> GRIMOR_INGOT = ITEMS.register(
            "grimor_ingot",
            () -> new Item(new Item.Properties()));

    // Grimor Sword
    public static final RegistrySupplier<SwordItem> GRIMOR_SWORD = ITEMS.register(
            "grimor_sword",
            () -> new SwordItem(ModToolTiers.GRIMOR,
                    new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.GRIMOR, 5, -2.4f))));

    // Grimor Pickaxe
    public static final RegistrySupplier<PickaxeItem> GRIMOR_PICKAXE = ITEMS.register(
            "grimor_pickaxe",
            () -> new PickaxeItem(ModToolTiers.GRIMOR,
                    new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.GRIMOR, 2, -2.8f))));

    // Grimor Axe
    public static final RegistrySupplier<AxeItem> GRIMOR_AXE = ITEMS.register(
            "grimor_axe",
            () -> new AxeItem(ModToolTiers.GRIMOR,
                    new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.GRIMOR, 7, -3f))));

    // Grimor Shovel
    public static final RegistrySupplier<ShovelItem> GRIMOR_SHOVEL = ITEMS.register(
            "grimor_shovel",
            () -> new ShovelItem(ModToolTiers.GRIMOR,
                    new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.GRIMOR, 2.5f, -3f))));

    // Grimor Hoe
    public static final RegistrySupplier<HoeItem> GRIMOR_HOE = ITEMS.register(
            "grimor_hoe",
            () -> new HoeItem(ModToolTiers.GRIMOR,
                    new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.GRIMOR, -4, -3f))));

    // Grimor Ppgrade Smithing Template
    public static final RegistrySupplier<SmithingTemplateItem> GRIMOR_UPGRADE_SMITHING_TEMPLATE = ITEMS.register(
            "grimor_upgrade_smithing_template",
            () -> new SmithingTemplateItem(
                    Component.translatable("tooltip.grimorium.upgrade_applies_to").withStyle(ChatFormatting.BLUE),
                    Component.translatable("tooltip.grimorium.upgrade_ingredients").withStyle(ChatFormatting.BLUE),
                    Component.translatable("tooltip.grimorium.upgrade_title").withStyle(ChatFormatting.GRAY),
                    Component.translatable("tooltip.grimorium.upgrade_base_slot_description"),
                    Component.translatable("tooltip.grimorium.upgrade_additions_slot_description"),
                    List.of(
                            ResourceLocation.withDefaultNamespace("item/empty_slot_sword"),
                            ResourceLocation.withDefaultNamespace("item/empty_slot_pickaxe"),
                            ResourceLocation.withDefaultNamespace("item/empty_slot_axe"),
                            ResourceLocation.withDefaultNamespace("item/empty_slot_shovel"),
                            ResourceLocation.withDefaultNamespace("item/empty_slot_hoe"),
                            ResourceLocation.withDefaultNamespace("item/empty_armor_slot_helmet"),
                            ResourceLocation.withDefaultNamespace("item/empty_armor_slot_chestplate"),
                            ResourceLocation.withDefaultNamespace("item/empty_armor_slot_leggings"),
                            ResourceLocation.withDefaultNamespace("item/empty_armor_slot_boots")),
                    List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_ingot"))));

    public static void register() {
        ITEMS.register();
    }
}

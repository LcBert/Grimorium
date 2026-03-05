package com.lucab.grimorium.fabric;

import net.fabricmc.api.ModInitializer;

import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.items.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public final class GrimoriumFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Grimorium.init();
        LootTableEvents.MODIFY.register((ResourceKey<LootTable> key, LootTable.Builder tableBuilder,
                LootTableSource source, HolderLookup.Provider provider) -> {
            if (key.location().equals(ResourceLocation.fromNamespaceAndPath("minecraft", "chests/end_city_treasure"))) {
                LootPool pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.GRIMOR_UPGRADE_SMITHING_TEMPLATE.get())
                                .setWeight(1)
                                .when(LootItemRandomChanceCondition.randomChance(0.15f)))
                        .build();
                tableBuilder.pool(pool);
            }
        });
    }
}

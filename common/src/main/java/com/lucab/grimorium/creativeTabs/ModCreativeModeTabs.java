package com.lucab.grimorium.creativeTabs;

import com.lucab.grimorium.blocks.altar.AltarRegister;
import com.lucab.grimorium.blocks.pedestal.PedestalRegister;
import com.lucab.grimorium.blocks.padlock.PadlockRegister;
import com.lucab.grimorium.blocks.stand.StandRegister;
import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.items.ModItems;
import com.lucab.grimorium.blocks.ModBlocks;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.Row;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Grimorium.MODID,
            Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> GRIMORIUM_TAB = TABS.register("grimorium_tab",
            () -> CreativeModeTab.builder(Row.TOP, 1)
                    .title(Component.translatable("itemGroup.grimorium"))
                    .icon(() -> new ItemStack(ModItems.GRIMOR_INGOT.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.GRIMOR_ORE_ITEM.get());
                        output.accept(ModItems.RAW_GRIMOR.get());
                        output.accept(ModItems.GRIMOR_INGOT.get());
                        output.accept(AltarRegister.ALTAR_ITEM.get());
                        output.accept(PedestalRegister.PEDESTAL_ITEM.get());
                        output.accept(PadlockRegister.PADLOCK_ITEM.get());
                        output.accept(ModItems.PADLOCK_KEY.get());
                        output.accept(StandRegister.STAND_ITEM.get());
                    }).build());

    public static void register() {
        TABS.register();
    }
}

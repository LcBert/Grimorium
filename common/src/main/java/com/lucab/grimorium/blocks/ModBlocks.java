package com.lucab.grimorium.blocks;

import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.blocks.altar.AltarRegister;
import com.lucab.grimorium.blocks.pedestal.PedestalRegister;
import com.lucab.grimorium.blocks.stand.StandRegister;
import com.lucab.grimorium.items.ModItems;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Grimorium.MODID, Registries.BLOCK);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Grimorium.MODID,
            Registries.BLOCK_ENTITY_TYPE);

    // Grimor Ore
    public static final RegistrySupplier<Block> GRIMOR_ORE = BLOCKS.register(
            "grimor_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3.0F)
                    .requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Item> GRIMOR_ORE_ITEM = ModItems.ITEMS
            .register("grimor_ore",
                    () -> new BlockItem(GRIMOR_ORE.get(), new Item.Properties()));

    public static void register() {
        BLOCKS.register();
        BLOCK_ENTITIES.register();

        AltarRegister.register();
        PedestalRegister.register();
        StandRegister.register();
    }
}

package com.lucab.grimorium.blocks.stand;

import com.lucab.grimorium.blocks.ModBlocks;
import com.lucab.grimorium.items.ModItems;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class StandRegister {
    public static final RegistrySupplier<Block> STAND_BLOCK = ModBlocks.BLOCKS
            .register("stand",
                    () -> new StandBlock(BlockBehaviour.Properties.of()
                            .strength(1.5F)
                            .noOcclusion()));

    public static final RegistrySupplier<BlockEntityType<StandBlockEntity>> STAND_BLOCK_ENTITY = ModBlocks.BLOCK_ENTITIES
            .register("stand",
                    () -> BlockEntityType.Builder.of(StandBlockEntity::new, STAND_BLOCK.get())
                            .build(null));

    public static final RegistrySupplier<Item> STAND_ITEM = ModItems.ITEMS
            .register("stand",
                    () -> new BlockItem(STAND_BLOCK.get(), new Item.Properties()));

    public static void register() {
    }
}

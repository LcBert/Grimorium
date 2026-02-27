package com.lucab.grimorium.blocks.pedestal;

import com.lucab.grimorium.blocks.ModBlocks;
import com.lucab.grimorium.items.ModItems;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PedestalRegister {
    public static final RegistrySupplier<Block> PEDESTAL_BLOCK = ModBlocks.BLOCKS
            .register("pedestal",
                    () -> new PedestalBlock(BlockBehaviour.Properties.of()
                            .strength(3.5F)
                            .noOcclusion()));

    public static final RegistrySupplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BLOCK_ENTITY = ModBlocks.BLOCK_ENTITIES
            .register("pedestal",
                    () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, PEDESTAL_BLOCK.get())
                            .build(null));

    public static final RegistrySupplier<Item> PEDESTAL_ITEM = ModItems.ITEMS
            .register("pedestal",
                    () -> new BlockItem(PEDESTAL_BLOCK.get(), new Item.Properties()));

    public static void register() {
    }
}

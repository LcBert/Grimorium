package com.lucab.grimorium.blocks.padlock;

import com.lucab.grimorium.blocks.ModBlocks;
import com.lucab.grimorium.items.ModItems;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PadlockRegister {
    public static final RegistrySupplier<Block> PADLOCK_BLOCK = ModBlocks.BLOCKS
            .register("padlock",
                    () -> new PadlockBlock(BlockBehaviour.Properties.of()
                            .strength(2.0F)
                            .noOcclusion()
                            .requiresCorrectToolForDrops()));

    public static final RegistrySupplier<BlockEntityType<PadlockBlockEntity>> PADLOCK_BLOCK_ENTITY = ModBlocks.BLOCK_ENTITIES
            .register("padlock",
                    () -> BlockEntityType.Builder.of(PadlockBlockEntity::new, PADLOCK_BLOCK.get())
                            .build(null));

    public static final RegistrySupplier<Item> PADLOCK_ITEM = ModItems.ITEMS
            .register("padlock",
                    () -> new BlockItem(PADLOCK_BLOCK.get(), new Item.Properties()));

    public static void register() {
    }
}

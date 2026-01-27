package com.lucab.grimorium.blocks;

import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.blocks.altar.AltarBlock;
import com.lucab.grimorium.blocks.altar.AltarBlockEntity;
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

    public static final RegistrySupplier<Block> ALTAR_BLOCK = BLOCKS.register("altar",
            () -> new AltarBlock(BlockBehaviour.Properties.of().strength(3.5F)));

    public static final RegistrySupplier<BlockEntityType<AltarBlockEntity>> ALTAR_BLOCK_ENTITY = BLOCK_ENTITIES
            .register("altar",
                    () -> BlockEntityType.Builder.of(AltarBlockEntity::new, ModBlocks.ALTAR_BLOCK.get()).build(null));

    public static final RegistrySupplier<Item> ALTAR_ITEM = ModItems.ITEMS.register("altar",
            () -> new BlockItem(ModBlocks.ALTAR_BLOCK.get(), new Item.Properties()));

    public static void register() {
        BLOCKS.register();
        BLOCK_ENTITIES.register();
    }
}

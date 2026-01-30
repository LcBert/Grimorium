package com.lucab.grimorium.blocks;

import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.blocks.altar.AltarBlock;
import com.lucab.grimorium.blocks.altar.AltarBlockEntity;
import com.lucab.grimorium.blocks.padlock.PadlockBlock;
import com.lucab.grimorium.blocks.padlock.PadlockBlockEntity;
import com.lucab.grimorium.blocks.pedestal.PedestalBlock;
import com.lucab.grimorium.blocks.pedestal.PedestalBlockEntity;
import com.lucab.grimorium.blocks.stand.SmallStandBlock;
import com.lucab.grimorium.blocks.stand.SmallStandBlockEntity;
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

    // Altar
    public static final RegistrySupplier<Block> ALTAR_BLOCK = BLOCKS
            .register("altar",
                    () -> new AltarBlock(BlockBehaviour.Properties.of()
                            .strength(3.5F)
                            .noOcclusion()));

    public static final RegistrySupplier<BlockEntityType<AltarBlockEntity>> ALTAR_BLOCK_ENTITY = BLOCK_ENTITIES
            .register("altar",
                    () -> BlockEntityType.Builder.of(AltarBlockEntity::new, ALTAR_BLOCK.get()).build(null));

    public static final RegistrySupplier<Item> ALTAR_ITEM = ModItems.ITEMS
            .register("altar",
                    () -> new BlockItem(ALTAR_BLOCK.get(), new Item.Properties()));

    // Pedestal
    public static final RegistrySupplier<Block> PEDESTAL_BLOCK = BLOCKS
            .register("pedestal",
                    () -> new PedestalBlock(BlockBehaviour.Properties.of()
                            .strength(3.5F)
                            .noOcclusion()));

    public static final RegistrySupplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BLOCK_ENTITY = BLOCK_ENTITIES
            .register("pedestal",
                    () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, PEDESTAL_BLOCK.get())
                            .build(null));

    public static final RegistrySupplier<Item> PEDESTAL_ITEM = ModItems.ITEMS
            .register("pedestal",
                    () -> new BlockItem(PEDESTAL_BLOCK.get(), new Item.Properties()));

    // Padlock
    public static final RegistrySupplier<Block> PADLOCK_BLOCK = BLOCKS
            .register("padlock",
                    () -> new PadlockBlock(BlockBehaviour.Properties.of()
                            .strength(2.0F)
                            .noOcclusion()
                            .requiresCorrectToolForDrops()));

    public static final RegistrySupplier<BlockEntityType<PadlockBlockEntity>> PADLOCK_BLOCK_ENTITY = BLOCK_ENTITIES
            .register("padlock",
                    () -> BlockEntityType.Builder.of(PadlockBlockEntity::new, PADLOCK_BLOCK.get())
                            .build(null));

    public static final RegistrySupplier<Item> PADLOCK_ITEM = ModItems.ITEMS
            .register("padlock",
                    () -> new BlockItem(PADLOCK_BLOCK.get(), new Item.Properties()));

    // Grimor Ore
    public static final RegistrySupplier<Block> GRIMOR_ORE = BLOCKS.register(
            "grimor_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3.0F)
                    .requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Item> GRIMOR_ORE_ITEM = ModItems.ITEMS
            .register("grimor_ore",
                    () -> new BlockItem(GRIMOR_ORE.get(), new Item.Properties()));

    // Stands
    public static final RegistrySupplier<Block> SMALL_STAND_BLOCK = BLOCKS
            .register("small_stand",
                    () -> new SmallStandBlock(BlockBehaviour.Properties.of()
                            .strength(1.5F)
                            .noOcclusion()));

    public static final RegistrySupplier<BlockEntityType<SmallStandBlockEntity>> SMALL_STAND_BLOCK_ENTITY = BLOCK_ENTITIES
            .register("small_stand",
                    () -> BlockEntityType.Builder.of(SmallStandBlockEntity::new, SMALL_STAND_BLOCK.get())
                            .build(null));

    public static final RegistrySupplier<Item> SMALL_STAND_ITEM = ModItems.ITEMS
            .register("small_stand",
                    () -> new BlockItem(SMALL_STAND_BLOCK.get(), new Item.Properties()));

    public static void register() {
        BLOCKS.register();
        BLOCK_ENTITIES.register();
    }
}

package com.lucab.grimorium.blocks;

import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.blocks.altar.AltarRegister;
import com.lucab.grimorium.blocks.pedestal.PedestalRegister;
import com.lucab.grimorium.blocks.stand.StandRegister;
import com.lucab.grimorium.items.ModItems;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

import java.util.Optional;

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

    // Arcane Forest Blocks
    public static final RegistrySupplier<Block> ARCANE_LOG = BLOCKS.register(
            "arcane_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .strength(2.0F)
                    .sound(SoundType.WOOD)) {
                @Override
                public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
                    if (random.nextInt(5) == 0) {
                        Direction direction = net.minecraft.core.Direction.getRandom(random);
                        if (direction != net.minecraft.core.Direction.UP) {
                            BlockPos blockpos = pos.relative(direction);
                            BlockState blockstate = level.getBlockState(blockpos);
                            if (!state.canOcclude()
                                    || !blockstate.isFaceSturdy(level, blockpos, direction.getOpposite())) {
                                double d0 = direction.getStepX() == 0 ? random.nextDouble()
                                        : 0.5D + (double) direction.getStepX() * 0.6D;
                                double d1 = direction.getStepY() == 0 ? random.nextDouble()
                                        : 0.5D + (double) direction.getStepY() * 0.6D;
                                double d2 = direction.getStepZ() == 0 ? random.nextDouble()
                                        : 0.5D + (double) direction.getStepZ() * 0.6D;
                                level.addParticle(net.minecraft.core.particles.ParticleTypes.DRIPPING_OBSIDIAN_TEAR,
                                        (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2,
                                        0.0D, 0.0D, 0.0D);
                            }
                        }
                    }
                }
            });

    public static final RegistrySupplier<Block> ARCANE_LEAVES = BLOCKS.register(
            "arcane_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.of()
                    .strength(0.2F)
                    .randomTicks()
                    .sound(SoundType.GRASS)
                    .noOcclusion()
                    .isSuffocating((state, level, pos) -> false)
                    .isViewBlocking((state, level, pos) -> false)
                    .lightLevel(state -> 5)));

    public static final TreeGrower ARCANE_TREE_GROWER = new TreeGrower(
            "arcane_tree",
            Optional.empty(),
            Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(Grimorium.MODID, "arcane_tree"))),
            Optional.empty());

    public static final RegistrySupplier<Block> ARCANE_SAPLING = BLOCKS.register(
            "arcane_sapling",
            () -> new SaplingBlock(
                    ARCANE_TREE_GROWER,
                    BlockBehaviour.Properties.of()
                            .noCollission()
                            .randomTicks()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .pushReaction(net.minecraft.world.level.material.PushReaction.DESTROY)));

    public static final RegistrySupplier<Block> ARCANE_PLANKS = BLOCKS.register(
            "arcane_planks",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final RegistrySupplier<Block> ARCANE_SLAB = BLOCKS.register(
            "arcane_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final RegistrySupplier<Block> ARCANE_STAIRS = BLOCKS.register(
            "arcane_stairs",
            () -> new StairBlock(ARCANE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of()
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final RegistrySupplier<Block> ARCANE_FENCE = BLOCKS.register(
            "arcane_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of()
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final RegistrySupplier<Block> ARCANE_FENCE_GATE = BLOCKS.register(
            "arcane_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.of()
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final RegistrySupplier<Item> GRIMOR_ORE_ITEM = ModItems.ITEMS
            .register("grimor_ore",
                    () -> new BlockItem(GRIMOR_ORE.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> ARCANE_LOG_ITEM = ModItems.ITEMS
            .register("arcane_log",
                    () -> new BlockItem(ARCANE_LOG.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> ARCANE_LEAVES_ITEM = ModItems.ITEMS
            .register("arcane_leaves",
                    () -> new BlockItem(ARCANE_LEAVES.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> ARCANE_SAPLING_ITEM = ModItems.ITEMS
            .register("arcane_sapling",
                    () -> new BlockItem(ARCANE_SAPLING.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> ARCANE_PLANKS_ITEM = ModItems.ITEMS
            .register("arcane_planks",
                    () -> new BlockItem(ARCANE_PLANKS.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> ARCANE_SLAB_ITEM = ModItems.ITEMS
            .register("arcane_slab",
                    () -> new BlockItem(ARCANE_SLAB.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> ARCANE_STAIRS_ITEM = ModItems.ITEMS
            .register("arcane_stairs",
                    () -> new BlockItem(ARCANE_STAIRS.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> ARCANE_FENCE_ITEM = ModItems.ITEMS
            .register("arcane_fence",
                    () -> new BlockItem(ARCANE_FENCE.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> ARCANE_FENCE_GATE_ITEM = ModItems.ITEMS
            .register("arcane_fence_gate",
                    () -> new BlockItem(ARCANE_FENCE_GATE.get(), new Item.Properties()));

    public static void register() {
        BLOCKS.register();
        BLOCK_ENTITIES.register();

        AltarRegister.register();
        PedestalRegister.register();
        StandRegister.register();
    }
}

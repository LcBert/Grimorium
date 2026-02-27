package com.lucab.grimorium.blocks.altar;

import java.util.List;

import com.lucab.grimorium.blocks.ModBlocks;
import com.lucab.grimorium.items.ModItems;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AltarRegister {
    public static final RegistrySupplier<Block> ALTAR_BLOCK = ModBlocks.BLOCKS
            .register("altar",
                    () -> new AltarBlock(BlockBehaviour.Properties.of()
                            .strength(3.5F)
                            .noOcclusion()));

    public static final RegistrySupplier<BlockEntityType<AltarBlockEntity>> ALTAR_BLOCK_ENTITY = ModBlocks.BLOCK_ENTITIES
            .register("altar",
                    () -> BlockEntityType.Builder.of(AltarBlockEntity::new, ALTAR_BLOCK.get()).build(null));

    public static final RegistrySupplier<Item> ALTAR_ITEM = ModItems.ITEMS
            .register("altar",
                    () -> new BlockItem(ALTAR_BLOCK.get(), new Item.Properties()) {
                        @Override
                        public void appendHoverText(ItemStack stack, Item.TooltipContext context,
                                List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                            tooltipComponents.add(
                                    Component.translatable("tooltip.grimorium.altar.activate").withColor(0x6002A8));
                        }
                    });

    public static void register() {
    }
}

package com.lucab.grimorium.items;

import com.lucab.grimorium.blocks.padlock.PadlockBlockEntity;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import java.util.UUID;

public class PadlockKey extends Item {
    public PadlockKey(Properties properties) {
        super(properties);
    }

    public static void setCode(ItemStack stack, UUID code) {
        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY,
                data -> data.update(tag -> tag.putUUID("Code", code)));
    }

    public static UUID getCode(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag tag = data.copyTag();
            if (tag.hasUUID("Code")) {
                return tag.getUUID("Code");
            }
        }
        return null;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockEntity blockEntity = level.getBlockEntity(context.getClickedPos());
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();

        if (level.isClientSide()) {
            return blockEntity instanceof PadlockBlockEntity ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        if (blockEntity instanceof PadlockBlockEntity padlockBlockEntity) {
            UUID keyUuid = getCode(stack);

            if (player.isShiftKeyDown()) {
                // Set lock's code to key's code
                UUID lockUuid = padlockBlockEntity.getCode(player.getUUID());
                if (lockUuid != null) {
                    setCode(stack, lockUuid);
                    player.displayClientMessage(
                            Component.literal("Key bound to lock"), true);
                    return InteractionResult.SUCCESS;
                } else {
                    player.displayClientMessage(
                            Component.literal("You are not the owner of this lock"), true);
                    return InteractionResult.FAIL;
                }
            } else {
                if (keyUuid != null) {
                    // Key has code, attempt unlock
                    if (padlockBlockEntity.attemptUnlock(stack)) {
                        level.playSound(null,
                                context.getClickedPos(),
                                SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS,
                                1.0F, 1.0F);
                        return InteractionResult.SUCCESS;
                    } else {
                        level.playSound(null,
                                context.getClickedPos(),
                                SoundEvents.CHEST_CLOSE, SoundSource.BLOCKS,
                                1.0F, 1.0F);
                        return InteractionResult.FAIL;
                    }
                } else {
                    player.displayClientMessage(Component.literal("This key has no code"), true);
                    return InteractionResult.FAIL;
                }
            }
        }

        return super.useOn(context);
    }
}

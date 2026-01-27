package com.lucab.grimorium.recipes.altar;

import java.util.ArrayList;
import java.util.List;

import com.lucab.grimorium.recipes.ModRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class AltarRecipe implements Recipe<RecipeInput> {
    private final Ingredient catalyst;
    private final List<Ingredient> inputs;
    private final ItemStack result;

    public AltarRecipe(Ingredient catalyst, List<Ingredient> inputs, ItemStack result) {
        this.catalyst = catalyst;
        this.inputs = inputs;
        this.result = result;
    }

    public Ingredient getCatalyst() {
        return catalyst;
    }

    public List<Ingredient> getInputs() {
        return inputs;
    }

    public ItemStack getResult() {
        return result;
    }

    @Override
    public boolean matches(RecipeInput pInput, Level pLevel) {
        if (!(pInput instanceof AltarRecipeInput input)) {
            return false;
        }

        if (!catalyst.test(input.catalyst)) {
            return false;
        }

        List<ItemStack> remainingInputs = new ArrayList<>(input.inputs);
        for (Ingredient ingredient : inputs) {
            boolean matched = false;
            for (int i = 0; i < remainingInputs.size(); i++) {
                if (ingredient.test(remainingInputs.get(i))) {
                    remainingInputs.remove(i);
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(RecipeInput pInput, HolderLookup.Provider pRegistries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ALTAR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ALTAR_TYPE.get();
    }

    public static class AltarRecipeInput implements RecipeInput {
        private final ItemStack catalyst;
        private final List<ItemStack> inputs;

        public AltarRecipeInput(ItemStack catalyst, List<ItemStack> inputs) {
            this.catalyst = catalyst;
            this.inputs = inputs;
        }

        @Override
        public ItemStack getItem(int index) {
            if (index == 0)
                return catalyst;
            if (index - 1 < inputs.size())
                return inputs.get(index - 1);
            return ItemStack.EMPTY;
        }

        @Override
        public int size() {
            return 1 + inputs.size();
        }

        public ItemStack catalyst() {
            return catalyst;
        }

        public List<ItemStack> inputs() {
            return inputs;
        }

        public boolean isEmpty() {
            return catalyst.isEmpty() && inputs.isEmpty();
        }
    }

    public static class Type implements RecipeType<AltarRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "altar";
    }

    public static class Serializer implements RecipeSerializer<AltarRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public static final MapCodec<AltarRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("catalyst").forGetter(AltarRecipe::getCatalyst),
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("inputs").forGetter(AltarRecipe::getInputs),
                ItemStack.CODEC.fieldOf("result").forGetter(AltarRecipe::getResult)).apply(instance, AltarRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AltarRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, AltarRecipe::getCatalyst,
                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), AltarRecipe::getInputs,
                ItemStack.STREAM_CODEC, AltarRecipe::getResult,
                AltarRecipe::new);

        @Override
        public MapCodec<AltarRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AltarRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}

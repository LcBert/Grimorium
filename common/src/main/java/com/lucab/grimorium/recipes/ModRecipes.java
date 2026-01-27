package com.lucab.grimorium.recipes;

import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.recipes.altar.AltarRecipe;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Grimorium.MODID,
            Registries.RECIPE_SERIALIZER);

    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Grimorium.MODID,
            Registries.RECIPE_TYPE);

    public static final RegistrySupplier<RecipeSerializer<AltarRecipe>> ALTAR_SERIALIZER = SERIALIZERS.register("altar",
            () -> AltarRecipe.Serializer.INSTANCE);

    public static final RegistrySupplier<RecipeType<AltarRecipe>> ALTAR_TYPE = TYPES.register("altar",
            () -> AltarRecipe.Type.INSTANCE);

    public static void register() {
        SERIALIZERS.register();
        TYPES.register();
    }
}

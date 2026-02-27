package com.lucab.grimorium.client.jei;

import com.lucab.grimorium.Grimorium;
import com.lucab.grimorium.blocks.altar.AltarRegister;
import com.lucab.grimorium.recipes.altar.AltarRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class AltarRecipeCategory implements IRecipeCategory<AltarRecipe> {
    public static final RecipeType<AltarRecipe> RECIPE_TYPE = RecipeType.create(Grimorium.MODID, "altar",
            AltarRecipe.class);

    private final IDrawable background;
    private final IDrawableStatic slot;
    private final IDrawableAnimated arrow;
    private final IDrawable icon;

    private final int centerX = 40;
    private final int centerY = 35;
    private final int radius = 35;

    private final int arrow_pos_x = centerX + radius + 25;
    private final int arrow_pos_y = centerY;

    public AltarRecipeCategory(IGuiHelper helper) {
        // Create a blank background 176x85
        this.background = helper.createBlankDrawable(155, 86);
        this.slot = helper.getSlotDrawable();
        this.arrow = helper.createAnimatedRecipeArrow(100);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(AltarRegister.ALTAR_BLOCK.get()));
    }

    @Override
    public RecipeType<AltarRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe.grimorium.altar");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(AltarRecipe recipe, IRecipeSlotsView recipeSlotView, GuiGraphics guiGraphics, double mouseX,
            double mouseY) {
        // progress arrow
        arrow.draw(guiGraphics, arrow_pos_x, arrow_pos_y);

        // Catalyst
        slot.draw(guiGraphics, centerX - 1, centerY - 1);

        // Result
        slot.draw(guiGraphics, arrow_pos_x + 30 - 1, centerY);

        // Inputs -> Arrange inputs in a circle around the center
        int count = Math.min(recipe.getInputs().size(), 8); // At least 8 inputs
        if (count > 0) {
            double angleStep = (2 * Math.PI) / count;
            for (int i = 0; i < count; i++) {
                double angle = i * angleStep - Math.PI / 2; // Start from top
                int x = (int) (centerX + radius * Math.cos(angle));
                int y = (int) (centerY + radius * Math.sin(angle));

                slot.draw(guiGraphics, x - 1, y - 1);
            }
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AltarRecipe recipe, IFocusGroup focuses) {
        // Catalyst
        builder.addSlot(RecipeIngredientRole.INPUT, centerX, centerY)
                .addIngredients(recipe.getCatalyst());

        // Result
        builder.addSlot(RecipeIngredientRole.OUTPUT, arrow_pos_x + 30, centerY)
                .addItemStack(recipe.getResult());

        // Inputs
        // Arrange inputs in a circle or row around the center
        int count = Math.min(recipe.getInputs().size(), 8); // At least 8 inputs
        if (count > 0) {
            double angleStep = (2 * Math.PI) / count;
            for (int i = 0; i < count; i++) {
                double angle = i * angleStep - Math.PI / 2; // Start from top
                int x = (int) (centerX + radius * Math.cos(angle));
                int y = (int) (centerY + radius * Math.sin(angle));

                builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                        .addIngredients(recipe.getInputs().get(i));
            }
        }
    }
}

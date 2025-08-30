package com.natesky9.patina.JEICompat;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.MinceratorRecipe;
import com.natesky9.patina.init.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MinceratorCategory implements IRecipeCategory<MinceratorRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Patina.MODID, "mincerator");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Patina.MODID,
            "textures/gui/mincerator.png");
    public static final IRecipeType<MinceratorRecipe> MINCERATOR_RECIPE_RECIPE_TYPE =
            IRecipeType.create(UID, MinceratorRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public MinceratorCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MACHINE_MINCERATOR.get()));
    }

    @Override
    public IRecipeType<MinceratorRecipe> getRecipeType() {
        return MINCERATOR_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.patina.mincerator");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Nullable
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MinceratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34).add(recipe.input1());
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34+18).add(recipe.input2());
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34+18+18).add(recipe.input3());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34+18).add(recipe.output());
    }
}

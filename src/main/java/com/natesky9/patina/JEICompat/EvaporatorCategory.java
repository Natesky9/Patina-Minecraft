package com.natesky9.patina.JEICompat;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.EvaporatorRecipe;
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

public class EvaporatorCategory implements IRecipeCategory<EvaporatorRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Patina.MODID, "evaporator");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Patina.MODID,
            "textures/gui/evaporator.png");
    public static final IRecipeType<EvaporatorRecipe> EVAPORATOR_RECIPE_RECIPE_TYPE =
            IRecipeType.create(UID, EvaporatorRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public EvaporatorCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MACHINE_EVAPORATOR.get()));
    }

    @Override
    public IRecipeType<EvaporatorRecipe> getRecipeType() {
        return EVAPORATOR_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.patina.evaporator");
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
    public void setRecipe(IRecipeLayoutBuilder builder, EvaporatorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34).add(recipe.input());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34).add(recipe.output());
    }
}

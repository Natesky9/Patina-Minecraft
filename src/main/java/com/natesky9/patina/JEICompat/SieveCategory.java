package com.natesky9.patina.JEICompat;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.SieveRecipe;
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

public class SieveCategory implements IRecipeCategory<SieveRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Patina.MODID, "sieve");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Patina.MODID,
            "textures/gui/sieve.png");
    public static final IRecipeType<SieveRecipe> SIEVE_RECIPE_RECIPE_TYPE =
            IRecipeType.create(UID, SieveRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public SieveCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 84);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MACHINE_SIEVE.get()));
    }

    @Override
    public IRecipeType<SieveRecipe> getRecipeType() {
        return SIEVE_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.patina.sieve");
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
    public void setRecipe(IRecipeLayoutBuilder builder, SieveRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 16).add(recipe.input());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 136, 34).add(recipe.output());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 136, 55).add(recipe.secondary());
    }
}

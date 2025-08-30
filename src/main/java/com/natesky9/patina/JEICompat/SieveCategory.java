package com.natesky9.patina.JEICompat;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.SieveRecipe;
import com.natesky9.patina.init.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
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
    public void draw(SieveRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        if (recipe.water())
        {
            guiGraphics.blit(RenderType::guiTextured,TEXTURE,41,57,176,0,12,19,256,256);
        }
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
        builder.addSlot(RecipeIngredientRole.INPUT, 72, 22).add(recipe.input());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 72, 56).add(recipe.output());
    }
}

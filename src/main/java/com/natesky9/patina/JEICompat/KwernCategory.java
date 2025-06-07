package com.natesky9.patina.JEICompat;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.KwernRecipe;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class KwernCategory implements IRecipeCategory<KwernRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Patina.MODID, "kwern");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Patina.MODID,
            "textures/gui/kwern.png");
    public static final IRecipeType<KwernRecipe> KWERN_RECIPE_RECIPE_TYPE =
            IRecipeType.create(UID, KwernRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public KwernCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 84);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MACHINE_KWERN.get()));
    }

    @Override
    public void draw(KwernRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(Minecraft.getInstance().font, "1.3x",104,32, -1);
    }

    @Override
    public IRecipeType<KwernRecipe> getRecipeType() {
        return KWERN_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.patina.kwern");
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
    public void setRecipe(IRecipeLayoutBuilder builder, KwernRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 34).add(recipe.input());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 136, 34).add(recipe.output());
    }
}

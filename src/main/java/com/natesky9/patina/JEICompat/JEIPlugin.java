package com.natesky9.patina.JEICompat;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.*;
import com.natesky9.patina.Screen.*;
import com.natesky9.patina.init.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeAccess;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Patina.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new KwernCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FoundryCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new EvaporatorCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MinceratorCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SieveCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeAccess access = Minecraft.getInstance().level.recipeAccess();
        RecipeManager manager = ServerLifecycleHooks.getCurrentServer().getRecipeManager();
        //TODO: apparently this might not work on dedicated servers

        List<KwernRecipe> kwernRecipes = manager.recipeMap().byType(ModRecipeTypes.KWERN_RECIPE.get())
                .stream().map(RecipeHolder::value).toList();
        List<FoundryRecipe> foundryRecipes = manager.recipeMap().byType(ModRecipeTypes.FOUNDRY_RECIPE.get())
                .stream().map(RecipeHolder::value).toList();
        List<MinceratorRecipe> minceratorRecipes = manager.recipeMap().byType(ModRecipeTypes.MINCERATOR_RECIPE.get())
                .stream().map(RecipeHolder::value).toList();
        List<EvaporatorRecipe> evaporatorRecipes = manager.recipeMap().byType(ModRecipeTypes.EVAPORATOR_RECIPE.get())
                .stream().map(RecipeHolder::value).toList();
        List<SieveRecipe> sieveRecipes = manager.recipeMap().byType(ModRecipeTypes.SIEVE_RECIPE.get())
                .stream().map(RecipeHolder::value).toList();


        registration.addRecipes(KwernCategory.KWERN_RECIPE_RECIPE_TYPE, kwernRecipes);
        registration.addRecipes(FoundryCategory.FOUNDRY_RECIPE_RECIPE_TYPE, foundryRecipes);
        registration.addRecipes(MinceratorCategory.MINCERATOR_RECIPE_RECIPE_TYPE, minceratorRecipes);
        registration.addRecipes(EvaporatorCategory.EVAPORATOR_RECIPE_RECIPE_TYPE, evaporatorRecipes);
        registration.addRecipes(SieveCategory.SIEVE_RECIPE_RECIPE_TYPE, sieveRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(KwernScreen.class, 70, 30, 25, 20,
                KwernCategory.KWERN_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(EvaporatorScreen.class, 70, 30, 25, 20,
                EvaporatorCategory.EVAPORATOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(FoundryScreen.class, 70, 30, 25, 20,
                FoundryCategory.FOUNDRY_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(MinceratorScreen.class, 70, 30, 25, 20,
                MinceratorCategory.MINCERATOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(SieveScreen.class, 70, 30, 25, 20,
                SieveCategory.SIEVE_RECIPE_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);
    }
}

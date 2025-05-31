package com.natesky9.patina.DataGen;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.FoundryRecipe;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes()
    {
        HolderLookup.RegistryLookup<Item> getter = registries.lookupOrThrow(Registries.ITEM);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, ModBlocks.APPLIANCE_PLINTH.get())
                .pattern("AAA")
                .pattern(" A ")
                .pattern("AAA")
                .define('A', Items.SMOOTH_STONE)
                .unlockedBy("has_stone", has(Items.SMOOTH_STONE))
                .save(output);
        //region foundry
        output.accept(key("foundry/prime"),
                new FoundryRecipe(Items.PRISMARINE_SHARD.builtInRegistryHolder(),
                        ModItems.PRIME_CRYSTAL.get().builtInRegistryHolder(),
                        1),
                null);
        output.accept(key("foundry/anima"),
                new FoundryRecipe(Items.CHORUS_FLOWER.builtInRegistryHolder(),
                        ModItems.ANIMA_CRYSTAL.get().builtInRegistryHolder(),
                        1),
                        null);
        //endregion foundry
    }

    public static class Runner extends RecipeProvider.Runner
    {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected  RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "Patina Recipes";
        }
    }
    ResourceLocation name(String string)
    {
        return ResourceLocation.fromNamespaceAndPath(Patina.MODID, string);
    }
    ResourceKey<Recipe<?>> key(String string)
    {
        return ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Patina.MODID, string));
    }
}

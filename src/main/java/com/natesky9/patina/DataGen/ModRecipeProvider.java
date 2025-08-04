package com.natesky9.patina.DataGen;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.*;
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

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes()
    {
        HolderLookup.RegistryLookup<Item> getter = registries.lookupOrThrow(Registries.ITEM);
        //
        //region ore processing
        output.accept(key("ore_raw_to_foundry"),
                new FoundryRecipe(Items.RAW_IRON.getDefaultInstance(),
                        Items.IRON_INGOT.getDefaultInstance(),
                        stack(Items.IRON_NUGGET,3),false),
                null);//raw ore to 1.3x

        output.accept(key("ore_crushing"),
                new KwernRecipe(Items.RAW_IRON.getDefaultInstance(),
                        ModItems.ORE_CHUNK.toStack(),
                        3),
                null);//raw ore to chunk
        output.accept(key("ore_crushed_foundry"),
                new FoundryRecipe(ModItems.ORE_CHUNK.toStack(),
                        Items.IRON_INGOT.getDefaultInstance(),
                        stack(Items.IRON_NUGGET,6),false),
                null);//chunk to 1.6x

        output.accept(key("ore_presieve"),
                new KwernRecipe(ModItems.ORE_CHUNK.toStack(),
                        ModItems.ORE_COBBLE.toStack(),
                        4),
                null);//chunk to cobble
        output.accept(key("ore_sieving"),
                new SieveRecipe(ModItems.ORE_COBBLE.toStack(),
                        ModItems.ORE_CLUMP.toStack(),
                        Items.BONE.getDefaultInstance(),16),//flint as byproduct
                null);//cobble to clump
        output.accept(key("ore_sieved_foundry"),
                new FoundryRecipe(ModItems.ORE_CLUMP.toStack(),
                        Items.IRON_INGOT.getDefaultInstance(),
                        stack(Items.IRON_INGOT),false),
                null);//clump to 2x

        output.accept(key("ore_prewash"),
                new KwernRecipe(ModItems.ORE_CLUMP.toStack(),
                        ModItems.ORE_GRAVEL.toStack(),
                        5),
                null);//clump to gravel
        output.accept(key("ore_washing"),
                new SieveRecipe(ModItems.ORE_GRAVEL.toStack(),
                        ModItems.ORE_PEBBLE.toStack(),
                        Items.CLAY_BALL.getDefaultInstance(),4),//clay as byproduct
                null);//gravel to pebble
        output.accept(key("ore_drying"),
                new EvaporatorRecipe(ModItems.ORE_PEBBLE.toStack(),
                        ModItems.ORE_LUMP.toStack()),
                null);//pebble to lump
        output.accept(key("ore_washed_foundry"),
                new FoundryRecipe(ModItems.ORE_LUMP.toStack(),
                        stack(Items.IRON_INGOT,2),
                        stack(Items.IRON_NUGGET,3),false),
                null);//lump to 2.3x

        output.accept(key("ore_blending"),
                new MinceratorRecipe(ModItems.ORE_LUMP.toStack(),
                        ModItems.ORE_BLEND.toStack()),
                null);//lump to blend
        output.accept(key("ore_slag_smelting"),
                new FoundryRecipe(ModItems.ORE_BLEND.toStack(),
                        ModItems.ORE_SLAG.toStack(),
                        Items.FIRE_CHARGE.getDefaultInstance(),true),
                null);//blend to slag
        output.accept(key("ore_slag_crushing"),
                new KwernRecipe(ModItems.ORE_SLAG.toStack(),
                        ModItems.ORE_MIX.toStack(),
                        6),
                null);//slag to mix
        output.accept(key("ore_slag_filtering"),
                new SieveRecipe(ModItems.ORE_MIX.toStack(),
                        ModItems.ORE_HUNK.toStack(),
                        Items.FLINT.getDefaultInstance(),4),
                null);//mix to hunk
        output.accept(key("ore_slagged_foundry"),
                new FoundryRecipe(ModItems.ORE_HUNK.toStack(),
                        stack(Items.IRON_INGOT,2),
                        stack(Items.IRON_NUGGET,6),false),
                null);//hunk to 2.6x

        output.accept(key("ore_predissolve"),
                new KwernRecipe(ModItems.ORE_HUNK.toStack(),
                        ModItems.ORE_GRIT.toStack(),
                        7),
                null);//hunk to grit
        //dissolution and dilution are potions
        output.accept(key("ore_flaked_foundry"),
                new FoundryRecipe(ModItems.ORE_FLAKE.toStack(),
                        stack(Items.IRON_INGOT,2),
                        stack(Items.IRON_INGOT),false),
                null);//flake to 3x


        //endregion ore processing

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, ModBlocks.APPLIANCE_PLINTH.get())
                .pattern("AAA")
                .pattern(" A ")
                .pattern("AAA")
                .define('A', Items.SMOOTH_STONE)
                .unlockedBy("has_stone", has(Items.SMOOTH_STONE))
                .save(output);
        //region foundry
        output.accept(key("foundry/prime"),
                new FoundryRecipe(Items.PRISMARINE_CRYSTALS.getDefaultInstance(),
                        Items.SANDSTONE.getDefaultInstance(),
                        ModItems.PRIME_CRYSTAL.get().getDefaultInstance(),true),
                null);
        output.accept(key("foundry/anima"),
                new FoundryRecipe(ModItems.PRIME_CRYSTAL.toStack(),
                        Items.CHORUS_FLOWER.getDefaultInstance(),
                        ModItems.ANIMA_CRYSTAL.get().getDefaultInstance(),true),
                        null);
        output.accept(key("foundry/tinted_glass"),
                new FoundryRecipe(Items.AMETHYST_SHARD.getDefaultInstance(),
                        Items.GLASS.getDefaultInstance(),
                        Items.TINTED_GLASS.getDefaultInstance(),true),
                null);
        output.accept(key("foundry/netherite"),
                new FoundryRecipe(stack(Items.NETHERITE_SCRAP),
                        stack(Items.GOLD_NUGGET),
                        stack(ModItems.NETHERITE_NUGGET.asItem(),3),true),
                null);
        //endregion foundry
        //test recipes
        output.accept(key("evaporator/test"),
                new EvaporatorRecipe(Items.SUGAR_CANE.getDefaultInstance(),
                        new ItemStack(Items.SUGAR,3)),
                null);
        output.accept(key("kwern/test"),
                new KwernRecipe(Items.COBBLESTONE.getDefaultInstance(),
                        Items.GRAVEL.getDefaultInstance(),
                        8),
                null);
        output.accept(key("mincerator/test"),
                new MinceratorRecipe(Items.DANDELION.getDefaultInstance(),
                        new ItemStack(Items.YELLOW_DYE,2)),
                null);
        output.accept(key("textiler/test"),
                new TextilerRecipe(new ItemStack(Items.WHITE_WOOL,2),
                        Items.WHITE_BANNER.getDefaultInstance()),
                null);
    }
    //------------------------------------
    ItemStack stack(Item item)
    {
        return stack(item,1);
    }
    ItemStack stack(Item item, int count)
    {
        return new ItemStack(item, count);
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

package com.natesky9.patina.DataGen;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.*;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.Tags;

import java.util.Map;
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
                        Items.FIRE_CHARGE.getDefaultInstance(),
                        ModItems.ORE_SLAG.toStack(),true),
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

        //region appliances
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, ModBlocks.APPLIANCE_PLINTH.get())
                .pattern("AAA")
                .pattern(" A ")
                .pattern("AAA")
                .define('A', Items.SMOOTH_STONE)
                .unlockedBy("has_stone", has(Items.SMOOTH_STONE))
                .save(output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, ModBlocks.APPLIANCE_ICEBOX.get())
                .pattern("AA")
                .pattern("BC")
                .pattern("AA")
                .define('A', Items.POLISHED_DIORITE)
                .define('B', Items.POWDER_SNOW_BUCKET)
                .define('C', ItemTags.WOODEN_DOORS)
                .unlockedBy("discovered_cold",has(Items.POWDER_SNOW_BUCKET))
                .save(output);
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, ModBlocks.APPLIANCE_WARDROBE.get())
                .pattern("AA")
                .pattern("BC")
                .pattern("AA")
                .define('A', ItemTags.WOODEN_STAIRS)
                .define('B', Items.ARMOR_STAND)
                .define('C', ItemTags.WOODEN_DOORS)
                .unlockedBy("discovered_fashion",has(Items.ARMOR_STAND))
                .save(output);

        //endregion appliances
        //region magic

        output.accept(key("arcana_addition"),
                new ShapedRecipe("arcana_addition", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.LECTERN),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_UNIFIER.toStack()),
                null);
        output.accept(key("arcana_subtraction"),
                new ShapedRecipe("arcana_subtraction", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.GRINDSTONE),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_ABSTRACTOR.toStack()),
                null);
        output.accept(key("arcana_multiplication"),
                new ShapedRecipe("arcana_multiplication", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.ANVIL,Items.CHIPPED_ANVIL,Items.DAMAGED_ANVIL),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_AUGMENTOR.toStack()),
                null);
        output.accept(key("arcana_division"),
                new ShapedRecipe("arcana_division", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.PHANTOM_MEMBRANE),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_EXTRACTOR.toStack()),
                null);
        output.accept(key("arcana_exponent"),
                new ShapedRecipe("arcana_exponent", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.ENCHANTING_TABLE),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_REPLICATOR.toStack()),
                null);
        output.accept(key("arcana_radical"),
                new ShapedRecipe("arcana_radical", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.END_CRYSTAL),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_ARBITRATOR.toStack()),
                null);
        //endregion magic
        //region foundry
        output.accept(key("foundry/prime"),
                new FoundryRecipe(Items.PRISMARINE_CRYSTALS.getDefaultInstance(),
                        Items.SANDSTONE.getDefaultInstance(),
                        ModItems.PRIME_CRYSTAL.get().getDefaultInstance(),true),
                null);
        output.accept(key("foundry/anima"),
                new FoundryRecipe(ModItems.PRIME_CRYSTAL.toStack(),
                        Items.CHORUS_FLOWER.getDefaultInstance(),
                        ModItems.ANIMA_CRYSTAL.toStack(2),true),
                        null);
        output.accept(key("foundry/ferus"),
                new FoundryRecipe(ModItems.PRIME_CRYSTAL.toStack(),
                        Items.GOAT_HORN.getDefaultInstance(),
                        ModItems.FERUS_CRYSTAL.toStack(2),true),
                null);
        output.accept(key("foundry/fortis"),
                new FoundryRecipe(ModItems.PRIME_CRYSTAL.toStack(),
                        Items.NETHERITE_SCRAP.getDefaultInstance(),
                        ModItems.FORTIS_CRYSTAL.toStack(2),true),
                null);
        output.accept(key("foundry/regima"),
                new FoundryRecipe(ModItems.PRIME_CRYSTAL.toStack(),
                        Items.GOLD_BLOCK.getDefaultInstance(),
                        ModItems.REGIMA_CRYSTAL.toStack(2),true),
                null);
        output.accept(key("foundry/eterna"),
                new FoundryRecipe(ModItems.PRIME_CRYSTAL.toStack(),
                        Items.NETHER_STAR.getDefaultInstance(),
                        ModItems.PERPETUUM_CRYSTAL.toStack(2),true),
                null);
        output.accept(key("foundry/tinted_glass"),
                new FoundryRecipe(Items.AMETHYST_SHARD.getDefaultInstance(),
                        Items.GLASS.getDefaultInstance(),
                        stack(Items.TINTED_GLASS,2),true),
                null);
        output.accept(key("foundry/netherite"),
                new FoundryRecipe(stack(Items.NETHERITE_SCRAP),
                        stack(Items.GOLD_NUGGET),
                        ModItems.NETHERITE_NUGGET.toStack(3),true),
                null);
        //endregion foundry
        //region copper
        Criterion<InventoryChangeTrigger.TriggerInstance> hasCopper = has(Items.COPPER_INGOT);
        shaped(RecipeCategory.TOOLS,ModItems.COPPER_AXE.get())
                .define('I', Items.COPPER_INGOT)
                .define('S', Items.STICK)
                .pattern("II").pattern("IS").pattern(" S")
                .unlockedBy("has_copper",hasCopper)
                .save(output);
        shaped(RecipeCategory.TOOLS,ModItems.COPPER_PICK.get())
                .define('I', Items.COPPER_INGOT)
                .define('S', Items.STICK)
                .pattern("III").pattern(" S ").pattern(" S ")
                .unlockedBy("has_copper",hasCopper)
                .save(output);
        shaped(RecipeCategory.TOOLS,ModItems.COPPER_SHOVEL.get())
                .define('I', Items.COPPER_INGOT)
                .define('S', Items.STICK)
                .pattern("I").pattern("S").pattern("S")
                .unlockedBy("has_copper",hasCopper)
                .save(output);
        shaped(RecipeCategory.TOOLS,ModItems.COPPER_SWORD.get())
                .define('I', Items.COPPER_INGOT)
                .define('S', Items.STICK)
                .pattern("I").pattern("I").pattern("S")
                .unlockedBy("has_copper",hasCopper)
                .save(output);
        shaped(RecipeCategory.TOOLS,ModItems.COPPER_HOE.get())
                .define('I', Items.COPPER_INGOT)
                .define('S', Items.STICK)
                .pattern("II").pattern(" S").pattern(" S")
                .unlockedBy("has_copper",hasCopper)
                .save(output);
        shaped(RecipeCategory.COMBAT,ModItems.COPPER_HELMET.get())
                .define('I', Items.COPPER_INGOT)
                .pattern("III").pattern("I I")
                .unlockedBy("has_copper",hasCopper)
                .save(output);
        shaped(RecipeCategory.COMBAT,ModItems.COPPER_CHESTPLATE.get())
                .define('I', Items.COPPER_INGOT)
                .pattern("I I").pattern("III").pattern("III")
                .unlockedBy("has_copper",hasCopper)
                .save(output);
        shaped(RecipeCategory.COMBAT,ModItems.COPPER_LEGGINGS.get())
                .define('I', Items.COPPER_INGOT)
                .pattern("III").pattern("I I").pattern("I I")
                .unlockedBy("has_copper",hasCopper)
                .save(output);
        shaped(RecipeCategory.COMBAT,ModItems.COPPER_BOOTS.get())
                .define('I', Items.COPPER_INGOT)
                .pattern("I I").pattern("I I")
                .unlockedBy("has_copper",hasCopper)
                .save(output);
        //endregion copper
        //region prime_crystal
        Criterion<InventoryChangeTrigger.TriggerInstance> hasCrystal = has(ModItems.PRIME_CRYSTAL);
        shaped(RecipeCategory.TOOLS,ModItems.CRYSTAL_AXE.get())
                .define('I', ModItems.PRIME_CRYSTAL.get())
                .define('S', Items.STICK)
                .pattern("II").pattern("IS").pattern(" S")
                .unlockedBy("has_crystal",hasCrystal)
                .save(output);
        shaped(RecipeCategory.TOOLS,ModItems.CRYSTAL_PICK.get())
                .define('I', ModItems.PRIME_CRYSTAL.get())
                .define('S', Items.STICK)
                .pattern("III").pattern(" S ").pattern(" S ")
                .unlockedBy("has_crystal",hasCrystal)
                .save(output);
        shaped(RecipeCategory.TOOLS,ModItems.CRYSTAL_SHOVEL.get())
                .define('I', ModItems.PRIME_CRYSTAL.get())
                .define('S', Items.STICK)
                .pattern("I").pattern("S").pattern("S")
                .unlockedBy("has_crystal",hasCrystal)
                .save(output);
        shaped(RecipeCategory.TOOLS,ModItems.CRYSTAL_SWORD.get())
                .define('I', ModItems.PRIME_CRYSTAL.get())
                .define('S', Items.STICK)
                .pattern("I").pattern("I").pattern("S")
                .unlockedBy("has_crystal",hasCrystal)
                .save(output);
        shaped(RecipeCategory.TOOLS,ModItems.CRYSTAL_HOE.get())
                .define('I', ModItems.PRIME_CRYSTAL.get())
                .define('S', Items.STICK)
                .pattern("II").pattern(" S").pattern(" S")
                .unlockedBy("has_crystal",hasCrystal)
                .save(output);
        shaped(RecipeCategory.COMBAT,ModItems.PRIME_HELMET.get())
                .define('I', ModItems.PRIME_CRYSTAL.get())
                .pattern("III").pattern("I I")
                .unlockedBy("has_crystal",hasCrystal)
                .save(output);
        shaped(RecipeCategory.COMBAT,ModItems.PRIME_CHESTPLATE.get())
                .define('I', ModItems.PRIME_CRYSTAL.get())
                .pattern("I I").pattern("III").pattern("III")
                .unlockedBy("has_crystal",hasCrystal)
                .save(output);
        shaped(RecipeCategory.COMBAT,ModItems.PRIME_LEGGINGS.get())
                .define('I', ModItems.PRIME_CRYSTAL.get())
                .pattern("III").pattern("I I").pattern("I I")
                .unlockedBy("has_crystal",hasCrystal)
                .save(output);
        //no crystal boots
        //endregion prime crystal

        //region machines
        output.accept(key("machine_foundry"),
                new ShapedRecipe("machine_foundry", CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of(
                                'a', Ingredient.of(Items.BLAST_FURNACE),
                                'b', Ingredient.of(Items.CAULDRON),
                                'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","cbc"),
                        ModBlocks.MACHINE_FOUNDRY.toStack()),
                null);
        output.accept(key("machine_alembic"),
                new ShapedRecipe("machine_alembic", CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of(
                                'a', Ingredient.of(Items.COPPER_GRATE),
                                'b', Ingredient.of(Items.BREWING_STAND),
                                'c', Ingredient.of(Items.QUARTZ_STAIRS),
                                'd', Ingredient.of(Items.BUCKET),
                                'e', Ingredient.of(Items.TINTED_GLASS)),
                                "abd","cde"),
                        ModBlocks.MACHINE_ALEMBIC.toStack()),
                null);
        //endregion machines
        //region flasks
        output.accept(key("brewing/prime_flask"),
                new ShapedRecipe("prime_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.PRIME_CRYSTAL.get())),
                                " a ","b b"," b "),
                        ModItems.FLASK_CRYSTAL.toStack()),
                null);
        output.accept(key("brewing/vita_flask"),
                new ShapedRecipe("vita_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.ANIMA_CRYSTAL.get())),
                                " a ","b b"," b "),
                        ModItems.FLASK_VITA.toStack()),
                null);
        output.accept(key("brewing/impetus_flask"),
                new ShapedRecipe("impetus_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.FERUS_CRYSTAL.get())),
                                " a ","b b"," b "),
                        ModItems.FLASK_PUGNA.toStack()),
                null);
        output.accept(key("brewing/magna_flask"),
                new ShapedRecipe("magna_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.FORTIS_CRYSTAL.get())),
                                " a ","b b"," b "),
                        ModItems.FLASK_MAGNA.toStack()),
                null);
        output.accept(key("brewing/pluvia_flask"),
                new ShapedRecipe("pluvia_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.REGIMA_CRYSTAL.get())),
                                " a ","b b"," b "),
                        ModItems.FLASK_PLUVIA.toStack()),
                null);
        ItemStack klein = new ItemStack(ModItems.FLASK_ETERNA.get());
        klein.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WEAKNESS));
        output.accept(key("brewing/eterna_flask"),
                new ShapedRecipe("eterna_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.PERPETUUM_CRYSTAL.get())),
                                " a ","b b"," b "),
                        klein),
                null);
        //endregion flasks



        //test recipes
        //output.accept(key("evaporator/test"),
        //        new EvaporatorRecipe(Items.SUGAR_CANE.getDefaultInstance(),
        //                new ItemStack(Items.SUGAR,3)),
        //        null);
        //output.accept(key("kwern/test"),
        //        new KwernRecipe(Items.COBBLESTONE.getDefaultInstance(),
        //                Items.GRAVEL.getDefaultInstance(),
        //                8),
        //        null);
        //output.accept(key("mincerator/test"),
        //        new MinceratorRecipe(Items.DANDELION.getDefaultInstance(),
        //                new ItemStack(Items.YELLOW_DYE,2)),
        //        null);
        //output.accept(key("textiler/test"),
        //        new TextilerRecipe(new ItemStack(Items.WHITE_WOOL,2),
        //                Items.WHITE_BANNER.getDefaultInstance()),
        //        null);
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

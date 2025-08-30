package com.natesky9.patina.DataGen;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.*;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModItems;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    static Criterion<ImpossibleTrigger.TriggerInstance> none = CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance());

    public static final ResourceKey<Recipe<?>> FOUNDRY_RECIPE = key("machine_foundry");
    public static final ResourceKey<Recipe<?>> KWERN_RECIPE = key("machine_kwern");
    public static final ResourceKey<Recipe<?>> MINCERATOR_RECIPE = key("machine_mincerator");
    public static final ResourceKey<Recipe<?>> SIEVE_RECIPE = key("machine_sieve");
    public static final ResourceKey<Recipe<?>> ALEMBIC_RECIPE = key("machine_alembic");
    public static final ResourceKey<Recipe<?>> EVAPORATOR_RECIPE = key("machine_evaporator");


    public static final ResourceKey<Recipe<?>> UNIFIER_RECIPE = key("machine_unifier");
    public static final ResourceKey<Recipe<?>> ABSTRACTOR_RECIPE = key("machine_abstractor");
    public static final ResourceKey<Recipe<?>> AUGMENTOR_RECIPE = key("machine_augmentor");
    public static final ResourceKey<Recipe<?>> EXTRACTOR_RECIPE = key("machine_extractor");
    public static final ResourceKey<Recipe<?>> REPLICATOR_RECIPE = key("machine_replicator");
    public static final ResourceKey<Recipe<?>> ARBITRATOR_RECIPE = key("machine_arbitrator");
    public static final ResourceKey<Recipe<?>> MATRIX_RECIPE = key("machine_matrix");

    public static final ResourceKey<Recipe<?>> PRIME_RECIPE = key("foundry/prime");
    public static final ResourceKey<Recipe<?>> ANIMA_RECIPE = key("foundry/anima");
    public static final ResourceKey<Recipe<?>> FERUS_RECIPE = key("foundry/ferus");
    public static final ResourceKey<Recipe<?>> FORTIS_RECIPE = key("foundry/fortis");
    public static final ResourceKey<Recipe<?>> REGIMA_RECIPE = key("foundry/regima");
    public static final ResourceKey<Recipe<?>> PERPETUUM_RECIPE = key("foundry/perpetuum");

    public static final ResourceKey<Recipe<?>> TINTED_RECIPE = key("foundry/tinted");

    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes()
    {
        HolderLookup.RegistryLookup<Item> getter = registries.lookupOrThrow(Registries.ITEM);

        //region appliances
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, ModBlocks.APPLIANCE_PLINTH.get())
                .pattern("AAA")
                .pattern(" A ")
                .pattern("AAA")
                .define('A', Items.SMOOTH_STONE)
                .unlockedBy("has_stone", has(Items.ITEM_FRAME))
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

        output.accept(UNIFIER_RECIPE,
                new ShapedRecipe("arcana_addition", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.LECTERN),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_UNIFIER.toStack()),
                null);
        output.accept(ABSTRACTOR_RECIPE,
                new ShapedRecipe("arcana_subtraction", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.GRINDSTONE),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_ABSTRACTOR.toStack()),
                null);
        output.accept(AUGMENTOR_RECIPE,
                new ShapedRecipe("arcana_multiplication", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.ANVIL,Items.CHIPPED_ANVIL,Items.DAMAGED_ANVIL),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_AUGMENTOR.toStack()),
                null);
        output.accept(EXTRACTOR_RECIPE,
                new ShapedRecipe("arcana_division", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.PHANTOM_MEMBRANE),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_EXTRACTOR.toStack()),
                null);
        output.accept(REPLICATOR_RECIPE,
                new ShapedRecipe("arcana_exponent", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.ENCHANTING_TABLE),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_REPLICATOR.toStack()),
                null);
        output.accept(ARBITRATOR_RECIPE,
                new ShapedRecipe("arcana_radical", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.OBSIDIAN),
                                        'b', Ingredient.of(Items.END_CRYSTAL),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","aba","cac"),
                        ModBlocks.MACHINE_ARBITRATOR.toStack()),
                null);
        output.accept(MATRIX_RECIPE,
                new ShapedRecipe("arcana_matrix", CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of(
                                'a',Ingredient.of(ModBlocks.MACHINE_UNIFIER),
                                'b',Ingredient.of(ModBlocks.MACHINE_ABSTRACTOR),
                                'c',Ingredient.of(ModBlocks.MACHINE_AUGMENTOR),
                                'd',Ingredient.of(ModBlocks.MACHINE_EXTRACTOR),
                                'e',Ingredient.of(ModBlocks.MACHINE_REPLICATOR),
                                'f',Ingredient.of(ModBlocks.MACHINE_ARBITRATOR),
                                'g',Ingredient.of(Blocks.QUARTZ_BLOCK),
                                'h',Ingredient.of(Items.ENDER_PEARL)),
                                "gab","chd","efg"),
                        ModBlocks.MACHINE_MATRIX.toStack()),
                null);


        output.accept(key("appliance_consolidator"),
                new ShapedRecipe("appliance_consolidator", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(ModBlocks.MACHINE_ABSTRACTOR),
                                        'b', Ingredient.of(Items.LAPIS_BLOCK),
                                        'c', Ingredient.of(Items.QUARTZ_STAIRS)),
                                "bcb","cac","bcb"),
                        ModBlocks.MACHINE_ABSTRACTOR.toStack()),
                ModAdvancementProvider.consolidator);
        //endregion magic
        //region foundry
        //region ore processing
        chain(output,Items.RAW_COPPER,Items.COPPER_INGOT,ModItems.COPPER_NUGGET.get(),ModItems.COPPER_CHUNK.get(),ModItems.COPPER_CLUMP.get(),
                ModItems.COPPER_LUMP.get(),ModItems.COPPER_HUNK.get(),ModItems.COPPER_FLAKE.get(),ModItems.COPPER_COBBLE.get(),
                ModItems.COPPER_GRAVEL.get(),ModItems.COPPER_PEBBLE.get(),ModItems.COPPER_BLEND.get(),ModItems.COPPER_SLAG.get(),
                ModItems.COPPER_MIX.get(),ModItems.COPPER_GRIT.get());
        chain(output,Items.RAW_IRON,Items.IRON_INGOT,Items.IRON_NUGGET,ModItems.IRON_CHUNK.get(),ModItems.IRON_CLUMP.get(),
                ModItems.IRON_LUMP.get(),ModItems.IRON_HUNK.get(),ModItems.IRON_FLAKE.get(),ModItems.IRON_COBBLE.get(),
                ModItems.IRON_GRAVEL.get(),ModItems.IRON_PEBBLE.get(),ModItems.IRON_BLEND.get(),ModItems.IRON_SLAG.get(),
                ModItems.IRON_MIX.get(),ModItems.IRON_GRIT.get());
        chain(output,Items.RAW_GOLD,Items.GOLD_INGOT,Items.GOLD_NUGGET,ModItems.GOLD_CHUNK.get(),ModItems.GOLD_CLUMP.get(),
                ModItems.GOLD_LUMP.get(),ModItems.GOLD_HUNK.get(),ModItems.GOLD_FLAKE.get(),ModItems.GOLD_COBBLE.get(),
                ModItems.GOLD_GRAVEL.get(),ModItems.GOLD_PEBBLE.get(),ModItems.GOLD_BLEND.get(),ModItems.GOLD_SLAG.get(),
                ModItems.GOLD_MIX.get(),ModItems.GOLD_GRIT.get());
        //
        //output.accept(key("raw_copper_to_foundry"),
        //        new FoundryRecipe(stack(Items.RAW_COPPER),
        //                stack(Items.COPPER_INGOT),
        //                stack(ModItems.COPPER_NUGGET.get(),3),false), null);
        //output.accept(key("raw_iron_to_foundry"),
        //        new FoundryRecipe(stack(Items.RAW_IRON),
        //                stack(Items.IRON_INGOT),
        //                stack(Items.IRON_NUGGET,3),false), null);//raw ore to 1.3x
        //output.accept(key("raw_gold_to_foundry"),
        //        new FoundryRecipe(stack(Items.RAW_GOLD),
        //                stack(Items.GOLD_INGOT),
        //                stack(Items.GOLD_NUGGET,3),false), null);

        //output.accept(key("raw_copper_crushing"),
        //        new KwernRecipe(stack(Items.RAW_COPPER),
        //                stack(ModItems.COPPER_CHUNK.get()),3), null);
        //output.accept(key("raw_iron_crushing"),
        //        new KwernRecipe(Items.RAW_IRON.getDefaultInstance(),
        //                ModItems.COPPER_CHUNK.toStack(), 3), null);//raw ore to chunk
        //output.accept(key("raw_gold_crushing"),
        //        new KwernRecipe(stack(Items.RAW_GOLD),
        //                stack(ModItems.COPPER_CHUNK.get()),3), null);
        //from here on, all the outputs are generic, and only the quantity counts
        //output.accept(key("ore_crushed_foundry"),
        //        new FoundryRecipe(ModItems.COPPER_CHUNK.toStack(),
        //                Items.IRON_INGOT.getDefaultInstance(),
        //                stack(Items.IRON_NUGGET,6),false),
        //        null);//chunk to 1.6x
//
        //output.accept(key("ore_presieve"),
        //        new KwernRecipe(ModItems.COPPER_CHUNK.toStack(),
        //                ModItems.COPPER_COBBLE.toStack(),
        //                4),
        //        null);//chunk to cobble
        //output.accept(key("ore_sieving"),
        //        new SieveRecipe(ModItems.COPPER_COBBLE.toStack(),
        //                ModItems.COPPER_CLUMP.toStack(),
        //                Items.BONE.getDefaultInstance(),16),//flint as byproduct
        //        null);//cobble to clump
        //output.accept(key("ore_sieved_foundry"),
        //        new FoundryRecipe(ModItems.COPPER_CLUMP.toStack(),
        //                Items.IRON_INGOT.getDefaultInstance(),
        //                stack(Items.IRON_INGOT),false),
        //        null);//clump to 2x
//
        //output.accept(key("ore_prewash"),
        //        new KwernRecipe(ModItems.COPPER_CLUMP.toStack(),
        //                ModItems.COPPER_GRAVEL.toStack(),
        //                5),
        //        null);//clump to gravel
        //output.accept(key("ore_washing"),
        //        new SieveRecipe(ModItems.COPPER_GRAVEL.toStack(),
        //                ModItems.COPPER_PEBBLE.toStack(),
        //                Items.CLAY_BALL.getDefaultInstance(),4),//clay as byproduct
        //        null);//gravel to pebble
        //output.accept(key("ore_drying"),
        //        new EvaporatorRecipe(ModItems.COPPER_PEBBLE.toStack(),
        //                ModItems.COPPER_LUMP.toStack()),
        //        null);//pebble to lump
        //output.accept(key("ore_washed_foundry"),
        //        new FoundryRecipe(ModItems.COPPER_LUMP.toStack(),
        //                stack(Items.IRON_INGOT,2),
        //                stack(Items.IRON_NUGGET,3),false),
        //        null);//lump to 2.3x
//
        //output.accept(key("ore_blending"),
        //        new MinceratorRecipe(ModItems.COPPER_LUMP.toStack(),
        //                ModItems.COPPER_BLEND.toStack()),
        //        null);//lump to blend
        //output.accept(key("ore_slag_smelting"),
        //        new FoundryRecipe(ModItems.COPPER_BLEND.toStack(),
        //                Items.FIRE_CHARGE.getDefaultInstance(),
        //                ModItems.COPPER_SLAG.toStack(),true),
        //        null);//blend to slag
        //output.accept(key("ore_slag_crushing"),
        //        new KwernRecipe(ModItems.COPPER_SLAG.toStack(),
        //                ModItems.COPPER_MIX.toStack(),
        //                6),
        //        null);//slag to mix
        //output.accept(key("ore_slag_filtering"),
        //        new SieveRecipe(ModItems.COPPER_MIX.toStack(),
        //                ModItems.COPPER_HUNK.toStack(),
        //                Items.FLINT.getDefaultInstance(),4),
        //        null);//mix to hunk
        //output.accept(key("ore_slagged_foundry"),
        //        new FoundryRecipe(ModItems.COPPER_HUNK.toStack(),
        //                stack(Items.IRON_INGOT,2),
        //                stack(Items.IRON_NUGGET,6),false),
        //        null);//hunk to 2.6x
//
        //output.accept(key("ore_predissolve"),
        //        new KwernRecipe(ModItems.COPPER_HUNK.toStack(),
        //                ModItems.COPPER_GRIT.toStack(),
        //                7),
        //        null);//hunk to grit
        ////dissolution and dilution are potions
        //output.accept(key("ore_flaked_foundry"),
        //        new FoundryRecipe(ModItems.COPPER_FLAKE.toStack(),
        //                stack(Items.IRON_INGOT,2),
        //                stack(Items.IRON_INGOT),false),
        //        null);//flake to 3x
        //endregion ore processing
        output.accept(PRIME_RECIPE,
                new FoundryRecipe(Items.PRISMARINE_CRYSTALS.getDefaultInstance(),
                        Items.SANDSTONE.getDefaultInstance(),
                        ModItems.PRIME_CRYSTAL.get().getDefaultInstance(),true),
                null);
        output.accept(ANIMA_RECIPE,
                new FoundryRecipe(ModItems.PRIME_CRYSTAL.toStack(),
                        Items.CHORUS_FLOWER.getDefaultInstance(),
                        ModItems.ANIMA_CRYSTAL.toStack(1),true),
                        null);
        output.accept(FERUS_RECIPE,
                new FoundryRecipe(ModItems.PRIME_CRYSTAL.toStack(),
                        Items.GOAT_HORN.getDefaultInstance(),
                        ModItems.FERUS_CRYSTAL.toStack(1),true),
                null);
        output.accept(FORTIS_RECIPE,
                new FoundryRecipe(ModItems.PRIME_CRYSTAL.toStack(),
                        Items.NETHERITE_SCRAP.getDefaultInstance(),
                        ModItems.FORTIS_CRYSTAL.toStack(1),true),
                null);
        output.accept(REGIMA_RECIPE,
                new FoundryRecipe(ModItems.PRIME_CRYSTAL.toStack(),
                        Items.GOLD_BLOCK.getDefaultInstance(),
                        ModItems.REGIMA_CRYSTAL.toStack(1),true),
                null);
        output.accept(PERPETUUM_RECIPE,
                new FoundryRecipe(ModItems.PRIME_CRYSTAL.toStack(),
                        Items.NETHER_STAR.getDefaultInstance(),
                        ModItems.PERPETUUM_CRYSTAL.toStack(1),true),
                null);
        output.accept(TINTED_RECIPE,
                new FoundryRecipe(Items.AMETHYST_SHARD.getDefaultInstance(),
                        Items.GLASS.getDefaultInstance(),
                        stack(Items.TINTED_GLASS,2),true),
                ModAdvancementProvider.foundry);
        output.accept(key("foundry/netherite"),
                new FoundryRecipe(stack(Items.NETHERITE_SCRAP),
                        stack(Items.GOLD_NUGGET),
                        ModItems.NETHERITE_NUGGET.toStack(3),true),
                ModAdvancementProvider.foundry);
        //endregion foundry
        //region copper

        output.accept(key("copper_sword"),sword(Items.COPPER_INGOT,ModItems.COPPER_SWORD,
                "copper_sword"),ModAdvancementProvider.foundry);
        output.accept(key("copper_axe"),axe(Items.COPPER_INGOT,ModItems.COPPER_AXE,
                "copper_axe"),ModAdvancementProvider.foundry);
        output.accept(key("copper_pick"),pick(Items.COPPER_INGOT,ModItems.COPPER_PICK,
                "copper_pick"),ModAdvancementProvider.foundry);
        output.accept(key("copper_shovel"),shovel(Items.COPPER_INGOT,ModItems.COPPER_SHOVEL,
                "copper_shovel"),ModAdvancementProvider.foundry);
        output.accept(key("copper_hoe"),hoe(Items.COPPER_INGOT,ModItems.COPPER_HOE,
                "copper_hoe"),ModAdvancementProvider.foundry);

        output.accept(key("copper_helmet"),helmet(Items.COPPER_INGOT,ModItems.COPPER_HELMET,
                "copper_helmet"),ModAdvancementProvider.foundry);
        output.accept(key("copper_chestplate"),chestplate(Items.COPPER_INGOT,ModItems.COPPER_CHESTPLATE,
                "copper_chestplate"),ModAdvancementProvider.foundry);
        output.accept(key("copper_leggings"),leggings(Items.COPPER_INGOT,ModItems.COPPER_LEGGINGS,
                "copper_leggings"),ModAdvancementProvider.foundry);
        output.accept(key("copper_boots"),boots(Items.COPPER_INGOT,ModItems.COPPER_BOOTS,
                "copper_boots"),ModAdvancementProvider.foundry);
        //endregion copper
        //region prime crystal
        output.accept(key("prime_sword"),sword(ModItems.PRIME_CRYSTAL.get(),ModItems.CRYSTAL_SWORD,
                "prime_sword"),ModAdvancementProvider.material_crystal);
        output.accept(key("prime_axe"),axe(ModItems.PRIME_CRYSTAL.get(),ModItems.CRYSTAL_AXE,
                "prime_axe"),ModAdvancementProvider.material_crystal);
        output.accept(key("prime_pick"),pick(ModItems.PRIME_CRYSTAL.get(),ModItems.CRYSTAL_AXE,
                "prime_pick"),ModAdvancementProvider.material_crystal);
        output.accept(key("prime_shovel"),shovel(ModItems.PRIME_CRYSTAL.get(),ModItems.CRYSTAL_SHOVEL,
                "prime_shovel"),ModAdvancementProvider.material_crystal);
        output.accept(key("prime_hoe"),hoe(ModItems.PRIME_CRYSTAL.get(),ModItems.CRYSTAL_HOE,
                "prime_hoe"),ModAdvancementProvider.material_crystal);

        output.accept(key("prime_helmet"),helmet(ModItems.PRIME_CRYSTAL.get(),ModItems.PRIME_HELMET,
                "prime_helmet"),ModAdvancementProvider.material_crystal);
        output.accept(key("prime_chestplate"),chestplate(ModItems.PRIME_CRYSTAL.get(),ModItems.PRIME_CHESTPLATE,
                "prime_chestplate"),ModAdvancementProvider.material_crystal);
        output.accept(key("prime_leggings"),leggings(ModItems.PRIME_CRYSTAL.get(),ModItems.PRIME_LEGGINGS,
                "prime_leggings"),ModAdvancementProvider.material_crystal);
        //endregion prime crystal

        //region anima crystal
        //no unique weapons yet
        //output.accept(key("anima_sword"),sword(ModItems.ANIMA_CRYSTAL.get(),ModItems.CRYSTAL_SWORD,
        //        "anima_sword"),ModAdvancementProvider.material_crystal);
        //output.accept(key("anima_axe"),axe(ModItems.ANIMA_CRYSTAL.get(),ModItems.CRYSTAL_AXE,
        //        "anima_axe"),ModAdvancementProvider.material_crystal);
        //output.accept(key("anima_shovel"),shovel(ModItems.ANIMA_CRYSTAL.get(),ModItems.CRYSTAL_SHOVEL,
        //        "anima_shovel"),ModAdvancementProvider.material_crystal);
        //output.accept(key("anima_hoe"),hoe(ModItems.ANIMA_CRYSTAL.get(),ModItems.CRYSTAL_HOE,
        //        "anima_hoe"),ModAdvancementProvider.material_crystal);

        output.accept(key("anima_helmet"),helmet(ModItems.ANIMA_CRYSTAL.get(),ModItems.ANIMA_HELMET,
                "anima_helmet"),ModAdvancementProvider.material_anima);
        output.accept(key("anima_chestplate"),chestplate(ModItems.ANIMA_CRYSTAL.get(),ModItems.ANIMA_CHESTPLATE,
                "anima_chestplate"),ModAdvancementProvider.material_anima);
        output.accept(key("anima_leggings"),leggings(ModItems.ANIMA_CRYSTAL.get(),ModItems.ANIMA_LEGGINGS,
                "anima_leggings"),ModAdvancementProvider.material_anima);
        //endregion anima crystal
        //region ferus crystal
        output.accept(key("ferus_helmet"),helmet(ModItems.FERUS_CRYSTAL.get(),ModItems.FERUS_HELMET,
                "ferus_helmet"),ModAdvancementProvider.material_ferus);
        output.accept(key("ferus_chestplate"),chestplate(ModItems.FERUS_CRYSTAL.get(),ModItems.FERUS_CHESTPLATE,
                "ferus_chestplate"),ModAdvancementProvider.material_ferus);
        output.accept(key("ferus_leggings"),leggings(ModItems.FERUS_CRYSTAL.get(),ModItems.FERUS_LEGGINGS,
                "ferus_leggings"),ModAdvancementProvider.material_ferus);
        //endregion ferus crystal
        //region fortis crystal
        output.accept(key("fortis_helmet"),helmet(ModItems.FORTIS_CRYSTAL.get(),ModItems.FORTIS_HELMET,
                "fortis_helmet"),ModAdvancementProvider.material_fortis);
        output.accept(key("fortis_chestplate"),chestplate(ModItems.FORTIS_CRYSTAL.get(),ModItems.FORTIS_CHESTPLATE,
                "fortis_chestplate"),ModAdvancementProvider.material_fortis);
        output.accept(key("fortis_leggings"),leggings(ModItems.FORTIS_CRYSTAL.get(),ModItems.FORTIS_LEGGINGS,
                "fortis_leggings"),ModAdvancementProvider.material_fortis);
        //endregion fortis crystal
        //region regima crystal
        output.accept(key("regima_helmet"),helmet(ModItems.REGIMA_CRYSTAL.get(),ModItems.IMPERIUM_HELMET,
                "regima_helmet"),ModAdvancementProvider.material_crystal);
        output.accept(key("regima_chestplate"),chestplate(ModItems.REGIMA_CRYSTAL.get(),ModItems.IMPERIUM_CHESTPLATE,
                "regima_chestplate"),ModAdvancementProvider.material_crystal);
        output.accept(key("regima_leggings"),leggings(ModItems.REGIMA_CRYSTAL.get(),ModItems.IMPERIUM_LEGGINGS,
                "regima_leggings"),ModAdvancementProvider.material_crystal);
        //endregion regima crystal
        //region eterna crystal
        //no eterna crystals yet
        //output.accept(key("eterna_helmet"),helmet(ModItems.PERPETUUM_CRYSTAL.get(),ModItems.ANIMA_HELMET,
        //        "eterna_helmet"),ModAdvancementProvider.material_crystal);
        //output.accept(key("eterna_chestplate"),chestplate(ModItems.PERPETUUM_CRYSTAL.get(),ModItems.ANIMA_CHESTPLATE,
        //        "eterna_chestplate"),ModAdvancementProvider.material_crystal);
        //output.accept(key("eterna_leggings"),leggings(ModItems.PERPETUUM_CRYSTAL.get(),ModItems.ANIMA_LEGGINGS,
        //        "eterna_leggings"),ModAdvancementProvider.material_crystal);
        //endregion eterna crystal

        //region machines
        output.accept(FOUNDRY_RECIPE,
                new ShapedRecipe("machine_foundry", CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.BLAST_FURNACE),
                                        'b', Ingredient.of(Items.CAULDRON),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","cbc"),
                        ModBlocks.MACHINE_FOUNDRY.toStack()),
                null);
        output.accept(KWERN_RECIPE,
                new ShapedRecipe("machine_kwern", CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.GRINDSTONE),
                                        'b', Ingredient.of(Items.BARREL),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","cbc"),
                        ModBlocks.MACHINE_KWERN.toStack()),
                null);
        output.accept(SIEVE_RECIPE,
                new ShapedRecipe("machine_sieve", CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.COPPER_GRATE),
                                        'b', Ingredient.of(Items.PISTON),
                                        'c', Ingredient.of(Items.CUT_COPPER_STAIRS)),
                                "cac","cbc"),
                        ModBlocks.MACHINE_SIEVE.toStack()),
                null);
        output.accept(ALEMBIC_RECIPE,
                new ShapedRecipe("machine_alembic", CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.COPPER_GRATE),
                                        'b', Ingredient.of(Items.BREWING_STAND),
                                        'c', Ingredient.of(Items.QUARTZ_STAIRS),
                                        'd', Ingredient.of(Items.BUCKET),
                                        'e', Ingredient.of(Items.TINTED_GLASS)),
                                "abd","cde"),
                        ModBlocks.MACHINE_ALEMBIC.toStack()),
                ModAdvancementProvider.alembic);
        output.accept(MINCERATOR_RECIPE,
                new ShapedRecipe("machine_mincerator", CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.IRON_BARS),
                                        'b',Ingredient.of(Items.PISTON),
                                        'c',Ingredient.of(Items.BUCKET),
                                        'd',Ingredient.of(Items.BRICK_SLAB)),
                                "bab","dcd"),
                        ModBlocks.MACHINE_MINCERATOR.toStack()),
                ModAdvancementProvider.mincerator);
        output.accept(EVAPORATOR_RECIPE,
                new ShapedRecipe("machine_evaporator", CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of(
                                        'a', Ingredient.of(Items.CAULDRON),
                                        'b', Ingredient.of(Items.CAMPFIRE),
                                        'c', Ingredient.of(Items.BRICK_SLAB),
                                        'd', Ingredient.of(Items.COPPER_GRATE)),
                                "dad","bcb"),
                        ModBlocks.MACHINE_EVAPORATOR.toStack()),
                null);
        //endregion machines
        //region flasks
        output.accept(key("brewing/prime_flask"),
                new ShapedRecipe("prime_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.PRIME_CRYSTAL.get())),
                                " a ","b b"," b "),
                        ModItems.FLASK_CRYSTAL.toStack()),
                ModAdvancementProvider.prime_flask);
        output.accept(key("brewing/vita_flask"),
                new ShapedRecipe("vita_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.ANIMA_CRYSTAL.get())),
                                " a ","b b"," b "),
                        ModItems.FLASK_VITA.toStack()),
                ModAdvancementProvider.vita_flask);
        output.accept(key("brewing/impetus_flask"),
                new ShapedRecipe("pugna_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.FERUS_CRYSTAL.get())),
                                " a ","b b"," b "),
                        ModItems.FLASK_PUGNA.toStack()),
                ModAdvancementProvider.pugna_flask);
        output.accept(key("brewing/magna_flask"),
                new ShapedRecipe("magna_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.FORTIS_CRYSTAL.get())),
                                " a ","b b"," b "),
                        ModItems.FLASK_MAGNA.toStack()),
                ModAdvancementProvider.magna_flask);
        output.accept(key("brewing/pluvia_flask"),
                new ShapedRecipe("pluvia_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.REGIMA_CRYSTAL.get())),
                                " a ","b b"," b "),
                        ModItems.FLASK_PLUVIA.toStack()),
                ModAdvancementProvider.pluvia_flask);
        ItemStack klein = new ItemStack(ModItems.FLASK_ETERNA.get());
        klein.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WEAKNESS));
        output.accept(key("brewing/eterna_flask"),
                new ShapedRecipe("eterna_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.COPPER_INGOT),
                                        'b',Ingredient.of(ModItems.PERPETUUM_CRYSTAL.get())),
                                " a ","b b"," b "),
                        klein),
                ModAdvancementProvider.eterna_flask);
        //endregion flasks

        //region chorus
        output.accept(key("chorus_teleporter"),
                new ShapedRecipe("chorus_teleporter",CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of(
                                        'a',Ingredient.of(Items.PURPUR_BLOCK),
                                        'b',Ingredient.of(Items.END_STONE_BRICK_STAIRS),
                                        'c',Ingredient.of(Items.ENDER_EYE)),
                                "aba","bcb","aba"),
                        ModBlocks.APPLIANCE_CHORUS_TELEPORTER.toStack()),
                ModAdvancementProvider.teleporter);
        output.accept(key("chorus_cable"),
        new ShapedRecipe("chorus_cable",CraftingBookCategory.REDSTONE,
                ShapedRecipePattern.of(Map.of(
                                'a',Ingredient.of(tag(ItemTags.WOOL_CARPETS).getValues()),
                                'b',Ingredient.of(Items.POPPED_CHORUS_FRUIT)),
                        " a ","aba"," a "),
                ModBlocks.CHORUS_CABLE.toStack()),
                ModAdvancementProvider.teleporter);
        //endregion chorus



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
    void chain(RecipeOutput output, Item raw_ore, Item ingot, Item nugget, Item chunk,Item clump,
               Item lump, Item hunk, Item flake, Item cobble, Item gravel, Item pebble, Item blend,
               Item slag, Item mix, Item grit)
    {
        AdvancementHolder holder = ModAdvancementProvider.foundry;
        String string = BuiltInRegistries.ITEM.getKey(raw_ore).getPath();
        output.accept(key("foundry/" + string + "_to_ingot"),
                new FoundryRecipe(stack(raw_ore),stack(ingot),stack(nugget,3),false),
                holder);//1.3x

        output.accept(key("kwern/" + string + "_to_chunk"),
                new KwernRecipe(stack(raw_ore),stack(chunk)),holder);
        output.accept(key("foundry/" + string + "_chunk_to_ingot"),
                new FoundryRecipe(stack(chunk),stack(ingot),stack(nugget,6),false),
                holder);//1.6x

        output.accept(key("kwern/" + string + "_chunk_to_cobble"),
                new KwernRecipe(stack(chunk),stack(cobble)),holder);
        output.accept(key("sieve/" + string + "_cobble_to_clump"),
                new SieveRecipe(stack(cobble),stack(clump),false),holder);
        output.accept(key("foundry/" + string + "_clump_to_ingot"),
                new FoundryRecipe(stack(clump),stack(ingot),stack(ingot),false), holder);//2x

        output.accept(key("kwern/" + string + "_clump_to_gravel"),
                new KwernRecipe(stack(clump),stack(gravel)),holder);
        output.accept(key("sieve/" + string + "_gravel_to_pebble"),
                new SieveRecipe(stack(gravel),stack(pebble),true),holder);
        output.accept(key("sieve/" + string + "_pebble_to_lump"),
                new SieveRecipe(stack(pebble),stack(lump),false),holder);
        output.accept(key("foundry/" + string + "_lump_to_ingot"),
                new FoundryRecipe(stack(lump),stack(ingot,2),stack(nugget,3),false),
                holder);//2.3x
        //this is the end of the first string of recipes
        //output.accept(key(string + "_lump_to_kwern"),
        //        new KwernRecipe(stack(lump),stack(hunk)),holder);
        //output.accept(key(string + "_hunk_to_foundry"),
        //        new FoundryRecipe(stack(hunk),stack(ingot,2),stack(nugget,6),false),
        //        holder);//2.6x
        //output.accept(key(string + "_hunk_to_kwern"),
        //        new KwernRecipe(stack(hunk),stack(grit)),holder);
        //output.accept(key(string + "_grit_to_foundry"),
        //        new FoundryRecipe(stack(grit),stack(ingot,2),stack(ingot),false),
        //        holder);//1.6x

    }
    ShapedRecipe sword(Item item, Holder<Item> sword, String name)
    {
        return new ShapedRecipe(name, CraftingBookCategory.EQUIPMENT,
                ShapedRecipePattern.of(Map.of(
                                'a', Ingredient.of(item),
                                'b', Ingredient.of(Items.STICK)),
                        "a","a","b"),
                stack(sword.value()));
    }
    ShapedRecipe axe(Item item, Holder<Item> axe, String name)
    {
        return new ShapedRecipe(name, CraftingBookCategory.EQUIPMENT,
                ShapedRecipePattern.of(Map.of(
                                'a', Ingredient.of(item),
                                'b', Ingredient.of(Items.STICK)),
                        "aa","ab"," b"),
                stack(axe.value()));
    }
    ShapedRecipe pick(Item item, Holder<Item> pick, String name)
    {
        return new ShapedRecipe(name, CraftingBookCategory.EQUIPMENT,
                ShapedRecipePattern.of(Map.of(
                                'a', Ingredient.of(item),
                                'b', Ingredient.of(Items.STICK)),
                        "aaa"," b "," b "),
                stack(pick.value()));
    }
    ShapedRecipe shovel(Item item, Holder<Item> shovel, String name)
    {
        return new ShapedRecipe(name, CraftingBookCategory.EQUIPMENT,
                ShapedRecipePattern.of(Map.of(
                                'a', Ingredient.of(item),
                                'b', Ingredient.of(Items.STICK)),
                        "a","b","b"),
                stack(shovel.value()));
    }
    ShapedRecipe hoe(Item item, Holder<Item> hoe, String name)
    {
        return new ShapedRecipe(name, CraftingBookCategory.EQUIPMENT,
                ShapedRecipePattern.of(Map.of(
                                'a', Ingredient.of(item),
                                'b', Ingredient.of(Items.STICK)),
                        "a","a","b"),
                stack(hoe.value()));
    }
    ShapedRecipe helmet(Item item, Holder<Item> helmet, String name)
    {
        return new ShapedRecipe(name, CraftingBookCategory.EQUIPMENT,
                ShapedRecipePattern.of(Map.of(
                                'a', Ingredient.of(item)),
                        "aaa","a a"),
                stack(helmet.value()));
    }
    ShapedRecipe chestplate(Item item, Holder<Item> chestplate, String name)
    {
        return new ShapedRecipe(name, CraftingBookCategory.EQUIPMENT,
                ShapedRecipePattern.of(Map.of(
                                'a', Ingredient.of(item)),
                        "a a","aaa","aaa"),
                stack(chestplate.value()));
    }
    ShapedRecipe leggings(Item item, Holder<Item> leggings, String name)
    {
        return new ShapedRecipe(name, CraftingBookCategory.EQUIPMENT,
                ShapedRecipePattern.of(Map.of(
                                'a', Ingredient.of(item)),
                        "aaa","a a","a a"),
                stack(leggings.value()));
    }
    ShapedRecipe boots(Item item, Holder<Item> boots, String name)
    {
        return new ShapedRecipe(name, CraftingBookCategory.EQUIPMENT,
                ShapedRecipePattern.of(Map.of(
                                'a', Ingredient.of(item)),
                        "a a","a a"),
                stack(boots.value()));
    }


    //--------------------------------------//
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
    static ResourceKey<Recipe<?>> key(String string)
    {
        return ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Patina.MODID, string));
    }
}

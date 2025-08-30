package com.natesky9.patina.DataGen;

import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class ModAdvancementProvider implements AdvancementSubProvider {
    static Criterion<ImpossibleTrigger.TriggerInstance> research = CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance());
    static Criterion<InventoryChangeTrigger.TriggerInstance> has_copper =
            InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT);
    static AdvancementRewards.Builder xp(int experience)
    {
        return AdvancementRewards.Builder.experience(experience);
    }
    //region root
    public static AdvancementHolder root = Advancement.Builder.advancement()
            .display(Items.COPPER_INGOT, Component.translatable("advancement.patina.root"),
                    Component.translatable("advancement.patina.root.description"),
                    ResourceLocation.withDefaultNamespace("textures/block/copper_block.png"),
                    AdvancementType.TASK,true,false,false)
            .addCriterion("has_copper",has_copper)
            .rewards(xp(10))
            .build(name("root"));//endregion root
    //region arcana
    public static final AdvancementHolder arcana = Advancement.Builder.advancement()
            .display(Items.EXPERIENCE_BOTTLE, Component.translatable("advancement.patina.arcana"),
                    Component.translatable("advancement.patina.arcana.description"),null,
                    AdvancementType.CHALLENGE,true,true,false)
            .addCriterion("minecraft:story/cure_zombie_villager", research)
            .addCriterion("minecraft:story/enchant_item", research)
            .addCriterion("minecraft:nether/brew_potion", research)
            .rewards(AdvancementRewards.Builder.experience(80))
            .parent(root)
            .build(name("arcana"));//endregion arcana
    //region machina
    public static final AdvancementHolder machina = Advancement.Builder.advancement()
            .display(Items.CRAFTER, Component.literal("Automation"),
                    Component.literal("Using technology for gain"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:adventure/crafters_crafting_crafters", research)
            .parent(root)
            .rewards(xp(10))
            .build(name("automation"));
    //endregion machina
    //region enchanting
    public static final AdvancementHolder enchanting = Advancement.Builder.advancement()
            .display(Blocks.ENCHANTING_TABLE, Component.translatable("advancement.patina.enchanting"),
                    Component.translatable("advancement.patina.enchanting.description"),null,
                    AdvancementType.TASK,true,true,true)
            .addCriterion("minecraft:story/enchant_item",research)
            .rewards(xp(20)
                    .addRecipe(ModRecipeProvider.UNIFIER_RECIPE)
                    .addRecipe(ModRecipeProvider.ABSTRACTOR_RECIPE)
                    .addRecipe(ModRecipeProvider.AUGMENTOR_RECIPE)
                    .addRecipe(ModRecipeProvider.EXTRACTOR_RECIPE)
                    .addRecipe(ModRecipeProvider.REPLICATOR_RECIPE)
                    .addRecipe(ModRecipeProvider.ARBITRATOR_RECIPE))
            .parent(arcana)
            .build(name("enchanting"));//endregion enchanting
    //region matrix
    public static final AdvancementHolder matrix = Advancement.Builder.advancement()
            .display(Items.END_CRYSTAL,Component.translatable("advancement.patina.matrix"),
                    Component.translatable("advancement.patina.matrix.description"),null,
                    AdvancementType.CHALLENGE,true,true,false)
            .addCriterion("patina:enchanting",research)
            .rewards(xp(100)
                    .addRecipe(ModRecipeProvider.MATRIX_RECIPE))
            .parent(enchanting)
            .build(name("matrix_enchanting"));//endregion matrix

    //region foundry
    public static final AdvancementHolder foundry = Advancement.Builder.advancement()
            .display(Blocks.BLAST_FURNACE,Component.translatable("advancement.patina.foundry"),
                    Component.translatable("advancement.patina.foundry.description"),null,
                    AdvancementType.GOAL,true,false,false)
            .addCriterion("minecraft:story/smelt_iron",research)
            .parent(machina)
            .rewards(xp(10)
                    .addRecipe(ModRecipeProvider.FOUNDRY_RECIPE))
            .build(name("foundry"));//endregion foundry

    //region crystal
    public static final AdvancementHolder material_crystal = Advancement.Builder.advancement()
            .display(ModItems.PRIME_CRYSTAL.get(),Component.literal("The finest crystal"),
                    Component.literal("Advanced alloying techniques"), null,
                    AdvancementType.TASK, true, true, false)
            .addCriterion("minecraft:story/smelt_iron", research)
            .addCriterion("minecraft:nether/distract_piglin", research)
            .parent(foundry)
            .rewards(xp(10))
            .build(name( "prime_crystal"));
    public static final AdvancementHolder material_fortis = Advancement.Builder.advancement()
            .display(ModItems.FORTIS_CRYSTAL.get(),Component.literal("Reinforced glass"),
                    Component.literal("what's that about a glass house again?"),null,
                    AdvancementType.GOAL,true,true,false)
            .addCriterion("patina/root", research)
            .parent(material_crystal)
            .rewards(xp(20))
            .build(name("fortis_crystal"));
    public static final AdvancementHolder material_anima = Advancement.Builder.advancement()
            .display(ModItems.ANIMA_CRYSTAL.get(),Component.literal("Living glass"),
                    Component.literal("cold, but pulsing with energy"),null,
                    AdvancementType.GOAL,true,true,false)
            .addCriterion("patina:patina/root", research)
            .parent(material_crystal)
            .rewards(xp(20))
            .build(name("anima_crystal"));
    public static final AdvancementHolder material_ferus = Advancement.Builder.advancement()
            .display(ModItems.FERUS_CRYSTAL.get(),Component.literal("Ferus glass"),
                    Component.literal("symbiotic silicon synergy"),null,
                    AdvancementType.GOAL,true,true,false)
            .addCriterion("patina:patina/root", research)
            .parent(material_crystal)
            .rewards(xp(20))
            .build(name("ferus_crystal"));
    public static final AdvancementHolder material_imperium = Advancement.Builder.advancement()
            .display(ModItems.FERUS_CRYSTAL.get(),Component.literal("Imperium glass"),
                    Component.literal("gleaming with regal glamour"),null,
                    AdvancementType.GOAL,true,true,false)
            .addCriterion("patina:patina/root", research)
            .parent(material_ferus)
            .rewards(xp(20))
            .build(name("imperium_crystal"));
    public static final AdvancementHolder material_perpetuum = Advancement.Builder.advancement()
            .display(ModItems.PERPETUUM_CRYSTAL.get(),Component.literal("Perpetuum glass"),
                    Component.literal("...wibbly wobbly, timey wimey stuff"),null,
                    AdvancementType.GOAL,true,true,false)
            .addCriterion("minecraft:nether/all_effects", research)
            .parent(material_fortis)
            .rewards(xp(20))
            .build(name("perpetuum_crystal"));
    //endregion crystal
    //region mincerator
    public static final AdvancementHolder mincerator = Advancement.Builder.advancement()
            .display(ModBlocks.MACHINE_MINCERATOR.get(), Component.literal("Cooking up a Storm!"),
                    Component.literal("Experimenting with new food"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:husbandry/plant_seed",research)
            .addCriterion("minecraft:husbandry/breed_an_animal",research)
            .addCriterion("minecraft:adventure/trade",research)
            .parent(machina)
            .rewards(xp(10)
                    .addRecipe(key("mincerator/blink_brownie"))
                    .addRecipe(key("mincerator/bread"))
                    .addRecipe(key("mincerator/pumpkin_pie"))
                    .addRecipe(key("mincerator/fire_charge")))
            .build(name("mincerator"));
    //endregion mincerator
    //region evaporator
    public static final AdvancementHolder evaporator = Advancement.Builder.advancement()
            .display(ModBlocks.MACHINE_EVAPORATOR.get(), Component.translatable("advancement.patina.evaporator"),
                    Component.translatable("advancement.patina.evaporator.description"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:husbandry/make_a_sign_glow",research)
            .addCriterion("minecraft:husbandry/safely_harvest_honey",research)
            .parent(machina)
            .rewards(xp(10))
            .build(name("evaporator"));//endregion evaporator
    //region teleporter
    public static final AdvancementHolder teleporter = Advancement.Builder.advancement()
            .display(Items.CHORUS_FLOWER, Component.literal("The Chorus Sings"),
                    Component.literal("Not so random after all"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:end/enter_end_gateway", research)
            .addCriterion("minecraft:nether/fast_travel", research)
            .rewards(xp(50))
            .parent(arcana)
            .build(name("teleporter"));//endregion teleporter
    //region consolidator
    public static final AdvancementHolder consolidator = Advancement.Builder.advancement()
            .display(Items.EXPERIENCE_BOTTLE,Component.translatable("advancement.patina.consolidator"),
                    Component.translatable("advancement.patina.consolidator.description"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:story/enchant_item",research)
            .addCriterion("minecraft:nether/obtain_crying_obsidian",research)
            .parent(enchanting)
            .rewards(xp(100))
            .build(name("consolidator"));//endregion consolidator
    //region brewing
    public static final AdvancementHolder alembic = Advancement.Builder.advancement()
            .display(Items.BREWING_STAND,Component.translatable("advancement.patina.alembic"),
                    Component.translatable("advancement.patina.alembic.description"), null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:nether/obtain_blaze_rod", research)
            .parent(machina)
            .rewards(xp(10))
            .build(name("alembic"));
    //endregion brewing
    //region flasks
    public static final AdvancementHolder prime_flask = Advancement.Builder.advancement()
            .display(ModItems.FLASK_CRYSTAL.get(),Component.translatable("advancement.patina.prime_crystal"),
                    Component.translatable(""),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:husbandry/wax_on", research)
            .addCriterion("minecraft:nether/distract_piglin", research)
            .parent(alembic)
            .rewards(xp(10))
            .build(name("prime_flask"));
    public static final AdvancementHolder pugna_flask = Advancement.Builder.advancement()
            .display(ModItems.FLASK_PUGNA.get(),Component.literal("Juicing for combat"),
                    Component.literal("test title"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:husbandry/ride_a_boat_with_a_goat", research)
            .parent(prime_flask)
            .rewards(xp(20))
            .build(name("pugna_flask"));
    public static final AdvancementHolder magna_flask = Advancement.Builder.advancement()
            .display(ModItems.FLASK_MAGNA.get(),Component.literal("Did you say more?"),
                    Component.literal("for when you're REALLY thirsty"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:adventure/fall_from_world_height", research)
            .parent(prime_flask)
            .rewards(xp(20))
            .build(name("magna_flask"));
    public static final AdvancementHolder vita_flask = Advancement.Builder.advancement()
            .display(ModItems.FLASK_VITA.get(),Component.literal("For when you need it most"),
                    Component.literal(""),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:husbandry/kill_axolotl_target", research)
            .parent(prime_flask)
            .rewards(xp(20))
            .build(name("vita_flask"));
    public static final AdvancementHolder pluvia_flask = Advancement.Builder.advancement()
            .display(ModItems.FLASK_PLUVIA,Component.literal("Bad Spider!"),
                    Component.literal("applying effects at a safe distance"),null,
                    AdvancementType.CHALLENGE,true,true,false)
            .addCriterion("minecraft:story/cure_zombie_villager",research)
            .addCriterion("minecraft:end/dragon_breath",research)
            .parent(prime_flask)
            .rewards(xp(20))
            .build(name("pluvia_flask"));
    public static final AdvancementHolder eterna_flask = Advancement.Builder.advancement()
            .display(Items.NETHER_STAR,Component.literal("Max Herblore"),
                    Component.literal("the pinnacle of alchemy"),null,
                    AdvancementType.CHALLENGE,true,true,false)
            .addCriterion("minecraft:story/cure_zombie_villager",research)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:nether/all_effects",research)
            .addCriterion("minecraft:end/dragon_breath",research)
            .parent(magna_flask)
            .rewards(xp(20))
            .build(name("eterna_flask"));
    //endregion flasks

    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
        consumer.accept(root);
        consumer.accept(arcana);
        consumer.accept(machina);
        consumer.accept(enchanting);
        consumer.accept(matrix);
        consumer.accept(foundry);
        consumer.accept(mincerator);
        consumer.accept(evaporator);
        consumer.accept(teleporter);
        consumer.accept(consolidator);
        consumer.accept(alembic);
        consumer.accept(prime_flask);
        consumer.accept(pugna_flask);
        consumer.accept(magna_flask);
        consumer.accept(vita_flask);
        consumer.accept(pluvia_flask);
        consumer.accept(eterna_flask);
    }
    static ResourceLocation name(String name)
    {
        return ResourceLocation.fromNamespaceAndPath(Patina.MODID,name);
    }
    static ResourceKey<Recipe<?>> key(String name)
    {
        return ResourceKey.create(Registries.RECIPE,ResourceLocation.fromNamespaceAndPath(Patina.MODID,name));
    }
}

package com.natesky9.datagen;

import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class ModAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
    static Criterion<InventoryChangeTrigger.TriggerInstance> smelt_copper =
            InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT);
    AdvancementRewards.Builder xp(AdvancementRewards.Builder builder)
    {
        return builder.addExperience(10);
    }
    static AdvancementRewards.Builder xp()
    {
        return AdvancementRewards.Builder.experience(10);
    }



    static Criterion<ImpossibleTrigger.TriggerInstance> research = CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance());

    //AdvancementType, toast, announce, hidden
    //region root
    public static final AdvancementHolder root = Advancement.Builder.advancement()
            .display(Items.COPPER_INGOT,Component.literal("Patina"),
                    Component.literal("The dawn of tech"),ResourceLocation.withDefaultNamespace("textures/block/copper_block.png"),
                    AdvancementType.TASK,true,true,false)
            .addCriterion("has_copper",smelt_copper)
            .rewards(xp())
            .build(name("root"));
    //endregion root
    //region arcana
    public static final AdvancementHolder arcana = Advancement.Builder.advancement()
            .display(Items.EXPERIENCE_BOTTLE, Component.literal("Advanced Arcana"),
                    Component.literal("Unlocking the science of magic!"),null,
                    AdvancementType.CHALLENGE,true,true,false)
            .addCriterion("minecraft:story/cure_zombie_villager", research)
            .addCriterion("minecraft:story/enchant_item", research)
            .addCriterion("minecraft:nether/use_lodestone", research)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:end/dragon_breath", research)
            .addCriterion("minecraft:adventure/totem_of_undying", research)
            .addCriterion("minecraft:husbandry/axolotl_in_a_bucket", research)
            .addCriterion("minecraft:husbandry/obtain_sniffer_egg", research)
            .rewards(AdvancementRewards.Builder.experience(80))
            .parent(root)
            .build(name("arcana"));
    //endregion arcana
    //region machina
    public static final AdvancementHolder machina = Advancement.Builder.advancement()
            .display(Items.CRAFTER, Component.literal("Automation"),
                    Component.literal("Using technology for gain"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:adventure/crafters_crafting_crafters", research)
            .parent(root)
            .rewards(xp())
            .build(name("automation"));
    //endregion machina
    //region teleporter
    public static final AdvancementHolder teleporter = Advancement.Builder.advancement()
            .display(Items.CHORUS_FLOWER, Component.literal("The Chorus Sings"),
                    Component.literal("Not so random after all"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:end/enter_end_gateway", research)
            .addCriterion("minecraft:nether/fast_travel", research)
            .rewards(xp().addRecipe(name("chorus_teleporter"))
                    .addRecipe(name("chorus_cable")))
            .parent(arcana)
            .build(name("teleporter"));
    //endregion teleporter
    //region consolidator
    public static final AdvancementHolder consolidator = Advancement.Builder.advancement()
            .display(ModItems.ESSENCE.get(),Component.literal("Mind over Matter"),
                    Component.literal("Experience the Rainbow"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:story/enchant_item",research)
            .addCriterion("minecraft:nether/obtain_crying_obsidian",research)
            .parent(arcana)
            .rewards(xp().addRecipe(name("appliance_arcane_consolidator"))
                    .addRecipe(name("evaporator/enchanting")))
            .build(name("consolidator"));
    //endregion consolidator
    //region mincerator
    public static final AdvancementHolder mincerator = Advancement.Builder.advancement()
            .display(ModBlocks.MACHINE_MINCERATOR.get(), Component.literal("Cooking up a Storm!"),
                    Component.literal("Experimenting with new food"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:husbandry/plant_seed",research)
            .addCriterion("minecraft:husbandry/breed_an_animal",research)
            .addCriterion("minecraft:adventure/trade",research)
            .parent(machina)
            .rewards(xp().addRecipe(name("mincerator"))
                    .addRecipe(name("mincerator/apple_pie"))
                    .addRecipe(name("mincerator/blink_brownie"))
                    .addRecipe(name("mincerator/bread"))
                    .addRecipe(name("mincerator/pumpkin_pie"))
                    .addRecipe(name("mincerator/fire_charge"))
                    .addRecipe(name("mincerator/borger"))
                    .addRecipe(name("mincerator/chili"))
                    .addRecipe(name("mincerator/sweets"))
                    .addRecipe(name("mincerator/triple_meat_treat"))
                    .addRecipe(name("mincerator/icecream")))
            .build(name("mincerator"));
    //endregion mincerator
    //region mincerator_advanced
    public static final AdvancementHolder mincerator_advanced_recipes = Advancement.Builder.advancement()
            .display(Items.GOLDEN_APPLE, Component.literal("Playing with your Food"),
                    Component.literal("More gold = More good"),null,
                    AdvancementType.GOAL,true,true,false)
            .addCriterion("patina:root/mincerator",research)
            .addCriterion("minecraft:story/cure_zombie_villager",research)
            .addCriterion("minecraft:adventure/honey_block_slide",research)
            .parent(mincerator)
            .rewards(xp().addRecipe(name("mincerator/golden_carrot"))
                    .addRecipe(name("mincerator/golden_apple"))
                    .addRecipe(name("mincerator/golden_cookie")))
            .build(name("mincerator_gold_recipes"));
    //endregion mincerator_advanced
    //region lighter
    public static final AdvancementHolder trinket_lighter = Advancement.Builder.advancement()
            .display(ModItems.LIGHTER.get(), Component.literal("Lighting the path"),
                    Component.literal("Pocket full of sunshine"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:adventure/lighten_up",research)
            .parent(machina)
            .rewards(xp().addRecipe(name("lighter")))
            .build(name("lighter"));
    //endregion lighter
    //region brewing
    public static final AdvancementHolder brewing = Advancement.Builder.advancement()
            .display(Items.BREWING_STAND,Component.literal("Bubble and Boil"),
                    Component.literal("alchemy for the daring"), null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:nether/obtain_blaze_rod", research)
            .parent(root)
            .rewards(xp().addRecipe(name("machine_alembic")))
            .build(name("brewing"));
    //endregion brewing
    //region brewing_augment
    public static final AdvancementHolder brewing_augment = Advancement.Builder.advancement()
            .display(ModBlocks.ADDON_ALEMBIC.get().asItem(),Component.literal("Why is it spicy"),
                    Component.literal("improved yield for your autobrewers"),null,
                            AdvancementType.GOAL,true,true,false)
            .addCriterion("minecraft:nether/all_potions", research)
            .addCriterion("minecraft:nether/obtain_crying_obsidian", research)
            .parent(brewing)
            .rewards(xp().addRecipe(name("addon_alembic")))
            .build(name("brewing_augment"));
    //endregion brewing_augment
    //region foundry
    public static final AdvancementHolder foundry = Advancement.Builder.advancement()
            .display(Items.BLAST_FURNACE,Component.literal("More melty than blasty"),
                    Component.literal("advanced metalurgy"), null,
                    AdvancementType.TASK,true,false,false)
            .addCriterion("minecraft:story/lava_bucket", research)
            .addCriterion("minecraft:story/smelt_iron", research)
            .rewards(xp().addRecipe(name("machine_foundry"))
                    .addRecipe(name("foundry/iron"))
                    .addRecipe(name("foundry/copper"))
                    .addRecipe(name("foundry/gold"))
                    .addRecipe(name("foundry/netherite"))
                    .addRecipe(name("foundry/netherite_alternative"))
                    .addRecipe(name("foundry/bronze"))
                    .addRecipe(name("foundry/tinted_glass")))
            .parent(root)
            .build(name("foundry"));
    //endregion foundry
    //region foundry_addon
    public static final AdvancementHolder foundry_augment = Advancement.Builder.advancement()
            .display(ModBlocks.ADDON_FOUNDRY.get(), Component.literal("Productivity Mk 1"),
                    Component.literal("when it's just not hot enough"),null,
                    AdvancementType.GOAL,true,false,false)
            .addCriterion("patina:root/foundry", research)
            .parent(foundry)
            .rewards(xp().addRecipe(name("addon_foundry")))
            .build(name("foundry_augment"));
    //endregion foundry_addon
    //region evaporator
    public static final AdvancementHolder evaporator = Advancement.Builder.advancement()
            .display(ModBlocks.MACHINE_EVAPORATOR.get(), Component.literal("This ain't sugar, Mr White"),
                    Component.literal("Break out your bunsen burners!"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:husbandry/make_a_sign_glow",research)
            .addCriterion("minecraft:husbandry/safely_harvest_honey",research)
            .parent(root)
            .rewards(xp().addRecipe(name("machine_evaporator"))
                    .addRecipe(name("evaporator/salt"))
                    .addRecipe(name("evaporator/gunpowder")))
            .build(name("evaporator"));
    //endregion evaporator
    public static final AdvancementHolder void_salt = Advancement.Builder.advancement()
            .display(ModItems.VOID_SALT.get(), Component.literal("This doesn't look edible"),
                    Component.literal("You're not sure what this stuff is..."),null,
                    AdvancementType.GOAL,true,true,false)
            .addCriterion("patina:root/evaporator",research)
            .addCriterion("minecraft:nether/all_potions",research)
            .parent(evaporator)
            .rewards(xp())//add the void salt recipe later
            .build(name("potency"));
    //public static final AdvancementHolder loom = Advancement.Builder.advancement()
    //    .display(Items.LOOM, Component.literal("Tight Fit"),
    //                Component.literal("unlocked advanced loom techniques"), null,
    //                AdvancementType.TASK,true,true,false)
    //        .addCriterion("tight_fit", textilerCriteria)
    //        .parent(root)
    //    .build(name("tight_fit"));
    //region glass
    public static final AdvancementHolder material_crystal = Advancement.Builder.advancement()
            .display(ModItems.PRIME_GLASS.get(),Component.literal("The finest crystal"),
                    Component.literal("Advanced alloying techniques"), null,
                            AdvancementType.TASK, true, true, false)
            .addCriterion("minecraft:story/smelt_iron", research)
            .addCriterion("minecraft:nether/distract_piglin", research)
            .parent(foundry)
            .rewards(xp().addRecipe(name("foundry/prime"))
                    .addRecipe(name("crystal_prime_helmet"))
                    .addRecipe(name("crystal_prime_chestplate"))
                    .addRecipe(name("crystal_prime_leggings")))
            .build(name( "crystal_glass"));
    public static final AdvancementHolder material_fortis = Advancement.Builder.advancement()
            .display(ModItems.FORTIS_GLASS.get(),Component.literal("Reinforced glass"),
                    Component.literal("what's that about a glass house again?"),null,
                    AdvancementType.GOAL,true,true,false)
            .addCriterion("patina/root", research)
            .parent(material_crystal)
            .rewards(xp().addRecipe(name("foundry/fortis"))
                    .addRecipe(name("crystal_fortis_helmet"))
                    .addRecipe(name("crystal_fortis_chestplate"))
                    .addRecipe(name("crystal_fortis_leggings")))
            .build(name("fortis_glass"));
    public static final AdvancementHolder material_anima = Advancement.Builder.advancement()
            .display(ModItems.ANIMA_GLASS.get(),Component.literal("Living glass"),
                    Component.literal("cold, but pulsing with energy"),null,
                    AdvancementType.GOAL,true,true,false)
            .addCriterion("patina:patina/root", research)
            .parent(material_crystal)
            .rewards(xp().addRecipe(name("foundry/anima"))
                    .addRecipe(name("crystal_anima_helmet"))
                    .addRecipe(name("crystal_anima_chestplate"))
                    .addRecipe(name("crystal_anima_leggings")))
            .build(name("anima_glass"));
    public static final AdvancementHolder material_ferus = Advancement.Builder.advancement()
            .display(ModItems.FERUS_GLASS.get(),Component.literal("Ferus glass"),
                    Component.literal("symbiotic silicon synergy"),null,
                    AdvancementType.GOAL,true,true,false)
            .addCriterion("patina:patina/root", research)
            .parent(material_crystal)
            .rewards(xp().addRecipe(name("foundry/ferus"))
                    .addRecipe(name("crystal_ferus_helmet"))
                    .addRecipe(name("crystal_ferus_chestplate"))
                    .addRecipe(name("crystal_ferus_leggings")))
            .build(name("ferus_glass"));
    //potion flasks
    public static final AdvancementHolder prime_flask = Advancement.Builder.advancement()
            .display(ModItems.POTION_FLASK.get(),Component.literal("Improved capacity!"),
                    Component.literal("test title"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:husbandry/wax_on", research)
            .addCriterion("minecraft:nether/distract_piglin", research)
            .parent(brewing)
            .rewards(xp().addRecipe(name("brewing/prime_flask")))
            .build(name("flask1"));
    public static final AdvancementHolder impetus_flask = Advancement.Builder.advancement()
            .display(ModItems.IMPETUS_FLASK.get(),Component.literal("Juicing for combat"),
                    Component.literal("test title"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:husbandry/ride_a_boat_with_a_goat", research)
            .parent(prime_flask)
            .rewards(xp().addRecipe(name("brewing/impetus_flask")))
            .build(name("flask2"));
    public static final AdvancementHolder magna_flask = Advancement.Builder.advancement()
            .display(ModItems.MAGNA_FLASK.get(),Component.literal("Did you say more?"),
                    Component.literal("For when you're REALLY thirsty"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:adventure/fall_from_world_height", research)
            .parent(prime_flask)
            .rewards(xp().addRecipe(name("brewing/magna_flask")))
            .build(name("flask3"));
    public static final AdvancementHolder vita_flask = Advancement.Builder.advancement()
            .display(ModItems.VITA_FLASK.get(),Component.literal("For when you need it most"),
                    Component.literal("test title"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:husbandry/kill_axolotl_target", research)
            .parent(prime_flask)
            .rewards(xp().addRecipe(name("brewing/vita_flask")))
            .build(name("flask4"));
    public static final AdvancementHolder eterna_flask = Advancement.Builder.advancement()
            .display(Items.NETHER_STAR,Component.literal("Max Herblore"),
                    Component.literal("the pinnacle of alchemy"),null,
                    AdvancementType.CHALLENGE,true,true,false)
            .addCriterion("minecraft:story/cure_zombie_villager",research)
            .addCriterion("minecraft:nether/brew_potion", research)
            .addCriterion("minecraft:nether/all_effects",research)
            .addCriterion("minecraft:end/dragon_breath",research)
            .parent(prime_flask)
            .rewards(xp().addRecipe(name("matrix/eterna_flask")))
            .build(name("flask5"));
    //endregion glass
    //public static final AdvancementHolder phantom = Advancement.Builder.advancement()
    //        .display(Items.PHANTOM_MEMBRANE,Component.literal("Insomniatic"),
    //                Component.literal("wear the skin of your nightmare"), null,
    //                AdvancementType.TASK,true,true,false)
    //        .addCriterion("minecraft:adventure/two_birds_one_arrow",impossible_criteria)
    //        .parent(loom)
    //        .build(name("phantom"));
    public static final AdvancementHolder dragon = Advancement.Builder.advancement()
            .display(ModItems.DRAGON_SCALE.get(),Component.literal("Dwagon"),
                    Component.literal("unlocked dragon scale"), null,
                    AdvancementType.CHALLENGE,true,true,false)
            .addCriterion("killed_dragon", research)
            .addCriterion("minecraft:end/kill_dragon",research)
            .parent(root)
            .rewards(xp().addRecipe(name("dragon_helmet"))
                    .addRecipe(name("dragon_chestplate"))
                    .addRecipe(name("dragon_leggings")))
            .build(name("dragon"));
    //newer advancements here
    //region fragment weapons
    public static final AdvancementHolder weaponBeeShield = Advancement.Builder.advancement()
            .display(ModItems.BEE_SHIELD.get(),Component.literal("Bee our guest!"),
                    Component.literal("research advanced weaponry"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:story/deflect_arrow", research)
            .addCriterion("minecraft:husbandry/silk_touch_nest", research)
            .parent(arcana)
            .build(name("bee_shield"));
    public static final AdvancementHolder weaponBeeSword = Advancement.Builder.advancement()
            .display(ModItems.BEE_SWORD.get(),Component.literal("Put our service to the test!"),
                    Component.literal("research advanced weaponry"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:adventure/honey_block_slide", research)
            .addCriterion("minecraft:adventure/salvage_sherd", research)
            .parent(weaponBeeShield)
            .build(name("bee_sword"));
    public static final AdvancementHolder weaponPigBow = Advancement.Builder.advancement()
            .display(ModItems.PIG_CROSSBOW.get(),Component.literal("Heavy Hitter"),
                    Component.literal("research advanced weaponry"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:adventure/arbalistic", research)
            .addCriterion("minecraft:adventure/sniper_duel", research)
            .parent(arcana)
            .build(name("pig_bow"));

    public static final AdvancementHolder weaponPigAxe = Advancement.Builder.advancement()
            .display(ModItems.PIG_SWORD.get(),Component.literal("Like the mace, but reversed!"),
                    Component.literal("research advanced weaponry"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/find_bastion", research)
            .addCriterion("minecraft:adventure/under_lock_and_key", research)
            .parent(weaponPigBow)
            .build(name("pig_sword"));

    public static final AdvancementHolder weaponWitherStaff = Advancement.Builder.advancement()
            .display(ModItems.WITHER_STAFF.get(),Component.literal("That sinking feeling..."),
                    Component.literal("research advanced weaponry"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/return_to_sender", research)
            .addCriterion("minecraft:nether/charge_respawn_anchor", research)
            .addCriterion("minecraft:nether/summon_wither", research)
            .parent(arcana)
            .build(name("wither_staff"));
    public static final AdvancementHolder witherWings = Advancement.Builder.advancement()
            .display(ModItems.WITHER_WINGS.get(),Component.literal("Beneath the veil"),
                    Component.literal("be careful what you wish for"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/uneasy_alliance", research)
            .addCriterion("minecraft:nether/get_wither_skull", research)
            .addCriterion("minecraft:nether/summon_wither", research)
            .parent(weaponWitherStaff)
            .build(name("wither_wings"));
    //endregion fragment weapons
    public static final AdvancementHolder material_bismuth = Advancement.Builder.advancement()
            .display(ModItems.BISMUTH_INGOT.get(),Component.literal("Advanced Materials"),
                    Component.literal("Ooh, pretty!"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:nether/distract_piglin",research)
            .parent(foundry)
            .rewards(xp().addRecipe(name("evaporator/bismuth")))
            .build(name("material_bismuth"));
    public static final AdvancementHolder material_malachite = Advancement.Builder.advancement()
            .display(ModItems.MALACHITE.get(), Component.literal("Malachite"),
                    Component.literal("Emeralds for days"),null,
                    AdvancementType.TASK,true,true,false)
            .addCriterion("minecraft:adventure/trade",research)
            .addCriterion("minecraft:story/smelt_iron",research)
            .parent(foundry)
            .rewards(xp().addRecipe(name("foundry/malachite")))
            .build(name("material_malachite"));
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        saver.accept(root);
        saver.accept(arcana);
        saver.accept(machina);
        //saver.accept(loom);
        saver.accept(foundry);
        saver.accept(foundry_augment);
        saver.accept(brewing);
        saver.accept(brewing_augment);
        saver.accept(teleporter);
        saver.accept(consolidator);
        saver.accept(mincerator);
        saver.accept(mincerator_advanced_recipes);

        saver.accept(void_salt);
        saver.accept(trinket_lighter);

        //saver.accept(torch);

        saver.accept(material_crystal);
        saver.accept(material_fortis);
        saver.accept(material_ferus);
        saver.accept(material_anima);
        saver.accept(material_bismuth);
        saver.accept(material_malachite);
        saver.accept(dragon);

        //saver.accept(phantom);
        //saver.accept(dragon);
        saver.accept(weaponBeeShield);
        saver.accept(weaponBeeSword);
        saver.accept(weaponPigBow);
        saver.accept(weaponPigAxe);
        saver.accept(weaponWitherStaff);
        saver.accept(witherWings);

        saver.accept(prime_flask);
        saver.accept(impetus_flask);
        saver.accept(magna_flask);
        saver.accept(vita_flask);
        saver.accept(eterna_flask);
    }
    static ResourceLocation name(String string)
    {
        return ResourceLocation.fromNamespaceAndPath(Patina.MODID,string);
    }
}

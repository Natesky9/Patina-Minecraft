package com.natesky9.datagen;

import com.mojang.datafixers.util.Either;
import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.EvaporatorRecipe;
import com.natesky9.patina.Recipe.FoundryRecipe;
import com.natesky9.patina.Recipe.MatrixRecipe;
import com.natesky9.patina.Recipe.MinceratorRecipe;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModItems;
import com.natesky9.patina.init.ModTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.BundleRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.natesky9.patina.init.ModPotions.*;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output,provider);
    }
    static Criterion<ImpossibleTrigger.TriggerInstance> none = CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance());

    List<Ingredient> recipe(Ingredient one, Ingredient two, Ingredient three, Ingredient four)
    {
        return List.of(one,two,three,four);
    }

    void saltRecipe(RecipeOutput pWriter,int count)
    {
        ArrayList<Ingredient> list = new ArrayList<>();
        list.add(Ingredient.of(Items.WITHER_ROSE));
        list.add(Ingredient.of(Items.FERMENTED_SPIDER_EYE));
        list.add(Ingredient.of(Items.OMINOUS_BOTTLE));
        for (int i = 0;i < count;i++)
            list.add(Ingredient.of(ModItems.POTION_SALT.get()));
        pWriter.accept(name("matrix/void_salt_" + count),
                new MatrixRecipe(new ItemStack(ModItems.VOID_SALT.get()),
                list.stream().toList()),ModAdvancementGenerator.void_salt.id(),null);
    }

    @Override
    public void buildRecipes(@NotNull RecipeOutput pWriter) {
        //TODO: add the recipes, you dummy
        //region matrix recipes
        pWriter.accept(name("matrix/basic_boots"),
                new MatrixRecipe(new ItemStack(ModItems.CLOTH_BOOTS.get()),List.of(
                        Ingredient.of(Items.LEATHER_BOOTS),
                        Ingredient.of(Items.FEATHER),
                        Ingredient.of(Items.FEATHER),
                        Ingredient.of(Items.BREEZE_ROD),
                        Ingredient.of(Items.BREEZE_ROD),
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(ItemTags.WOOL)
                )),null);
        pWriter.accept(name("matrix/gilded_rod"),
                new MatrixRecipe(new ItemStack(ModItems.GILDED_ROD.get()),List.of(
                        Ingredient.of(Items.FISHING_ROD),
                        Ingredient.of(Items.GOLD_INGOT),
                        Ingredient.of(Items.GOLD_INGOT),
                        Ingredient.of(Items.GOLD_INGOT),
                        Ingredient.of(Items.COMPASS),
                        Ingredient.of(Items.CLOCK),
                        Ingredient.of(Items.BAMBOO)
                )),null);
        pWriter.accept(name("matrix/angler_rod"),
                new MatrixRecipe(new ItemStack(ModItems.ANGLER_ROD.get()),List.of(
                        Ingredient.of(Items.FISHING_ROD),
                        Ingredient.of(Items.BAMBOO),
                        Ingredient.of(Items.PRISMARINE_CRYSTALS),
                        Ingredient.of(Items.PRISMARINE_CRYSTALS),
                        Ingredient.of(Items.PRISMARINE_CRYSTALS),
                        Ingredient.of(Items.NAUTILUS_SHELL),
                        Ingredient.of(Items.NAUTILUS_SHELL)
                )),null);
        pWriter.accept(name("matrix/dragon_claw"),
                new MatrixRecipe(new ItemStack(ModItems.DRAGON_CLAW.get()),List.of(
                        Ingredient.of(ModItems.CRYSTAL_CLAW.get()),
                        Ingredient.of(ModItems.DRAGON_SCALE.get()),
                        Ingredient.of(ModItems.DRAGON_SCALE.get()),
                        Ingredient.of(ModItems.DRAGON_SCALE.get()),
                        Ingredient.of(ModItems.NETHERITE_NUGGET.get()),
                        Ingredient.of(ModItems.NETHERITE_NUGGET.get()),
                        Ingredient.of(Items.DRAGON_BREATH)
                )),null);
        pWriter.accept(name("matrix/umbral_helmet"),
                new MatrixRecipe(new ItemStack(ModItems.UMBRA_HAT.get()),List.of(
                        Ingredient.of(Items.LEATHER_HELMET),
                        Ingredient.of(Items.PHANTOM_MEMBRANE),
                        Ingredient.of(Items.PHANTOM_MEMBRANE),
                        Ingredient.of(Items.RABBIT_HIDE),
                        Ingredient.of(Items.RABBIT_HIDE),
                        Ingredient.of(Items.RABBIT_HIDE),
                        Ingredient.of(Items.WIND_CHARGE)
                )),ModAdvancementGenerator.arcana);
        pWriter.accept(name("matrix/umbral_chestplate"),
                new MatrixRecipe(new ItemStack(ModItems.UMBRA_TOP.get()),List.of(
                        Ingredient.of(Items.LEATHER_CHESTPLATE),
                        Ingredient.of(Items.PHANTOM_MEMBRANE),
                        Ingredient.of(Items.PHANTOM_MEMBRANE),
                        Ingredient.of(Items.PHANTOM_MEMBRANE),
                        Ingredient.of(Items.LEAD),
                        Ingredient.of(Items.LEAD),
                        Ingredient.of(Items.WIND_CHARGE)
                )),ModAdvancementGenerator.arcana);
        pWriter.accept(name("matrix/umbral_leggings"),
                new MatrixRecipe(new ItemStack(ModItems.UMBRA_BOTTOM.get()),List.of(
                        Ingredient.of(Items.LEATHER_LEGGINGS),
                        Ingredient.of(Items.PHANTOM_MEMBRANE),
                        Ingredient.of(Items.PHANTOM_MEMBRANE),
                        Ingredient.of(Items.BRUSH),
                        Ingredient.of(Items.BRUSH),
                        Ingredient.of(Items.WIND_CHARGE)
                )),ModAdvancementGenerator.arcana);
        pWriter.accept(name("matrix/upgraded_plinth"),
                new MatrixRecipe(new ItemStack(ModBlocks.APPLIANCE_REINFORCED_PLINTH.get()),List.of(
                        Ingredient.of(ModBlocks.APPLIANCE_PLINTH.get()),
                        Ingredient.of(Items.OBSIDIAN),
                        Ingredient.of(Items.OBSIDIAN),
                        Ingredient.of(Items.OBSIDIAN),
                        Ingredient.of(ModItems.FORTIS_GLASS.get()),
                        Ingredient.of(ModItems.FORTIS_GLASS.get()),
                        Ingredient.of(Items.ENDER_PEARL)
                )),null);
        pWriter.accept(name("matrix/pig_axe"),
                new MatrixRecipe(new ItemStack(ModItems.PIG_SWORD.get()),List.of(
                        Ingredient.of(Items.GOLDEN_AXE),
                        Ingredient.of(ModItems.PIG_FRAGMENT_1.get()),
                        Ingredient.of(ModItems.PIG_FRAGMENT_2.get()),
                        Ingredient.of(ModItems.PIG_FRAGMENT_3.get()),
                        Ingredient.of(ModItems.PIG_FRAGMENT_4.get())
                )),ModAdvancementGenerator.weaponPigAxe);
        pWriter.accept(name("matrix/pig_crossbow"),
                new MatrixRecipe(new ItemStack(ModItems.PIG_CROSSBOW.get()),List.of(
                        Ingredient.of(Items.CROSSBOW),
                        Ingredient.of(ModItems.PIG_FRAGMENT_1.get()),
                        Ingredient.of(ModItems.PIG_FRAGMENT_2.get()),
                        Ingredient.of(ModItems.PIG_FRAGMENT_3.get()),
                        Ingredient.of(ModItems.PIG_FRAGMENT_4.get())
                )),ModAdvancementGenerator.weaponPigAxe);
        pWriter.accept(name("matrix/bee_sword"),
                new MatrixRecipe(new ItemStack(ModItems.BEE_SWORD.get()),List.of(
                        Ingredient.of(Items.GOLDEN_SWORD),
                        Ingredient.of(ModItems.BEE_FRAGMENT_1.get()),
                        Ingredient.of(ModItems.BEE_FRAGMENT_2.get()),
                        Ingredient.of(ModItems.BEE_FRAGMENT_3.get()),
                        Ingredient.of(ModItems.BEE_FRAGMENT_4.get())
                )),ModAdvancementGenerator.weaponBeeSword);
        pWriter.accept(name("matrix/bee_shield"),
                new MatrixRecipe(new ItemStack(ModItems.BEE_SHIELD.get()),List.of(
                        Ingredient.of(Items.SHIELD),
                        Ingredient.of(ModItems.BEE_FRAGMENT_1.get()),
                        Ingredient.of(ModItems.BEE_FRAGMENT_2.get()),
                        Ingredient.of(ModItems.BEE_FRAGMENT_3.get()),
                        Ingredient.of(ModItems.BEE_FRAGMENT_4.get())
                )),ModAdvancementGenerator.weaponBeeShield);
        pWriter.accept(name("matrix/wither_staff"),
                new MatrixRecipe(new ItemStack(ModItems.WITHER_STAFF.get()),List.of(
                        Ingredient.of(Items.GOLDEN_SWORD),
                        Ingredient.of(ModItems.WITHER_FRAGMENT_1.get()),
                        Ingredient.of(ModItems.WITHER_FRAGMENT_2.get()),
                        Ingredient.of(ModItems.WITHER_FRAGMENT_3.get()),
                        Ingredient.of(ModItems.WITHER_FRAGMENT_4.get())
                )),ModAdvancementGenerator.weaponWitherStaff);
        pWriter.accept(name("matrix/wither_wings"),
                new MatrixRecipe(new ItemStack(ModItems.WITHER_WINGS.get()),List.of(
                        Ingredient.of(Items.GOLDEN_CHESTPLATE),
                        Ingredient.of(ModItems.BEE_FRAGMENT_1.get()),
                        Ingredient.of(ModItems.BEE_FRAGMENT_2.get()),
                        Ingredient.of(ModItems.BEE_FRAGMENT_3.get()),
                        Ingredient.of(ModItems.BEE_FRAGMENT_4.get())
                )),ModAdvancementGenerator.witherWings);
        //region void salt
        saltRecipe(pWriter,1);
        //saltRecipe(pWriter,2);
        //saltRecipe(pWriter,3);
        //saltRecipe(pWriter,4);
        //saltRecipe(pWriter,5);
        //saltRecipe(pWriter,6);
        //saltRecipe(pWriter,7);
        //saltRecipe(pWriter,8);
        //saltRecipe(pWriter,9);
        //endregion void salt
        ItemStack klein = new ItemStack(ModItems.ETERNA_FLASK.get());
        klein.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WEAKNESS));
        pWriter.accept(name("matrix/eterna_flask"),
                new MatrixRecipe(klein,List.of(
                        Ingredient.of(ModItems.POTION_FLASK.get()),
                        Ingredient.of(ModItems.VITA_FLASK.get()),
                        Ingredient.of(ModItems.IMPETUS_FLASK.get()),
                        Ingredient.of(ModItems.MAGNA_FLASK.get()),
                        Ingredient.of(Items.NETHER_WART),
                        Ingredient.of(ModItems.BISMUTH_INGOT.get()),
                        Ingredient.of(ModItems.BISMUTH_INGOT.get()),
                        Ingredient.of(Items.NETHER_STAR)
                )),null);
        //endregion matrix recipes
        //region foundry recipes
        //alloys
        pWriter.accept(name("foundry/prime"),
                new FoundryRecipe(new ItemStack(ModItems.PRIME_GLASS.get()),
                        Ingredient.of(Items.PRISMARINE_CRYSTALS), Ingredient.of(Items.SOUL_SAND)),
                null);
        pWriter.accept(name("foundry/anima"),
                new FoundryRecipe(new ItemStack(ModItems.ANIMA_GLASS.get()),
                        Ingredient.of(ModItems.PRIME_GLASS.get()), Ingredient.of(Items.CHORUS_FLOWER)),
                ModAdvancementGenerator.material_anima);
        pWriter.accept(name("foundry/fortis"),
                new FoundryRecipe(new ItemStack(ModItems.FERUS_GLASS.get()),
                        Ingredient.of(ModItems.PRIME_GLASS.get()), Ingredient.of(Items.GOAT_HORN)),
                ModAdvancementGenerator.material_fortis);
        pWriter.accept(name("foundry/magna"),
                new FoundryRecipe(new ItemStack(ModItems.FORTIS_GLASS.get()),
                        Ingredient.of(ModItems.PRIME_GLASS.get()), Ingredient.of(Items.NETHERITE_INGOT)),
                ModAdvancementGenerator.material_fortis);
        pWriter.accept(name("foundry/malachite"),
                new FoundryRecipe(new ItemStack(ModItems.MALACHITE.get()),
                        Ingredient.of(Items.EMERALD_BLOCK), Ingredient.of(Items.COPPER_INGOT)),
                ModAdvancementGenerator.material_malachite);
        pWriter.accept(name("foundry/bronze"),
                new FoundryRecipe(new ItemStack(ModItems.BRONZE_INGOT.get()),
                        Ingredient.of(ModItems.BISMUTH_INGOT.get()),Ingredient.of(Items.COPPER_INGOT)),
                ModAdvancementGenerator.foundry);
        pWriter.accept(name("foundry/tinted_glass"),
                new FoundryRecipe(new ItemStack(Items.TINTED_GLASS),
                        Ingredient.of(Items.GLASS),Ingredient.of(Items.AMETHYST_SHARD)),
                ModAdvancementGenerator.foundry);
        //refine
        pWriter.accept(name("foundry/copper"),
                new FoundryRecipe(new ItemStack(ModItems.COPPER_NUGGET.get(),10),
                        Ingredient.of(Items.RAW_COPPER), Ingredient.of(Items.FIRE_CHARGE)),
                ModAdvancementGenerator.foundry);
        pWriter.accept(name("foundry/iron"),
                new FoundryRecipe(new ItemStack(Items.IRON_NUGGET,10),
                        Ingredient.of(Items.RAW_IRON),Ingredient.of(Items.FIRE_CHARGE)),
                ModAdvancementGenerator.foundry);
        pWriter.accept(name("foundry/gold"),
                new FoundryRecipe(new ItemStack(Items.GOLD_NUGGET,10),
                        Ingredient.of(Items.RAW_GOLD),Ingredient.of(Items.FIRE_CHARGE)),
                ModAdvancementGenerator.foundry);
        pWriter.accept(name("foundry/netherite"),
                new FoundryRecipe(new ItemStack(ModItems.NETHERITE_NUGGET.get(),3),
                        Ingredient.of(Items.ANCIENT_DEBRIS),Ingredient.of(Items.GOLD_INGOT)),
                ModAdvancementGenerator.foundry);
        pWriter.accept(name("foundry/netherite_alternative"),
                new FoundryRecipe(new ItemStack(ModItems.NETHERITE_NUGGET.get()),
                        Ingredient.of(Items.NETHERITE_SCRAP),Ingredient.of(Items.GOLD_INGOT)),
                ModAdvancementGenerator.foundry);

        //endregion foundry
        //region evaporator
        pWriter.accept(name("evaporator/salt"),
                new EvaporatorRecipe(map(Ingredient.of(Items.POTION)),
                        ModItems.POTION_SALT.get().getDefaultInstance()),
                null);
        pWriter.accept(name("evaporator/bismuth"),
                new EvaporatorRecipe(map(getHolder(IRIDESCENT_POTION)),
                        ModItems.BISMUTH_NUGGET.get().getDefaultInstance()),
                null);
        pWriter.accept(name("evaporator/gunpowder"),
                new EvaporatorRecipe(map(getHolder(VOLATILE_POTION)),
                        Items.GUNPOWDER.getDefaultInstance()),
                ModAdvancementGenerator.evaporator);
        pWriter.accept(name("evaporator/enchanting"),
                new EvaporatorRecipe(map(getHolder(KNOWLEDGE_POTION)),
                        ModItems.ESSENCE.get().getDefaultInstance()),
                ModAdvancementGenerator.evaporator);
        //endregion evaporator
        //region mincerator
        //if you add one, you need to add the resourcelocation to the advancement rewards
        pWriter.accept(name("mincerator/apple_pie"),
                new MinceratorRecipe(ModItems.FOOD_APPLE_PIE.get().getDefaultInstance(),
                        recipe(Ingredient.of(ModTags.SWEET),Ingredient.of(ModTags.GRAIN)
                                ,Ingredient.of(Tags.Items.EGGS),Ingredient.of(Items.APPLE))),
                ModAdvancementGenerator.mincerator);
        pWriter.accept(name("mincerator/blink_brownie"),
                new MinceratorRecipe(ModItems.FOOD_BLINK_BROWNIE.get().getDefaultInstance(),
                        recipe(Ingredient.of(Items.CHORUS_FRUIT),Ingredient.of(ModTags.GRAIN),
                                Ingredient.of(ModTags.SWEET),Ingredient.of(Items.COCOA_BEANS))),
                ModAdvancementGenerator.mincerator);
        //region vanilla foods?
        pWriter.accept(name("mincerator/bread"),
                new MinceratorRecipe(new ItemStack(Items.BREAD,2),
                        recipe(Ingredient.of(ModTags.GRAIN),Ingredient.of(ModTags.GRAIN),
                                Ingredient.of(ModTags.SWEET),Ingredient.of(Tags.Items.EGGS))),
                ModAdvancementGenerator.mincerator);
        pWriter.accept(name("mincerator/pumpkin_pie"),
                new MinceratorRecipe(new ItemStack(Items.PUMPKIN_PIE,2),
                        recipe(Ingredient.of(Items.PUMPKIN),Ingredient.of(Items.SUGAR),
                                Ingredient.of(Tags.Items.EGGS),Ingredient.of(ModTags.GRAIN))),
                ModAdvancementGenerator.mincerator);
        //golden recipes to be added to another machine later
        pWriter.accept(name("mincerator/golden_carrot"),
                new MinceratorRecipe(Items.GOLDEN_CARROT.getDefaultInstance(),
                        recipe(Ingredient.of(Items.CARROT),Ingredient.of(Items.GOLD_NUGGET),
                                Ingredient.of(Items.GOLD_NUGGET),Ingredient.of(Items.GOLD_NUGGET))),
                ModAdvancementGenerator.mincerator);
        pWriter.accept(name("mincerator/golden_apple"),
                new MinceratorRecipe(Items.GOLDEN_APPLE.getDefaultInstance(),
                        recipe(Ingredient.of(Items.APPLE),Ingredient.of(Items.GOLD_INGOT),
                                Ingredient.of(Items.GOLD_INGOT),Ingredient.of(Items.GOLD_INGOT))),
                ModAdvancementGenerator.mincerator);
        pWriter.accept(name("mincerator/fire_charge"),
                new MinceratorRecipe(new ItemStack(Items.FIRE_CHARGE,8),
                        recipe(Ingredient.of(Items.CHARCOAL,Items.COAL),Ingredient.of(Items.BLAZE_POWDER),
                                Ingredient.of(Items.GUNPOWDER),Ingredient.of(Items.PAPER))),
                ModAdvancementGenerator.mincerator);
        //endregion
        pWriter.accept(name("mincerator/golden_cookie"),
                new MinceratorRecipe(ModItems.FOOD_GOLDEN_COOKIE.get().getDefaultInstance(),
                        recipe(Ingredient.of(Items.COOKIE),Ingredient.of(Items.GOLD_BLOCK),
                                Ingredient.of(Items.GOLD_BLOCK),Ingredient.of(Items.GOLD_BLOCK))),
                ModAdvancementGenerator.mincerator);
        pWriter.accept(name("mincerator/borger"),
                new MinceratorRecipe(ModItems.FOOD_BURGER.get().getDefaultInstance(),
                        recipe(Ingredient.of(Items.BREAD),Ingredient.of(Items.COOKED_BEEF),
                                Ingredient.of(Items.DRIED_KELP),Ingredient.of(Items.BREAD))),
                ModAdvancementGenerator.mincerator);
        pWriter.accept(name("mincerator/chili"),
                new MinceratorRecipe(ModItems.FOOD_CHILI.get().getDefaultInstance(),
                        recipe(Ingredient.of(ModTags.MEAT),Ingredient.of(ModTags.VEGETABLE),
                                Ingredient.of(Items.BOWL),Ingredient.of(Items.BLAZE_POWDER))),
                ModAdvancementGenerator.mincerator);
        pWriter.accept(name("mincerator/sweets"),
                new MinceratorRecipe(new ItemStack(ModItems.FOOD_SWEETS.get(),8),
                        recipe(Ingredient.of(Items.OCHRE_FROGLIGHT,Items.PEARLESCENT_FROGLIGHT),
                                Ingredient.of(Items.PEARLESCENT_FROGLIGHT,Items.VERDANT_FROGLIGHT),
                                Ingredient.of(Items.VERDANT_FROGLIGHT,Items.OCHRE_FROGLIGHT),
                                Ingredient.of(ModTags.SWEET))),
                ModAdvancementGenerator.mincerator_advanced_recipes);
        pWriter.accept(name("mincerator/triple_meat_treat"),
                new MinceratorRecipe(new ItemStack(ModItems.FOOD_TRIPLE_MEAT_TREAT.get(),3),
                        recipe(Ingredient.of(ModTags.MEAT),Ingredient.of(ModTags.MEAT),
                                Ingredient.of(ModTags.SWEET),Ingredient.of(ModTags.MEAT))),
                ModAdvancementGenerator.mincerator);
        pWriter.accept(name("mincerator/icecream"),
                new MinceratorRecipe(ModItems.FOOD_ICECREAM.get().getDefaultInstance(),
                        recipe(Ingredient.of(Items.SNOW_BLOCK),Ingredient.of(Items.SNOW_BLOCK),
                                Ingredient.of(ModTags.SWEET),Ingredient.of(Items.MILK_BUCKET))),
                ModAdvancementGenerator.mincerator);
        //endregion mincerator

        //pWriter.accept(ResourceLocation.fromNamespaceAndPath(Patina.MODID,"textiler/silk"),
        //        new TextilerRecipe(new ItemStack(ModItems.SILK.get()), NonNullList.withSize(9,Ingredient.of(Items.STRING))),
        //        ModAdvancementGenerator.loom);
        //region fragment weapons
        //smithingTable(ModItems.PIG_FRAGMENT_A.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.PIG_FRAGMENT_1.get(), ModItems.PIG_FRAGMENT_2.get()
        //        ,pWriter);
        //smithingTable(ModItems.PIG_FRAGMENT_B.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.PIG_FRAGMENT_3.get(), ModItems.PIG_FRAGMENT_4.get()
        //        ,pWriter);
        //smithingTable(ModItems.PIG_FRAGMENT_C.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.PIG_FRAGMENT_1.get(), ModItems.PIG_FRAGMENT_3.get()
        //        ,pWriter);
        //smithingTable(ModItems.PIG_FRAGMENT_D.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.PIG_FRAGMENT_2.get(), ModItems.PIG_FRAGMENT_4.get()
        //        ,pWriter);
        //smithingTable(ModItems.PIG_SWORD.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.PIG_FRAGMENT_A.get(), ModItems.PIG_FRAGMENT_B.get()
        //        ,pWriter);
        //smithingTable(ModItems.PIG_CROSSBOW.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.PIG_FRAGMENT_C.get(), ModItems.PIG_FRAGMENT_D.get()
        //        ,pWriter);
        //Bee
        //smithingTable(ModItems.BEE_FRAGMENT_A.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.BEE_FRAGMENT_1.get(), ModItems.BEE_FRAGMENT_2.get()
        //        ,pWriter);
        //smithingTable(ModItems.BEE_FRAGMENT_B.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.BEE_FRAGMENT_3.get(), ModItems.BEE_FRAGMENT_4.get()
        //        ,pWriter);
        //smithingTable(ModItems.BEE_FRAGMENT_C.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.BEE_FRAGMENT_1.get(), ModItems.BEE_FRAGMENT_3.get()
        //        ,pWriter);
        //smithingTable(ModItems.BEE_FRAGMENT_D.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.BEE_FRAGMENT_2.get(), ModItems.BEE_FRAGMENT_4.get()
        //        ,pWriter);
        //smithingTable(ModItems.BEE_SWORD.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.BEE_FRAGMENT_A.get(), ModItems.BEE_FRAGMENT_B.get()
        //        ,pWriter);
        //smithingTable(ModItems.BEE_SHIELD.get(),
        //        ModItems.CHARM_FRAGMENT.get(), ModItems.BEE_FRAGMENT_C.get(), ModItems.BEE_FRAGMENT_D.get()
        //        ,pWriter);
        //
        //endregion fragment weapons
        //region charms
        Criterion<EffectsChangedTrigger.TriggerInstance> alchemy_criteria = EffectsChangedTrigger.TriggerInstance//has these effects
                .hasEffects(MobEffectsPredicate.Builder.effects().and(MobEffects.MOVEMENT_SPEED).and(MobEffects.DAMAGE_BOOST).and(MobEffects.REGENERATION).and(MobEffects.ABSORPTION));
        Criterion<BredAnimalsTrigger.TriggerInstance> fertility_criteria = BredAnimalsTrigger.TriggerInstance//bred these animals
                        .bredAnimals(EntityPredicate.Builder.entity().of(EntityType.SHEEP).of(EntityType.PIG).of(EntityType.COW).of(EntityType.BEE).of(EntityType.CHICKEN).of(EntityType.RABBIT));
        Criterion<PlayerTrigger.TriggerInstance> ambush_criteria = PlayerTrigger.TriggerInstance.avoidVibration();
        Criterion<InventoryChangeTrigger.TriggerInstance> vitality_criteria = has(Items.ENCHANTED_GOLDEN_APPLE);
        Criterion<KilledTrigger.TriggerInstance> detonation_criteria = KilledTrigger.TriggerInstance
                        .playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.CREEPER), DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_EXPLOSION)));
        Criterion<EffectsChangedTrigger.TriggerInstance> contraband_criteria = EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects()
                .and(MobEffects.CONDUIT_POWER).and(MobEffects.WITHER).and(MobEffects.LEVITATION).and(MobEffects.WEAKNESS).and(MobEffects.BLINDNESS));
        Criterion<InventoryChangeTrigger.TriggerInstance> warding_criteria = has(Items.RABBIT_FOOT);
        Criterion<KilledTrigger.TriggerInstance> dragon = KilledTrigger.TriggerInstance
                .playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.ENDER_DRAGON));
        Criterion<KilledTrigger.TriggerInstance> vanilla_totem = KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.EVOKER));
        //this needs to be reworked for the new system
        charmCrafting(ModItems.CHARM_ALCHEMY.get(),Items.BREWING_STAND,alchemy_criteria,pWriter);
        charmCrafting(ModItems.CHARM_AMBUSH.get(),Items.SCULK_SENSOR,ambush_criteria,pWriter);
        charmCrafting(ModItems.CHARM_FERTILITY.get(),Items.GOLDEN_CARROT,fertility_criteria,pWriter);
        charmCrafting(ModItems.CHARM_VITALITY.get(),Items.ENCHANTED_GOLDEN_APPLE,vitality_criteria,pWriter);
        charmCrafting(ModItems.CHARM_DETONATION.get(),Items.FIRE_CHARGE,detonation_criteria,pWriter);
        charmCrafting(ModItems.CHARM_CONTRABAND.get(),Items.EMERALD_BLOCK,contraband_criteria,pWriter);
        charmCrafting(ModItems.CHARM_WARDING.get(),Items.RABBIT_FOOT,warding_criteria,pWriter);
        charmCrafting(ModItems.CHARM_EXPERIENCE.get(),Items.EXPERIENCE_BOTTLE,dragon,pWriter);
        charmCrafting(Items.TOTEM_OF_UNDYING,ModItems.CHARM_FRAGMENT.get(),vanilla_totem,pWriter);
        //endregion charms
        //ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.CLOTH_BOOTS.get())
        //    .define('A',ModItems.SILK.get())
        //    .pattern("A A").pattern("A A")
        //    .unlockedBy("unlocked_silk",ModAdvancementGenerator.textilerCriteria)
        //    .save(pWriter);
        //temporary recipes
        //region void salt
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.VOID_SALT.get())
                .requires(ModItems.POTION_SALT.get(),9)
                .unlockedBy("",none)
                .save(pWriter);
        //endregion void salt
        nineBlockStorageRecipes(pWriter,RecipeCategory.MISC,ModItems.BISMUTH_NUGGET.get(),RecipeCategory.MISC,ModItems.BISMUTH_INGOT.get());
        //region flasks
        pWriter.accept(name("brewing/prime_flask"),
                new ShapedRecipe("prime_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(ModItems.COPPER_NUGGET.get()),
                                        'b',Ingredient.of(ModItems.PRIME_GLASS.get())),
                                " a ","b b"," b "),
                        ModItems.POTION_FLASK.get().getDefaultInstance()),
                null);
        pWriter.accept(name("brewing/vita_flask"),
                new ShapedRecipe("vita_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(ModItems.COPPER_NUGGET.get()),
                                        'b',Ingredient.of(ModItems.ANIMA_GLASS.get())),
                                " a ","b b"," b "),
                        ModItems.VITA_FLASK.get().getDefaultInstance()),
                null);
        pWriter.accept(name("brewing/impetus_flask"),
                new ShapedRecipe("impetus_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(ModItems.COPPER_NUGGET.get()),
                                        'b',Ingredient.of(ModItems.FERUS_GLASS.get())),
                                " a ","b b"," b "),
                        ModItems.IMPETUS_FLASK.get().getDefaultInstance()),
                null);
        pWriter.accept(name("brewing/magna_flask"),
                new ShapedRecipe("magna_flask", CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(ModItems.COPPER_NUGGET.get()),
                                        'b',Ingredient.of(ModItems.FORTIS_GLASS.get())),
                                " a ","b b"," b "),
                        ModItems.MAGNA_FLASK.get().getDefaultInstance()),
                null);
        //endregion flask
        //region flask recycling recipes
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.POTION_FLASK.get()),RecipeCategory.MISC,ModItems.PRIME_GLASS.get(),0f,400)
                .unlockedBy("has_flask",has(ModItems.POTION_FLASK.get()))
                .save(pWriter);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.VITA_FLASK.get()),RecipeCategory.MISC,ModItems.ANIMA_GLASS.get(),0f,400)
                .unlockedBy("has_flask",has(ModItems.VITA_FLASK.get()))
                .save(pWriter);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.IMPETUS_FLASK.get()),RecipeCategory.MISC,ModItems.FERUS_GLASS.get(),0f,400)
                .unlockedBy("has_flask",has(ModItems.POTION_FLASK.get()))
                .save(pWriter);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.MAGNA_FLASK.get()),RecipeCategory.MISC,ModItems.FORTIS_GLASS.get(),0f,400)
                .unlockedBy("has_flask",has(ModItems.MAGNA_FLASK.get()))
                .save(pWriter);
        //endregion flask recycling
        //region pouches
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.SEED_POUCH.get())
                .define('A', Items.LEATHER)
                .define('B',Items.STRING)
                .define('C',ModItems.COPPER_NUGGET.get())
                .pattern("BAC").pattern("A A").pattern("AAA")
                .unlockedBy("bundle",RecipeCraftedTrigger.TriggerInstance
                        .craftedItem(ResourceLocation.withDefaultNamespace(BundleRecipeProvider.getItemName(Items.BUNDLE))))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.DUST_POUCH.get())
                .define('A', Items.LEATHER)
                .define('B',Items.STRING)
                .define('C',Items.IRON_NUGGET)
                .pattern("BAC").pattern("A A").pattern("AAA")
                .unlockedBy("bundle",RecipeCraftedTrigger.TriggerInstance
                        .craftedItem(ResourceLocation.withDefaultNamespace(BundleRecipeProvider.getItemName(Items.BUNDLE))))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.GEM_POUCH.get())
                .define('A', Items.LEATHER)
                .define('B',Items.STRING)
                .define('C',Items.GOLD_NUGGET)
                .pattern("BAC").pattern("A A").pattern("AAA")
                .unlockedBy("bundle",RecipeCraftedTrigger.TriggerInstance
                        .craftedItem(ResourceLocation.withDefaultNamespace(BundleRecipeProvider.getItemName(Items.BUNDLE))))
                .save(pWriter);
        //endregion pouches
        //region lighter
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.LIGHTER.get())
                .define('A', Items.DAYLIGHT_DETECTOR)
                .define('B', Items.COMPARATOR)
                .define('C', Items.DISPENSER)
                .define('D', Items.LEVER)
                .define('E', ModItems.COPPER_NUGGET.get())
                .pattern("EAE").pattern("BCD").pattern("E E")
                .unlockedBy("",none)
                .save(pWriter);
        //endregion lighter
        //region machines
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModBlocks.MACHINE_EVAPORATOR.get())
                .define('C',Items.COPPER_INGOT)
                .define('K',Items.CAULDRON)
                .define('F',Items.SOUL_CAMPFIRE)
                .pattern("CKC")
                .pattern("CFC")
                .pattern("CCC")
                .unlockedBy("",none)
                .save(pWriter);
        //region foundry
        pWriter.accept(name("foundry"),
                new ShapedRecipe("foundry",CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.BLAST_FURNACE),
                                        'b',Ingredient.of(Items.CAULDRON),
                                        'c',Ingredient.of(Items.CUT_COPPER_STAIRS),
                                        'd',Ingredient.of(Items.BLAZE_ROD)),
                                "cbc","ada","cbc"),
                        ModBlocks.MACHINE_FOUNDRY.get().asItem().getDefaultInstance())
                ,null);
        pWriter.accept(name("foundry_addon"),
                new ShapedRecipe("foundry_addon",CraftingBookCategory.MISC,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.LEATHER),
                                        'b',Ingredient.of(Items.PISTON),
                                        'c',Ingredient.of(Items.CUT_COPPER_STAIRS),
                                        'd',Ingredient.of(Items.BREEZE_ROD)),
                                "cbc","ada","cbc"),
                        ModBlocks.ADDON_FOUNDRY.get().asItem().getDefaultInstance())
                ,null);
        //endregion foundry
        //region alembic
        pWriter.accept(name("alembic"),
                new ShapedRecipe("alembic",CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.IRON_INGOT),
                                        'b',Ingredient.of(ModItems.POTION_FLASK.get()),
                                        'c',Ingredient.of(Items.WATER_BUCKET),
                                        'd',Ingredient.of(Items.CUT_COPPER_SLAB)),
                                "bab","aca","ddd"),
                        ModBlocks.MACHINE_ALEMBIC.get().asItem().getDefaultInstance()),
                null);
        pWriter.accept(name("alembic_addon_1"),
                new ShapedRecipe("alembic_addon_1",CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.QUARTZ_SLAB),
                                        'b',Ingredient.of(ModItems.MAGNA_FLASK.get()),
                                        'c',Ingredient.of(Items.SEA_PICKLE),
                                        'd',Ingredient.of(Items.CUT_COPPER_SLAB),
                                        'e',Ingredient.of(Items.WATER_BUCKET)),
                                "aba","aca","ded"),
                        ModBlocks.ADDON_ALEMBIC.get().asItem().getDefaultInstance()),
                null);
        pWriter.accept(name("alembic_addon_2"),
                new ShapedRecipe("alembic_addon_2",CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.QUARTZ_SLAB),
                                        'b',Ingredient.of(ModItems.VITA_FLASK.get()),
                                        'c',Ingredient.of(Items.SEA_PICKLE),
                                        'd',Ingredient.of(Items.CUT_COPPER_SLAB),
                                        'e',Ingredient.of(Items.WATER_BUCKET)),
                                "aba","aca","ded"),
                        ModBlocks.ADDON_ALEMBIC.get().asItem().getDefaultInstance()),
                null);
        pWriter.accept(name("alembic_addon_3"),
                new ShapedRecipe("alembic_addon_3",CraftingBookCategory.REDSTONE,
                        ShapedRecipePattern.of(Map.of('a',Ingredient.of(Items.QUARTZ_SLAB),
                                        'b',Ingredient.of(ModItems.IMPETUS_FLASK.get()),
                                        'c',Ingredient.of(Items.SEA_PICKLE),
                                        'd',Ingredient.of(Items.CUT_COPPER_SLAB),
                                        'e',Ingredient.of(Items.WATER_BUCKET)),
                                "aba","aca","ded"),
                        ModBlocks.ADDON_ALEMBIC.get().asItem().getDefaultInstance()),
                null);
        //endregion alembic
        //region mincerator
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModBlocks.MACHINE_MINCERATOR.get())
                .pattern("AAA").pattern("AGA").pattern("ASA")
                .define('A', Items.CUT_COPPER_SLAB)
                .define('G', Items.GRINDSTONE)
                .define('S', Items.SMOKER)
                //unlocked by curing a zombie?
                .unlockedBy("",none)
                .save(pWriter);
        //endregion mincerator
        //region appliances
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.APPLIANCE_ICEBOX.get())
                .define('A',Items.CUT_COPPER_SLAB)
                .define('B',Items.IRON_DOOR)
                .define('C',Items.SNOW_BLOCK)
                .pattern("AAA").pattern("ACB").pattern("AAA")
                .unlockedBy("unlocked_fridge",has(Items.SNOWBALL))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModBlocks.APPLIANCE_WARDROBE.get())
                .define('A',ItemTags.WOODEN_SLABS)
                .define('B',Items.ARMOR_STAND)
                .define('C',ItemTags.WOODEN_DOORS)
                .pattern("AAA").pattern("ABC").pattern("AAA")
                .unlockedBy("unlocked_wardrobe",has(Items.ARMOR_STAND))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.APPLIANCE_ARCANE_CONSOLIDATOR.get())
                .pattern("ABA").pattern("BCB").pattern("ABA")
                .define('A', Items.CUT_COPPER_STAIRS)
                .define('B', Items.LAPIS_BLOCK)
                .define('C', Items.DISPENSER)
                .unlockedBy("",none)
                .save(pWriter);
        //endregion appliances
        //region chorus
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION,ModBlocks.CHORUS_TELEPORTER.get())
                .pattern("PEP").pattern("ENE").pattern("PEP")
                .define('P',Items.PURPUR_BLOCK)
                .define('E',Items.END_STONE_BRICK_STAIRS)
                .define('N',Items.ENDER_EYE)
                .unlockedBy("",none)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION,ModBlocks.CHORUS_CABLE.get())
                .pattern("CPC").pattern("CPC").pattern("CPC")
                .define('C', ItemTags.WOOL_CARPETS).define('P',Items.POPPED_CHORUS_FRUIT)
                .unlockedBy("",none)
                .save(pWriter);
        //endregion chorus
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModBlocks.CHARGE_CABLE.get())
                .pattern("WCW").pattern("WCW").pattern("WCW")
                .define('W',ItemTags.WOOL_CARPETS).define('C',Items.CUT_COPPER)
                .unlockedBy("has_copper",has(Items.CUT_COPPER))
                .save(pWriter);
        //endregion machines
        //region armor
        //region copper
        nineBlockStorageRecipes(pWriter,RecipeCategory.MISC,ModItems.COPPER_NUGGET.get(),RecipeCategory.MISC,Items.COPPER_INGOT);
        Criterion<InventoryChangeTrigger.TriggerInstance> hasCopper = has(Items.COPPER_INGOT);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.COPPER_AXE.get())
                .define('I', Items.COPPER_INGOT)
                .define('S', Items.STICK)
                .pattern("II").pattern("IS").pattern(" S")
                .unlockedBy("has_copper",hasCopper)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.COPPER_PICK.get())
                .define('I', Items.COPPER_INGOT)
                .define('S', Items.STICK)
                .pattern("III").pattern(" S ").pattern(" S ")
                .unlockedBy("has_copper",hasCopper)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.COPPER_SHOVEL.get())
                .define('I', Items.COPPER_INGOT)
                .define('S', Items.STICK)
                .pattern("I").pattern("S").pattern("S")
                .unlockedBy("has_copper",hasCopper)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.COPPER_SWORD.get())
                .define('I', Items.COPPER_INGOT)
                .define('S', Items.STICK)
                .pattern("I").pattern("I").pattern("S")
                .unlockedBy("has_copper",hasCopper)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.COPPER_HOE.get())
                .define('I', Items.COPPER_INGOT)
                .define('S', Items.STICK)
                .pattern("II").pattern(" S").pattern(" S")
                .unlockedBy("has_copper",hasCopper)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.COPPER_HELMET.get())
                .define('I', Items.COPPER_INGOT)
                .pattern("III").pattern("I I")
                .unlockedBy("has_copper",hasCopper)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.COPPER_CHESTPLATE.get())
                .define('I', Items.COPPER_INGOT)
                .pattern("I I").pattern("III").pattern("III")
                .unlockedBy("has_copper",hasCopper)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.COPPER_LEGGINGS.get())
                .define('I', Items.COPPER_INGOT)
                .pattern("III").pattern("I I").pattern("I I")
                .unlockedBy("has_copper",hasCopper)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.COPPER_BOOTS.get())
                .define('I', Items.COPPER_INGOT)
                .pattern("I I").pattern("I I")
                .unlockedBy("has_copper",hasCopper)
                .save(pWriter);
        //endregion copper
        //region bronze
        Criterion<InventoryChangeTrigger.TriggerInstance> hasBronze = has(ModItems.BRONZE_INGOT.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.BRONZE_AXE.get())
                .define('I', ModItems.BRONZE_INGOT.get())
                .define('S', Items.STICK)
                .pattern("II").pattern("IS").pattern(" S")
                .unlockedBy("has_bronze",hasBronze)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.BRONZE_PICK.get())
                .define('I', ModItems.BRONZE_INGOT.get())
                .define('S', Items.STICK)
                .pattern("III").pattern(" S ").pattern(" S ")
                .unlockedBy("has_bronze",hasBronze)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.BRONZE_SHOVEL.get())
                .define('I', ModItems.BRONZE_INGOT.get())
                .define('S', Items.STICK)
                .pattern("I").pattern("S").pattern("S")
                .unlockedBy("has_bronze",hasBronze)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.BRONZE_SWORD.get())
                .define('I', ModItems.BRONZE_INGOT.get())
                .define('S', Items.STICK)
                .pattern("I").pattern("I").pattern("S")
                .unlockedBy("has_bronze",hasBronze)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.BRONZE_HOE.get())
                .define('I', ModItems.BRONZE_INGOT.get())
                .define('S', Items.STICK)
                .pattern("II").pattern(" S").pattern(" S")
                .unlockedBy("has_bronze",hasBronze)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.BRONZE_HELMET.get())
                .define('I', ModItems.BRONZE_INGOT.get())
                .pattern("III").pattern("I I")
                .unlockedBy("has_bronze",hasBronze)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.BRONZE_CHESTPLATE.get())
                .define('I', ModItems.BRONZE_INGOT.get())
                .pattern("I I").pattern("III").pattern("III")
                .unlockedBy("has_bronze",hasBronze)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.BRONZE_LEGGINGS.get())
                .define('I', ModItems.BRONZE_INGOT.get())
                .pattern("III").pattern("I I").pattern("I I")
                .unlockedBy("has_bronze",hasBronze)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.BRONZE_BOOTS.get())
                .define('I', ModItems.BRONZE_INGOT.get())
                .pattern("I I").pattern("I I")
                .unlockedBy("has_bronze",hasBronze)
                .save(pWriter);
        //endregion bronze
        //region crystal armor(s)
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.PRIME_HELMET.get())
                .define('A',ModItems.PRIME_GLASS.get())
                .pattern("AAA").pattern("A A")
                .unlockedBy("",none)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.PRIME_CHESTPLATE.get())
                .define('A',ModItems.PRIME_GLASS.get())
                .pattern("A A").pattern("AAA").pattern("AAA")
                .unlockedBy("",none)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.PRIME_LEGGINGS.get())
                .define('A',ModItems.PRIME_GLASS.get())
                .pattern("AAA").pattern("A A").pattern("A A")
                .unlockedBy("",none)
                .save(pWriter);
        //
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.ANIMA_HELMET.get())
                .define('A',ModItems.ANIMA_GLASS.get())
                .pattern("AAA").pattern("A A")
                .unlockedBy("",none)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.ANIMA_CHESTPLATE.get())
                .define('A',ModItems. ANIMA_GLASS.get())
                .pattern("A A").pattern("AAA").pattern("AAA")
                .unlockedBy("",none)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.ANIMA_LEGGINGS.get())
                .define('A',ModItems.ANIMA_GLASS.get())
                .pattern("AAA").pattern("A A").pattern("A A")
                .unlockedBy("",none)
                .save(pWriter);
        //
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.FERUS_HELMET.get())
                .define('A',ModItems.FERUS_GLASS.get())
                .pattern("AAA").pattern("A A")
                .unlockedBy("",none)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.FERUS_CHESTPLATE.get())
                .define('A',ModItems. FERUS_GLASS.get())
                .pattern("A A").pattern("AAA").pattern("AAA")
                .unlockedBy("",none)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.FERUS_LEGGINGS.get())
                .define('A',ModItems.FERUS_GLASS.get())
                .pattern("AAA").pattern("A A").pattern("A A")
                .unlockedBy("",none)
                .save(pWriter);
        //
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.FORTIS_HELMET.get())
                .define('A',ModItems.FORTIS_GLASS.get())
                .pattern("AAA").pattern("A A")
                .unlockedBy("",none)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.FORTIS_CHESTPLATE.get())
                .define('A',ModItems. FORTIS_GLASS.get())
                .pattern("A A").pattern("AAA").pattern("AAA")
                .unlockedBy("",none)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.FORTIS_LEGGINGS.get())
                .define('A',ModItems.FORTIS_GLASS.get())
                .pattern("AAA").pattern("A A").pattern("A A")
                .unlockedBy("",none)
                .save(pWriter);
        //endregion crystal
        //region umbral
        //ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.UMBRA_HAT.get())
        //        .define('A',ModItems.UMBRA.get())
        //        .pattern("AAA").pattern("A A")
        //        .unlockedBy("unlocked_umbra", ModAdvancementGenerator.killed_phantom)
        //        .save(pWriter);
        //ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.UMBRA_TOP.get())
        //        .define('A',ModItems.UMBRA.get())
        //        .pattern("A A").pattern("AAA").pattern("AAA")
        //        .unlockedBy("unlocked_umbra", ModAdvancementGenerator.killed_phantom)
        //        .save(pWriter);
        //ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.UMBRA_BOTTOM.get())
        //        .define('A',ModItems.UMBRA.get())
        //        .pattern("AAA").pattern("A A").pattern("A A")
        //        .unlockedBy("unlocked_umbra", ModAdvancementGenerator.killed_phantom)
        //        .save(pWriter);
        //endregion umbral
        //region dragon armor
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.DRAGON_HELMET.get())
                .define('I', ModItems.DRAGON_SCALE.get())
                .define('C', Items.CHAINMAIL_HELMET)
                .pattern("ICI").pattern("I I")
                .unlockedBy("",none)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.DRAGON_CHESTPLATE.get())
                .define('I', ModItems.DRAGON_SCALE.get())
                .define('C', Items.CHAINMAIL_CHESTPLATE)
                .pattern("I I").pattern("ICI").pattern("III")
                .unlockedBy("",none)
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.DRAGON_LEGGINGS.get())
                .define('I', ModItems.DRAGON_SCALE.get())
                .define('C', Items.CHAINMAIL_LEGGINGS)
                .pattern("ICI").pattern("I I").pattern("I I")
                .unlockedBy("",none)
                .save(pWriter);
        //endregion dragon armor
        //endregion armor
        //ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.DRAGON_BOOTS.get())
        //        .define('I', ModItems.DRAGON_SCALE.get())
        //        .define('C', Items.CHAINMAIL_BOOTS)
        //        .pattern("ICI").pattern("I I")
        //        .unlockedBy("has_bronze",hasDragon)
        //        .save(pWriter);
        //region claws
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS,ModItems.COPPER_CLAW.get())
                .define('A',ModItems.CLAW.get())
                .define('B',Items.COPPER_INGOT)
                .define('C',ModItems.COPPER_NUGGET.get())
                .pattern("CBC").pattern("BAB").pattern(" BC")
                .unlockedBy("has_claw",has(ModItems.CLAW.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,ModItems.CRYSTAL_CLAW.get())
                .define('A',ModItems.CRYSTAL_CLAW.get())
                .define('B',ModItems.FERUS_GLASS.get())
                .define('C',Items.GOLD_INGOT)
                .unlockedBy("has_ferus",has(ModItems.FERUS_GLASS.get()));
        //endregion claws
    }

    private void smithingTable(Item result,Item template,Item item1, Item item2, RecipeOutput pWriter)
    {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(template),
                        Ingredient.of(item1),
                        Ingredient.of(item2),
                        RecipeCategory.MISC, result)
                .unlocks(item1.getDescriptionId(),has(item1))
                .unlocks(item2.getDescriptionId(),has(item2))
                .save(pWriter,result.getDescriptionId());
    }
    private void charmCrafting(Item result, Item catalyst, Criterion<?> criteria, RecipeOutput pWriter)
    {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.CHARM_FRAGMENT.get()),
                        Ingredient.of(catalyst),
                        Ingredient.of(ModItems.CHARM_FRAGMENT.get()),
                        RecipeCategory.MISC, result)
                .unlocks(result.getDescriptionId() + "criteria",criteria)
                .save(pWriter,result.getDescriptionId());
    }
    static Either<Ingredient,Holder<Potion>> map(Object input)
    {//no more funny generics :(
        if (input instanceof Ingredient)
            return Either.left((Ingredient)input);
        return Either.right((Holder<Potion>)input);
    }
    static ResourceLocation name(String string)
    {
        return ResourceLocation.fromNamespaceAndPath(Patina.MODID,string);
    }
}

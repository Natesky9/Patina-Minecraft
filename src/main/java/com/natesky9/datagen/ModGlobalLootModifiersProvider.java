package com.natesky9.datagen;

import com.natesky9.patina.Loot.AddItemModifier;
import com.natesky9.patina.Loot.AddMinMax;
import com.natesky9.patina.Loot.AddSingleItemModifier;
import com.natesky9.patina.Loot.ReplaceItemModifier;
import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModEntityTypes;
import com.natesky9.patina.init.ModItems;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output,CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, Patina.MODID,lookup);
    }

    @Override
    protected void start(HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<Enchantment> enchantmentLookup = provider.lookupOrThrow(Registries.ENCHANTMENT);

        //region wither fragments
        add("add_wither_fragment_1", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition(BuiltInLootTables.NETHER_BRIDGE.location())
        }, ModItems.WITHER_FRAGMENT_1.get(),.1f));
        add("add_wither_fragment_2", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(BuiltInLootTables.RUINED_PORTAL.location()).build()

        }, ModItems.WITHER_FRAGMENT_1.get(),.5f));
        add("add_wither_fragment_3",new AddSingleItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().of(EntityType.WITHER_SKELETON))
                        .and(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.ATTACKER,
                        EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment()
                                .mainhand(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS,
                                        ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(
                                                enchantmentLookup.getOrThrow(Enchantments.SMITE),
                                                MinMaxBounds.Ints.ANY)))))))).build()
        },ModItems.WITHER_FRAGMENT_3.get()));
        add("add_wither_fragment_4",new AddSingleItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().of(EntityType.WITHER_SKELETON))
                        .and(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.ATTACKER,
                        EntityPredicate.Builder.entity().of(EntityType.PIGLIN))).build()
        },ModItems.WITHER_FRAGMENT_4.get()));
        //endregion wither fragments
        //region bee fragments
        ItemEnchantmentsPredicate pred = ItemEnchantmentsPredicate.enchantments(List.of(
                new EnchantmentPredicate(enchantmentLookup.getOrThrow(Enchantments.SILK_TOUCH),
                        MinMaxBounds.Ints.ANY)
        ));
        //TODO: fix this mess lol
        add("add_bee_fragment_1", new AddItemModifier(new LootItemCondition[]
                {
                        new LootTableIdCondition.Builder(Blocks.BEE_NEST.getLootTable().location()).build(),
                        hasSilkTouch(provider).invert().build()
                }


        ,ModItems.BEE_FRAGMENT_1.get(),.1f));
        add("add_bee_fragment_2", new AddItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().of(EntityType.BEE))
                        .and(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.ATTACKER,
                                EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment()
                                        .mainhand(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS,
                                                ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(
                                                        enchantmentLookup.getOrThrow(Enchantments.BANE_OF_ARTHROPODS),
                                                        MinMaxBounds.Ints.ANY))))))))
                        .build()
        },ModItems.BEE_FRAGMENT_2.get(), .1f));
        add("add_bee_fragment_3", new AddSingleItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().of(ModEntityTypes.BEE_BOSS.get())).build()
        },ModItems.BEE_FRAGMENT_3.get()));
        add("add_bee_fragment_4",new AddSingleItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.ATTACKER,
                                EntityPredicate.Builder.entity().of(EntityType.BEE)).build()
        },ModItems.BEE_FRAGMENT_4.get()));
        //endregion bee fragments
        //region pig fragments
        add("add_pig_fragment_1", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition(BuiltInLootTables.BASTION_OTHER.location())
        },ModItems.PIG_FRAGMENT_1.get(), .25f));
        add("add_pig_fragment_2", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition(ResourceLocation.withDefaultNamespace("gameplay/piglin_bartering"))
        },ModItems.PIG_FRAGMENT_2.get(), .1f));
        add("add_pig_fragment_3",new AddSingleItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.PIGLIN))
                        .and(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.ATTACKER,
                                EntityPredicate.Builder.entity().of(EntityType.WITHER_SKELETON))).build()
        },ModItems.PIG_FRAGMENT_3.get()));
        add("add_pig_fragment_4", new AddItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().of(EntityType.PIGLIN_BRUTE))
                        .build()
        },ModItems.PIG_FRAGMENT_4.get(), .1f));
        //endregion pig fragments

        add("turtle_shell_from_turtle",new AddSingleItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().of(EntityType.TURTLE)
                                .flags(EntityFlagsPredicate.Builder.flags().setIsBaby(false))).build()
        }, Items.TURTLE_HELMET));
        add("dragon_scale_from_dragon",new AddMinMax(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/ender_dragon")).build()
        },4,8,ModItems.DRAGON_SCALE.get()));
        //
        add("crab_claw_from_fishing",new ReplaceItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("gameplay/fishing/treasure")).build()
        },ModItems.CLAW.get()));
    }
    LootItemCondition.Builder hasSilkTouch(HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = provider.lookupOrThrow(Registries.ENCHANTMENT);
        return MatchTool.toolMatches(
                ItemPredicate.Builder.item()
                        .withSubPredicate(
                                ItemSubPredicates.ENCHANTMENTS,
                                ItemEnchantmentsPredicate.enchantments(
                                        List.of(new EnchantmentPredicate(registrylookup.getOrThrow(Enchantments.SILK_TOUCH),
                                                MinMaxBounds.Ints.atLeast(1)))
                                )
                        )
        );
    }
}

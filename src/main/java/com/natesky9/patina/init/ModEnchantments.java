package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.item.enchantment.effects.RemoveBinomial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS
            = DeferredRegister.create(Registries.ENCHANTMENT, Patina.MODID);


    //region enchants
    public static final ResourceKey<Enchantment> CURSE_ENVY = key("envy");
    public static final ResourceKey<Enchantment> CURSE_GLUTTONY = key("gluttony");
    public static final ResourceKey<Enchantment> CURSE_PRIDE = key("pride");
    public static final ResourceKey<Enchantment> CURSE_GREED = key("greed");
    public static final ResourceKey<Enchantment> CURSE_SLOTH = key("sloth");
    public static final ResourceKey<Enchantment> CURSE_LUST = key("lust");
    public static final ResourceKey<Enchantment> CURSE_WRATH = key("wrath");

    public static final ResourceKey<Enchantment> BLESSING_ENVY = key("strife");
    public static final ResourceKey<Enchantment> BLESSING_GLUTTONY = key("plethora");
    public static final ResourceKey<Enchantment> BLESSING_PRIDE = key("humility");
    public static final ResourceKey<Enchantment> BLESSING_GREED = key("avarice");
    public static final ResourceKey<Enchantment> BLESSING_SLOTH = key("ambition");
    public static final ResourceKey<Enchantment> BLESSING_LUST = key("coercion");
    public static final ResourceKey<Enchantment> BLESSING_WRATH = key("retribution");

    public static final ResourceKey<Enchantment> BLESSING_VANISHING = key("soulbound");
    //endregion enchants
    private static ResourceKey<Enchantment> key(String string) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Patina.MODID,string));
    }
    public static void bootstrap(BootstrapContext<Enchantment> context)
    {
        HolderGetter<DamageType> holderDamageType = context.lookup(Registries.DAMAGE_TYPE);
        HolderGetter<Enchantment> holderEnchantment = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> holderItem = context.lookup(Registries.ITEM);
        HolderGetter<Block> holderBlock = context.lookup(Registries.BLOCK);
        HolderGetter<DataComponentType<?>> holderType = context.lookup(Registries.DATA_COMPONENT_TYPE);
        //
        register(context, CURSE_ENVY,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                10,1, Enchantment.constantCost(1),
                                Enchantment.constantCost(1),
                                1, EquipmentSlotGroup.ARMOR))
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.CURSE_EXCLUSIVE)));
        //
        register(context, CURSE_GLUTTONY,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                10,1,Enchantment.dynamicCost(1,1),
                                Enchantment.constantCost(1),
                                1, EquipmentSlotGroup.ARMOR))
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.CURSE_EXCLUSIVE)));
        //
        register(context, CURSE_GREED,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                10,1,Enchantment.constantCost(1),
                                Enchantment.constantCost(1),
                                1,EquipmentSlotGroup.ARMOR))
                        //TODO inventory size attribute
                        //.withEffect(EnchantmentEffectComponents.ATTRIBUTES,
                        //        new EnchantmentAttributeEffect(ResourceLocation.fromNamespaceAndPath(
                        //                Patina.MODID,"enchantment.greed"),
                        //                ModAttributes.GREED.getHolder().get(), LevelBasedValue.perLevel(1),
                        //                AttributeModifier.Operation.ADD_VALUE))
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.CURSE_EXCLUSIVE)));
        //
        register(context, CURSE_LUST,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                10,1,Enchantment.constantCost(1),
                                Enchantment.constantCost(1),
                                1,EquipmentSlotGroup.ARMOR))
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.CURSE_EXCLUSIVE)));
        register(context, CURSE_SLOTH,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                10,1,Enchantment.constantCost(1),
                                Enchantment.constantCost(1),
                                1,EquipmentSlotGroup.ARMOR))
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.CURSE_EXCLUSIVE)));
        register(context, CURSE_WRATH,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                10,1,Enchantment.constantCost(1),
                                Enchantment.constantCost(1),
                                1,EquipmentSlotGroup.ARMOR))
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.CURSE_EXCLUSIVE)));
        //curse of pride causes equipment to take extra durability damage
        register(context, CURSE_PRIDE,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(Tags.Items.ENCHANTABLES),
                                10,1,Enchantment.constantCost(1),
                                Enchantment.constantCost(1),
                                1,EquipmentSlotGroup.ANY))
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.CURSE_EXCLUSIVE))
                        .withEffect(//apply to armor
                                EnchantmentEffectComponents.ITEM_DAMAGE,
                                new RemoveBinomial(new LevelBasedValue.Constant(-1)),
                                MatchTool.toolMatches(ItemPredicate.Builder.item().of(holderItem, ItemTags.ARMOR_ENCHANTABLE))
                        )
                        .withEffect(//apply to items
                                EnchantmentEffectComponents.ITEM_DAMAGE,
                                new RemoveBinomial(new LevelBasedValue.Constant(-1)),
                                InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(holderItem, ItemTags.ARMOR_ENCHANTABLE)))
                        ));
        register(context, BLESSING_PRIDE,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(Tags.Items.ENCHANTABLES),
                        10,100,Enchantment.constantCost(60),
                        Enchantment.constantCost(60),
                        60,EquipmentSlotGroup.ANY))
                        .withEffect(
                                EnchantmentEffectComponents.ITEM_DAMAGE,
                                new RemoveBinomial(new LevelBasedValue.Fraction(LevelBasedValue.perLevel(100,1),LevelBasedValue.constant(100))),
                                MatchTool.toolMatches(ItemPredicate.Builder.item().of(holderItem, Tags.Items.ENCHANTABLES))
                        ));
        register(context, BLESSING_VANISHING,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(Tags.Items.ENCHANTABLES),
                        1,1,Enchantment.constantCost(10),
                        Enchantment.constantCost(1),
                        30,EquipmentSlotGroup.ANY))
                        //.withEffect(ModDataComponents.KEEP_INVENTORY_ITEM.value())
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.VANISHING_EXCLUSIVE)));
        register(context, BLESSING_GLUTTONY,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(Tags.Items.ENCHANTABLES),
                                1,1,Enchantment.constantCost(60),
                                Enchantment.constantCost(60),
                                60,EquipmentSlotGroup.ARMOR))
                        .withEffect(
                                EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID,"enchantment.gluttony"),
                                        ModAttributes.GLUTTONY_BLESSING,
                                        LevelBasedValue.perLevel(1),
                                        AttributeModifier.Operation.ADD_VALUE
                                )
                        ));
    }
    //

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> resourceKey, Enchantment.Builder builder) {
        context.register(resourceKey, builder.build(resourceKey.location()));
    }
}

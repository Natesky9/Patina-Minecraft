package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
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
        register(context, CURSE_ENVY,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                10,1, Enchantment.constantCost(1),
                                Enchantment.constantCost(1),
                                1, EquipmentSlotGroup.ARMOR))
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.CURSE_EXCLUSIVE)));
        //
        register(context, CURSE_GLUTTONY,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                10,1,Enchantment.dynamicCost(1,1),
                                Enchantment.constantCost(1),
                                1, EquipmentSlotGroup.ARMOR))
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.CURSE_EXCLUSIVE)));
        //
        register(context, CURSE_GREED,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
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
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                10,1,Enchantment.constantCost(1),
                                Enchantment.constantCost(1),
                                1,EquipmentSlotGroup.ARMOR))
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.CURSE_EXCLUSIVE)));
        register(context, CURSE_SLOTH,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.MINING_ENCHANTABLE),
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
        register(context, CURSE_PRIDE,
                Enchantment.enchantment(Enchantment.definition(holderItem.getOrThrow(ItemTags.LEG_ARMOR_ENCHANTABLE),
                                10,1,Enchantment.constantCost(1),
                                Enchantment.constantCost(1),
                                1,EquipmentSlotGroup.ARMOR))
                        .exclusiveWith(holderEnchantment.getOrThrow(ModTags.CURSE_EXCLUSIVE)));
    }
    //

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> resourceKey, Enchantment.Builder builder) {
        context.register(resourceKey, builder.build(resourceKey.location()));
    }
}

package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.neoforged.neoforge.common.Tags;

import java.util.EnumMap;

public class ModArmor {
    //
    //protection enum maps
    private static final EnumMap<ArmorType, Integer> simple = Util.make(new EnumMap<>(ArmorType.class), value -> {
        value.put(ArmorType.HELMET,1);
        value.put(ArmorType.CHESTPLATE,3);
        value.put(ArmorType.LEGGINGS,2);
        value.put(ArmorType.BOOTS,1);
        value.put(ArmorType.BODY,3);
    });
    private static final EnumMap<ArmorType, Integer> standard = Util.make(new EnumMap<>(ArmorType.class), value -> {
        value.put(ArmorType.HELMET,2);
        value.put(ArmorType.CHESTPLATE,6);
        value.put(ArmorType.LEGGINGS,5);
        value.put(ArmorType.BOOTS,2);
        value.put(ArmorType.BODY,5);
    });
    private static final EnumMap<ArmorType, Integer> strong = Util.make(new EnumMap<>(ArmorType.class), value -> {
        value.put(ArmorType.HELMET,3);
        value.put(ArmorType.CHESTPLATE,8);
        value.put(ArmorType.LEGGINGS,6);
        value.put(ArmorType.BOOTS,3);
        value.put(ArmorType.BODY,11);
    });
    private static final EnumMap<ArmorType,Integer> advanced = Util.make(new EnumMap<>(ArmorType.class), value ->
    {
        value.put(ArmorType.HELMET,4);
        value.put(ArmorType.CHESTPLATE,9);
        value.put(ArmorType.LEGGINGS,7);
        value.put(ArmorType.BOOTS,4);
        value.put(ArmorType.BODY,12);
    });
    //
    public static final ArmorMaterial COPPER = new ArmorMaterial(12,
            simple,
            8, SoundEvents.ARMOR_EQUIP_CHAIN, 0, 0, Tags.Items.INGOTS_COPPER,name("copper"));
    public static final ArmorMaterial CRYSTAL = new ArmorMaterial(20,
            standard,
            20, SoundEvents.ARMOR_EQUIP_DIAMOND, 0, 0, Tags.Items.GEMS_AMETHYST,
            name("crystal"));
    //
    static ResourceKey<EquipmentAsset> name(String name)
    {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(Patina.MODID,name));
    }

    //enum maps
    //public static final Holder<ArmorMaterial> BRONZE = register("bronze", standard, 24, Holder.direct(SoundEvents.AMETHYST_BLOCK_CHIME),
    //        1f, 1f, () -> Ingredient.of(ModItems.BISMUTH_INGOT.get()));
    //public static final Holder<ArmorMaterial> CRYSTAL = register("crystal_prime", strong, 30, SoundEvents.ARMOR_EQUIP_DIAMOND,
    //        1f, 0f, () -> Ingredient.of(ModItems.PRIME_GLASS.get()));
    //public static final Holder<ArmorMaterial> CRYSTAL_FORTIS = register("crystal_fortis", strong, 30, SoundEvents.ARMOR_EQUIP_DIAMOND,
    //        1f, 0f, () -> Ingredient.of(ModItems.FORTIS_GLASS.get()));
    //public static final Holder<ArmorMaterial> CRYSTAL_FERUS = register("crystal_ferus", strong, 30, SoundEvents.ARMOR_EQUIP_DIAMOND,
    //        1f, 0f, () -> Ingredient.of(ModItems.FERUS_GLASS.get()));
    //public static final Holder<ArmorMaterial> CRYSTAL_ANIMA = register("crystal_anima", strong, 30, SoundEvents.ARMOR_EQUIP_DIAMOND,
    //        1f, 0f, () -> Ingredient.of(ModItems.ANIMA_GLASS.get()));

}

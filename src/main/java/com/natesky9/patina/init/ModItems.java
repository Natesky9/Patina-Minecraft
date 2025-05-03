package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Patina.MODID);

    //region copper
    public static final DeferredItem<Item> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new SwordItem(ModTiers.COPPER, 3.0F, -2.4F,
                    baseProperties("copper_sword")));
    public static final DeferredItem<Item> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ModTiers.COPPER, 5.0F, -3.0F,
                    baseProperties("copper_axe")));
    public static final DeferredItem<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ModTiers.COPPER, 1.5F, -3F,
                    baseProperties("copper_shovel")));
    public static final DeferredItem<Item> COPPER_PICK = ITEMS.register("copper_pick",
            () -> new PickaxeItem(ModTiers.COPPER, 1.0F, -2.8F,
                    baseProperties("copper_pick")));
    public static final DeferredItem<Item> COPPER_HOE = ITEMS.register("copper_hoe",
            (properties) -> new HoeItem(ModTiers.COPPER,-1F, -1F,
                    baseProperties("copper_hoe")));

    public static final DeferredItem<Item> COPPER_HELMET = ITEMS.register("copper_helmet",
            () -> new ArmorItem(ModArmor.COPPER, ArmorType.HELMET,
                    baseProperties("copper_helmet")));
    public static final DeferredItem<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate",
            () -> new ArmorItem(ModArmor.COPPER, ArmorType.HELMET,
                    baseProperties("copper_helmet")));
    public static final DeferredItem<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings",
            () -> new ArmorItem(ModArmor.COPPER, ArmorType.HELMET,
                    baseProperties("copper_helmet")));
    public static final DeferredItem<Item> COPPER_BOOTS = ITEMS.register("copper_boots",
            () -> new ArmorItem(ModArmor.COPPER, ArmorType.HELMET,
            baseProperties("copper_helmet")));
    public static final DeferredItem<Item> COPPER_SHIELD = ITEMS.register("copper_shield",
            () -> new ArmorItem(ModArmor.COPPER, ArmorType.HELMET,
            baseProperties("copper_helmet")));
    //endregion copper tools
    //region crystal
    public static final DeferredItem<Item> CRYSTAL_SWORD = ITEMS.register("crystal_sword",
            () -> new SwordItem(ModTiers.CRYSTAL,3,-1.8F,
                    baseProperties("crystal_sword")));
    public static final DeferredItem<Item> CRYSTAL_AXE = ITEMS.register("crystal_axe",
            () -> new SwordItem(ModTiers.CRYSTAL,3,-1.8F,
                    baseProperties("crystal_axe")));
    public static final DeferredItem<Item> CRYSTAL_SHOVEL = ITEMS.register("crystal_shovel",
            () -> new SwordItem(ModTiers.CRYSTAL,3,-1.8F,
                    baseProperties("crystal_shovel")));
    public static final DeferredItem<Item> CRYSTAL_PICK = ITEMS.register("crystal_pick",
            () -> new SwordItem(ModTiers.CRYSTAL,3,-1.8F,
                    baseProperties("crystal_pick")));
    public static final DeferredItem<Item> CRYSTAL_HOE = ITEMS.register("crystal_hoe",
            () -> new SwordItem(ModTiers.CRYSTAL,3,-1.8F,
                    baseProperties("crystal_hoe")));

    public static final DeferredItem<Item> PRIME_HELMET = ITEMS.register("crystal_prime_helmet",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.HELMET,
                    baseProperties("crystal_prime_helmet")));
    public static final DeferredItem<Item> PRIME_CHESTPLATE = ITEMS.register("crystal_prime_chestplate",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.CHESTPLATE,
                    baseProperties("crystal_prime_chestplate")));
    public static final DeferredItem<Item> PRIME_LEGGINGS = ITEMS.register("crystal_prime_leggings",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.LEGGINGS,
                    baseProperties("crystal_prime_leggings")));

    public static final DeferredItem<Item> FERUS_HELMET = ITEMS.register("crystal_ferus_helmet",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.HELMET,
                    baseProperties("crystal_ferus_helmet")));
    public static final DeferredItem<Item> FERUS_CHESTPLATE = ITEMS.register("crystal_ferus_chestplate",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.CHESTPLATE,
                    baseProperties("crystal_ferus_chestplate")));
    public static final DeferredItem<Item> FERUS_LEGGINGS = ITEMS.register("crystal_ferus_leggings",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.LEGGINGS,
                    baseProperties("crystal_ferus_leggings")));

    public static final DeferredItem<Item> ANIMA_HELMET = ITEMS.register("crystal_anima_helmet",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.HELMET,
                    baseProperties("crystal_anima_helmet")));
    public static final DeferredItem<Item> ANIMA_CHESTPLATE = ITEMS.register("crystal_anima_chestplate",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.CHESTPLATE,
                    baseProperties("crystal_anima_chestplate")));
    public static final DeferredItem<Item> ANIMA_LEGGINGS = ITEMS.register("crystal_anima_leggings",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.LEGGINGS,
                    baseProperties("crystal_anima_leggings")));

    public static final DeferredItem<Item> FORTIS_HELMET = ITEMS.register("crystal_fortis_helmet",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.HELMET,
                    baseProperties("crystal_fortis_helmet")));
    public static final DeferredItem<Item> FORTIS_CHESTPLATE = ITEMS.register("crystal_fortis_chestplate",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.CHESTPLATE,
                    baseProperties("crystal_fortis_chestplate")));
    public static final DeferredItem<Item> FORTIS_LEGGINGS = ITEMS.register("crystal_fortis_leggings",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.LEGGINGS,
                    baseProperties("crystal_fortis_leggings")));
    //endregion crystal

    //region materials
    public static final DeferredItem<Item> POTION_SALT = ITEMS.register("potion_salt",
            () -> new Item(baseProperties("potion_salt")));
    public static final DeferredItem<Item> VOID_SALT = ITEMS.register("void_salt",
            () -> new Item(baseProperties("void_salt")));
    public static final DeferredItem<Item> KERATIN = ITEMS.register("kraten",
            () -> new Item(baseProperties("kraten")));

    public static final DeferredItem<Item> PRIME_CRYSTAL = ITEMS.register("crystal_prime",
            () -> new Item(baseProperties("crystal_prime")));
    public static final DeferredItem<Item> ANIMA_CRYSTAL = ITEMS.register("crystal_anima",
            () -> new Item(baseProperties("crystal_anima")));
    public static final DeferredItem<Item> FERUS_CRYSTAL = ITEMS.register("crystal_ferus",
            () -> new Item(baseProperties("crystal_ferus")));
    public static final DeferredItem<Item> FORTIS_CRYSTAL = ITEMS.register("crystal_fortis",
            () -> new Item(baseProperties("crystal_fortis")));
    public static final DeferredItem<Item> MALACHITE = ITEMS.register("malachite",
            () -> new Item(baseProperties("malachite")));
    public static final DeferredItem<Item> WYRE = ITEMS.register("wyre",
            () -> new Item(baseProperties("wyre").stacksTo(96)));


    public static final DeferredItem<Item> SILK = ITEMS.register("seelk",
            () -> new Item(baseProperties("seelk")));
    public static final DeferredItem<Item> UMBRA = ITEMS.register("umbra",
            () -> new Item(baseProperties("umbra")));

    public static final DeferredItem<Item> BISMUTH_INGOT = ITEMS.register("bismuth_ingot",
            () -> new Item(baseProperties("bismuth_ingot")));
    public static final DeferredItem<Item> BISMUTH_NUGGET = ITEMS.register("bismuth_nugget",
            () -> new Item(baseProperties("bismuth_nugget")));
    public static final DeferredItem<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget",
            () -> new Item(baseProperties("netherite_nugget")));
    public static final DeferredItem<Item> BRON_INGOT = ITEMS.register("bron_ingot",
            () -> new Item(baseProperties("bronze_ingot")));
    public static final DeferredItem<Item> CHROMATIC_SCALE = ITEMS.register("chromatic_scale",
            () -> new Item(baseProperties("chromatic_scale")));
    //endregion materials

    //region tools
    public static final DeferredItem<Item> FLASK_CRYSTAL = ITEMS.register("flask_crystal",
            () -> new Item(baseProperties("flask_crystal").stacksTo(1)));
    public static final DeferredItem<Item> FLASK_FERUS = ITEMS.register("flask_ferus",
            () -> new Item(baseProperties("flask_ferus").stacksTo(1)));
    public static final DeferredItem<Item> FLASK_ANIMA = ITEMS.register("flask_anima",
            () -> new Item(baseProperties("flask_anima").stacksTo(1)));
    public static final DeferredItem<Item> FLASK_MAGNA = ITEMS.register("flask_fortis",
            () -> new Item(baseProperties("flask_fortis").stacksTo(1)));
    public static final DeferredItem<Item> FLASK_ETERNA = ITEMS.register("flask_eterna",
            () -> new Item(baseProperties("flask_eterna").stacksTo(1)));

    static ItemAttributeModifiers small_claw = ItemAttributeModifiers.builder()
            .add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(name("reach"),1, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.OFFHAND).build();
    static ItemAttributeModifiers medium_claw = ItemAttributeModifiers.builder()
            .add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(name("reach"),2, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.OFFHAND).build();
    static ItemAttributeModifiers large_claw = ItemAttributeModifiers.builder()
            .add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(name("reach"), 2, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.OFFHAND)
            .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(name("range"),1, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.OFFHAND).build();

    public static final DeferredItem<Item> CRAB_CLAW = ITEMS.register("crab_claw",
            () -> new Item(baseProperties("crab_claw").attributes(small_claw).stacksTo(1)));
    public static final DeferredItem<Item> COPPER_CLAW = ITEMS.register("copper_claw",
            () -> new Item(baseProperties("copper_claw").attributes(medium_claw)));
    public static final DeferredItem<Item> DRAGON_CLAW = ITEMS.register("dragon_claw",
            () -> new Item(baseProperties("dragon_claw").attributes(large_claw).stacksTo(1)));
    //endregion tools


    static ResourceLocation name(String name)
    {
        return ResourceLocation.fromNamespaceAndPath(Patina.MODID, name);
    }
    static Item.Properties baseProperties(String name)
    {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Patina.MODID,name)));
    }

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
        //eventBus.register(ITEMS);
    }
}

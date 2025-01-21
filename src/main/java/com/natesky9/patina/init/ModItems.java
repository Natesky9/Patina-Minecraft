package com.natesky9.patina.init;

import com.natesky9.patina.Item.*;
import com.natesky9.patina.Item.flasks.EternaFlask;
import com.natesky9.patina.Item.flasks.ImpetusFlask;
import com.natesky9.patina.Item.flasks.PotionFlaskItem;
import com.natesky9.patina.Item.flasks.SemiVitaFlask;
import com.natesky9.patina.Item.pouches.DustPouchItem;
import com.natesky9.patina.Item.pouches.GemPouchItem;
import com.natesky9.patina.Item.pouches.LighterItem;
import com.natesky9.patina.Item.pouches.SeedPouchItem;
import com.natesky9.patina.Patina;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Patina.MODID);
    //add items here
    //region shards
    //buzz
    public static final RegistryObject<Item> BEE_FRAGMENT_1= ITEMS.register("bee_fragment_1",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEE_FRAGMENT_2 = ITEMS.register("bee_fragment_2",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEE_FRAGMENT_3 = ITEMS.register("bee_fragment_3",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEE_FRAGMENT_4 = ITEMS.register("bee_fragment_4",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEE_FRAGMENT_A = ITEMS.register("bee_fragment_a",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEE_FRAGMENT_B = ITEMS.register("bee_fragment_b",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEE_FRAGMENT_C = ITEMS.register("bee_fragment_c",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEE_FRAGMENT_D = ITEMS.register("bee_fragment_d",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEE_SWORD = ITEMS.register("bee_sword",
            () -> (new BeeWeaponItem(ModTiers.BOSS,2,-1.8F,
                    new Item.Properties()
                            .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                            .rarity(Rarity.RARE))));
    public static final RegistryObject<Item> BEE_SHIELD = ITEMS.register("bee_shield",
            () -> new BeeShieldItem(new Item.Properties().rarity(Rarity.RARE)
                    .durability(333)));
    //spider
    public static final RegistryObject<Item> SPIDER_FRAGMENT_1 = ITEMS.register("spider_fragment_1",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPIDER_FRAGMENT_2 = ITEMS.register("spider_fragment_2",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPIDER_FRAGMENT_3 = ITEMS.register("spider_fragment_3",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPIDER_FRAGMENT_4 = ITEMS.register("spider_fragment_4",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPIDER_FRAGMENT_A = ITEMS.register("spider_fragment_a",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPIDER_FRAGMENT_B = ITEMS.register("spider_fragment_b",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPIDER_FRAGMENT_C = ITEMS.register("spider_fragment_c",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPIDER_FRAGMENT_D = ITEMS.register("spider_fragment_d",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPIDER_WEAPON = ITEMS.register("spider_weapon",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPIDER_ARMOR = ITEMS.register("spider_armor",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    //oink
    public static final RegistryObject<Item> PIG_FRAGMENT_1= ITEMS.register("pig_fragment_1",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PIG_FRAGMENT_2 = ITEMS.register("pig_fragment_2",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PIG_FRAGMENT_3 = ITEMS.register("pig_fragment_3",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PIG_FRAGMENT_4 = ITEMS.register("pig_fragment_4",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PIG_FRAGMENT_A = ITEMS.register("pig_fragment_a",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PIG_FRAGMENT_B = ITEMS.register("pig_fragment_b",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PIG_FRAGMENT_C = ITEMS.register("pig_fragment_c",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PIG_FRAGMENT_D = ITEMS.register("pig_fragment_d",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PIG_SWORD = ITEMS.register("pig_sword",
            () -> (new PigWeaponItem(ModTiers.BOSS,4,-3.0F,
                    new Item.Properties()
                            .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                            .rarity(Rarity.RARE))));
    public static final RegistryObject<Item> PIG_CROSSBOW = ITEMS.register("pig_crossbow",
            () -> new PigCrossbowItem(new Item.Properties()
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                    .rarity(Rarity.RARE).stacksTo(1)));
    //bear

    public static final RegistryObject<Item> BEAR_FRAGMENT_1 = ITEMS.register("bear_fragment_1",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEAR_FRAGMENT_2 = ITEMS.register("bear_fragment_2",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEAR_FRAGMENT_3 = ITEMS.register("bear_fragment_3",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEAR_FRAGMENT_4 = ITEMS.register("bear_fragment_4",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEAR_FRAGMENT_A = ITEMS.register("bear_fragment_a",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEAR_FRAGMENT_B = ITEMS.register("bear_fragment_b",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEAR_FRAGMENT_C = ITEMS.register("bear_fragment_c",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEAR_FRAGMENT_D = ITEMS.register("bear_fragment_d",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEAR_WEAPON = ITEMS.register("bear_weapon",
            () -> new Item(new Item.Properties()
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                    .rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BEAR_ARMOR = ITEMS.register("bear_armor",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    //spooky
    public static final RegistryObject<Item>  WITHER_FRAGMENT_1 = ITEMS.register("wither_fragment_1",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item>  WITHER_FRAGMENT_2 = ITEMS.register("wither_fragment_2",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item>  WITHER_FRAGMENT_3 = ITEMS.register("wither_fragment_3",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item>  WITHER_FRAGMENT_4 = ITEMS.register("wither_fragment_4",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item>  WITHER_FRAGMENT_A = ITEMS.register("wither_fragment_a",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item>  WITHER_FRAGMENT_B = ITEMS.register("wither_fragment_b",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item>  WITHER_FRAGMENT_C = ITEMS.register("wither_fragment_c",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item>  WITHER_FRAGMENT_D = ITEMS.register("wither_fragment_d",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item>  WITHER_STAFF = ITEMS.register("wither_staff",
            () -> new Item(new Item.Properties()
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                    .rarity(Rarity.RARE)));
    public static final RegistryObject<Item>  WITHER_WINGS = ITEMS.register("wither_wings",
            () -> new ElytraItem(new Item.Properties()
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                    .rarity(Rarity.RARE)));
    //endregion shards
    //region copper
    //removing the rust aspect for now
    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new SwordItem(ModTiers.COPPER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModTiers.COPPER,3,-2.4F))));
    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ModTiers.COPPER,new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModTiers.COPPER,7,-3F))));
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ModTiers.COPPER,new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModTiers.COPPER,1.5F, -3F))));
    public static final RegistryObject<Item> COPPER_PICK = ITEMS.register("copper_pick",
            () -> new PickaxeItem(ModTiers.COPPER,new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModTiers.COPPER,1,-2.8F))));
    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new HoeItem(ModTiers.COPPER,new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModTiers.COPPER,0,-2f))));
    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet",
            () -> new ArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.HELMET,new Item.Properties()));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate",
            () -> new ArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.CHESTPLATE,new Item.Properties()));
    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings",
            () -> new ArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.LEGGINGS,new Item.Properties()));
    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots",
            () -> new ArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.BOOTS,new Item.Properties()));
    //endregion copper
    //region glass
    public static final RegistryObject<Item> GLASS_SWORD = ITEMS.register("glass_sword",
            () -> new SwordItem(ModTiers.GLASS, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModTiers.GLASS,3, -1.8F))
                    .rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GLASS_AXE = ITEMS.register("glass_axe",
            () -> new AxeItem(ModTiers.GLASS, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModTiers.GLASS,7,-2.4F))
                    .rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GLASS_SHOVEL = ITEMS.register("glass_shovel",
            () -> new ShovelItem(ModTiers.GLASS, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModTiers.GLASS,2,-2.4F))
                    .rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GLASS_PICK = ITEMS.register("glass_pick",
            () -> new PickaxeItem(ModTiers.GLASS, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModTiers.GLASS,2,-2.4F))
                    .rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GLASS_HOE = ITEMS.register("glass_hoe",
            () -> new HoeItem(ModTiers.GLASS, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModTiers.GLASS,2,-2.4F))
                    .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> PRIME_HELMET = ITEMS.register("crystal_prime_helmet",
            () -> new CrystalArmorItem(ArmorItem.Type.HELMET,new Item.Properties()));
    public static final RegistryObject<Item> PRIME_CHESTPLATE = ITEMS.register("crystal_prime_chestplate",
            () -> new CrystalArmorItem(ArmorItem.Type.CHESTPLATE,new Item.Properties()));
    public static final RegistryObject<Item> PRIME_LEGGINGS = ITEMS.register("crystal_prime_leggings",
            () -> new CrystalArmorItem(ArmorItem.Type.LEGGINGS,new Item.Properties()));
    public static final RegistryObject<Item> FERUS_HELMET = ITEMS.register("crystal_ferus_helmet",
            () -> new CrystalFerusArmorItem(ArmorItem.Type.HELMET,new Item.Properties()));
    public static final RegistryObject<Item> FERUS_CHESTPLATE = ITEMS.register("crystal_ferus_chestplate",
            () -> new CrystalFerusArmorItem(ArmorItem.Type.CHESTPLATE,new Item.Properties()));
    public static final RegistryObject<Item> FERUS_LEGGINGS = ITEMS.register("crystal_ferus_leggings",
            () -> new CrystalFerusArmorItem(ArmorItem.Type.LEGGINGS,new Item.Properties()));
    public static final RegistryObject<Item> ANIMA_HELMET = ITEMS.register("crystal_anima_helmet",
            () -> new CrystalAnimaArmorItem(ArmorItem.Type.HELMET,new Item.Properties()));
    public static final RegistryObject<Item> ANIMA_CHESTPLATE = ITEMS.register("crystal_anima_chestplate",
            () -> new CrystalAnimaArmorItem(ArmorItem.Type.CHESTPLATE,new Item.Properties()));
    public static final RegistryObject<Item> ANIMA_LEGGINGS = ITEMS.register("crystal_anima_leggings",
            () -> new CrystalAnimaArmorItem(ArmorItem.Type.LEGGINGS,new Item.Properties()));
    public static final RegistryObject<Item> FORTIS_HELMET = ITEMS.register("crystal_fortis_helmet",
            () -> new CrystalFortisArmorItem(ArmorItem.Type.HELMET,new Item.Properties()));
    public static final RegistryObject<Item> FORTIS_CHESTPLATE = ITEMS.register("crystal_fortis_chestplate",
            () -> new CrystalFortisArmorItem(ArmorItem.Type.CHESTPLATE,new Item.Properties()));
    public static final RegistryObject<Item> FORTIS_LEGGINGS = ITEMS.register("crystal_fortis_leggings",
            () -> new CrystalFortisArmorItem(ArmorItem.Type.LEGGINGS,new Item.Properties()));
    //endregion glass
    //region bronze
    public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword",
            () -> new SwordItem(ModTiers.BRONZE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModTiers.BRONZE,3,-2.4F))));
    public static final RegistryObject<Item> BRONZE_AXE = ITEMS.register("bronze_axe",
            () -> new AxeItem(ModTiers.BRONZE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModTiers.BRONZE,7,-3F))));
    public static final RegistryObject<Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel",
            () -> new ShovelItem(ModTiers.BRONZE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModTiers.BRONZE,1.5F, -3F))));
    public static final RegistryObject<Item> BRONZE_PICK = ITEMS.register("bronze_pick",
            () -> new PickaxeItem(ModTiers.BRONZE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModTiers.BRONZE,1,-2.8F))));
    public static final RegistryObject<Item> BRONZE_HOE = ITEMS.register("bronze_hoe",
            () -> new HoeItem(ModTiers.BRONZE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModTiers.BRONZE,0,-2F))));
    public static final RegistryObject<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet",
            () -> new ArmorItem(ModArmorMaterials.BRONZE,ArmorItem.Type.HELMET,new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BRONZE,ArmorItem.Type.CHESTPLATE,new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings",
            () -> new ArmorItem(ModArmorMaterials.BRONZE,ArmorItem.Type.LEGGINGS,new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots",
            () -> new ArmorItem(ModArmorMaterials.BRONZE,ArmorItem.Type.BOOTS,new Item.Properties()));
    //endregion bronze
    //region dragon
    static AttributeModifier hp = new AttributeModifier(
            name("health"),
            2, AttributeModifier.Operation.ADD_VALUE);
    public static final RegistryObject<Item> DRAGON_HELMET = ITEMS.register("dragon_helmet",
            () -> new ArmorItem(ModArmorMaterials.DRAGON,ArmorItem.Type.HELMET,new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.MAX_HEALTH, hp, EquipmentSlotGroup.HEAD).build())));
    public static final RegistryObject<Item> DRAGON_CHESTPLATE = ITEMS.register("dragon_chestplate",
            () -> new ArmorItem(ModArmorMaterials.DRAGON,ArmorItem.Type.HELMET,new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder().add(Attributes.MAX_HEALTH, hp, EquipmentSlotGroup.HEAD).build())));
    public static final RegistryObject<Item> DRAGON_LEGGINGS = ITEMS.register("dragon_leggings",
            () -> new ArmorItem(ModArmorMaterials.DRAGON,ArmorItem.Type.HELMET,new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder().add(Attributes.MAX_HEALTH, hp, EquipmentSlotGroup.HEAD).build())));
    //the world doesn't deserve dragon boots
    //public static final RegistryObject<Item> DRAGON_BOOTS = ITEMS.register("dragon_boots",
    //        () -> new TemplateArmorItem(ModArmorMaterials.DRAGON,ArmorItem.Type.BOOTS,new Item.Properties()));

    //endregion dragon
    //region charms
    public static final RegistryObject<Item> CHARM_AMBUSH = ITEMS.register("charm_ambush",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CHARM_EXPERIENCE = ITEMS.register("charm_experience",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CHARM_VITALITY = ITEMS.register("charm_vitality",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CHARM_FERTILITY = ITEMS.register("charm_fertility",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CHARM_ALCHEMY = ITEMS.register("charm_alchemy",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CHARM_CONTRABAND = ITEMS.register("charm_contraband",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CHARM_DETONATION = ITEMS.register("charm_detonation",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CHARM_WARDING = ITEMS.register("charm_warding",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CHARM_FRAGMENT = ITEMS.register("charm_fragment",
            CharmFragmentItem::makeFragment);
    //region magic
    public static final RegistryObject<Item> ESSENCE = ITEMS.register("essence",
            () -> new EssenceItem(new Item.Properties()
                    .rarity(Rarity.UNCOMMON).fireResistant().stacksTo(1)));
    //endregion magic
    //region tech
    public static final RegistryObject<Item> LIGHTER = ITEMS.register("trinket_lighter",
            () -> new LighterItem(new Item.Properties()
                    .rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> POCKET_BREWER = ITEMS.register("trinket_brewer",
            () -> new PocketBrewerItem(new Item.Properties()
                    .rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> OMINOUS_TRINKET = ITEMS.register("trinket_ominous",
            () -> new OminousTrinketItem(new Item.Properties()
                    .durability(500)
                    .component(DataComponents.UNBREAKABLE,new Unbreakable(false))
                    .rarity(Rarity.UNCOMMON).stacksTo(1)));

    public static final RegistryObject<Item> DUST_POUCH = ITEMS.register("dust_pouch",
            () -> new DustPouchItem(new Item.Properties()
                    .rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> GEM_POUCH = ITEMS.register("gem_pouch",
            () -> new GemPouchItem(new Item.Properties()
                    .rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> SEED_POUCH = ITEMS.register("seed_pouch",
            () -> new SeedPouchItem(new Item.Properties()
                    .rarity(Rarity.UNCOMMON).stacksTo(1)));
    //endregion tech
    //region potions/ores
    public static final RegistryObject<Item> POTION_SALT = ITEMS.register("potion_salt",
            () -> new PotionSaltItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> VOID_SALT = ITEMS.register("void_salt",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BISMUTH_ORE = ITEMS.register("bismuth_ore",
            () -> new BlockItem(ModBlocks.BISMUTH_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> BISMUTH_NUGGET = ITEMS.register("bismuth_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BISMUTH_INGOT = ITEMS.register("bismuth_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_NUGGET = ITEMS.register("netherite_nugget",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> POTION_FLASK = ITEMS.register("potion_flask",
            () -> new PotionFlaskItem(new Item.Properties().rarity(Rarity.UNCOMMON)
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                    .durability(3)));
    public static final RegistryObject<Item> IMPETUS_FLASK = ITEMS.register("potion_flask_impetus",
            () -> new ImpetusFlask(new Item.Properties().rarity(Rarity.UNCOMMON)
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                    .durability(4)));
    public static final RegistryObject<Item> VITA_FLASK = ITEMS.register("potion_flask_vita",
            () -> new SemiVitaFlask(new Item.Properties().rarity(Rarity.UNCOMMON)
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                    .durability(4)));
    public static final RegistryObject<Item> MAGNA_FLASK = ITEMS.register("potion_flask_magna",
            () -> new PotionFlaskItem(new Item.Properties().rarity(Rarity.UNCOMMON)
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                    .durability(6)));
    public static final RegistryObject<Item> ETERNA_FLASK = ITEMS.register("potion_flask_eterna",
            () -> new EternaFlask(new Item.Properties().rarity(Rarity.EPIC)
                    .component(DataComponents.UNBREAKABLE,new Unbreakable(true))));

    public static final RegistryObject<Item> DRAGON_SCALE = ITEMS.register("dragon_scale",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> PRIME_GLASS = ITEMS.register("prime_glass",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ANIMA_GLASS = ITEMS.register("anima_glass",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FERUS_GLASS = ITEMS.register("ferus_glass",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FORTIS_GLASS = ITEMS.register("fortis_glass",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SMITHING_FLUX = ITEMS.register("smithing_flux",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SILK = ITEMS.register("silk",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(16)));
    public static final RegistryObject<Item> UMBRA = ITEMS.register("umbra",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    //umbra
    static ItemAttributeModifiers umbra = ItemAttributeModifiers.builder()
            .add(Attributes.GRAVITY, new AttributeModifier(name("gravity"),-.20, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),EquipmentSlotGroup.ARMOR)
            .add(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(name("gravity"),2,AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.ANY)
            .build();
    public static final RegistryObject<Item> UMBRA_HAT = ITEMS.register("umbra_hat",
            () -> new UmbraArmorItem(ArmorItem.Type.HELMET,new Item.Properties().attributes(umbra)));
    public static final RegistryObject<Item> UMBRA_TOP = ITEMS.register("umbra_top",
            () -> new UmbraArmorItem(ArmorItem.Type.CHESTPLATE,new Item.Properties().attributes(umbra)));
    public static final RegistryObject<Item> UMBRA_BOTTOM = ITEMS.register("umbra_bottom",
            () -> new UmbraArmorItem(ArmorItem.Type.LEGGINGS,new Item.Properties().attributes(umbra)));
    public static final RegistryObject<Item> MALACHITE = ITEMS.register("malachite",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    //claws
    static ItemAttributeModifiers small_claw = ItemAttributeModifiers.builder()
            .add(Attributes.BLOCK_INTERACTION_RANGE,new AttributeModifier(name("reach"),1,AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.OFFHAND).build();
    static ItemAttributeModifiers medium_claw = ItemAttributeModifiers.builder()
            .add(Attributes.BLOCK_INTERACTION_RANGE,new AttributeModifier(name("reach"),2,AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.OFFHAND).build();
    static ItemAttributeModifiers large_claw = ItemAttributeModifiers.builder()
            .add(Attributes.BLOCK_INTERACTION_RANGE,new AttributeModifier(name("reach"),3,AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.OFFHAND).build();
    static ItemAttributeModifiers strong_claw = ItemAttributeModifiers.builder()
            .add(Attributes.ENTITY_INTERACTION_RANGE,new AttributeModifier(name("reach"),1,AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.OFFHAND)
            .add(Attributes.ATTACK_DAMAGE,new AttributeModifier(name("attack"),1,AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.OFFHAND).build();
    public static final RegistryObject<Item> CLAW = ITEMS.register("claw",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)
                    .attributes(small_claw)));
    public static final RegistryObject<Item> COPPER_CLAW = ITEMS.register("copper_claw",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)
                    .attributes(medium_claw)));
    public static final RegistryObject<Item> CRYSTAL_CLAW = ITEMS.register("crystal_claw",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)
                    .attributes(large_claw)));
    public static final RegistryObject<Item> DRAGON_CLAW = ITEMS.register("dragon_claw",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)
                    .attributes(strong_claw)));
    //endregion potion/ores
    //region food
    public static final RegistryObject<Item> FOOD_MEATBALLS = ITEMS.register("meatballs",
            () -> new FoodItem(new Item.Properties().food(ModFoods.MEATBALLS)));
    public static final RegistryObject<Item> FOOD_HONEY_NUGGETS = ITEMS.register("honey_nuggets",
            () -> new FoodItem(new Item.Properties().food(ModFoods.HONEY_NUGGETS)));
    public static final RegistryObject<Item> FOOD_CHILI = ITEMS.register("chili",
            () -> new FoodItem(new Item.Properties().food(ModFoods.CHILI)));
    public static final RegistryObject<Item> FOOD_ICECREAM = ITEMS.register("icecream",
            () -> new ColdFoodItem(new Item.Properties().food(ModFoods.ICECREAM)));
    public static final RegistryObject<Item> FOOD_BURGER = ITEMS.register("borger",
            () -> new FoodItem(new Item.Properties().food(ModFoods.BURGER)));
    public static final RegistryObject<Item> FOOD_SKEWERS = ITEMS.register("skewers",
            () -> new FoodItem(new Item.Properties().food(ModFoods.SKEWERS)));
    public static final RegistryObject<Item> FOOD_SWEETS = ITEMS.register("sweets",
            () -> new FoodItem(new Item.Properties().food(ModFoods.SWEETS)));
    public static final RegistryObject<Item> FOOD_APPLE_PIE = ITEMS.register("apple_pie",
            () -> new FoodItem(new Item.Properties().food(ModFoods.APPLE_PIE)));
    public static final RegistryObject<Item> FOOD_BLINK_BROWNIE = ITEMS.register("blink_brownie",
            () -> new BlinkBrownieItem(new Item.Properties().food(ModFoods.BROWNIE)));
    public static final RegistryObject<Item> FOOD_TRIPLE_MEAT_TREAT = ITEMS.register("triple_meat_treat",
            () -> new FoodItem(new Item.Properties().food(ModFoods.TRIPLE)));
    public static final RegistryObject<Item> FOOD_PIEROGI = ITEMS.register("pierogi",
            () -> new FoodItem(new Item.Properties().food(ModFoods.PIEROGI)));
    public static final RegistryObject<Item> FOOD_OMELETTE = ITEMS.register("omelette",
            () -> new FoodItem(new Item.Properties().food(ModFoods.OMLETTE)));
    public static final RegistryObject<Item> FOOD_GOLDEN_COOKIE = ITEMS.register("golden_cookie",
            () -> new FoodItem(new Item.Properties().food(ModFoods.GOLDEN_COOKIE))
            {
                @Override
                public boolean isFoil(ItemStack p_41453_) {
                    return true;
                }
            });
    //test for tier 2 foods?
    public static final RegistryObject<Item> FOOD_LOAF = ITEMS.register("loaf",
            () -> new FoodItem(new Item.Properties().food(ModFoods.LOAF)));


    //endregion food
    //region infusion items
    static ItemAttributeModifiers speedMod = ItemAttributeModifiers.builder().add(Attributes.MOVEMENT_SPEED, new AttributeModifier(name("speed"),
            .1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.FEET).build();
    public static final RegistryObject<Item> CLOTH_BOOTS = ITEMS.register("cloth_boots",
            () -> new ArmorItem(ArmorMaterials.LEATHER,ArmorItem.Type.BOOTS,new Item.Properties()
                    .attributes(speedMod)));

    static ItemAttributeModifiers luckMod = ItemAttributeModifiers.builder().add(Attributes.LUCK, new AttributeModifier(name("luck"),
            .1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.HAND).build();
    public static final RegistryObject<Item> GILDED_ROD = ITEMS.register("gilded_rod",
            () -> new GildedRodItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)
                    .attributes(luckMod)));
    public static final RegistryObject<Item> ANGLER_ROD = ITEMS.register("angler_rod",
            () -> new AnglerRodItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
    //endregion infusion items
    //helper methods
    static ResourceLocation name(String name)
    {
        return ResourceLocation.fromNamespaceAndPath("patina", name);
    }
    //
    public static void register(IEventBus eventBus)
    {ITEMS.register(eventBus);}
}

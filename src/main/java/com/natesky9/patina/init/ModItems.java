package com.natesky9.patina.init;

import com.natesky9.patina.Items.*;
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
    //
    public static final DeferredItem<Item> ESSENCE_BUCKET = ITEMS.register("essence_bucket",
            () -> new BucketItem(ModFluids.ESSENCE_SOURCE.get(),
                    baseProperties("essence_bucket").stacksTo(1)));

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
    public static final DeferredItem<Item> COPPER_PICK = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ModTiers.COPPER, 1.0F, -2.8F,
                    baseProperties("copper_pickaxe")));
    public static final DeferredItem<Item> COPPER_HOE = ITEMS.register("copper_hoe",
            (properties) -> new HoeItem(ModTiers.COPPER,-1F, -1F,
                    baseProperties("copper_hoe")));

    public static final DeferredItem<Item> COPPER_HELMET = ITEMS.register("copper_helmet",
            () -> new ArmorItem(ModArmor.COPPER, ArmorType.HELMET,
                    baseProperties("copper_helmet")));
    public static final DeferredItem<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate",
            () -> new ArmorItem(ModArmor.COPPER, ArmorType.HELMET,
                    baseProperties("copper_chestplate")));
    public static final DeferredItem<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings",
            () -> new ArmorItem(ModArmor.COPPER, ArmorType.HELMET,
                    baseProperties("copper_leggings")));
    public static final DeferredItem<Item> COPPER_BOOTS = ITEMS.register("copper_boots",
            () -> new ArmorItem(ModArmor.COPPER, ArmorType.HELMET,
            baseProperties("copper_boots")));
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
    public static final DeferredItem<Item> CRYSTAL_PICK = ITEMS.register("crystal_pickaxe",
            () -> new SwordItem(ModTiers.CRYSTAL,3,-1.8F,
                    baseProperties("crystal_pickaxe")));
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
    public static final DeferredItem<Item> IMPERIUM_HELMET = ITEMS.register("crystal_imperium_helmet",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.HELMET,
                    baseProperties("crystal_imperium_helmet")));
    public static final DeferredItem<Item> IMPERIUM_CHESTPLATE = ITEMS.register("crystal_imperium_chestplate",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.CHESTPLATE,
                    baseProperties("crystal_imperium_helmet")));
    public static final DeferredItem<Item> IMPERIUM_LEGGINGS = ITEMS.register("crystal_imperium_leggings",
            () -> new ArmorItem(ModArmor.CRYSTAL, ArmorType.HELMET,
                    baseProperties("crystal_imperium_leggings")));
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
    public static final DeferredItem<Item> PERPETUUM_CRYSTAL = ITEMS.register("crystal_perpetuum",
            () -> new Item(baseProperties("crystal_perpetuum")));
    public static final DeferredItem<Item> REGIMA_CRYSTAL = ITEMS.register("crystal_regima",
            () -> new Item(baseProperties("crystal_regima")));
    public static final DeferredItem<Item> MALACHITE = ITEMS.register("crystal_malachite",
            () -> new Item(baseProperties("crystal_malachite")));
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
            () -> new Item(baseProperties("bron_ingot")));
    public static final DeferredItem<Item> CHROMATIC_SCALE = ITEMS.register("chromatic_scale",
            () -> new Item(baseProperties("chromatic_scale")));
    //endregion materials
    //region ore processing
    public static final DeferredItem<Item> ORE_CHUNK = ITEMS.register("ore_chunk",
            () -> new Item(baseProperties("ore_chunk")));//1.3x
    public static final DeferredItem<Item> ORE_CLUMP = ITEMS.register("ore_clump",
            () -> new Item(baseProperties("ore_clump")));//1.6x
    public static final DeferredItem<Item> ORE_LUMP = ITEMS.register("ore_lump",
            () -> new Item(baseProperties("ore_lump")));
    public static final DeferredItem<Item> ORE_HUNK = ITEMS.register("ore_hunk",
            () -> new Item(baseProperties("ore_hunk")));
    public static final DeferredItem<Item> ORE_FLAKE = ITEMS.register("ore_flake",
            () -> new Item(baseProperties("ore_flake")));
    //transit items
    public static final DeferredItem<Item> ORE_COBBLE = ITEMS.register("ore_cobble",
            () -> new Item(baseProperties("ore_cobble")));

    public static final DeferredItem<Item> ORE_GRAVEL = ITEMS.register("ore_gravel",
            () -> new Item(baseProperties("ore_gravel")));
    public static final DeferredItem<Item> ORE_PEBBLE = ITEMS.register("ore_pebble",
            () -> new Item(baseProperties("ore_pebble")));

    public static final DeferredItem<Item> ORE_BLEND = ITEMS.register("ore_blend",
            () -> new Item(baseProperties("ore_blend")));
    public static final DeferredItem<Item> ORE_SLAG = ITEMS.register("ore_slag",
            () -> new Item(baseProperties("ore_slag")));
    public static final DeferredItem<Item> ORE_MIX = ITEMS.register("ore_mix",
            () -> new Item(baseProperties("ore_mix")));

    public static final DeferredItem<Item> ORE_GRIT = ITEMS.register("ore_grit",
            () -> new Item(baseProperties("ore_grit")));

    //endregion ore processing

    //region tools
    public static final DeferredItem<Item> FLASK_CRYSTAL = ITEMS.register("flask_crystal",
            () -> new CrystalFlaskItem(baseProperties("flask_crystal")
                    .stacksTo(1).durability(3)));
    public static final DeferredItem<Item> FLASK_PUGNA = ITEMS.register("flask_pugna",
            () -> new CrystalFlaskItem(baseProperties("flask_pugna")
                    .stacksTo(1).durability(4)));
    public static final DeferredItem<Item> FLASK_VITA = ITEMS.register("flask_vita",
            () -> new CrystalFlaskItem(baseProperties("flask_vita")
                    .stacksTo(1).durability(4)));
    public static final DeferredItem<Item> FLASK_MAGNA = ITEMS.register("flask_magna",
            () -> new MagnaFlaskItem(baseProperties("flask_magna")
                    .stacksTo(1).durability(6)));
    public static final DeferredItem<Item> FLASK_PLUVIA = ITEMS.register("flask_pluvia",
            () -> new PluviaFlaskItem(baseProperties("flask_pluvia")
                    .stacksTo(1).durability(16)));
    public static final DeferredItem<Item> FLASK_ETERNA = ITEMS.register("flask_eterna",
            () -> new EternaFlaskItem(baseProperties("flask_eterna")
                    .stacksTo(1).durability(Integer.MAX_VALUE)));

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
    //region blocks
    //comment these out as needed as the blockentities are added
    //since this is done via the new register method
    //public static final DeferredItem<BlockItem> MACHINE_UNIFIER = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_UNIFIER);
    //public static final DeferredItem<BlockItem> MACHINE_ABSTRACTOR = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_ABSTRACTOR);
    //public static final DeferredItem<BlockItem> MACHINE_REPLICATOR = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_REPLICATOR);
    //public static final DeferredItem<BlockItem> MACHINE_EXTRACTOR = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_EXTRACTOR);
    //public static final DeferredItem<BlockItem> MACHINE_AUGMENTOR = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_AUGMENTOR);
    //public static final DeferredItem<BlockItem> MACHINE_ARBITRATOR = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_ARBITRATOR);
    //public static final DeferredItem<BlockItem> MACHINE_MATRIX = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_MATRIX);
    //public static final DeferredItem<BlockItem> APPLIANCE_PLINTH = ITEMS.registerSimpleBlockItem(ModBlocks.APPLIANCE_PLINTH);
    //public static final DeferredItem<BlockItem> APPLIANCE_REINFORCED_PLINTH = ITEMS.registerSimpleBlockItem(ModBlocks.APPLIANCE_REINFORCED_PLINTH);
    //public static final DeferredItem<BlockItem> MACHINE_FOUNDRY = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_FOUNDRY);
    //public static final DeferredItem<BlockItem> ADDON_FOUNDRY = ITEMS.registerSimpleBlockItem(ModBlocks.ADDON_FOUNDRY);
    //public static final DeferredItem<BlockItem> MACHINE_ALEMBIC = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_ALEMBIC);
    //public static final DeferredItem<BlockItem> ADDON_ALEMBIC = ITEMS.registerSimpleBlockItem(ModBlocks.ADDON_ALEMBIC);
    //public static final DeferredItem<BlockItem> MACHINE_MINCERATOR = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_MINCERATOR);
    //public static final DeferredItem<BlockItem> MACHINE_KWERN = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_KWERN);
    //public static final DeferredItem<BlockItem> MACHINE_TEXTILER = ITEMS.registerSimpleBlockItem(ModBlocks.MACHINE_TEXTILER);
    //public static final DeferredItem<BlockItem> APPLIANCE_WARDROBE = ITEMS.registerSimpleBlockItem(ModBlocks.APPLIANCE_WARDROBE);
    //public static final DeferredItem<BlockItem> APPLIANCE_ICEBOX = ITEMS.registerSimpleBlockItem(ModBlocks.APPLIANCE_ICEBOX);
    //public static final DeferredItem<BlockItem> APPLIANCE_RESEARCH_DESK = ITEMS.registerSimpleBlockItem(ModBlocks.APPLIANCE_RESEARCH_DESK);
    //public static final DeferredItem<BlockItem> APPLIANCE_BENCHMARK = ITEMS.registerSimpleBlockItem(ModBlocks.APPLIANCE_BENCHMARK);
    //public static final DeferredItem<BlockItem> APPLIANCE_ARCANE_CONSOLIDATOR = ITEMS.registerSimpleBlockItem(ModBlocks.APPLIANCE_ARCANE_CONSOLIDATOR);
    //public static final DeferredItem<BlockItem> APPLIANCE_CHORUS_TELEPORTER = ITEMS.registerSimpleBlockItem(ModBlocks.APPLIANCE_CHORUS_TELEPORTER);
    //public static final DeferredItem<BlockItem> CHORUS_CABLE = ITEMS.registerSimpleBlockItem(ModBlocks.CHORUS_CABLE);
    //public static final DeferredItem<BlockItem> WYRE_CABLE = ITEMS.registerSimpleBlockItem(ModBlocks.WYRE_CABLE);
    //public static final DeferredItem<BlockItem> FLUUD_PIPE = ITEMS.registerSimpleBlockItem(ModBlocks.FLUUD_PIPE);
    //endregion blocks


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

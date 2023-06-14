package com.natesky9.patina.init;

import com.natesky9.patina.Item.BeeShieldItem;
import com.natesky9.patina.Item.BeeWeaponItem;
import com.natesky9.patina.Item.PigCrossbowItem;
import com.natesky9.patina.Item.PigWeaponItem;
import com.natesky9.patina.Patina;
import net.minecraft.world.item.*;
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
    public static final RegistryObject<Item> BEE_SWORD = ITEMS.register("bee_sword",
            () -> (new BeeWeaponItem(ModTiers.BOSS,2,-1.8F,
                    new Item.Properties().rarity(Rarity.RARE))));
    public static final RegistryObject<Item> BEE_SHIELD = ITEMS.register("bee_shield",
            () -> new BeeShieldItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
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
    public static final RegistryObject<Item> PIG_SWORD = ITEMS.register("pig_sword",
            () -> (new PigWeaponItem(ModTiers.BOSS,4,3.0F,
                    new Item.Properties().rarity(Rarity.RARE))));
    public static final RegistryObject<Item> PIG_CROSSBOW = ITEMS.register("pig_crossbow",
            () -> new PigCrossbowItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
    //endregion shards
    //region copper
    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ModTiers.COPPER,3,-2.4F, new Item.Properties()));
    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ModTiers.COPPER,7,-3.0F,new Item.Properties()));
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ModTiers.COPPER,1.5F,-3.0F,new Item.Properties()));
    public static final RegistryObject<Item> COPPER_PICK = ITEMS.register("copper_pick",
            () -> new AxeItem(ModTiers.COPPER,1,-2.8F,new Item.Properties()));
    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new AxeItem(ModTiers.COPPER,0,-2.0F,new Item.Properties()));
    //endregion copper
    //region charms
    public static final RegistryObject<Item> CHARM_AMBUSH = ITEMS.register("charm_ambush",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    //charm of pounce
    //charm of preservation
    //charm of warding
    //charm of blessing
    //TODO add charms
    //endregion charms
    //
    public static void register(IEventBus eventBus)
    {ITEMS.register(eventBus);}
}

package com.natesky9.patina.datagen;

import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Patina.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        folderItem(ModItems.BEE_FRAGMENT_1.get(),"fragment");
        folderItem(ModItems.BEE_FRAGMENT_2.get(),"fragment");
        folderItem(ModItems.BEE_FRAGMENT_3.get(),"fragment");
        folderItem(ModItems.BEE_FRAGMENT_4.get(),"fragment");
        folderItem(ModItems.BEE_FRAGMENT_A.get(),"fragment");
        folderItem(ModItems.BEE_FRAGMENT_B.get(),"fragment");
        //handheldItem(ModItems.BEE_WEAPON.get());

        folderItem(ModItems.PIG_FRAGMENT_1.get(),"fragment");
        folderItem(ModItems.PIG_FRAGMENT_2.get(),"fragment");
        folderItem(ModItems.PIG_FRAGMENT_3.get(),"fragment");
        folderItem(ModItems.PIG_FRAGMENT_4.get(),"fragment");
        folderItem(ModItems.PIG_FRAGMENT_A.get(),"fragment");
        folderItem(ModItems.PIG_FRAGMENT_B.get(),"fragment");
        handheldItem(ModItems.PIG_WEAPON.get());

        handheldItem(ModItems.PIGLIN_BALLISTA.get());

        simpleItem(ModItems.COAL_COKE.get());
        simpleItem(ModItems.INERT_ROD.get());
        simpleItem(ModItems.ROYAL_JELLY.get());
        simpleItem(ModItems.SPIDER_NEST.get());
        simpleItem(ModItems.CLOTH.get());
        simpleItem(ModItems.MAGIC_CLOTH.get());

        simpleItem(ModItems.HERB_SEEDS.get());
        simpleItem(ModItems.HERB.get());

        folderItem(ModItems.COPPER_HELMET.get(),"copper");
        folderItem(ModItems.COPPER_CHESTPLATE.get(),"copper");
        folderItem(ModItems.COPPER_LEGGINGS.get(),"copper");
        folderItem(ModItems.COPPER_BOOTS.get(),"copper");
        folderItem(ModItems.CRYSTAL_HELMET.get(),"crystal");
        folderItem(ModItems.CRYSTAL_CHESTPLATE.get(),"crystal");
        folderItem(ModItems.CRYSTAL_LEGGINGS.get(),"crystal");
        folderItem(ModItems.CRYSTAL_BOOTS.get(),"crystal");
        folderItem(ModItems.ARCHER_HELMET.get(), "archer");
        folderItem(ModItems.ARCHER_CHESTPLATE.get(), "archer");
        folderItem(ModItems.ARCHER_LEGGINGS.get(), "archer");
        folderItem(ModItems.ARCHER_BOOTS.get(), "archer");

        simpleItem(ModItems.CUSTOM_NUGGET.get());
        simpleItem(ModItems.CUSTOM_INGOT.get());
        simpleItem(ModItems.CUSTOM_AXE.get());
        simpleItem(ModItems.CUSTOM_SWORD.get());
        simpleItem(ModItems.CUSTOM_SHOVEL.get());
        simpleItem(ModItems.CUSTOM_HOE.get());
        simpleItem(ModItems.CUSTOM_PICK.get());

        simpleItem(ModItems.MELEE_OFFHAND.get());
        simpleItem(ModItems.RANGE_OFFHAND.get());
        simpleItem(ModItems.MAGIC_OFFHAND.get());
        simpleItem(ModItems.FERTILIZER.get());
        //potion salt is done differently
        simpleItem(ModItems.VOID_SALT.get());

        simpleItem(ModItems.POTION_FLASK.get());
        simpleItem(ModItems.SOUP_BOTTLE.get());

        //foods
        simpleItem(ModItems.TEST_FOOD.get());
        simpleItem(ModItems.BLINK_BROWNIE.get());
        simpleItem(ModItems.CANDY_WARTS.get());
        simpleItem(ModItems.CHEESE.get());
        simpleItem(ModItems.MONSTER_MEATBALLS.get());
        simpleItem(ModItems.TRAIL_MIX.get());
        simpleItem(ModItems.SWEETS.get());
        simpleItem(ModItems.CARROT_CAKE.get());
        simpleItem(ModItems.FISH_ROLL.get());
        simpleItem(ModItems.DANDELION_SALAD.get());
        simpleItem(ModItems.WELLINGTON.get());
        simpleItem(ModItems.NACHOS.get());


        simpleItem(ModItems.DUST_BAG.get());
        simpleItem(ModItems.GEM_BAG.get());
        //feral lantern is a model

        simpleItem(ModItems.CHARGED_SHEARS.get());
        simpleItem(ModItems.CHARGED_PICK.get());
        //simpleItem(ModItems.PISTON_SHIELD.get());
        simpleItem(ModItems.FIRE_PIPE.get());

        simpleItem(ModItems.VENOM_SWORD.get());

        simpleItem(ModItems.LUXOMETER.get());
        simpleItem(ModItems.TEST.get());
        //Runescape items
        simpleItem(ModItems.KNIFE.get());
        simpleItem(ModItems.HAMMER.get());
        simpleItem(ModItems.CHISEL.get());
        simpleItem(ModItems.RUBY.get());
        simpleItem(ModItems.BOLT_TIPS.get());
        //region Bolts
        withExistingParent(ModItems.UNFINISHED_BOLTS.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Patina.MOD_ID,"item/component/bolt_unfinished"));
        withExistingParent(ModItems.BOLTS.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Patina.MOD_ID,"item/component/bolt_unfinished"))
                .texture("layer1", new ResourceLocation(Patina.MOD_ID,"item/component/bolt_fletching"));
        withExistingParent(ModItems.TIPPED_BOLTS.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Patina.MOD_ID,"item/component/bolt_unfinished"))
                .texture("layer1", new ResourceLocation(Patina.MOD_ID,"item/component/bolt_fletching"))
                .texture("layer2", new ResourceLocation(Patina.MOD_ID,"item/component/bolt_tipped"));
        withExistingParent(ModItems.ENCHANTED_BOLTS.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Patina.MOD_ID,"item/component/bolt_unfinished"))
                .texture("layer1", new ResourceLocation(Patina.MOD_ID,"item/component/bolt_fletching"))
                .texture("layer2", new ResourceLocation(Patina.MOD_ID,"item/component/bolt_tipped"));
        //endregion
        //region Crossbow
        withExistingParent(ModItems.UNSTRUNG_CROSSBOW.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Patina.MOD_ID, "item/crossbow_stock"))
                .texture("layer1", new ResourceLocation(Patina.MOD_ID,"item/crossbow_limbs"));
        withExistingParent(ModItems.CROSSBOW.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Patina.MOD_ID,"item/crossbow_stock"))
                .texture("layer1", new ResourceLocation(Patina.MOD_ID,"item/crossbow_limbs"))
                .texture("layer2", new ResourceLocation(Patina.MOD_ID,"item/crossbow_string"));
        //endregion
        simpleItem(ModItems.DART_TIPS.get());
        withExistingParent(ModItems.INGOT_1.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Patina.MOD_ID,"item/custom_ingot"));
        withExistingParent(ModItems.INGOT_2.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Patina.MOD_ID,"item/custom_ingot"));
        simpleItem(ModItems.CROSSBOW_LIMB.get());
        simpleItem(ModItems.CROSSBOW_STOCK.get());
        simpleItem(ModItems.BOWSTRING.get());
        simpleItem(ModItems.BOWSTRING.get());
        simpleItem(ModItems.UNFINISHED_STAFF.get());
        withExistingParent(ModItems.IMPERFECT_STAFF.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Patina.MOD_ID,"item/unfinished_staff"))
                .texture("layer1", new ResourceLocation(Patina.MOD_ID,"item/staff_decoration"));
        withExistingParent(ModItems.STAFF.get().getRegistryName().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(Patina.MOD_ID,"item/unfinished_staff"))
                .texture("layer1", new ResourceLocation(Patina.MOD_ID,"item/staff_decoration"))
                .texture("layer2", new ResourceLocation(Patina.MOD_ID,"item/orb"));
        simpleItem(ModItems.ORB.get());
        simpleItem(ModItems.ENCHANTING_ORB.get());
        //----------//
        //begin block items
        simpleBlock(ModBlocks.MACHINE_BLAST_CAULDRON.get());
        simpleBlock(ModBlocks.MACHINE_CAULDRON_BREWING.get());
        simpleBlock(ModBlocks.MACHINE_SMOKER_GRINDSTONE.get());
        simpleBlock(ModBlocks.MACHINE_ANVIL_SMITHING.get());
        simpleBlock(ModBlocks.MACHINE_CAULDRON_SMOKER.get());
        simpleBlock(ModBlocks.CUSTOM_BLOCK.get());
        simpleBlock(ModBlocks.TELECHORUS.get());
        withExistingParent(ModBlocks.CHORUS_CABLE.get().getRegistryName().getPath(),modLoc("block/chorus_cable_inventory"));
        withExistingParent(ModBlocks.HONEY_PUDDLE.get().getRegistryName().getPath(),modLoc("block/honey_puddle"));
        simpleBlock(ModBlocks.TEST_BLOCK.get());
        withExistingParent(ModBlocks.TEST_SLAB.get().getRegistryName().getPath(),modLoc("block/test_slab"));
        withExistingParent(ModBlocks.TEST_STAIRS.get().getRegistryName().getPath(),modLoc("block/test_stairs"));
        withExistingParent(ModBlocks.TEST_WALL.get().getRegistryName().getPath(),mcLoc("block/wall_inventory"))
                .texture("wall",modLoc("block/test_block"));
        //withExistingParent(ModBlocks.HONEY_PUDDLE.get().getRegistryName().getPath(),modLoc("block/honey_puddle"));
    }

    private ItemModelBuilder simpleBlock(Block block)
    {
        return withExistingParent(block.getRegistryName().getPath(),
                new ResourceLocation("block/cube_all")).texture("all",
                new ResourceLocation(Patina.MOD_ID,"block/" + block.getRegistryName().getPath()));
    }

    private ItemModelBuilder simpleItem(Item item)
    {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Patina.MOD_ID,"item/" + item.getRegistryName().getPath()));
    }
    private ItemModelBuilder copperItem(Item item)
    {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Patina.MOD_ID,"item/copper/" + item.getRegistryName().getPath() + "_clean"))
                .override().predicate(ModItems.rustlevel,.25F).model(withExistingParent(item.getRegistryName().getPath(),
                        new ResourceLocation("item/generated")).texture("layer0",
                        new ResourceLocation(Patina.MOD_ID,"item/copper/" + item.getRegistryName().getPath() + "_exposed"))).end()
                .override().predicate(ModItems.rustlevel,.5F).model(withExistingParent(item.getRegistryName().getPath(),
                        new ResourceLocation("item/generated")).texture("layer0",
                        new ResourceLocation(Patina.MOD_ID,"item/copper/" + item.getRegistryName().getPath() + "_weathered"))).end()
                .override().predicate(ModItems.rustlevel,.75F).model(withExistingParent(item.getRegistryName().getPath(),
                        new ResourceLocation("item/generated")).texture("layer0",
                        new ResourceLocation(Patina.MOD_ID,"item/copper/" + item.getRegistryName().getPath() + "_oxidized"))).end();
    }
    private ItemModelBuilder handheldItem(Item item)
    {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Patina.MOD_ID,"item/" + item.getRegistryName().getPath()));
    }
    private ItemModelBuilder folderItem(Item item, String folder)
    {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Patina.MOD_ID,"item/" + folder + "/" + item.getRegistryName().getPath()));

    }

}

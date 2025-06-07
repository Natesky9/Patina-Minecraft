package com.natesky9.patina.DataGen;

import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModItems;
import net.minecraft.client.color.item.Potion;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.renderer.item.properties.numeric.Damage;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {
    ItemModelGenerators gen;
    public ModModelProvider(PackOutput output) {
        super(output, Patina.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        gen = itemModels;
        itemModels.generateFlatItem(ModItems.COPPER_AXE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_SWORD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_SHOVEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_PICK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_HOE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CRYSTAL_AXE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CRYSTAL_SWORD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CRYSTAL_SHOVEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CRYSTAL_PICK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CRYSTAL_HOE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PRIME_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PRIME_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PRIME_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ANIMA_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ANIMA_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ANIMA_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FERUS_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FERUS_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FERUS_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FORTIS_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FORTIS_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FORTIS_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.IMPERIUM_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.IMPERIUM_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.IMPERIUM_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.BISMUTH_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BRON_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PRIME_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ANIMA_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FERUS_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FORTIS_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PERPETUUM_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.REGIMA_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.NETHERITE_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SILK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UMBRA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHROMATIC_SCALE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MALACHITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.KERATIN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.POTION_SALT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.VOID_SALT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.WYRE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BISMUTH_NUGGET.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.CRAB_CLAW.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_CLAW.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DRAGON_CLAW.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.ORE_CHUNK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORE_CLUMP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORE_LUMP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORE_HUNK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORE_FLAKE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORE_COBBLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORE_GRAVEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORE_PEBBLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORE_BLEND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORE_SLAG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORE_MIX.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORE_GRIT.get(), ModelTemplates.FLAT_ITEM);

        //this is done better below
        //ResourceLocation location = itemModels.generateLayeredItem(ModItems.FLASK_CRYSTAL.get(),
        //        ModelLocationUtils.getModelLocation(ModItems.FLASK_CRYSTAL.get(),"_fluid_1"),
        //        ModelLocationUtils.getModelLocation(ModItems.FLASK_CRYSTAL.get(),"_bottle"));
        //itemModels.addPotionTint(ModItems.FLASK_CRYSTAL.get(), location);

        Item prime = ModItems.FLASK_CRYSTAL.get();
        itemModels.itemModelOutput.accept(prime, ItemModelUtils.rangeSelect(new Damage(true), 1,
                ItemModelUtils.plainModel(itemModels.createFlatItemModel(prime, ModelTemplates.FLAT_HANDHELD_ITEM)),
                ItemModelUtils.override(generateFlaskBottle(prime, "_fluid_1"),.33F),
                ItemModelUtils.override(generateFlaskBottle(prime, "_fluid_2"),.66F),
                ItemModelUtils.override(generateFlaskBottle(prime, "_fluid_3"),1F)));
        Item vita = ModItems.FLASK_VITA.get();
        itemModels.itemModelOutput.accept(vita, ItemModelUtils.rangeSelect(new Damage(true), 1,
                ItemModelUtils.plainModel(itemModels.createFlatItemModel(vita, ModelTemplates.FLAT_HANDHELD_ITEM)),
                ItemModelUtils.override(generateFlaskBottle(vita, "_fluid_1"),.25F),
                ItemModelUtils.override(generateFlaskBottle(vita, "_fluid_2"),.5F),
                ItemModelUtils.override(generateFlaskBottle(vita, "_fluid_3"),.75F),
                ItemModelUtils.override(generateFlaskBottle(vita, "_fluid_4"),1F)));
        Item pugna = ModItems.FLASK_PUGNA.get();
        itemModels.itemModelOutput.accept(pugna, ItemModelUtils.rangeSelect(new Damage(true), 1,
                ItemModelUtils.plainModel(itemModels.createFlatItemModel(pugna, ModelTemplates.FLAT_HANDHELD_ITEM)),
                ItemModelUtils.override(generateFlaskBottle(pugna, "_fluid_1"),.25F),
                ItemModelUtils.override(generateFlaskBottle(pugna, "_fluid_2"),.5F),
                ItemModelUtils.override(generateFlaskBottle(pugna, "_fluid_3"),.75F),
                ItemModelUtils.override(generateFlaskBottle(pugna, "_fluid_4"),1F)));
        Item magna = ModItems.FLASK_MAGNA.get();
        itemModels.itemModelOutput.accept(magna, ItemModelUtils.rangeSelect(new Damage(true), 1,
                ItemModelUtils.plainModel(itemModels.createFlatItemModel(magna, ModelTemplates.FLAT_HANDHELD_ITEM)),
                ItemModelUtils.override(generateFlaskBottle(magna, "_fluid_1"),.16F),
                ItemModelUtils.override(generateFlaskBottle(magna, "_fluid_2"),.33F),
                ItemModelUtils.override(generateFlaskBottle(magna, "_fluid_3"),.5F),
                ItemModelUtils.override(generateFlaskBottle(magna, "_fluid_4"),.66F),
                ItemModelUtils.override(generateFlaskBottle(magna, "_fluid_5"),.83F),
                ItemModelUtils.override(generateFlaskBottle(magna, "_fluid_6"),1F)));
        Item eterna = ModItems.FLASK_ETERNA.get();
        itemModels.itemModelOutput.accept(eterna, ItemModelUtils.rangeSelect(new Damage(false), 1,
                ItemModelUtils.plainModel(itemModels.createFlatItemModel(eterna, ModelTemplates.FLAT_HANDHELD_ITEM)),
                ItemModelUtils.override(generateFlaskBottle(eterna, "_fluid_0"),0),
                ItemModelUtils.override(generateFlaskBottle(eterna, "_fluid_1"),1)));
        Item pluvia = ModItems.FLASK_PLUVIA.get();
        itemModels.itemModelOutput.accept(pluvia, ItemModelUtils.rangeSelect(new Damage(false), 0.5F,
                ItemModelUtils.plainModel(itemModels.createFlatItemModel(pluvia, ModelTemplates.FLAT_HANDHELD_ITEM)),
                ItemModelUtils.override(generateFlaskBottle(pluvia, "_fluid_1"),1),
                ItemModelUtils.override(generateFlaskBottle(pluvia, "_fluid_2"),2),
                ItemModelUtils.override(generateFlaskBottle(pluvia, "_fluid_3"),3),
                ItemModelUtils.override(generateFlaskBottle(pluvia, "_fluid_4"),4),
                ItemModelUtils.override(generateFlaskBottle(pluvia, "_fluid_5"),5),
                ItemModelUtils.override(generateFlaskBottle(pluvia, "_fluid_6"),6),
                ItemModelUtils.override(generateFlaskBottle(pluvia, "_fluid_7"),7),
                ItemModelUtils.override(generateFlaskBottle(pluvia, "_fluid_8"),8)));


        //itemModels.generateFlatItem(ModItems.FLASK_VITA.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.FLASK_PUGNA.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.FLASK_MAGNA.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.FLASK_ETERNA.get(), ModelTemplates.FLAT_ITEM);


        for (DeferredHolder<Block, ? extends Block> block: ModBlocks.BLOCKS.getEntries())
        {//lazy solution to get blockstates in place
            if (block != ModBlocks.APPLIANCE_PLINTH)
                blockModels.createTrivialCube(block.get());
        }
    }

    ItemModel.Unbaked generateFlaskBottle(Item item, String suffix)
    {
        ResourceLocation flask = ModelLocationUtils.getModelLocation(item);
        ResourceLocation fluid = flask.withSuffix(suffix);
        return ItemModelUtils.tintedModel(gen.generateLayeredItem(fluid, fluid, flask),new Potion(-13083194));
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().filter(entry -> !entry.is(ModBlocks.APPLIANCE_PLINTH));
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream();
    }
}

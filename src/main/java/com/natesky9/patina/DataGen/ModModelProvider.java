package com.natesky9.patina.DataGen;

import com.natesky9.patina.Blocks.ApplianceEssenceCauldronBlock;
import com.natesky9.patina.Blocks.ApplianceFluidTank;
import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModItems;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.color.item.Potion;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.renderer.item.properties.numeric.Damage;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {
    ItemModelGenerators gen;
    //region cauldron
    public static final ModelTemplate CAULDRON_LEVEL1 = ModelTemplates.create(
            Patina.MODID + ":" +
                    "template_cauldron_level1", TextureSlot.CONTENT, TextureSlot.INSIDE,
            TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate CAULDRON_LEVEL2 = ModelTemplates.create(
            Patina.MODID + ":" +
                    "template_cauldron_level2", TextureSlot.CONTENT, TextureSlot.INSIDE,
            TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate CAULDRON_LEVEL3 = ModelTemplates.create(
            Patina.MODID + ":" +
                    "template_cauldron_level3", TextureSlot.CONTENT, TextureSlot.INSIDE,
            TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate CAULDRON_LEVEL4 = ModelTemplates.create(
            Patina.MODID + ":" +
                    "template_cauldron_level4", TextureSlot.CONTENT, TextureSlot.INSIDE,
            TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate CAULDRON_LEVEL5 = ModelTemplates.create(
            Patina.MODID + ":" +
                    "template_cauldron_level5", TextureSlot.CONTENT, TextureSlot.INSIDE,
            TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate CAULDRON_LEVEL6 = ModelTemplates.create(
            Patina.MODID + ":" +
                    "template_cauldron_level6", TextureSlot.CONTENT, TextureSlot.INSIDE,
            TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate CAULDRON_LEVEL7 = ModelTemplates.create(
            Patina.MODID + ":" +
                    "template_cauldron_level7", TextureSlot.CONTENT, TextureSlot.INSIDE,
            TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate CAULDRON_LEVEL8 = ModelTemplates.create(
            Patina.MODID + ":" +
                    "template_cauldron_level8", TextureSlot.CONTENT, TextureSlot.INSIDE,
            TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate CAULDRON_LEVEL9 = ModelTemplates.create(
            Patina.MODID + ":" +
                    "template_cauldron_level9", TextureSlot.CONTENT, TextureSlot.INSIDE,
            TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE);
    public static final ModelTemplate CAULDRON_FULL = ModelTemplates.create(
            Patina.MODID + ":" +
                    "template_cauldron_full", TextureSlot.CONTENT, TextureSlot.INSIDE,
            TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE);
    //endregion cauldron

    public ModModelProvider(PackOutput output) {
        super(output, Patina.MODID);
    }


    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        //region items
        gen = itemModels;
        itemModels.generateFlatItem(ModItems.COPPER_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_PICK.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_HELMET.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_CHESTPLATE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_LEGGINGS.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_BOOTS.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.CRYSTAL_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.CRYSTAL_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.CRYSTAL_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.CRYSTAL_PICK.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.CRYSTAL_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
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

        itemModels.generateFlatItem(ModItems.RAW_DELTITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_BISMUTH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BISMUTH_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_NUGGET.get(), ModelTemplates.FLAT_ITEM);
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
        itemModels.generateFlatItem(ModItems.ESSENCE_BUCKET.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.CRAB_CLAW.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.COPPER_CLAW.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.DRAGON_CLAW.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.itemModelOutput.accept(ModItems.COPPER_CHUNK.get(), generateOreItem(ModItems.COPPER_CHUNK.get(),itemType.CHUNK));
        itemModels.itemModelOutput.accept(ModItems.COPPER_CLUMP.get(), generateOreItem(ModItems.COPPER_CLUMP.get(),itemType.CLUMP));
        itemModels.itemModelOutput.accept(ModItems.COPPER_LUMP.get(), generateOreItem(ModItems.COPPER_LUMP.get(),itemType.LUMP));
        itemModels.itemModelOutput.accept(ModItems.COPPER_HUNK.get(), generateOreItem(ModItems.COPPER_HUNK.get(),itemType.HUNK));
        itemModels.itemModelOutput.accept(ModItems.COPPER_FLAKE.get(), generateOreItem(ModItems.COPPER_FLAKE.get(),itemType.FLAKE));
        itemModels.itemModelOutput.accept(ModItems.COPPER_COBBLE.get(), generateOreItem(ModItems.COPPER_COBBLE.get(),itemType.COBBLE));
        itemModels.itemModelOutput.accept(ModItems.COPPER_GRAVEL.get(), generateOreItem(ModItems.COPPER_GRAVEL.get(),itemType.GRAVEL));
        itemModels.itemModelOutput.accept(ModItems.COPPER_PEBBLE.get(), generateOreItem(ModItems.COPPER_PEBBLE.get(),itemType.PEBBLE));
        itemModels.itemModelOutput.accept(ModItems.COPPER_BLEND.get(), generateOreItem(ModItems.COPPER_BLEND.get(),itemType.BLEND));
        itemModels.itemModelOutput.accept(ModItems.COPPER_SLAG.get(), generateOreItem(ModItems.COPPER_SLAG.get(),itemType.SLAG));
        itemModels.itemModelOutput.accept(ModItems.COPPER_MIX.get(), generateOreItem(ModItems.COPPER_MIX.get(),itemType.MIX));
        itemModels.itemModelOutput.accept(ModItems.COPPER_GRIT.get(), generateOreItem(ModItems.COPPER_GRIT.get(),itemType.GRIT));
        itemModels.itemModelOutput.accept(ModItems.IRON_CHUNK.get(), generateOreItem(ModItems.IRON_CHUNK.get(),itemType.CHUNK));
        itemModels.itemModelOutput.accept(ModItems.IRON_CLUMP.get(), generateOreItem(ModItems.IRON_CLUMP.get(),itemType.CLUMP));
        itemModels.itemModelOutput.accept(ModItems.IRON_LUMP.get(), generateOreItem(ModItems.IRON_LUMP.get(),itemType.LUMP));
        itemModels.itemModelOutput.accept(ModItems.IRON_HUNK.get(), generateOreItem(ModItems.IRON_HUNK.get(),itemType.HUNK));
        itemModels.itemModelOutput.accept(ModItems.IRON_FLAKE.get(), generateOreItem(ModItems.IRON_FLAKE.get(),itemType.FLAKE));
        itemModels.itemModelOutput.accept(ModItems.IRON_COBBLE.get(), generateOreItem(ModItems.IRON_COBBLE.get(),itemType.COBBLE));
        itemModels.itemModelOutput.accept(ModItems.IRON_GRAVEL.get(), generateOreItem(ModItems.IRON_GRAVEL.get(),itemType.GRAVEL));
        itemModels.itemModelOutput.accept(ModItems.IRON_PEBBLE.get(), generateOreItem(ModItems.IRON_PEBBLE.get(),itemType.PEBBLE));
        itemModels.itemModelOutput.accept(ModItems.IRON_BLEND.get(), generateOreItem(ModItems.IRON_BLEND.get(),itemType.BLEND));
        itemModels.itemModelOutput.accept(ModItems.IRON_SLAG.get(), generateOreItem(ModItems.IRON_SLAG.get(),itemType.SLAG));
        itemModels.itemModelOutput.accept(ModItems.IRON_MIX.get(), generateOreItem(ModItems.IRON_MIX.get(),itemType.MIX));
        itemModels.itemModelOutput.accept(ModItems.IRON_GRIT.get(), generateOreItem(ModItems.IRON_GRIT.get(),itemType.GRIT));
        itemModels.itemModelOutput.accept(ModItems.GOLD_CHUNK.get(), generateOreItem(ModItems.GOLD_CHUNK.get(),itemType.CHUNK));
        itemModels.itemModelOutput.accept(ModItems.GOLD_CLUMP.get(), generateOreItem(ModItems.GOLD_CLUMP.get(),itemType.CLUMP));
        itemModels.itemModelOutput.accept(ModItems.GOLD_LUMP.get(), generateOreItem(ModItems.GOLD_LUMP.get(),itemType.LUMP));
        itemModels.itemModelOutput.accept(ModItems.GOLD_HUNK.get(), generateOreItem(ModItems.GOLD_HUNK.get(),itemType.HUNK));
        itemModels.itemModelOutput.accept(ModItems.GOLD_FLAKE.get(), generateOreItem(ModItems.GOLD_FLAKE.get(),itemType.FLAKE));
        itemModels.itemModelOutput.accept(ModItems.GOLD_COBBLE.get(), generateOreItem(ModItems.GOLD_COBBLE.get(),itemType.COBBLE));
        itemModels.itemModelOutput.accept(ModItems.GOLD_GRAVEL.get(), generateOreItem(ModItems.GOLD_GRAVEL.get(),itemType.GRAVEL));
        itemModels.itemModelOutput.accept(ModItems.GOLD_PEBBLE.get(), generateOreItem(ModItems.GOLD_PEBBLE.get(),itemType.PEBBLE));
        itemModels.itemModelOutput.accept(ModItems.GOLD_BLEND.get(), generateOreItem(ModItems.GOLD_BLEND.get(),itemType.BLEND));
        itemModels.itemModelOutput.accept(ModItems.GOLD_SLAG.get(), generateOreItem(ModItems.GOLD_SLAG.get(),itemType.SLAG));
        itemModels.itemModelOutput.accept(ModItems.GOLD_MIX.get(), generateOreItem(ModItems.GOLD_MIX.get(),itemType.MIX));
        itemModels.itemModelOutput.accept(ModItems.GOLD_GRIT.get(), generateOreItem(ModItems.GOLD_GRIT.get(),itemType.GRIT));
        //itemModels.generateFlatItem(ModItems.ORE_CHUNK.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.ORE_CLUMP.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.ORE_LUMP.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.ORE_HUNK.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.ORE_FLAKE.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.ORE_COBBLE.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.ORE_GRAVEL.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.ORE_PEBBLE.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.ORE_BLEND.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.ORE_SLAG.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.ORE_MIX.get(), ModelTemplates.FLAT_ITEM);
        //itemModels.generateFlatItem(ModItems.ORE_GRIT.get(), ModelTemplates.FLAT_ITEM);

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
        //endregion items


        //ITexturedModelExtension.Provider provider = TexturedModel.createDefault(
        //        block -> new TextureMapping().put(TextureSlot.TOP, TextureMapping.getBlockTexture(block)),ModelTemplates.CUBE_BOTTOM_TOP);

        for (DeferredHolder<Block, ? extends Block> block: ModBlocks.BLOCKS.getEntries())
        {//lazy solution to get blockstates in place
            if (block != ModBlocks.APPLIANCE_PLINTH
            && block != ModBlocks.APPLIANCE_PEDESTAL
            && block != ModBlocks.APPLIANCE_ESSENCE_CAULDRON
            && block != ModBlocks.APPLIANCE_FLUID_TANK)
                blockModels.createTrivialCube(block.get());
            //cauldron is done via datagen
            //plinth is done via blockbench
            //tank is done via blockbench
        }
        itemModels.itemModelOutput.accept(ModBlocks.APPLIANCE_FLUID_TANK.asItem(),ItemModelUtils.plainModel(path("item/appliance_fluid_tank")));
        //Block block = ModBlocks.APPLIANCE_FLUID_TANK.get();
        //TextureMapping column = TextureMapping.column(TextureMapping.getBlockTexture(block, "_side"),
        //        TextureMapping.getBlockTexture(block));
        //ResourceLocation resourceLocation = ModelTemplates.CUBE_COLUMN.create(block, column, this.gen.modelOutput);
        //BlockStateGenerator generator = MultiVariantGenerator.multiVariant(block, Variant.variant()
        //        .with(VariantProperties.MODEL, resourceLocation));
        //blockModels.blockStateOutput.accept(generator);

        //blockModels.createCauldrons();
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(ModBlocks.APPLIANCE_FLUID_TANK.get())
                .with(PropertyDispatch.property(ApplianceFluidTank.HALF)
                        .select(DoubleBlockHalf.LOWER, Variant.variant()
                                .with(VariantProperties.MODEL, path("block/appliance_fluid_tank_bottom")))
                        .select(DoubleBlockHalf.UPPER, Variant.variant()
                                .with(VariantProperties.MODEL, path("block/appliance_fluid_tank_top")))));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get())
                .with(PropertyDispatch.property(ApplianceEssenceCauldronBlock.LEVEL).select(1, Variant.variant()
                .with(VariantProperties.MODEL, CAULDRON_LEVEL1.createWithSuffix(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get(), "_level1",
                        TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LIME_CONCRETE)), blockModels.modelOutput))).select(2, Variant.variant()
                        .with(VariantProperties.MODEL, CAULDRON_LEVEL2.createWithSuffix(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get(), "_level2",
                                TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LIME_CONCRETE)), blockModels.modelOutput))).select(3, Variant.variant()
                        .with(VariantProperties.MODEL, CAULDRON_LEVEL3.createWithSuffix(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get(), "_level3",
                                TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LIME_CONCRETE)), blockModels.modelOutput))).select(4, Variant.variant()
                        .with(VariantProperties.MODEL, CAULDRON_LEVEL4.createWithSuffix(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get(), "_level4",
                                TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LIME_CONCRETE)), blockModels.modelOutput))).select(5, Variant.variant()
                        .with(VariantProperties.MODEL, CAULDRON_LEVEL5.createWithSuffix(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get(), "_level5",
                                TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LIME_CONCRETE)), blockModels.modelOutput))).select(6, Variant.variant()
                        .with(VariantProperties.MODEL, CAULDRON_LEVEL6.createWithSuffix(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get(), "_level6",
                                TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LIME_CONCRETE)), blockModels.modelOutput))).select(7, Variant.variant()
                        .with(VariantProperties.MODEL, CAULDRON_LEVEL7.createWithSuffix(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get(), "_level7",
                                TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LIME_CONCRETE)), blockModels.modelOutput))).select(8, Variant.variant()
                        .with(VariantProperties.MODEL, CAULDRON_LEVEL8.createWithSuffix(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get(), "_level8",
                                TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LIME_CONCRETE)), blockModels.modelOutput))).select(9, Variant.variant()
                        .with(VariantProperties.MODEL, CAULDRON_LEVEL9.createWithSuffix(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get(), "_level9",
                        TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LIME_CONCRETE)), blockModels.modelOutput))).select(10, Variant.variant()
                .with(VariantProperties.MODEL, ModelTemplates.CAULDRON_FULL.createWithSuffix(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get(), "_full",
                        TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LIME_CONCRETE)), blockModels.modelOutput)))));

    }
    ResourceLocation path(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(Patina.MODID, path);
    }

    ItemModel.Unbaked generateFlaskBottle(Item item, String suffix)
    {
        ResourceLocation flask = ModelLocationUtils.getModelLocation(item);
        ResourceLocation fluid = flask.withSuffix(suffix);
        return ItemModelUtils.tintedModel(gen.generateLayeredItem(fluid, fluid, flask),new Potion(-13083194));
    }
    enum itemType{
        CHUNK,
        CLUMP,
        LUMP,
        HUNK,
        FLAKE,
        COBBLE,
        GRAVEL,
        PEBBLE,
        BLEND,
        SLAG,
        MIX,
        GRIT
    }
    ResourceLocation oreTexture(itemType type)
    {
        String string = "item/crab_claw";
        switch (type)
        {
            case CHUNK -> string = "item/ore_chunk";
            case CLUMP -> string = "item/ore_clump";
            case LUMP -> string = "item/ore_lump";
            case HUNK -> string = "item/ore_hunk";
            case FLAKE -> string = "item/ore_flake";
            case COBBLE -> string = "item/ore_cobble";
            case GRAVEL -> string = "item/ore_gravel";
            case PEBBLE -> string = "item/ore_pebble";
            case BLEND -> string = "item/ore_blend";
            case SLAG -> string = "item/ore_slag";
            case MIX -> string = "item/ore_mix";
            case GRIT -> string = "item/ore_grit";
        }
        return ResourceLocation.fromNamespaceAndPath(Patina.MODID,string);
    }
    public int oreColor(Item item)
    {
        String string = BuiltInRegistries.ITEM.getKey(item).getPath();
        if (string.contains("copper"))
            return ARGB.opaque(13196853);
        if (string.contains("iron"))
            return ARGB.opaque(11505271);
        if (string.contains("gold"))
            return ARGB.opaque(16237617);
        return ARGB.opaque(0);
    }
    ItemModel.Unbaked generateOreItem(Item item, itemType type)
    {
        ResourceLocation texture = oreTexture(type);
        int color = oreColor(item);
        TextureMapping mapping = new TextureMapping().put(TextureSlot.LAYER0, texture);
        ResourceLocation location = ModelTemplates.FLAT_ITEM.create(item, mapping, gen.modelOutput);
        return ItemModelUtils.tintedModel(location, new Constant(color));
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().filter(entry ->
                !entry.is(ModBlocks.APPLIANCE_PLINTH)
         && !entry.is(ModBlocks.APPLIANCE_PEDESTAL));
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream();
    }
}

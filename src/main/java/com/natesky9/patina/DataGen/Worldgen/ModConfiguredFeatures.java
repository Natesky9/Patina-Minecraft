package com.natesky9.patina.DataGen.Worldgen;

import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterloggedTransparentBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_PRISMARINE_CRYSTALS = registerKey("overworld_prismatic_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> BASALT_DELTA_DELTITE = registerKey("nether_deltite_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> END_CHROMATIC_ORE = registerKey("end_chromatic_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context)
    {
        RuleTest waterReplacables = new BlockMatchTest(Blocks.WATER);
        RuleTest basaltReplacables = new BlockMatchTest(Blocks.BASALT);
        RuleTest endstoneReplacables = new BlockMatchTest(Blocks.END_STONE);



        register(context, OVERWORLD_PRISMARINE_CRYSTALS, Feature.ORE, new OreConfiguration(
                waterReplacables,ModBlocks.PRISMATIC_ORE.get().defaultBlockState()
                .setValue(WaterloggedTransparentBlock.WATERLOGGED,true), 9));
        register(context, BASALT_DELTA_DELTITE, Feature.ORE, new OreConfiguration(
                basaltReplacables,ModBlocks.DELTITE_ORE.get().defaultBlockState(),4));
        register(context, END_CHROMATIC_ORE, Feature.ORE, new OreConfiguration(
                endstoneReplacables,ModBlocks.CHROMATIC_ORE.get().defaultBlockState(), 6));
    }
    //
    public static ResourceKey<ConfiguredFeature<?,?>>  registerKey(String name)
    {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Patina.MODID,name));
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>>
    void register(BootstrapContext<ConfiguredFeature<?,?>> context,
                  ResourceKey<ConfiguredFeature<?,?> >key, F feature, FC configuration)
    {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}

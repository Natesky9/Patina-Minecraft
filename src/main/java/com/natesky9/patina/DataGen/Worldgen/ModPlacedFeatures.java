package com.natesky9.patina.DataGen.Worldgen;

import com.natesky9.patina.Patina;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> OVERWORLD_PRISMATIC_PLACED_KEY = registerKey("prismatic_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_DELTITE_PLACED_KEY = registerKey("deltite_ore_placed");
    public static final ResourceKey<PlacedFeature> END_CHROMATIC_PLACED_KEY = registerKey("chromatic_ore_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context)
    {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, OVERWORLD_PRISMATIC_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_PRISMARINE_CRYSTALS),
                commonOrePlacement(8,HeightRangePlacement.uniform(VerticalAnchor.absolute(8),VerticalAnchor.absolute(48))));
        register(context, NETHER_DELTITE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.BASALT_DELTA_DELTITE),
                commonOrePlacement(16,HeightRangePlacement.uniform(VerticalAnchor.absolute(32),VerticalAnchor.absolute(120))));
        register(context, END_CHROMATIC_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_CHROMATIC_ORE),
                commonOrePlacement(8,HeightRangePlacement.uniform(VerticalAnchor.absolute(32),VerticalAnchor.absolute(96))));
    }
    //

    private static List<PlacementModifier> orePlacement(PlacementModifier countPlacement, PlacementModifier heightRange) {
        return List.of(countPlacement, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }

    private static List<PlacementModifier> rareOrePlacement(int chance, PlacementModifier heightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(chance), heightRange);
    }
    //
    private static ResourceKey<PlacedFeature> registerKey(String name)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Patina.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?,?>> configuration,
                                 List<PlacementModifier> modifiers)
    {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}

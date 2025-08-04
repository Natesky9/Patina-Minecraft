package com.natesky9.patina.DataGen;

import com.natesky9.patina.Patina;
import com.natesky9.patina.DataGen.Worldgen.ModBiomeModifiers;
import com.natesky9.patina.DataGen.Worldgen.ModConfiguredFeatures;
import com.natesky9.patina.DataGen.Worldgen.ModPlacedFeatures;
import com.natesky9.patina.init.ModEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackBuiltinEntriesProvider {
    //TODO: create datagen and add this
    public static DatapackBuiltinEntriesProvider Make(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        final RegistrySetBuilder builder = new RegistrySetBuilder();
        builder.add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);
        builder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        builder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
        builder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);
        return new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, builder, Set.of(Patina.MODID));
    }
}

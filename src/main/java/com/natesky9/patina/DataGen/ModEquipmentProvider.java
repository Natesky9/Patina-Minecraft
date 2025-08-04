package com.natesky9.patina.DataGen;

import com.natesky9.patina.Patina;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModEquipmentProvider implements DataProvider {
    private final PackOutput.PathProvider path;

    public ModEquipmentProvider(PackOutput output) {
        this.path = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    private void add(BiConsumer<ResourceLocation, EquipmentClientInfo> registrar) {
        registrar.accept(
                // Must match Equippable#assetId
                ResourceLocation.fromNamespaceAndPath(Patina.MODID, "copper"),
                EquipmentClientInfo.builder()
                        // For humanoid head, chest, and feet
                        .addLayers(
                                EquipmentClientInfo.LayerType.HUMANOID,
                                // Base texture
                                new EquipmentClientInfo.Layer(
                                        // The relative texture of the armor
                                        // Points to assets/examplemod/textures/entity/equipment/copper/outer.png
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "copper_outer"),
                                        Optional.empty(),
                                        false
                                )
                                // Overlay texture
                                //new EquipmentClientInfo.Layer(
                                //        // The overlay texture
                                //        // Points to assets/examplemod/textures/entity/equipment/copper/outer_overlay.png
                                //        ResourceLocation.fromNamespaceAndPath("examplemod", "copper/outer_overlay"),
                                //        // An RGB value (always opaque color)
                                //        // When not specified, set to 0 (meaning transparent or invisible)
                                //        Optional.of(new EquipmentClientInfo.Dyeable(Optional.of(0x7683DE))),
                                //        false
                                //)
                        )
                        // For humanoid legs
                        .addLayers(
                                EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                                new EquipmentClientInfo.Layer(
                                        // Points to assets/examplemod/textures/entity/equipment/copper/inner.png
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "copper_inner"),
                                        Optional.empty(),
                                        false
                                )
                                //new EquipmentClientInfo.Layer(
                                //        // Points to assets/examplemod/textures/entity/equipment/copper/inner_overlay.png
                                //        ResourceLocation.fromNamespaceAndPath("examplemod", "copper/inner_overlay"),
                                //        Optional.of(new EquipmentClientInfo.Dyeable(Optional.of(0x7683DE))),
                                //        false
                                //)
                        )
                        // For wolf armor
                        //.addLayers(
                        //        EquipmentClientInfo.LayerType.WOLF_BODY,
                        //        // Base texture
                        //        new EquipmentClientInfo.Layer(
                        //                // Points to assets/examplemod/textures/entity/equipment/copper/wolf.png
                        //                ResourceLocation.fromNamespaceAndPath("examplemod", "copper/wolf"),
                        //                Optional.empty(),
                        //                // When true, uses the texture passed into the layer renderer instead
                        //                true
                        //        )
                        //)
                        // For horse armor
                        //.addLayers(
                        //        EquipmentClientInfo.LayerType.HORSE_BODY,
                        //        // Base texture
                        //        new EquipmentClientInfo.Layer(
                        //                // Points to assets/examplemod/textures/entity/equipment/copper/horse.png
                        //                ResourceLocation.fromNamespaceAndPath("examplemod", "copper/horse"),
                        //                Optional.empty(),
                        //                true
                        //        )
                        //)
                        .build()
        );
        //region crystal
        registrar.accept(
                ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_prime"),
                EquipmentClientInfo.builder()
                        .addLayers(EquipmentClientInfo.LayerType.HUMANOID,
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_prime_outer"),
                                        Optional.empty(), false))
                        .addLayers(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_prime_inner"),
                                        Optional.empty(), false)).build());
        registrar.accept(
                ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_anima"),
                EquipmentClientInfo.builder()
                        .addLayers(EquipmentClientInfo.LayerType.HUMANOID,
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_anima_outer"),
                                        Optional.empty(), false))
                        .addLayers(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_anima_inner"),
                                        Optional.empty(), false)).build());
        registrar.accept(
                ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_fortis"),
                EquipmentClientInfo.builder()
                        .addLayers(EquipmentClientInfo.LayerType.HUMANOID,
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_fortis_outer"),
                                        Optional.empty(), false))
                        .addLayers(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_fortis_inner"),
                                        Optional.empty(), false)).build());
        registrar.accept(
                ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_ferus"),
                EquipmentClientInfo.builder()
                        .addLayers(EquipmentClientInfo.LayerType.HUMANOID,
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_ferus_outer"),
                                        Optional.empty(), false))
                        .addLayers(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_ferus_inner"),
                                        Optional.empty(), false)).build());
        registrar.accept(
                ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_imperium"),
                EquipmentClientInfo.builder()
                        .addLayers(EquipmentClientInfo.LayerType.HUMANOID,
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_imperium_outer"),
                                        Optional.empty(), false))
                        .addLayers(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(Patina.MODID, "crystal_imperium_inner"),
                                        Optional.empty(), false)).build());
        //endregion crystal
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Map<ResourceLocation, EquipmentClientInfo> map = new HashMap<>();
        this.add((name, info) -> {
            if (map.putIfAbsent(name, info) != null) {
                throw new IllegalStateException("Tried to register equipment client info twice for id: " + name);
            }
        });
        return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, path, map);
    }

    @Override
    public String getName() {
        return "Equipment Client Infos: " + Patina.MODID;
    }
}
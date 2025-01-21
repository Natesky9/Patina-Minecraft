package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import com.natesky9.patina.entity.*;
import com.natesky9.patina.entity.BearPrince.BearPrince;
import com.natesky9.patina.entity.BearPrince.BearStarEntity;
import com.natesky9.patina.entity.BeePrincess.BeePrincess;
import com.natesky9.patina.entity.Fishing.FishingBobbler;
import com.natesky9.patina.entity.Fishing.FishingLootBobbler;
import com.natesky9.patina.entity.SpiderNest.Spidernest;
import com.natesky9.patina.entity.SpiderQueen.SpiderQueen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Patina.MODID);
    //region entities
    public static final RegistryObject<EntityType<FishingBobbler>> FISHING_BOBBLER =
            ENTITY_TYPES.register("fishing_bobbler",
                    () -> EntityType.Builder.<FishingBobbler>of(FishingBobbler::new, MobCategory.MISC)
                            .sized(.25f,.25f)
                            .noSave().noSummon().clientTrackingRange(4).updateInterval(5)
                            .build(ResourceLocation.fromNamespaceAndPath(Patina.MODID,"fishing_bobber").toString()));
    public static final RegistryObject<EntityType<FishingLootBobbler>> FISHING_LOOT_BOBBLER =
            ENTITY_TYPES.register("fishing_loot_bobbler",
                    () -> EntityType.Builder.<FishingLootBobbler>of(FishingLootBobbler::new, MobCategory.MISC)
                            .sized(.25f,.25f)
                            .noSave().noSummon().clientTrackingRange(4).updateInterval(5)
                            .build(ResourceLocation.fromNamespaceAndPath(Patina.MODID,"fishing_loot_bobbler").toString()));
    public static final RegistryObject<EntityType<BeePrincess>> BEE_BOSS =
            ENTITY_TYPES.register("bee_princess",
                    () -> EntityType.Builder.of(BeePrincess::new, MobCategory.MONSTER)
                            .sized(1.4F,3)
                            .build(ResourceLocation.fromNamespaceAndPath(Patina.MODID, "bee_queen").toString()));
    public static final RegistryObject<EntityType<BearPrince>> BEAR_BOSS =
            ENTITY_TYPES.register("bear_prince",
                    () -> EntityType.Builder.of(BearPrince::new, MobCategory.MONSTER)
                            .sized(1.98f,1.98f)
                            .build(ResourceLocation.fromNamespaceAndPath(Patina.MODID, "bear_prince").toString()));
    public static final RegistryObject<EntityType<SpiderQueen>> SPIDER_BOSS =
            ENTITY_TYPES.register("spider_queen",
                    () -> EntityType.Builder.of(SpiderQueen::new, MobCategory.MONSTER)
                            .sized(3,3)
                            .build(ResourceLocation.fromNamespaceAndPath(Patina.MODID, "spider_queen").toString()));

    public static final RegistryObject<EntityType<SandwormKing>> SANDWORM_BOSS =
            ENTITY_TYPES.register("sandworm_king",
                    () -> EntityType.Builder.of(SandwormKing::new, MobCategory.MONSTER)
                            .sized(3,3)
                            .build(ResourceLocation.fromNamespaceAndPath(Patina.MODID, "sandworm_king").toString()));

    public static final RegistryObject<EntityType<SlimeKnight>> SLIME_BOSS =
            ENTITY_TYPES.register("slime_knight",
                    () -> EntityType.Builder.of(SlimeKnight::new, MobCategory.MONSTER)
                            .sized(3,3)
                            .build(ResourceLocation.fromNamespaceAndPath(Patina.MODID, "slime_knight").toString()));

    public static final RegistryObject<EntityType<PiglinBaron>> PIGLIN_BOSS =
            ENTITY_TYPES.register("piglin_baron",
                    () -> EntityType.Builder.of(PiglinBaron::new, MobCategory.MONSTER)
                            .sized(3,3)
                            .build(ResourceLocation.fromNamespaceAndPath(Patina.MODID, "piglin_king").toString()));

    public static final RegistryObject<EntityType<SinisterApothecary>> WITCH_BOSS =
            ENTITY_TYPES.register("apothecary",
                    () -> EntityType.Builder.of(SinisterApothecary::new, MobCategory.MONSTER)
                            .sized(3,3)
                            .build(ResourceLocation.fromNamespaceAndPath(Patina.MODID, "apothecary").toString()));
    //
    public static final RegistryObject<EntityType<BearStarEntity>> BEAR_STAR =
            ENTITY_TYPES.register("bear_star",
                    () -> EntityType.Builder.of(BearStarEntity::new, MobCategory.MISC)
                            .sized(.95f,.95f)
                            .build(ResourceLocation.fromNamespaceAndPath(Patina.MODID, "bear_star").toString()));
    public static final RegistryObject<EntityType<Spidernest>> SPIDER_NEST =
            ENTITY_TYPES.register("spider_nest",
                    () -> EntityType.Builder.of(Spidernest::new, MobCategory.MONSTER)
                            .sized(1,1)
                            .build(ResourceLocation.fromNamespaceAndPath(Patina.MODID, "spider_nest").toString()));
    //endregion entities
    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}

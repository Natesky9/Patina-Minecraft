package com.natesky9.patina.init;

import com.natesky9.patina.Block.ApplianceConsolidator.ApplianceConsolidatorEntity;
import com.natesky9.patina.Block.ApplianceIcebox.ApplianceIceboxEntity;
import com.natesky9.patina.Block.Plinth.AppliancePlinthEntity;
import com.natesky9.patina.Block.ApplianceWardrobe.ApplianceWardrobeEntity;
import com.natesky9.patina.Block.Gravestone.GravestoneBlockEntity;
import com.natesky9.patina.Block.MachineAlembic.MachineAlembicEntity;
import com.natesky9.patina.Block.MachineEnchanter.MachineEnchanterEntity;
import com.natesky9.patina.Block.MachineEvaporator.MachineEvaporatorEntity;
import com.natesky9.patina.Block.MachineFoundry.MachineFoundryEntity;
import com.natesky9.patina.Block.MachineMincerator.MachineMinceratorEntity;
import com.natesky9.patina.Block.MachineTextiler.MachineTextilerEntity;
import com.natesky9.patina.Patina;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Patina.MODID);

    //--------------------------------------------------//
    //public static final RegistryObject<BlockEntityType<MachineTemplateEntity>> MACHINE_TEMPLATE_BLOCK_ENTITY =
    //        BLOCK_ENTITIES.register("machine_template_block_entity", () ->
    //                BlockEntityType.Builder.of(MachineTemplateEntity::new,
    //                        ModBlocks.MACHINE_TEMPLATE.get()).build(null));
    //
    public static final RegistryObject<BlockEntityType<MachineEvaporatorEntity>> MACHINE_EVAPORATOR_ENTITY =
            BLOCK_ENTITIES.register("machine_evaporator_entity",
            () -> BlockEntityType.Builder.of(MachineEvaporatorEntity::new,
            ModBlocks.MACHINE_EVAPORATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineFoundryEntity>> MACHINE_FOUNDRY_ENTITY =
            BLOCK_ENTITIES.register("machine_foundry_entity",
            () -> BlockEntityType.Builder.of(MachineFoundryEntity::new,
            ModBlocks.MACHINE_FOUNDRY.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineMinceratorEntity>> MACHINE_MINCERATOR_ENTITY =
            BLOCK_ENTITIES.register("machine_mincerator_entity",
            () -> BlockEntityType.Builder.of(MachineMinceratorEntity::new,
            ModBlocks.MACHINE_MINCERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineAlembicEntity>> MACHINE_ALEMBIC_ENTITY =
            BLOCK_ENTITIES.register("machine_alembic_entity",
            () -> BlockEntityType.Builder.of(MachineAlembicEntity::new,
            ModBlocks.MACHINE_ALEMBIC.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineEnchanterEntity>> MACHINE_ENCHANTER_ENTITY =
            BLOCK_ENTITIES.register("machine_enchanter_entity",
            () -> BlockEntityType.Builder.of(MachineEnchanterEntity::new,
            ModBlocks.MACHINE_ENCHANTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineTextilerEntity>> MACHINE_TEXTILER_ENTITY =
            BLOCK_ENTITIES.register("machine_textiler_entity",
            () -> BlockEntityType.Builder.of(MachineTextilerEntity::new,
            ModBlocks.MACHINE_TEXTILER.get()).build(null));

    public static final RegistryObject<BlockEntityType<ApplianceWardrobeEntity>> APPLIANCE_WARDROBE_ENTITY =
            BLOCK_ENTITIES.register("appliance_wardrobe_entity",
            () -> BlockEntityType.Builder.of(ApplianceWardrobeEntity::new,
            ModBlocks.APPLIANCE_WARDROBE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ApplianceIceboxEntity>> APPLIANCE_ICEBOX_ENTITY =
            BLOCK_ENTITIES.register("appliance_icebox_entity",
            () -> BlockEntityType.Builder.of(ApplianceIceboxEntity::new,
            ModBlocks.APPLIANCE_ICEBOX.get()).build(null));
    public static final RegistryObject<BlockEntityType<ApplianceConsolidatorEntity>> APPLIANCE_CONSOLIDATOR_ENTITY =
            BLOCK_ENTITIES.register("appliance_arcane_consolidator_entity",
            () -> BlockEntityType.Builder.of(ApplianceConsolidatorEntity::new,
            ModBlocks.APPLIANCE_ARCANE_CONSOLIDATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<AppliancePlinthEntity>> APPLIANCE_PLINTH_ENTITY =
            BLOCK_ENTITIES.register("appliance_plinth_entity",
            () -> BlockEntityType.Builder.of(AppliancePlinthEntity::new,
            ModBlocks.APPLIANCE_PLINTH.get()).build(null));


    public static final RegistryObject<BlockEntityType<GravestoneBlockEntity>> GRAVESTONE_ENTITY =
            BLOCK_ENTITIES.register("gravestone_entity",
            () -> BlockEntityType.Builder.of(GravestoneBlockEntity::new,
            ModBlocks.GRAVESTONE.get()).build(null));

    //--------------------------------------------------//
    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}

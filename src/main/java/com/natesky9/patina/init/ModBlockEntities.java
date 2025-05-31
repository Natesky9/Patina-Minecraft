package com.natesky9.patina.init;

import com.natesky9.patina.Blocks.*;
import com.natesky9.patina.Blocks.MachineAlembicEntity;
import com.natesky9.patina.Patina;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Patina.MODID);
    //
    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<PlinthEntity>> PLINTH_ENTITY =
            BLOCK_ENTITIES.register("plinth",
                    () -> new BlockEntityType<>(PlinthEntity::new,
                            ModBlocks.APPLIANCE_PLINTH.get()));
    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<ArcaneMatrixEntity>> MATRIX_ENTITY =
            BLOCK_ENTITIES.register("matrix",
                    () -> new BlockEntityType<>(ArcaneMatrixEntity::new,
                            ModBlocks.MACHINE_MATRIX.get()));
    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<MachineAlembicEntity>> ALEMBIC_ENTITY =
            BLOCK_ENTITIES.register("alembic",
                    () -> new BlockEntityType<>(MachineAlembicEntity::new,
                            ModBlocks.MACHINE_ALEMBIC.get()));
    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<MachineEvaporatorEntity>> EVAPORATOR_ENTITY =
            BLOCK_ENTITIES.register("evaporator",
                    () -> new BlockEntityType<>(MachineEvaporatorEntity::new,
                            ModBlocks.MACHINE_EVAPORATOR.get()));
    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<MachineMinceratorEntity>> MINCERATOR_ENTITY =
            BLOCK_ENTITIES.register("mincerator",
                    () -> new BlockEntityType<>(MachineMinceratorEntity::new,
                            ModBlocks.MACHINE_MINCERATOR.get()));
    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<MachineTextilerEntity>> TEXTILER_ENTITY =
            BLOCK_ENTITIES.register("textiler",
                    () -> new BlockEntityType<>(MachineTextilerEntity::new,
                            ModBlocks.MACHINE_MINCERATOR.get()));
    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<MachineKwernEntity>> KWERN_ENTITY =
            BLOCK_ENTITIES.register("kwern",
                    () -> new BlockEntityType<>(MachineKwernEntity::new,
                            ModBlocks.MACHINE_KWERN.get()));
    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<MachineFoundryEntity>> FOUNDRY_ENTITY =
            BLOCK_ENTITIES.register("foundry",
                    () -> new BlockEntityType<>(MachineFoundryEntity::new,
                            ModBlocks.MACHINE_FOUNDRY.get()));
    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<ApplianceIceboxEntity>> ICEBOX_ENTITY =
            BLOCK_ENTITIES.register("icebox",
                    () -> new BlockEntityType<>(ApplianceIceboxEntity::new,
                            ModBlocks.APPLIANCE_ICEBOX.get()));
    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<ApplianceWardrobeEntity>> WARDROBE_ENTITY =
            BLOCK_ENTITIES.register("wardrobe",
                    () -> new BlockEntityType<>(ApplianceWardrobeEntity::new,
                            ModBlocks.APPLIANCE_WARDROBE.get()));
    //
    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}

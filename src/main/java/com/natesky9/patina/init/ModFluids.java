package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, Patina.MODID);
    //
    public static final DeferredHolder<Fluid, FlowingFluid> ESSENCE_SOURCE = FLUIDS.register("essence_still",
            () -> new BaseFlowingFluid.Source(ModFluids.ESSENCE_PROPERTIES));

    public static final DeferredHolder<Fluid, FlowingFluid> ESSENCE_FLOWING = FLUIDS.register("essence_flowing",
            () -> new BaseFlowingFluid.Flowing(ModFluids.ESSENCE_PROPERTIES));
    //
    public static final BaseFlowingFluid.Properties ESSENCE_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.ESSENCE, ESSENCE_SOURCE, ESSENCE_FLOWING)
            .block(ModBlocks.ESSENCE_FLUID::value)
            .bucket(ModItems.ESSENCE_BUCKET);
    //
    public static void register(IEventBus eventBus)
    {
        FLUIDS.register(eventBus);
    }
}

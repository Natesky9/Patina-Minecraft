package com.natesky9.patina.init;

import com.natesky9.patina.Fluids.EssenceFluid;
import com.natesky9.patina.Patina;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUIDS =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, Patina.MODID);
    //

    public static final DeferredHolder<FluidType, FluidType> ESSENCE =
            FLUIDS.register("essence",
                    () -> new EssenceFluid(FluidType.Properties.create().density(10).viscosity(5)));
    //
    public static void register(IEventBus eventBus)
    {
        FLUIDS.register(eventBus);
    }
}

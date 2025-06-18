package com.natesky9.patina.Fluids;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidType;

public class EssenceFluid extends FluidType {
    public static final ResourceLocation ESSENCE_STILL = ResourceLocation.withDefaultNamespace("block/lime_terracotta");
    public static final ResourceLocation ESSENCE_FLOWING = ResourceLocation.withDefaultNamespace("block/lime_concrete");
    public static final ResourceLocation ESSENCE_OVERLAY = ResourceLocation.withDefaultNamespace("block/lime_glass");
    public EssenceFluid(Properties properties) {
        super(properties);
    }
}

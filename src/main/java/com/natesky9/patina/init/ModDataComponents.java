package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Patina.MODID);
    //

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<SimpleFluidContent>> FLUID =
            DATA_COMPONENTS.registerComponentType("fluid",
            (value) -> value.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC));

    //enchantment data components
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Unit>> KEEP_INVENTORY_ITEM =
            DATA_COMPONENTS.registerComponentType("keep_item",
                    (value) -> value.persistent(Unit.CODEC));
    //
    public static void register(IEventBus eventBus)
    {
        DATA_COMPONENTS.register(eventBus);
    }
}

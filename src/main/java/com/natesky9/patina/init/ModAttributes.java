package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Patina.MODID);
    //
    public static final DeferredHolder<Attribute,Attribute> GLUTTONY = ATTRIBUTES.register("gluttony",
            () -> new RangedAttribute("attribute.name.stomach_drain",1,1,5)
                    .setSyncable(true));
    public static final DeferredHolder<Attribute,Attribute> GREED = ATTRIBUTES.register("greed",
            () -> new RangedAttribute("attribute.name.greed",1,1,1)
                    .setSyncable(true));
    public static final DeferredHolder<Attribute,Attribute> SLOTH = ATTRIBUTES.register("sloth",
            () -> new RangedAttribute("attribute.name.sloth",1,1,1)
                    .setSyncable(true));
    public static final DeferredHolder<Attribute,Attribute> ENVY = ATTRIBUTES.register("envy",
            () -> new RangedAttribute("attribute.name.envy",1,1,1)
                    .setSyncable(true));
    public static final DeferredHolder<Attribute,Attribute> PRIDE = ATTRIBUTES.register("pride",
            () -> new RangedAttribute("attribute.name.pride",1,1,1)
                    .setSyncable(true));
    public static final DeferredHolder<Attribute,Attribute> LUST = ATTRIBUTES.register("lust",
            () -> new RangedAttribute("attribute.name.greed",1,1,1)
                    .setSyncable(true));
    public static final DeferredHolder<Attribute,Attribute> WRATH = ATTRIBUTES.register("wrath",
            () -> new RangedAttribute("attribute.name.wrath",1,1,1)
                    .setSyncable(true));

    public static final DeferredHolder<Attribute,Attribute> GLUTTONY_BLESSING = ATTRIBUTES.register("gluttony_blessing",
            () -> new RangedAttribute("attribute.name.stomach_capacity", 1, 1, 5)
                    .setSyncable(true));

    //
    public static void register(IEventBus eventBus)
    {
        ATTRIBUTES.register(eventBus);
    }
}

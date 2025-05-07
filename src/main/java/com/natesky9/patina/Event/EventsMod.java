package com.natesky9.patina.Event;

import com.natesky9.patina.DataGen.DataGenerators;
import com.natesky9.patina.Patina;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;


@EventBusSubscriber(modid = Patina.MODID, bus = EventBusSubscriber.Bus.MOD)
public class EventsMod {
    //
    @SubscribeEvent
    public static void Creative(BuildCreativeModeTabContentsEvent event)
    {
        //for adding stuff to vanilla tabs
    }
    @SubscribeEvent
    public static void gatherDataEvent(GatherDataEvent.Client event)
    {
        DataGenerators.gatherData(event);
    }
}

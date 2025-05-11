package com.natesky9.patina.Event;

import com.natesky9.patina.Blocks.Renderer.PlinthEntityRenderer;
import com.natesky9.patina.DataGen.DataGenerators;
import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModBlockEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
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
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntities.PLINTH_ENTITY.get(), PlinthEntityRenderer::new);
    }

}

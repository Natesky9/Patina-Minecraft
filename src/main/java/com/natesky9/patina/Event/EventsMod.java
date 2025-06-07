package com.natesky9.patina.Event;

import com.natesky9.patina.Blocks.Renderer.PlinthEntityRenderer;
import com.natesky9.patina.DataGen.DataGenerators;
import com.natesky9.patina.Patina;
import com.natesky9.patina.Screen.*;
import com.natesky9.patina.init.ModBlockEntities;
import com.natesky9.patina.init.ModMenuTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
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
    @SubscribeEvent
    public static void renderScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModMenuTypes.ALEMBIC_MENU.get(), AlembicScreen::new);
        event.register(ModMenuTypes.MINCERATOR_MENU.get(), MinceratorScreen::new);
        event.register(ModMenuTypes.FOUNDRY_MENU.get(), FoundryScreen::new);
        event.register(ModMenuTypes.TEXTILER_MENU.get(), TextilerScreen::new);
        event.register(ModMenuTypes.KWERN_MENU.get(), KwernScreen::new);
        event.register(ModMenuTypes.EVAPORATOR_MENU.get(), EvaporatorScreen::new);
        event.register(ModMenuTypes.SIEVE_MENU.get(), SieveScreen::new);

        event.register(ModMenuTypes.ICEBOX_MENU.get(), IceboxScreen::new);
        event.register(ModMenuTypes.WARDROBE_MENU.get(), WardrobeScreen::new);
    }

}

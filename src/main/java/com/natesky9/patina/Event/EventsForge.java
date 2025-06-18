package com.natesky9.patina.Event;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Test.ProperFoodRenderer;
import com.natesky9.patina.init.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Patina.MODID, bus = EventBusSubscriber.Bus.GAME)
public class EventsForge {
    //someday there will be something here...
    @SubscribeEvent
    public static void BlockBreak(BlockEvent.BreakEvent event)
    {
        GluttonyEvent.causeMiningExhaustion(event);
    }
    @SubscribeEvent
    public static void EntityHeal(LivingHealEvent event)
    {
        //GluttonyEvent.causeHealingExhaustion(event);
    }
    @SubscribeEvent
    public static void AttributeChange(LivingEquipmentChangeEvent event)
    {
        GluttonyEvent.process(event);
    }
    @SubscribeEvent
    public static void onPlayerKill(PlayerEvent.PlayerRespawnEvent event)
    {

    }

    @SubscribeEvent
    public static void RenderLayer(RenderGuiLayerEvent.Pre event)
    {
        ProperFoodRenderer.cancelVanilla(event);
    }
}

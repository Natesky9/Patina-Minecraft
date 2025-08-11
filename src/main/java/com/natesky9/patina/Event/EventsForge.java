package com.natesky9.patina.Event;

import com.natesky9.patina.Items.CrystalFlaskItem;
import com.natesky9.patina.Patina;
import com.natesky9.patina.Test.ProperFoodRenderer;
import com.natesky9.patina.init.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Patina.MODID)
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
        GluttonyEvent.causeHealingExhaustion(event);
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
    public static void PugnaTriggerEvent(LivingDamageEvent.Pre event)
    {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        for (ItemStack stack:player.getInventory().items)
        {
            PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY);
            if (!stack.is(ModItems.FLASK_PUGNA.get())) continue;
            if (stack.getDamageValue() == 0) continue;

            Iterable<MobEffectInstance> effects = contents.getAllEffects();
            boolean alreadyActive = false;
            for (MobEffectInstance instance:effects)
            {
                Holder<MobEffect> effect = instance.getEffect();
                if (player.hasEffect(effect))
                    alreadyActive = true;
            }
            if (alreadyActive) continue;
            //apply if unique

            contents.onConsume(player.level(),player,stack,null);
            stack.setDamageValue(stack.getDamageValue()-1);
            player.awardStat(Stats.ITEM_USED.get(ModItems.FLASK_PUGNA.get()));
        }
    }
    @SubscribeEvent
    public static void LivingDamageEvent(LivingDamageEvent.Post event)
    {
        if (!(event.getEntity() instanceof Player player)) return;
        for (ItemStack stack:player.getInventory().items)
        {
            if (!stack.is(ModItems.FLASK_VITA.get())) continue;
            if (stack.getDamageValue() == 0) continue;
            PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY);
            Iterable<MobEffectInstance> effects = contents.getAllEffects();
            boolean has = false;
            boolean instant = false;
            for (MobEffectInstance instance:effects)
            {
                Holder<MobEffect> effect = instance.getEffect();
                if (player.hasEffect(effect)) {
                    has = true;
                }
                if (effect.value().isInstantenous()) {
                    instant = true;
                }
            }
            //applies if it doesn't have effect but also allows instants
            if (!has || instant) {
                //apply all flasks if effect doesn't exist
                contents.onConsume(player.level(),player,stack,null);
                stack.setDamageValue(stack.getDamageValue()-1);
                player.awardStat(Stats.ITEM_USED.get(ModItems.FLASK_VITA.get()));
                return;
            }
        }
    }

    @SubscribeEvent
    public static void RenderLayer(RenderGuiLayerEvent.Pre event)
    {
        ProperFoodRenderer.cancelVanilla(event);
    }
}

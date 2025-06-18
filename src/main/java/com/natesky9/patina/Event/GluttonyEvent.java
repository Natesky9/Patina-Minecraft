package com.natesky9.patina.Event;

import com.natesky9.patina.Test.ProperFoodData;
import com.natesky9.patina.init.ModAttributes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.util.AttributeUtil;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Collection;
import java.util.Optional;

public class GluttonyEvent {
    public static void process(LivingEquipmentChangeEvent event)
    {

        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Player player)) return;
        ItemStack from = event.getFrom();
        ItemStack to = event.getTo();

        Collection<AttributeModifier> collectFrom = AttributeUtil.getSortedModifiers(from, EquipmentSlotGroup.ARMOR)
                .get(ModAttributes.GLUTTONY_BLESSING);
        Collection<AttributeModifier> collectTo = AttributeUtil.getSortedModifiers(to, EquipmentSlotGroup.ARMOR)
                .get(ModAttributes.GLUTTONY_BLESSING);

        if (!(collectFrom.isEmpty() || collectTo.isEmpty())) return;

        AttributeInstance attribute = player.getAttribute(ModAttributes.GLUTTONY_BLESSING);
        if (attribute == null) return;

        double attributeValue = attribute.getValue();
        if (!collectFrom.isEmpty())
            attributeValue -= collectFrom.stream().findAny().get().amount();
        if (!collectTo.isEmpty())
            attributeValue += collectTo.stream().findAny().get().amount();
        System.out.println(attributeValue);

        //that might cause issues, verify

        {
            FoodData data = player.foodData;

            ProperFoodData newData = new ProperFoodData();
            newData.setFoodLevel(data.getFoodLevel());
            newData.setSaturation(data.getSaturationLevel());
            newData.setMaxFoodLevel((int) attributeValue);
            player.foodData = newData;
            System.out.println("food max: " + newData.getMaxFoodLevel());
        }
    }
    public static void causeHealingExhaustion(LivingHealEvent event)
    {

        if (!(event.getEntity() instanceof Player player)) return;

        AttributeInstance existing = player.getAttribute(ModAttributes.GLUTTONY);
        if (existing == null) return;
        int value = (int) player.getAttributeValue(ModAttributes.GLUTTONY) - 1;
        player.causeFoodExhaustion(0.01F * value);
    }
    public static void causeMiningExhaustion(BlockEvent.BreakEvent event)
    {
        Player player = event.getPlayer();

        AttributeInstance existing = player.getAttribute(ModAttributes.GLUTTONY);
        if (existing == null) return;
        int value = (int) player.getAttributeValue(ModAttributes.GLUTTONY) - 1;
        player.causeFoodExhaustion(0.01F * value);
    }
}

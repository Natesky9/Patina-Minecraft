package com.natesky9.patina.Items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

public class EternaFlaskItem extends CrystalFlaskItem{
    public EternaFlaskItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (!(action == ClickAction.SECONDARY)) return false;

        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        PotionContents contentsOther = other.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

        if (contentsOther != PotionContents.EMPTY)
        {
            //set the flask to it
            stack.set(DataComponents.POTION_CONTENTS, contentsOther);
            setUses(stack, Integer.MAX_VALUE);
            if (other.getItem() instanceof CrystalFlaskItem)
                CrystalFlaskItem.setUses(other,CrystalFlaskItem.getUses(other)-1);
            else
                access.set(other.getCraftingRemainder());
            player.level().playSound(player, player,
                    SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS,1,1);
            return true;
        }
        return false;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (getUses(stack) <= 0)
            return stack;
        apply(livingEntity,stack);
        setUses(stack, 0);

        if (livingEntity instanceof Player player)
            player.getCooldowns().addCooldown(stack,Integer.MAX_VALUE);

        return stack;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player)
        {
            if (!player.getCooldowns().isOnCooldown(stack))
                setUses(stack,Integer.MAX_VALUE);
        }
    }
}

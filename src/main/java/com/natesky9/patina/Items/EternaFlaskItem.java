package com.natesky9.patina.Items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

        if (contents == contentsOther) return false;

        if (contentsOther != PotionContents.EMPTY && other.getDamageValue() > 0)
        {
            //set the flask to it
            if (stack.getItem() instanceof CrystalFlaskItem)
            {
                stack.set(DataComponents.POTION_CONTENTS, contentsOther);
                stack.setDamageValue(0);
                other.setDamageValue(other.getDamageValue()-1);
                player.level().playSound(player, player,
                        SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS,1,1);
                return true;
            }
            if (other.is(Items.POTION))
            {
                stack.set(DataComponents.POTION_CONTENTS, contentsOther);
                stack.setDamageValue(1);
                access.set(other.getCraftingRemainder());
                player.level().playSound(player, player,
                        SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS,1,1);
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY);
        contents.onConsume(level,livingEntity,stack,null);

        if (livingEntity instanceof Player player)
            player.getCooldowns().addCooldown(stack,20*60*10);

        return stack;
    }
}

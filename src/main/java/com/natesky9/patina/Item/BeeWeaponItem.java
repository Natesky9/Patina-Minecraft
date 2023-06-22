package com.natesky9.patina.Item;

import com.mojang.math.Axis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class BeeWeaponItem extends SwordItem {
    public BeeWeaponItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction.equals(ToolActions.SWORD_DIG);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.isCrouching())
            pPlayer.addDeltaMovement(pPlayer.getLookAngle().add(0,.25,0));
        else
            pPlayer.addDeltaMovement(pPlayer.getLookAngle().reverse().add(0,.25,0));
        ItemStack item = pPlayer.getItemInHand(pUsedHand);
        pPlayer.getCooldowns().addCooldown(item.getItem(),40);
        return InteractionResultHolder.consume(item);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

}
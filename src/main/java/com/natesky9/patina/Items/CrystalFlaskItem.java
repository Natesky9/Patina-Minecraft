package com.natesky9.patina.Items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

public class CrystalFlaskItem extends Item {
    public CrystalFlaskItem(Properties properties) {
        super(properties);
    }

    public static float percentFull(ItemStack stack)
    {
        //used for visual fill
        return (float) getUses(stack)/stack.getMaxDamage();
    }

    public static int getUses(ItemStack stack)
    {
        return stack.getDamageValue();
    }
    public static void setUses(ItemStack stack, int value)
    {
        stack.setDamageValue(value);
        if (getUses(stack) == 0)
            stack.set(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        return getUses(stack) > 0 ? ItemUtils.startUsingInstantly(level, player, hand) : InteractionResult.FAIL;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (getUses(stack) <= 0)
            return stack;
        apply(livingEntity,stack);
        setUses(stack, getUses(stack)-1);

        return stack;
    }

    public static void apply(LivingEntity entity, ItemStack stack)
    {
        if (!((entity.level()) instanceof ServerLevel server)) return;
        PotionContents potion = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

        if (!potion.hasEffects()) return;

        potion.getAllEffects().forEach(
                (mobEffectInstance ->
                {
                    if (mobEffectInstance.getEffect().value().isInstantenous())
                        mobEffectInstance.getEffect().value().applyInstantenousEffect(server,entity,
                                entity,entity, mobEffectInstance.getAmplifier(),1);
                    else entity.addEffect(new MobEffectInstance(mobEffectInstance));
                }));
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (!(action == ClickAction.SECONDARY)) return false;
        //not necessary since we handle that
        //if (!other.is(Items.POTION) && !(other.getItem() instanceof CrystalFlaskItem)) return false;
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        PotionContents contentsOther = other.get(DataComponents.POTION_CONTENTS);
        if (getUses(stack) > stack.getMaxDamage()) return false;
        if (contents == null || contents == contentsOther || contents == PotionContents.EMPTY)
        {
            if (other.is(Items.POTION))
            {
                stack.set(DataComponents.POTION_CONTENTS, contentsOther);
                setUses(stack, getUses(stack)+1);
                access.set(other.getCraftingRemainder());
                player.level().playSound(null, player, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1, 1);
                return true;
            }
            if (other.getItem() instanceof CrystalFlaskItem)
            {
                int current = getUses(stack);
                int currentOther = getUses(other);
                int transfer = Math.min(stack.getMaxDamage()-current, currentOther);
                stack.set(DataComponents.POTION_CONTENTS, new PotionContents(contentsOther.potion().get()));
                setUses(stack, current+transfer);
                setUses(other, currentOther-transfer);
                player.level().playSound(null, player, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1, 1);
            }
        }
        return false;
    }
}

package com.natesky9.patina.Item.flasks;

import com.natesky9.patina.init.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

import java.util.List;

public class PotionFlaskItem extends PotionItem {
    public static final int drinking = 32;

    public PotionFlaskItem(Properties pProperties) {
        super(pProperties);
    }

    public static float percentFull(ItemStack stack)
    {//used for visual fill
        if (stack.is(ModItems.ETERNA_FLASK.get()))
            return 1;
        stack.getMaxDamage();
        return (float)getUses(stack)/stack.getMaxDamage();
    }
    public static int getUses(ItemStack stack)
    {
        if (stack.is(ModItems.ETERNA_FLASK.get()))
            return 1;
        return stack.getDamageValue();
    }
    public static void setUses(ItemStack stack, int value)
    {
        if (stack.is(ModItems.ETERNA_FLASK.get()))
            return;
        stack.setDamageValue(value);
        if (getUses(stack) == 0)
        {
            stack.set(DataComponents.POTION_CONTENTS,PotionContents.EMPTY);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        return getUses(stack) > 0 ? ItemUtils.startUsingInstantly(pLevel,pPlayer,pUsedHand) : InteractionResultHolder.fail(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (getUses(pStack) <= 0)
            return pStack;//check for empty
        apply(pLivingEntity,pStack);
        setUses(pStack,getUses(pStack)-1);

        if (pLivingEntity instanceof Player player)
            player.awardStat(Stats.ITEM_USED.get(this));

        return pStack;
    }
    public static void apply(LivingEntity player,ItemStack stack)
    {
        PotionContents potion = stack.get(DataComponents.POTION_CONTENTS);

        potion.getAllEffects().forEach(
                (effectInstance) -> {if (effectInstance.getEffect().get().isInstantenous()){
                    effectInstance.getEffect().get().applyInstantenousEffect(player, player, player, effectInstance.getAmplifier(), 1.0D);
                } else{player.addEffect(new MobEffectInstance(effectInstance));}});
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        //TODO:fix stacking
        if (!(pAction == ClickAction.SECONDARY)) return false;
        if (!pOther.is(Items.POTION) && !(pOther.getItem() instanceof PotionFlaskItem)) return false;
        PotionContents contents = pStack.get(DataComponents.POTION_CONTENTS);
        PotionContents other = pOther.get(DataComponents.POTION_CONTENTS);
        if (getUses(pStack) >= pStack.getMaxDamage()) return false;
        if (contents == null || contents == other || contents == PotionContents.EMPTY)
        {//if the flask is empty or it matches
            if (pOther.getItem() instanceof PotionFlaskItem)
            {//if it's our flask
                int current = getUses(pStack);
                int otherCurrent = getUses(pOther);
                int transfer = Math.min(pStack.getMaxDamage()-current,otherCurrent);
                pStack.set(DataComponents.POTION_CONTENTS,new PotionContents(other.potion().get()));
                setUses(pStack,current+transfer);
                setUses(pOther,otherCurrent-transfer);
                pPlayer.level().playSound(null,pPlayer, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS,1,1);
                return true;
            }
            if (pOther.getItem() instanceof PotionItem)
            {//if it's a vanilla potion
                pStack.set(DataComponents.POTION_CONTENTS,other);
                setUses(pStack,getUses(pStack)+1);
                pAccess.set(new ItemStack(Items.GLASS_BOTTLE));
                pPlayer.level().playSound(null,pPlayer, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS,1,1);
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("sips: " + getUses(stack)));
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        if (contents != null && contents != PotionContents.EMPTY)
            contents.addPotionTooltip(pTooltipComponents::add,1f,context.tickRate());
    }

    @Override
    public Component getName(ItemStack p_41458_) {
        return Component.translatable(getDescriptionId());
    }

    @Override
    public boolean isBarVisible(ItemStack p_150899_) {
        return false;
    }

    @Override
    public int getBarWidth(ItemStack pStack)
    {
        return (int)((getUses(pStack)/(float)pStack.getMaxDamage())*14);
    }

    @Override
    public int getBarColor(ItemStack pStack)
    {
        return PotionContents.getColor(pStack.get(DataComponents.POTION_CONTENTS).potion().get());
        //PotionUtils.getColor(pStack);
    }

    @Override
    public int getUseDuration(ItemStack p_43001_, LivingEntity p_345280_) {
        return 24;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack)
    {
        return UseAnim.DRINK;
    }
}

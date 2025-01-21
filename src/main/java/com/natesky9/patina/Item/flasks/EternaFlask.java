package com.natesky9.patina.Item.flasks;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

public class EternaFlask extends PotionFlaskItem{

    public EternaFlask(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity entity) {
        return 16;
    }


    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        apply(pLivingEntity,pStack);

        if (pLivingEntity instanceof Player player)
        {
            player.awardStat(Stats.ITEM_USED.get(this));
            player.getCooldowns().addCooldown(this,1200);
        }
        return pStack;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess)
    {//infinite flask
        if (!(pAction == ClickAction.SECONDARY)) return false;
        if (!pOther.is(Items.POTION) && !(pOther.getItem() instanceof PotionFlaskItem)) return false;

        PotionContents contents = pStack.get(DataComponents.POTION_CONTENTS);
        PotionContents other = pOther.get(DataComponents.POTION_CONTENTS);

        if (contents == null || contents != other || contents == PotionContents.EMPTY)
        {//if eterna is empty, or not the same
            if (pOther.getItem() instanceof PotionFlaskItem)
            {//if it's our flask
                int otherCurrent = getUses(pOther);
                if (otherCurrent <= 0) return false;
                pStack.set(DataComponents.POTION_CONTENTS,new PotionContents(other.potion().get()));
                setUses(pOther,otherCurrent-1);
                pPlayer.level().playSound(null,pPlayer, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS,1,1);
                return true;
            }
            if (pOther.getItem() instanceof PotionItem)
            {//if it's a vanilla potion
                pStack.set(DataComponents.POTION_CONTENTS,other);
                pAccess.set(new ItemStack(Items.GLASS_BOTTLE));
                pPlayer.level().playSound(null,pPlayer, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS,1,1);
                return true;
            }
        }
        return false;
    }
}

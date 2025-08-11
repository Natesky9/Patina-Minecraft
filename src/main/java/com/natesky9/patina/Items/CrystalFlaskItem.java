package com.natesky9.patina.Items;

import com.natesky9.patina.init.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class CrystalFlaskItem extends Item {
    public CrystalFlaskItem(Properties properties) {
        super(properties);
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
        PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY);
        contents.onConsume(level,livingEntity,stack,null);
        setUses(stack, getUses(stack)-1);

        return stack;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (!(action == ClickAction.SECONDARY)) return false;
        PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        PotionContents contentsOther = other.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);


        if (getUses(stack) >= stack.getMaxDamage()) return true;
        if (contentsOther.potion().isEmpty()) return true;
        boolean same = contents.is(contentsOther.potion().get()) || contents == PotionContents.EMPTY;
        System.out.println(same);

        if (same || contents.potion().isEmpty())
        {
            boolean special = stack.getItem() instanceof PluviaFlaskItem;
            boolean otherSpecial = other.getItem() instanceof PluviaFlaskItem;
            if (other.is(Items.POTION) && stack.getDamageValue() <= stack.getMaxDamage() - (special?3:1))
            {
                stack.set(DataComponents.POTION_CONTENTS, contentsOther);
                setUses(stack, getUses(stack) + (special?3:1));
                access.set(new ItemStack(Items.GLASS_BOTTLE));
                player.level().playSound(player, player, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1, 1);
                return true;
            }
            if (other.getItem() instanceof CrystalFlaskItem)
            {
                int current = getUses(stack) * (special?1:3);
                int currentOther = getUses(other) * (otherSpecial?1:3);
                int transfer = Math.min(stack.getMaxDamage() * (special?1:3)-current, currentOther);
                if (special != otherSpecial)
                    transfer = transfer / 3 * 3;
                stack.set(DataComponents.POTION_CONTENTS, new PotionContents(contentsOther.potion().get()));
                setUses(stack, (current+transfer) / (special?1:3));
                setUses(other, (currentOther-transfer) / (otherSpecial?1:3));
                player.level().playSound(player, player, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1, 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(getDescriptionId());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("sips: " + getUses(stack)));
        PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        if (contents != PotionContents.EMPTY)
            contents.addPotionTooltip(tooltipComponents::add,1f,context.tickRate());
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        //only enabled for testing
        return false;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return (int)((getUses(stack)/(float)stack.getMaxDamage())*14);
    }
}

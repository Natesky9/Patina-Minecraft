package com.natesky9.patina.Menu;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class IceboxSlot extends SlotItemHandler {
    public IceboxSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        //only allow food or potions
        return stack.has(DataComponents.FOOD) || stack.has(DataComponents.POTION_CONTENTS);
    }

    @Override
    public int getMaxStackSize() {
        return super.getMaxStackSize();
    }

    @Override
    public void setByPlayer(ItemStack stack) {
        stack.setPopTime(5);
        super.setByPlayer(stack);
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        stack.setPopTime(5);
        super.onTake(player, stack);
    }

    @Override
    public void initialize(ItemStack stack) {
        //stack.setPopTime(0);
        super.initialize(stack);
    }
}

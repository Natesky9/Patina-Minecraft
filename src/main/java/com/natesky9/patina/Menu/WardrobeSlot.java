package com.natesky9.patina.Menu;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class WardrobeSlot extends SlotItemHandler {
    int index;
    public WardrobeSlot(IItemHandler itemHandler, int i, int xPosition, int yPosition) {
        super(itemHandler, i, xPosition, yPosition);
        index = i;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        int row = index/5;
        Equippable equip = stack.get(DataComponents.EQUIPPABLE);
        //allow any item to be put on head
        if (equip == null) return row == 0;
        return switch (equip.slot())
        {
            case MAINHAND, BODY, OFFHAND -> false;
            case FEET -> row == 3;
            case LEGS -> row == 2;
            case CHEST -> row == 1;
            case HEAD -> row == 0;
        };
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}

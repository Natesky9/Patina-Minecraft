package com.natesky9.patina.Menu;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;

public class ArmorSlot extends Slot {
    int index;
    public ArmorSlot(Container container, int slot, int x, int y, int i) {
        super(container, slot, x, y);
        index = i;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        Equippable equip = stack.get(DataComponents.EQUIPPABLE);
        if (equip == null) return index == 0;

        return switch (equip.slot())
        {
            case MAINHAND, BODY, OFFHAND -> false;
            case FEET -> index == 3;
            case LEGS -> index == 2;
            case CHEST -> index == 1;
            case HEAD -> index == 0;
        };
    }
}

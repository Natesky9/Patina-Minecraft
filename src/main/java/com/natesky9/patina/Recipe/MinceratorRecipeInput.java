package com.natesky9.patina.Recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record MinceratorRecipeInput(ItemStack stack1, ItemStack stack2, ItemStack stack3) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return switch (i)
        {
            case 0 -> stack1;
            case 1 -> stack2;
            case 2 -> stack3;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 3;
    }
}

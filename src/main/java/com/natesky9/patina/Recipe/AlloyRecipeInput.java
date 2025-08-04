package com.natesky9.patina.Recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AlloyRecipeInput(ItemStack input1, ItemStack input2) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return switch (i)
        {
            case 0 -> input1;
            case 1 -> input2;
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public int size() {
        return 2;
    }
}

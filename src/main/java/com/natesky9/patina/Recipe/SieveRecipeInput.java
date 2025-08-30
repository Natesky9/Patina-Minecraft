package com.natesky9.patina.Recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record SieveRecipeInput(ItemStack input, boolean water) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return input;
    }
    public boolean water()
    {
        return water;
    }

    @Override
    public int size() {
        return 1;
    }
}

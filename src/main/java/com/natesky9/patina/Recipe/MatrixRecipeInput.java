package com.natesky9.patina.Recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.ArrayList;

public record MatrixRecipeInput(ArrayList items) implements RecipeInput {


    @Override
    public ItemStack getItem(int index) {
        if (index > items.size())
            return ItemStack.EMPTY;
        return (ItemStack) items.get(index);
        //moving to arraylist
        //return switch (index)
        //{
        //    case 0 -> this.input1;
        //    case 1 -> this.input2;
        //    case 2 -> this.input3;
        //    case 3 -> this.input4;
        //    case 4 -> this.input5;
        //    case 5 -> this.input6;
        //    case 6 -> this.input7;
        //    case 7 -> this.input8;
        //    case 8 -> this.input9;
        //    default -> throw new IllegalArgumentException("Recipe does not contain slot " + index);
        //};
    }

    @Override
    public int size() {
        return items.size();
    }
}

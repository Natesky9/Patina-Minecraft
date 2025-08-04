package com.natesky9.patina.Items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.FuelValues;
import org.jetbrains.annotations.Nullable;

public class FuelItem extends Item {
    private int burnTime = 0;
    public FuelItem(Properties properties,int items) {
        super(properties);
        //burn time
        burnTime = 200*items;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType, FuelValues fuelValues) {
        if (recipeType == RecipeType.BLASTING)
            return burnTime*2;
        return burnTime;
    }
}

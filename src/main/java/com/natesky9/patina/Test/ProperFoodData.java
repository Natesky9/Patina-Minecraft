package com.natesky9.patina.Test;

import net.minecraft.util.Mth;
import net.minecraft.world.food.FoodData;

public class ProperFoodData extends FoodData {
    //default is 20 (10 shanks)
    private int maxFoodLevel = 20;
    @Override
    public void add(int foodLevel, float saturationLevel) {
        setFoodLevel(Mth.clamp(getFoodLevel() + foodLevel, 0, getMaxFoodLevel()));
        setSaturation(Mth.clamp(getSaturationLevel() + saturationLevel, 0, getFoodLevel()));
    }

    @Override
    public boolean needsFood() {
        return getFoodLevel() < getMaxFoodLevel();
    }
    public int getMaxFoodLevel()
    {
        return this.maxFoodLevel;
    }
    public void setMaxFoodLevel(double mult)
    {
        maxFoodLevel = (int) (20 * mult);
    }
}

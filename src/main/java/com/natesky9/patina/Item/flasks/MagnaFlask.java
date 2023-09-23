package com.natesky9.patina.Item.flasks;

import net.minecraft.world.item.ItemStack;

public class MagnaFlask extends PotionFlaskItem{

    public MagnaFlask(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 16;
    }
}
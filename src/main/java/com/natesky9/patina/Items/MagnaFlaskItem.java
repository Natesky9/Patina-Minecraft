package com.natesky9.patina.Items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MagnaFlaskItem extends CrystalFlaskItem{
    public MagnaFlaskItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 16;
    }
}

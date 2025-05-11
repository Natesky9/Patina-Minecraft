package com.natesky9.patina.Blocks;

import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ApplianceWardrobeEntity extends BlockEntity {
    public final ItemStackHandler handler;
    public ApplianceWardrobeEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.WARDROBE_ENTITY.get(), pos, blockState);
        handler = new ItemStackHandler(20)
        {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return (stack.getItem() instanceof ArmorItem);
            }

            @Override
            protected int getStackLimit(int slot, ItemStack stack) {
                return 1;
            }
        };
    }
}

package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.WardrobeMenu;
import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ApplianceWardrobeEntity extends BlockEntity implements MenuProvider {
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

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.appliance_wardrobe");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new WardrobeMenu(i, inventory, this);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ApplianceWardrobeEntity applianceWardrobeEntity) {
        for (int i=0; i<20; i++)
        {
            ItemStack stack = applianceWardrobeEntity.handler.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.getPopTime() == 0) continue;
            stack.setPopTime(stack.getPopTime()-1);
        }
    }
}

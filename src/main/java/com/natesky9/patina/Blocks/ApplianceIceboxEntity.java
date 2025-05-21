package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.IceboxMenu;
import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ApplianceIceboxEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler handler;
    public ApplianceIceboxEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ICEBOX_ENTITY.get(), pos, blockState);
        handler = new ItemStackHandler(20)
        {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                FoodProperties food = stack.get(DataComponents.FOOD);
                return contents != PotionContents.EMPTY || food != null;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.appliance_icebox");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new IceboxMenu(i, inventory, this);
    }

}

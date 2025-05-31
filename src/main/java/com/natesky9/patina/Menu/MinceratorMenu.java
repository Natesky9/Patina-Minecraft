package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.MachineMinceratorEntity;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MinceratorMenu extends ModContainerMenu {
    MachineMinceratorEntity mincerator;
    public MinceratorMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public MinceratorMenu(int containerId, Inventory inv, BlockEntity entity)
    {
        super(ModMenuTypes.MINCERATOR_MENU.get(), containerId);
        inventory = inv;
        mincerator = (MachineMinceratorEntity) entity;
    }
    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(mincerator.getBlockPos(), 4);
    }
}

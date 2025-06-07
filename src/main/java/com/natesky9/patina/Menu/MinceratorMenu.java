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
import net.neoforged.neoforge.items.SlotItemHandler;

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
        addSlot(new SlotItemHandler(mincerator.handler, 0, 80, 8));
        addSlot(new SlotItemHandler(mincerator.handler, 1, 80+18, 8));
        addSlot(new SlotItemHandler(mincerator.handler, 2, 80, 26));
        addSlot(new SlotItemHandler(mincerator.handler, 3, 80+18, 26));
        addSlot(new SlotItemHandler(mincerator.handler, 4, 80+18, 44));
        addPlayerInventory(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index>=0 && index<5)
        {
            //click in alembic
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack, 5, 5+36,false);
        }
        if (index>=5)
        {
            //click in inventory
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack,0,5,false);
        }
        return ItemStack.EMPTY;
    }
    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(mincerator.getBlockPos(), 4);
    }
}

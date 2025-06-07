package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.ApplianceIceboxEntity;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class IceboxMenu extends ModContainerMenu {
    ApplianceIceboxEntity icebox;
    public IceboxMenu(int containerId, Inventory inventory, FriendlyByteBuf buf)
    {
        this(containerId, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public IceboxMenu(int containerId, Inventory inv, BlockEntity entity)
    {
        super(ModMenuTypes.ICEBOX_MENU.get(), containerId);
        inventory = inv;
        icebox = (ApplianceIceboxEntity) entity;
        //add slots
        for (int i=0; i<20; i++)
        {
            addSlot(new IceboxSlot(icebox.handler, i, 80+i%5*18, 8+i/5*18));
        }
        addPlayerInventory(inv);
    }
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index < 0)
            System.out.println("clicked outside!");
        if (index >= 0 && index < 20)
        {
            //clicked in icebox
            ItemStack stack = getSlot(index).getItem();
            //we have to set this here because move doesn't trigger our slot
            stack.setPopTime(5);
            moveItemStackTo(stack,20,20+36,false);

            //inventory.add(stack);
        }
        if (index >= 20)
        {
            //clicked in inventory
            ItemStack stack = getSlot(index).getItem();
            stack.setPopTime(5);
            moveItemStackTo(stack, 0, 20, false);
            //stack.setPopTime(5);
            //inventory.removeItem(stack);
//
            //for (int i=0; i<20; i++)
            //{
            //    stack = icebox.handler.insertItem(i, stack, false);
            //}
            //inventory.add(stack);
        }
        return ItemStack.EMPTY;

    }

    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(icebox.getBlockPos(), 4);
    }
}

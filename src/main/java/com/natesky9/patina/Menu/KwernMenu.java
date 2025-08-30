package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.MachineKwernEntity;
import com.natesky9.patina.init.ModMenuTypes;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class KwernMenu extends ModContainerMenu {
    MachineKwernEntity kwern;
    public KwernMenu(int containerId, Inventory inv, FriendlyByteBuf buf)
    {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public KwernMenu(int containerId, Inventory inv, BlockEntity entity)
    {
        super(ModMenuTypes.KWERN_MENU.get(), containerId);
        inventory = inv;
        kwern = (MachineKwernEntity) entity;
        addSlot(new SlotItemHandler(kwern.handler, 0, 71, 22));
        addSlot(new SlotItemHandler(kwern.handler, 1, 71, 56));
        addSlot(new SlotItemHandler(kwern.handler,2,91, 56));
        addSlot(new SlotItemHandler(kwern.handler, 3, 6, 62));
        addPlayerInventory(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index>=0 && index<4)
        {
            //click in kwern
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack, 4, 4+36,false);
        }
        if (index>=4)
        {
            //click in inventory
            ItemStack stack = getSlot(index).getItem();
            if (kwern.handler.isItemValid(0,stack))
            {
                moveItemStackTo(stack,0,0,false);
                return ItemStack.EMPTY;
            }
            if (stack.getBurnTime(ModRecipeTypes.KWERN_RECIPE.get(),kwern.getLevel().fuelValues()) > 0)
            {
                moveItemStackTo(stack,3,3,false);
                return ItemStack.EMPTY;
            }
            moveItemStackTo(stack,4,4+36,false);
        }
        return ItemStack.EMPTY;
    }
    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(kwern.getBlockPos(), 4);
    }
}

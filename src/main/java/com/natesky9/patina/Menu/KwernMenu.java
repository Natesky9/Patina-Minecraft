package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.MachineKwernEntity;
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
        addSlot(new SlotItemHandler(kwern.handler, 0, 62, 34));
        addSlot(new SlotItemHandler(kwern.handler, 1, 136, 34));
        addSlot(new SlotItemHandler(kwern.handler, 2, 6, 62));
        addPlayerInventory(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index>=0 && index<3)
        {
            //click in kwern
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack, 3, 3+36,false);
        }
        if (index>=3)
        {
            //click in inventory
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack,0,2,false);
        }
        return ItemStack.EMPTY;
    }
    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(kwern.getBlockPos(), 4);
    }
}

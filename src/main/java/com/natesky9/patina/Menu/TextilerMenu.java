package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.MachineTextilerEntity;
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

public class TextilerMenu extends ModContainerMenu {
    MachineTextilerEntity textiler;
    public TextilerMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public TextilerMenu(int containerId, Inventory inv, BlockEntity entity)
    {
        super(ModMenuTypes.TEXTILER_MENU.get(), containerId);
        inventory = inv;
        textiler = (MachineTextilerEntity) entity;
        addSlot(new SlotItemHandler(textiler.handler, 0, 80, 8));
        addSlot(new SlotItemHandler(textiler.handler, 1, 80+18, 8));
        addPlayerInventory(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index>=0 && index<2)
        {
            //click in alembic
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack, 2, 2+36,false);
        }
        if (index>=3)
        {
            //click in inventory
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack,0,1,false);
        }
        return ItemStack.EMPTY;
    }
    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(textiler.getBlockPos(), 4);
    }
}

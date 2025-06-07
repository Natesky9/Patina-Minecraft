package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.MachineAlembicEntity;
import com.natesky9.patina.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class AlembicMenu extends ModContainerMenu {
    Inventory inventory;
    MachineAlembicEntity alembic;
    Slot input;
    Slot output;
    public AlembicMenu(int containerId, Inventory inv, FriendlyByteBuf buf)
    {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public AlembicMenu(int containerId, Inventory inv, BlockEntity entity)
    {
        super(ModMenuTypes.ALEMBIC_MENU.get(), containerId);
        inventory = inv;
        alembic = (MachineAlembicEntity) entity;
        input = addSlot(new SlotItemHandler(alembic.handler, 0, 80, 8));
        output = addSlot(new SlotItemHandler(alembic.handler, 1, 96, 8));
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
        if (index>=2)
        {
            //click in inventory
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack,0,2,false);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(alembic.getBlockPos(), 4);
    }
}

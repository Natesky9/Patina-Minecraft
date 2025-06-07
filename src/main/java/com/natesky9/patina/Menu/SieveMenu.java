package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.MachineSieveEntity;
import com.natesky9.patina.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SieveMenu extends ModContainerMenu {
    Inventory inventory;
    MachineSieveEntity sieve;
    public SieveMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public SieveMenu(int containerId, Inventory inv, BlockEntity entity)
    {
        super(ModMenuTypes.SIEVE_MENU.get(), containerId);
        inventory = inv;
        sieve = (MachineSieveEntity) entity;
        addSlot(new SlotItemHandler(sieve.handler, 0, 62, 16));
        addSlot(new SlotItemHandler(sieve.handler, 0, 136, 34));
        addSlot(new SlotItemHandler(sieve.handler, 0, 136, 55));
        addSlot(new SlotItemHandler(sieve.handler, 0, 6, 62));
        addPlayerInventory(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index>=0 && index<4)
        {
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack, 4, 4+36, false);
        }
        if (index>=4)
        {
            //click in inventory
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack,0,0,false);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(sieve.getBlockPos(), 4);
    }
}

package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.MachineFoundryEntity;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class FoundryMenu extends ModContainerMenu {
    public MachineFoundryEntity foundry;
    public FoundryMenu(int containerId, Inventory inv, FriendlyByteBuf buf)
    {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public FoundryMenu(int containerId, Inventory inv, BlockEntity entity)
    {
        super(ModMenuTypes.FOUNDRY_MENU.get(), containerId);
        inventory = inv;
        foundry = (MachineFoundryEntity) entity;
        addSlot(new SlotItemHandler(foundry.handler, 0, 80, 10));
        addSlot(new SlotItemHandler(foundry.handler, 1, 96, 10));
        addSlot(new SlotItemHandler(foundry.handler,2,112,10));
        addSlot(new SlotItemHandler(foundry.handler,3,8,60));
        addStandardInventorySlots(inventory,8,94);
        addInventoryHotbarSlots(inventory,8,152);
        //addPlayerInventory(inventory);
    }
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index>=0 && index<4)
        {
            //click in foundry
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack, 4, 4+36,false);
        }
        if (index>4)
        {
            //click in inventory
            ItemStack stack = getSlot(index).getItem();
            moveItemStackTo(stack,0,3,false);
        }
        return ItemStack.EMPTY;
    }
    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(foundry.getBlockPos(), 4);
    }
}

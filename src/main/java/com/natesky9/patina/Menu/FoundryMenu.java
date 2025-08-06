package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.MachineFoundryEntity;
import com.natesky9.patina.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class FoundryMenu extends ModContainerMenu {
    public MachineFoundryEntity foundry;
    public final ContainerData data;
    public Slot toggleSlot;
    public FoundryMenu(int containerId, Inventory inv, FriendlyByteBuf buf)
    {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()),
                new SimpleContainerData(4));
    }
    public FoundryMenu(int containerId, Inventory inv, BlockEntity entity, ContainerData containerData)
    {
        super(ModMenuTypes.FOUNDRY_MENU.get(), containerId);
        inventory = inv;
        foundry = (MachineFoundryEntity) entity;
        data = containerData;
        boolean alloy = foundry.mode();
        addSlot(new SlotItemHandler(foundry.handler, 0, 47, 31));
        toggleSlot = addSlot(new SlotItemHandler(foundry.handler, 1, alloy?69:96, 31));
        addSlot(new SlotItemHandler(foundry.handler,2,126,31));
        addSlot(new SlotItemHandler(foundry.handler,3,6,62));
        addStandardInventorySlots(inventory,8,94);
        addInventoryHotbarSlots(inventory,8,152);
        addDataSlots(containerData);
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

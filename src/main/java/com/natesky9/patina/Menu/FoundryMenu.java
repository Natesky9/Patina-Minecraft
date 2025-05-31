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

public class FoundryMenu extends ModContainerMenu {
    MachineFoundryEntity foundry;
    public FoundryMenu(int containerId, Inventory inv, FriendlyByteBuf buf)
    {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public FoundryMenu(int containerId, Inventory inv, BlockEntity entity)
    {
        super(ModMenuTypes.FOUNDRY_MENU.get(), containerId);
        inventory = inv;
        foundry = (MachineFoundryEntity) entity;
        addPlayerInventory(inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(foundry.getBlockPos(), 4);
    }
}

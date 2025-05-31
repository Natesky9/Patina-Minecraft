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
    }
    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(kwern.getBlockPos(), 4);
    }
}

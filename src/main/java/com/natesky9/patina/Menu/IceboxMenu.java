package com.natesky9.patina.Menu;

import com.natesky9.patina.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class IceboxMenu extends AbstractContainerMenu {
    Inventory inventory;
    public IceboxMenu(int containerId, Inventory inventory, FriendlyByteBuf buf)
    {
        this(containerId, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public IceboxMenu(int containerId, Inventory inv, BlockEntity entity)
    {
        super(ModMenuTypes.ICEBOX_MENU.get(), containerId);
        inventory = inv;
    }
    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}

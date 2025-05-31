package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.MachineEvaporatorEntity;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import org.jetbrains.annotations.Nullable;

public class EvaporatorMenu extends ModContainerMenu {
    MachineEvaporatorEntity evaporator;
    public EvaporatorMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public EvaporatorMenu(int containerId, Inventory inv, BlockEntity entity)
    {
        super(ModMenuTypes.EVAPORATOR_MENU.get(), containerId);
        inventory = inv;
        evaporator = (MachineEvaporatorEntity) entity;
        addPlayerInventory(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(evaporator.getBlockPos(), 4);
    }
}

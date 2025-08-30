package com.natesky9.patina.Blocks.Research;

import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ResearchMenu extends AbstractContainerMenu {
    final Level level;
    final BlockPos pos;
    final ContainerData data;
    final Player player;

    public ResearchMenu(int containerId, Inventory inventory, Level level, BlockPos pos, ContainerData data)
    {
        super(ModMenuTypes.RESEARCH_MENU.get(),containerId);
        this.pos = pos;
        this.level = level;
        this.player = inventory.player;
        this.data = data;
        addStandardInventorySlots(inventory,8,94);
        addInventoryHotbarSlots(inventory,8,152);
        addDataSlots(data);
    }

    public ResearchMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, inv.player.level(), inv.player.blockPosition(), new SimpleContainerData(0));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level,pos),player, ModBlocks.APPLIANCE_RESEARCH_DESK.get());
    }
}

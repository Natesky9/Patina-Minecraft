package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.FoundryMenu;
import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MachineFoundryEntity extends BlockEntity implements MenuProvider {
    public static final int dataSlots = 2;
    public static final int slots = 4;
    public MachineFoundryEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FOUNDRY_ENTITY.get(), pos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.machine_foundry");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FoundryMenu(i, inventory, this);
    }
}

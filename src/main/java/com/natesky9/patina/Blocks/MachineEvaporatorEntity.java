package com.natesky9.patina.Blocks;

import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MachineEvaporatorEntity extends BlockEntity {
    public MachineEvaporatorEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EVAPORATOR_ENTITY.get(), pos, blockState);
    }
}

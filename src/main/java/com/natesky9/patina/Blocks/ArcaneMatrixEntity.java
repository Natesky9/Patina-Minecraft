package com.natesky9.patina.Blocks;

import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ArcaneMatrixEntity extends BlockEntity {
    public ArcaneMatrixEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MATRIX_ENTITY.get(), pos, blockState);
    }
}

package com.natesky9.patina.Block.MachineEnchanter;

import com.natesky9.patina.Block.Template.MachineTemplateBlock;
import com.natesky9.patina.Block.Template.MachineTemplateEntity;
import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MachineEnchanterBlock extends MachineTemplateBlock {
    public MachineEnchanterBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public BlockEntityType<? extends MachineTemplateEntity> getBlockEntityType() {
        return ModBlockEntities.MACHINE_ENCHANTER_ENTITY.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MachineEnchanterEntity(pPos,pState);
    }
}
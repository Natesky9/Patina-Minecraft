package com.natesky9.patina.block.AnvilSmithing;

import com.natesky9.patina.block.Template.MachineTemplateBlock;
import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MachineAnvilSmithingBlock extends MachineTemplateBlock
{
    @Override
    public BlockEntityType<MachineAnvilSmithingEntity> getBlockEntityType()
    {return ModBlockEntities.MACHINE_ANVIL_SMITHING_ENTITY.get();}

    //constructor
    public MachineAnvilSmithingBlock(Properties p_49224_) {
        super(p_49224_);

    }

    //Block Entity stuff
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MachineAnvilSmithingEntity(pPos, pState);
    }
}

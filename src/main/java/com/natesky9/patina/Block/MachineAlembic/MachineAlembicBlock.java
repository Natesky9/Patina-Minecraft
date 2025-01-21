package com.natesky9.patina.Block.MachineAlembic;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.Block.Template.MachineTemplateBlock;
import com.natesky9.patina.Block.Template.MachineTemplateEntity;
import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MachineAlembicBlock extends MachineTemplateBlock {
    public static final MapCodec<MachineAlembicBlock> CODEC = simpleCodec(MachineAlembicBlock::new);
    public MachineAlembicBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntityType<? extends MachineTemplateEntity> getBlockEntityType() {
        return ModBlockEntities.MACHINE_ALEMBIC_ENTITY.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MachineAlembicEntity(pos, state);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
        super.setPlacedBy(level, pos, state, p_49850_, p_49851_);
        if (level.getBlockEntity(pos) instanceof MachineAlembicEntity entity)
            entity.setNeighbors(level, pos);
    }

    @Override
    public BlockState updateShape(BlockState before, Direction direction, BlockState after, LevelAccessor level, BlockPos pos, BlockPos otherPos) {
        if (level.getBlockEntity(pos) instanceof MachineAlembicEntity entity)
            entity.setNeighbors(level, pos);
        return super.updateShape(before, direction, after, level, pos, otherPos);
    }
    //@Override
    //public void onNeighborChange(BlockState state, LevelReader level, BlockPos items, BlockPos neighbor) {
    //    super.onNeighborChange(state, level, items, neighbor);
    //    BlockEntity blockEntity = level.getBlockEntity(items);
    //    if (!(blockEntity instanceof MachineAlembicEntity alembic)) return;
    //    alembic.setNeighbors(level, items);
    //}
}

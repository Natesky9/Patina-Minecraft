package com.natesky9.patina.Blocks;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MachineKwernBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<MachineKwernBlock> CODEC = simpleCodec(MachineKwernBlock::new);
    public MachineKwernBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player instanceof ServerPlayer server)
        {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof MachineKwernEntity kwern)
                server.openMenu(kwern, pos);
            else
                throw new IllegalStateException("Container Provider missing, fool!");
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
        if (!(level.getBlockEntity(pos) instanceof MachineKwernEntity kwern)) return;

        int neighbors = 0;
        for (Direction direction:Direction.Plane.HORIZONTAL.stream().toList())
        {
            BlockPos adjacent = pos.relative(direction);
            if (level.getBlockState(adjacent).is(Blocks.PISTON))
                neighbors++;
        }
        kwern.secondaryMax = 4-neighbors;
        System.out.println("kwern: " + kwern.secondaryMax);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MachineKwernEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.KWERN_ENTITY.get(), MachineKwernEntity::tick);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() == newState.getBlock())
            return;

        if (level.getBlockEntity(pos) instanceof MachineKwernEntity kwern)
        {
            kwern.drops();
            super.onRemove(state,level,pos,newState,movedByPiston);
        }
        super.onRemove(state,level,pos,newState,movedByPiston);
    }
}

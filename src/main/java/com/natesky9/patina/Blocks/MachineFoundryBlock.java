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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MachineFoundryBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty MODE = BooleanProperty.create("mode");
    public static final MapCodec<MachineFoundryBlock> CODEC = simpleCodec(MachineFoundryBlock::new);
    public MachineFoundryBlock(Properties p_49795_) {
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
        builder.add(FACING).add(MODE);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player instanceof ServerPlayer server)
        {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof MachineFoundryEntity machine)
                server.openMenu(machine, pos);
            else
                throw new IllegalStateException("Container Provider missing, fool!");
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MachineFoundryEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.FOUNDRY_ENTITY.get(), MachineFoundryEntity::tick);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
        if (!(level.getBlockEntity(pos) instanceof MachineFoundryEntity foundry)) return;

        int neighbors = 0;
        for (Direction direction:Direction.Plane.HORIZONTAL.stream().toList())
        {
            BlockPos adjacent = pos.relative(direction);
            if (level.getBlockState(adjacent).is(Blocks.BLAST_FURNACE))
                neighbors++;
        }
        foundry.heatMax = 1000+neighbors*1000;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() == newState.getBlock())
            return;

        if (level.getBlockEntity(pos) instanceof MachineFoundryEntity foundry)
        {
            foundry.drops();
            super.onRemove(state,level,pos,newState,movedByPiston);
        }
    }
}

package com.natesky9.patina.Blocks;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ApplianceIceboxBlock extends BaseEntityBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final MapCodec<ApplianceIceboxBlock> CODEC = simpleCodec(ApplianceIceboxBlock::new);
    public ApplianceIceboxBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ApplianceIceboxEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF).add(FACING);
    }
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        BlockState other = defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)
                .setValue(FACING, state.getValue(FACING));
        level.setBlock(pos.above(), other, 3);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (half == DoubleBlockHalf.LOWER)
            return super.canSurvive(state, level, pos);
        else
        {
            BlockState below = level.getBlockState(pos.below());
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        //placement conditions
        //seems to only be the initial block
        //so put your checks here
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(HALF, DoubleBlockHalf.LOWER);

        return !level.isOutsideBuildHeight(pos.above())
                && level.getBlockState(pos.above()).canBeReplaced() ? state : null;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        //breaks adjacent when invalid
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);

        if (doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.UP)
        {
            return neighborState.is(this) && neighborState.getValue(HALF) == DoubleBlockHalf.UPPER
                    ? state : Blocks.AIR.defaultBlockState();
        }
        if (doubleblockhalf == DoubleBlockHalf.UPPER && direction == Direction.DOWN)
        {

            return neighborState.is(this) && neighborState.getValue(HALF) == DoubleBlockHalf.LOWER
                    ? state : Blocks.AIR.defaultBlockState();
        }
        //if neither, we good
        return state;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock())
        {
            BlockPos test = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos : pos.below();
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof ApplianceIceboxEntity icebox)
            {
                ItemStackHandler handler = icebox.handler;
                Containers.dropContents(level, pos, contain(handler));
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    SimpleContainer contain(ItemStackHandler handler)
    {
        SimpleContainer inventory = new SimpleContainer(handler.getSlots());
        for (int i=0;i < handler.getSlots();i++)
        {
            inventory.setItem(i,handler.getStackInSlot(i));
        }
        return inventory;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player instanceof ServerPlayer server)
        {
            BlockPos location = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos:pos.below();
            BlockEntity entity = level.getBlockEntity(location);
            if (entity instanceof ApplianceIceboxEntity icebox)
                server.openMenu(icebox, location);
            else
                throw new IllegalStateException("Container Provider missing, fool!");
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.ICEBOX_ENTITY.get(), ApplianceIceboxEntity::tick);
    }
}

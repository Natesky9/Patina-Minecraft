package com.natesky9.patina.Blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ApplianceWardrobeBlock extends BaseEntityBlock {
    public static EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<ApplianceWardrobeBlock> CODEC = simpleCodec(ApplianceWardrobeBlock::new);

    public ApplianceWardrobeBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        //only create the blockentity in the bottom half
        return blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? new ApplianceWardrobeEntity(blockPos, blockState) : null;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        BlockState other = defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)
                .setValue(FACING, state.getValue(FACING));
        level.setBlock(pos.above(), state, 3);
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
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState below = level.getBlockState(pos.below());
        boolean top = below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        BlockState state = defaultBlockState()
                .setValue(HALF, top ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER)
                .setValue(FACING, context.getHorizontalDirection().getOpposite());

        return pos.getY() < level.getMaxY() - 1
                && level.getBlockState(pos.above()).canBeReplaced() ? state : null;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        //copied over, ensure everything is fine
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (direction.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (direction == Direction.UP)
                || neighborState.is(this) && neighborState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN
                    && !state.canSurvive(level, pos) ?
                    Blocks.AIR.defaultBlockState() :
                    super.updateShape(state, level, scheduledTickAccess, pos,direction,neighborPos, neighborState, random);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock())
        {
            BlockPos test = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos : pos.below();
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof ApplianceWardrobeEntity wardrobe)
            {
                ItemStackHandler handler = wardrobe.handler;
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF).add(FACING);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player instanceof ServerPlayer server)
        {
            BlockPos location = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos:pos.below();
            BlockEntity entity = level.getBlockEntity(location);
            if (entity instanceof ApplianceWardrobeEntity wardrobe)
                server.openMenu(wardrobe, location);
            else
                throw new IllegalStateException("Container Provider missing, fool!");
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
}

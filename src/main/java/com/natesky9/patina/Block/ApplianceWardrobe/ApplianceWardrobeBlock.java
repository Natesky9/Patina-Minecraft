package com.natesky9.patina.Block.ApplianceWardrobe;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ApplianceWardrobeBlock extends BaseEntityBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<ApplianceWardrobeBlock> CODEC = simpleCodec(ApplianceWardrobeBlock::new);
    public ApplianceWardrobeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(HALF) == DoubleBlockHalf.LOWER ? new ApplianceWardrobeEntity(pPos,pState):null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult p_60508_) {
        if (pPlayer instanceof ServerPlayer player)
        {
            BlockPos pos = pState.getValue(HALF) == DoubleBlockHalf.LOWER ? pPos:pPos.below();
            BlockEntity entity = pLevel.getBlockEntity(pos);
            if (entity instanceof ApplianceWardrobeEntity wardrobe)
            {
                player.openMenu(wardrobe, pos);
            }
            else
            {
                throw new IllegalStateException("Container Provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        //set the top to face the same way
        BlockState blockstate = defaultBlockState().setValue(HALF,DoubleBlockHalf.UPPER).setValue(FACING,pState.getValue(FACING));
        pLevel.setBlock(pPos.above(),blockstate,3);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos)
    {
        DoubleBlockHalf half = pState.getValue(HALF);
        if (half == DoubleBlockHalf.LOWER)
        {//if it's the lower half
        return super.canSurvive(pState, pLevel, pPos);
        }
        else
        {
            BlockState below = pLevel.getBlockState(pPos.below());
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockState below = level.getBlockState(pos.below());
        boolean top = below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        BlockState state = defaultBlockState()
                .setValue(HALF,top ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER)
                .setValue(FACING,pContext.getHorizontalDirection().getOpposite());

        return pos.getY() < level.getMaxBuildHeight() - 1
                && level.getBlockState(pos.above()).canBeReplaced(pContext)
                ? state : null;
        //set half based on position


    }


    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {

        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (pDirection.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (pDirection == Direction.UP)
                || pNeighborState.is(this) && pNeighborState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && pDirection == Direction.DOWN
                    && !pState.canSurvive(pLevel, pCurrentPos) ?
                    Blocks.AIR.defaultBlockState() :
                    super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock())
        {
            BlockPos pos = pState.getValue(HALF) == DoubleBlockHalf.LOWER ? pPos : pPos.below();
            BlockEntity blockEntity = pLevel.getBlockEntity(pos);
            if (blockEntity instanceof ApplianceWardrobeEntity wardrobe)
            {
                ItemStackHandler itemStackHandler = wardrobe.handler;
                SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
                for (int i=0;i < itemStackHandler.getSlots();i++)
                {
                    inventory.setItem(i,itemStackHandler.getStackInSlot(i));
                }
                Containers.dropContents(pLevel,pPos,inventory);
            }
        }
        super.onRemove(pState,pLevel,pPos,pNewState,pIsMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF).add(FACING);
    }
}

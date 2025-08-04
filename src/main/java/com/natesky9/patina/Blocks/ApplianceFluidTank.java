package com.natesky9.patina.Blocks;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.*;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.fluids.crafting.DataComponentFluidIngredient;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ApplianceFluidTank extends BaseEntityBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final MapCodec<ApplianceFluidTank> CODEC = simpleCodec(ApplianceFluidTank::new);
    public ApplianceFluidTank(Properties p_49795_) {
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
        builder.add(HALF);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        BlockState other = defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER);


        level.setBlock(pos.above(), other, 3);
        //fill the new tank with the item's fluid
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof ApplianceFluidTankEntity tank)
        {
            if (other.getValue(HALF) == DoubleBlockHalf.UPPER)
            {
                if (level.getBlockEntity(pos.below()) instanceof ApplianceFluidTankEntity bottom)
                    tank.fluidHandler = bottom.fluidHandler;
            }
            FluidHandlerItemStack fluidFilledItemstack = new FluidHandlerItemStack(ModDataComponents.FLUID, stack, 4000);
            tank.fluidHandler.setFluid(fluidFilledItemstack.getFluid());
        }
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
        BlockState state = defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);

        return !level.isOutsideBuildHeight(pos.above())
                && level.getBlockState(pos.above()).canBeReplaced() ? state : null;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        DoubleBlockHalf half = state.getValue(HALF);

        if (half == DoubleBlockHalf.LOWER && direction == Direction.UP)
        {
            return neighborState.is(this) && neighborState.getValue(HALF) == DoubleBlockHalf.UPPER
                ? state : Blocks.AIR.defaultBlockState();
        }
        if (half == DoubleBlockHalf.UPPER && direction == Direction.DOWN)
        {
            return neighborState.is(this) && neighborState.getValue(HALF) == DoubleBlockHalf.LOWER
                    ? state : Blocks.AIR.defaultBlockState();
        }
        //if neither, we good
        return state;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        //this is now done by the loot table, thanks to XFact for the help
        //if (state.getBlock() != newState.getBlock())
        //{
        //    BlockEntity entity = level.getBlockEntity(pos);
        //    if (entity instanceof ApplianceFluidTankEntity tank)
        //    {
        //        ItemStack stack = new ItemStack(ModBlocks.APPLIANCE_FLUID_TANK.asItem());
        //        if (tank.fluidHandler.getFluidAmount() > 0)
        //        {
        //            FluidHandlerItemStack fluidFilledItemstack = new FluidHandlerItemStack(ModDataComponents.FLUID, stack, 4000);
        //            fluidFilledItemstack.fill(tank.fluidHandler.getFluid(), IFluidHandler.FluidAction.EXECUTE);
        //            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), fluidFilledItemstack.getContainer());
        //        }
        //        else
        //            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),stack);
        //    }
        //}
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER)
            pos = pos.below();

        if (!(level.getBlockEntity(pos) instanceof ApplianceFluidTankEntity tank)) return InteractionResult.FAIL;

        Optional<FluidStack> containedFluid = FluidUtil.getFluidContained(stack);
        if (containedFluid.isPresent())
        {
            int input = containedFluid.get().getAmount();
            boolean room = tank.fluidHandler.getSpace() >= input;
            boolean same = tank.fluidHandler.isFluidValid(containedFluid.get());
            //boolean same = tank.fluidHandler.getFluid().is(inputType)
            //        || tank.fluidHandler.isEmpty();
            if (room && same)
            {
                tank.fluidHandler.fill(containedFluid.get(), IFluidHandler.FluidAction.EXECUTE);
                if (!player.isCreative())
                    player.setItemInHand(hand, Items.BUCKET.getDefaultInstance());
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1, 1);
            }
        }
        if (stack.is(Items.BUCKET))
        {
            boolean enough = tank.fluidHandler.getFluidAmount() >= 1000;
            if (!enough) return InteractionResult.SUCCESS;

            ItemStack output = FluidUtil.getFilledBucket(tank.fluidHandler.getFluid());
            tank.fluidHandler.drain(1000, IFluidHandler.FluidAction.EXECUTE);
            if (!player.isCreative())
                player.setItemInHand(hand, output);
            level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1, 1);
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ApplianceFluidTankEntity(blockPos, blockState);
        //depreciated, as we can't point handlers to this blockentity anymore,
        //each half has to have its own, now just pointing to
        //the true blockentity
        //if (blockState.getValue(HALF) == DoubleBlockHalf.LOWER)
        //    return new ApplianceFluidTankEntity(blockPos, blockState);
        //don't create an entity for the top
        //return null;
    }
}

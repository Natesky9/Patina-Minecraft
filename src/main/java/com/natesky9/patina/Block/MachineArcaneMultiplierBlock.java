package com.natesky9.patina.Block;

import com.natesky9.patina.Block.Plinth.AppliancePlinthEntity;
import com.natesky9.patina.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class MachineArcaneMultiplierBlock extends Block {
    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public MachineArcaneMultiplierBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(DIRECTION, Direction.NORTH)
                .setValue(TRIGGERED,false));
    }

    @Override
    protected void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        boolean powered = pLevel.hasNeighborSignal(pPos);
        boolean triggered = pState.getValue(TRIGGERED);
        Direction direction = pState.getValue(DIRECTION);

        BlockPos inputOne = pPos.relative(direction.getOpposite());


        BlockPos output = pPos.relative(direction);
        boolean construct = pLevel.getBlockState(inputOne).is(ModBlocks.APPLIANCE_PLINTH.get())
                && pLevel.getBlockState(output).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!construct) return;

        if (powered && !triggered)
        {
            pLevel.setBlock(pPos,pState
                .setValue(TRIGGERED,true)
                .setValue(DIRECTION,pState.getValue(DIRECTION)),2);
            pLevel.scheduleTick(pPos,this,4);
        }
        if (!powered && triggered)
        {
            pLevel.setBlock(pPos, pState.setValue(TRIGGERED,false).setValue(DIRECTION,direction),2);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        //place against plinths if horizontal
        Direction direction = pContext.getClickedFace().getOpposite();
        if (Direction.Plane.HORIZONTAL.test(direction)
            && pContext.getLevel().getBlockState(pContext.getClickedPos()
                .relative(direction)).is(ModBlocks.APPLIANCE_PLINTH.get()))
            return defaultBlockState().setValue(DIRECTION,pContext.getClickedFace().getOpposite());
        //otherwise normal facing rules
        return defaultBlockState().setValue(DIRECTION,pContext.getHorizontalDirection());
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        Direction direction = pState.getValue(DIRECTION);

        BlockPos inputPos = pPos.relative(direction.getOpposite());
        BlockPos outputPos = pPos.relative(direction);

        boolean construct = pLevel.getBlockState(inputPos).is(ModBlocks.APPLIANCE_PLINTH.get()) && pLevel.getBlockState(outputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!construct) return;

        if (!(pLevel.getBlockEntity(inputPos) instanceof AppliancePlinthEntity inputPlinth)) return;
        if (!(pLevel.getBlockEntity(outputPos) instanceof AppliancePlinthEntity outputPlinth)) return;

        ItemStack input = inputPlinth.getStack();
        ItemStack output = outputPlinth.getStack();

        if (!(input.is(Items.ENCHANTED_BOOK))) return;
        if (!(output.is(Items.BOOK))) return;

        int cost = input.getOrDefault(DataComponents.REPAIR_COST,0);
        System.out.println("Repair cost is: " + cost);
        input.set(DataComponents.REPAIR_COST,cost+1);

        outputPlinth.handler.extractItem(0,1,false);
        outputPlinth.setStack(inputPlinth.getStack().copy());
        pLevel.sendBlockUpdated(outputPos,outputPlinth.getBlockState(),outputPlinth.getBlockState(),3);

        pLevel.playSound(null,pPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS,1,1);

        if (pLevel instanceof ServerLevel server)
            server.sendParticles(ParticleTypes.INSTANT_EFFECT,outputPos.getX()+.5,outputPos.getY()+1,outputPos.getZ()+.5,
                    15,0,0,0,1);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        //TODO:change with blockstate test
        Direction direction = pState.getValue(DIRECTION);

        BlockPos inputPos = pPos.relative(direction.getOpposite());
        BlockPos outputPos = pPos.relative(direction);
        int x = inputPos.getX()-outputPos.getX();
        int z = inputPos.getZ()-outputPos.getZ();

        boolean construct = pLevel.getBlockState(inputPos).is(ModBlocks.APPLIANCE_PLINTH.get()) && pLevel.getBlockState(outputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (construct)
            pLevel.addParticle(ParticleTypes.ENCHANT, outputPos.getX()+0.5,
                    outputPos.getY()+2,
                    outputPos.getZ()+0.5,
                    ((float)x) , (1 - pRandom.nextFloat() - 1.0F), ((float)z) );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DIRECTION).add(TRIGGERED);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}

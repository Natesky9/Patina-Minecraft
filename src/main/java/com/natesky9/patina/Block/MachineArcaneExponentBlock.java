package com.natesky9.patina.Block;

import com.natesky9.patina.Block.Plinth.AppliancePlinthEntity;
import com.natesky9.patina.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class MachineArcaneExponentBlock extends Block {
    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public MachineArcaneExponentBlock(Properties p_49795_) {
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

        BlockPos inputOne = pPos.relative(direction.getClockWise());
        BlockPos inputTwo = pPos.relative(direction.getCounterClockWise());
        BlockPos output = pPos.relative(direction);

        boolean construct = pLevel.getBlockState(inputOne).is(ModBlocks.APPLIANCE_PLINTH.get())
                && pLevel.getBlockState(inputTwo).is(ModBlocks.APPLIANCE_PLINTH.get())
                && pLevel.getBlockState(output).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!construct) return;


        if (powered && !triggered)
        {
            pLevel.setBlock(pPos,pState
                    .setValue(TRIGGERED,true)
                    .setValue(DIRECTION,pState.getValue(DIRECTION)),2);
            pLevel.scheduleTick(pPos,this,20);
        }

        if (!powered && triggered)
        {
            pLevel.setBlock(pPos, pState.setValue(TRIGGERED,false).setValue(DIRECTION,direction),2);
        }
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        Direction direction = pState.getValue(DIRECTION);

        BlockPos inputOnePos = pPos.relative(direction.getClockWise());
        BlockPos inputTwoPos = pPos.relative(direction.getCounterClockWise());
        BlockPos outputPos = pPos.relative(direction);

        boolean construct = pLevel.getBlockState(inputOnePos).is(ModBlocks.APPLIANCE_PLINTH.get())
                && pLevel.getBlockState(inputTwoPos).is(ModBlocks.APPLIANCE_PLINTH.get())
                && pLevel.getBlockState(outputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!construct) return;

        if (!(pLevel.getBlockEntity(inputOnePos) instanceof AppliancePlinthEntity inputPlinthOne)) return;
        if (!(pLevel.getBlockEntity(inputTwoPos) instanceof AppliancePlinthEntity inputPlinthTwo)) return;
        if (!(pLevel.getBlockEntity(outputPos) instanceof AppliancePlinthEntity outputPlinth)) return;

        System.out.println("starting");

        ItemStack inputOne = inputPlinthOne.getStack();
        ItemStack inputTwo = inputPlinthTwo.getStack();
        //ItemStack output = inputOne.copy();

        if (!(inputOne.is(Items.ENCHANTED_BOOK))) return;
        if (!(inputTwo.is(Items.ENCHANTED_BOOK))) return;

        ItemEnchantments inputEnchants = EnchantmentHelper.getEnchantmentsForCrafting(inputOne);
        ItemEnchantments secondaryEnchants = EnchantmentHelper.getEnchantmentsForCrafting(inputTwo);
        if (!inputEnchants.keySet().containsAll(secondaryEnchants.keySet()))
        {
            System.out.println("Enchantments don't match, aborting!");
            return;
        }

        ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(inputTwo);

        EnchantmentHelper.updateEnchantments(inputOne,(mutable ->
        {
            mutable.keySet().forEach(holder ->
            {
                System.out.println(EnchantmentHelper.getItemEnchantmentLevel(holder,inputTwo));
                System.out.println("max level of: " + holder.value().getMaxLevel());
                if (mutable.getLevel(holder) <= holder.value().getMaxLevel())
                    mutable.upgrade(holder,enchants.getLevel(holder)+1);
                //
                boolean overloaded = enchants.getLevel(holder)+1 > holder.value().getMaxLevel();
                if (overloaded)
                {
                    //output.set(DataComponents.LORE,new ItemLore(List.of(Component.literal("The enchantment is overloaded, " +
                    //        "going over its natural values. Currently it seems to do nothing, " +
                    //        "but the laws of magic are not to be toyed with..").withStyle(ChatFormatting.DARK_PURPLE))));
                    pLevel.sendParticles(ParticleTypes.FLAME,outputPos.getX()+.5,outputPos.getY()+1,outputPos.getZ()+.5,
                            1000,0,0,0,.05);
                }
                });
            //else
            //    inputEnchants.keySet().forEach(holder -> mutable.set(holder,
            //            Math.max(mutable.getLevel(holder),inputEnchants.getLevel(holder))));
        }));

        pLevel.sendParticles(ParticleTypes.INSTANT_EFFECT,outputPos.getX()+.5,outputPos.getY()+1,outputPos.getZ()+.5,
                15,0,0,0,1);

        pLevel.playSound(null,pPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS,1,1);

        ItemStack output = inputOne.copy();

        inputPlinthOne.handler.setStackInSlot(0,new ItemStack(Items.BOOK));
        inputPlinthTwo.handler.setStackInSlot(0,new ItemStack(Items.BOOK));
        outputPlinth.handler.setStackInSlot(0,output);

        pLevel.sendBlockUpdated(inputOnePos,inputPlinthOne.getBlockState(),inputPlinthOne.getBlockState(),3);
        pLevel.sendBlockUpdated(inputTwoPos,inputPlinthTwo.getBlockState(),inputPlinthTwo.getBlockState(),3);

        pLevel.sendBlockUpdated(outputPos,outputPlinth.getBlockState(),outputPlinth.getBlockState(),3);

        //do combiny stuff here
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

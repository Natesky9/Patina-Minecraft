package com.natesky9.patina.Block;

import com.natesky9.patina.Block.Plinth.AppliancePlinthEntity;
import com.natesky9.patina.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class MachineArcaneDivisionBlock extends Block {
    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    final ColorParticleOption essence =  ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, Color.GREEN.getRGB());

    public MachineArcaneDivisionBlock(Properties p_49795_) {
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

        BlockPos inputPos = pPos.relative(direction.getOpposite());
        BlockPos outputPos = pPos.relative(Direction.DOWN);

        //if the output block is a cauldron or essence cauldron
        boolean isCauldron = pLevel.getBlockState(outputPos).is(Blocks.CAULDRON)
                || pLevel.getBlockState(outputPos).is(ModBlocks.ESSENCE_CAULDRON.get());

        boolean construct = pLevel.getBlockState(inputPos).is(ModBlocks.APPLIANCE_PLINTH.get()) && isCauldron;
        if (!construct) return;

        if (!(pLevel.getBlockEntity(inputPos) instanceof AppliancePlinthEntity inputPlinth)) return;

        if (powered && !triggered)
        {
            ItemStack input = inputPlinth.getStack();
            if (input.isEmpty() || EnchantmentHelper.getEnchantmentsForCrafting(input).isEmpty()) return;

            pLevel.setBlock(pPos,pState
                    .setValue(TRIGGERED,true)
                    .setValue(DIRECTION,pState.getValue(DIRECTION)),2);
            pLevel.scheduleTick(pPos,this,20);
        }
        if (powered & triggered)
        {
            System.out.println("trigger");
            pLevel.scheduleTick(pPos, this, 20);
        }
        if (!powered && triggered)
        {
            pLevel.setBlock(pPos, pState.setValue(TRIGGERED,false).setValue(DIRECTION,direction),2);
        }
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        Direction direction = pState.getValue(DIRECTION);

        BlockPos inputPos = pPos.relative(direction.getOpposite());
        BlockPos outputPos = pPos.relative(Direction.DOWN);

        //convert cauldrons into essence cauldron
        BlockState cauldronState = pLevel.getBlockState(outputPos);
        if (cauldronState.is(Blocks.CAULDRON))
        {
            pLevel.setBlock(outputPos,ModBlocks.ESSENCE_CAULDRON.get().defaultBlockState(), 2);
            cauldronState = pLevel.getBlockState(outputPos);
        }
        if (!cauldronState.is(ModBlocks.ESSENCE_CAULDRON.get()))
            return;

        if (!(pLevel.getBlockEntity(inputPos) instanceof AppliancePlinthEntity inputPlinth)) return;


        ItemStack input = inputPlinth.getStack();

        if (cauldronState.getValue(EssenceCauldronBlock.LEVEL) >= 15)
        {
            System.out.println("Cauldron is full!");
            pLevel.setBlock(pPos,pState.setValue(TRIGGERED,false),2);
            return;
        }

        if (!EnchantmentHelper.hasAnyEnchantments(input))
        {
            pLevel.setBlock(pPos,pState.setValue(TRIGGERED,false),2);
            return;
        }
        //only process enchantments

        ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(input);
        Holder<Enchantment> first =  enchants.keySet().stream().findFirst().get();
        int level = enchants.getLevel(first);

        //remove one level from the first enchant
        EnchantmentHelper.updateEnchantments(input,(mutable -> mutable.set(first,level-1)));
        if (input.is(Items.ENCHANTED_BOOK) && !EnchantmentHelper.hasAnyEnchantments(input))
        {
            inputPlinth.handler.extractItem(0,1,false);
            inputPlinth.handler.insertItem(0,Items.BOOK.getDefaultInstance(),false);
            pLevel.setBlock(pPos,pState.setValue(TRIGGERED,false),2);
        }
        pLevel.sendBlockUpdated(inputPos,inputPlinth.getBlockState(),inputPlinth.getBlockState(),3);

        //effects
        if (pLevel instanceof ServerLevel server)
        {
            server.sendParticles(essence,outputPos.getX()+.5,outputPos.getY()+1,outputPos.getZ()+.5,
                    15,0,0,0,1);
            pLevel.playSound(null,pPos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS,.5f,.5f);
        }

        int current = cauldronState.getValue(EssenceCauldronBlock.LEVEL);
        pLevel.setBlock(outputPos,ModBlocks.ESSENCE_CAULDRON.get().defaultBlockState()
                .setValue(EssenceCauldronBlock.LEVEL,current+1),3);
        pLevel.playSound(null,pPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS,1,
                (float)cauldronState.getValue(EssenceCauldronBlock.LEVEL)/30+.5f);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        //TODO:change with blockstate test
        Direction direction = pState.getValue(DIRECTION);

        BlockPos inputPos = pPos.relative(direction.getOpposite());
        BlockPos outputPos = pPos.relative(Direction.DOWN);
        int x = inputPos.getX()-outputPos.getX();
        int z = inputPos.getZ()-outputPos.getZ();

        boolean isInput = pLevel.getBlockState(inputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        boolean isOutput = pLevel.getBlockState(outputPos).is(Blocks.CAULDRON)
                || pLevel.getBlockState(outputPos).is(ModBlocks.ESSENCE_CAULDRON.get());
        boolean construct = isInput && isOutput;
        if (construct && pState.getValue(TRIGGERED))
            pLevel.addParticle(ParticleTypes.ENCHANT, outputPos.getX()+0.5,
                outputPos.getY()+3,
                outputPos.getZ()+0.5,
                    ((float)x) , (1 - pRandom.nextFloat() - 1.0F), ((float)z) );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DIRECTION).add(TRIGGERED);
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
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}

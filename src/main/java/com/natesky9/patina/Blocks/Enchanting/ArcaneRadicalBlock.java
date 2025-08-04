package com.natesky9.patina.Blocks.Enchanting;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.Blocks.PlinthBlock;
import com.natesky9.patina.Blocks.PlinthEntity;
import com.natesky9.patina.init.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ArcaneRadicalBlock extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    public static final MapCodec<ArcaneRadicalBlock> CODEC = simpleCodec(ArcaneRadicalBlock::new);
    public ArcaneRadicalBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH)
                .setValue(TRIGGERED, false));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(TRIGGERED);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
        boolean powered = level.hasNeighborSignal(pos);
        boolean triggered = state.getValue(TRIGGERED);
        Direction direction = state.getValue(FACING);

        BlockPos inputPos = pos.relative(Direction.UP);
        BlockPos fluidPos = null;

        //find fluid
        Optional<IFluidHandler> handler;
        for (Direction test:Direction.values())
        {
            if (test.equals(Direction.UP))
                continue;
            handler = FluidUtil.getFluidHandler(level, pos.relative(test), null);
            if (handler.isPresent())
            {
                fluidPos = pos.relative(test);
                break;
            }
        }
        if (fluidPos == null)
        {
            System.out.println("No fluid item1! check sides");
            return;
        }

        boolean inputValid = level.getBlockState(inputPos).getBlock() instanceof PlinthBlock;
        if (!inputValid) return;

        if (!(level.getBlockEntity(inputPos) instanceof PlinthEntity plinth)) return;
        //
        if (powered && !triggered)
        {
            ItemStack inputStack = plinth.inventory.getStackInSlot(0);
            int rework = inputStack.getOrDefault(DataComponents.REPAIR_COST,0);
            if (inputStack.isEmpty() || rework <= 0) return;

            level.setBlock(pos, state.setValue(TRIGGERED, true), 2);
            System.out.println("triggered");
        }
        if (powered && triggered)
        {
            System.out.println("tick");
            level.scheduleTick(pos, this, 8);
        }
        if (!powered && triggered)
        {
            level.setBlock(pos, state.setValue(TRIGGERED, false), 2);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        BlockPos inputPos = pos.relative(Direction.UP);
        BlockPos fluidPos = null;
        boolean powered = level.hasNeighborSignal(pos);
        boolean triggered = state.getValue(TRIGGERED);

        int cost = 10;

        boolean inputValid = level.getBlockState(inputPos).getBlock() instanceof PlinthBlock;
        if (!inputValid) return;
        if (!(level.getBlockEntity(inputPos) instanceof PlinthEntity plinth)) return;
        ItemStack inputStack = plinth.inventory.getStackInSlot(0);
        if (inputStack.isEmpty()) return;

        int rework = inputStack.getOrDefault(DataComponents.REPAIR_COST,0);

        ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(inputStack);
        int totalEnchants = 0;
        for (Holder<Enchantment> enchant:enchants.keySet())
        {
            totalEnchants += enchants.getLevel(enchant)*3;
        }
        if (rework <= totalEnchants)
        {
            System.out.println("rework cost is lowest it can go!");
            return;
        }



        //find fluid
        Optional<IFluidHandler> handler = Optional.empty();
        for (Direction test:Direction.values())
        {
            if (test.equals(Direction.UP))
                continue;
            handler = FluidUtil.getFluidHandler(level, pos.relative(test), null);
            if (handler.isPresent())
            {
                fluidPos = pos.relative(test);
                break;
            }
        }
        if (fluidPos == null)
        {
            System.out.println("No fluid item1! check sides");
            return;
        }
        FluidStack fluid = handler.get().getFluidInTank(0);

        if (!fluid.is(ModFluids.ESSENCE_SOURCE))
        {
            System.out.println("Fluid is not the right type!");
            return;
        }
        if (fluid.getAmount() < cost)
        {
            System.out.println("Not enough fluid for operation!");
            return;
        }

        //all checks passed, process
        handler.get().drain(cost, IFluidHandler.FluidAction.EXECUTE);
        inputStack.set(DataComponents.REPAIR_COST,rework - 1);
        level.sendBlockUpdated(inputPos, plinth.getBlockState(),plinth.getBlockState(),3);
        //effects
        level.sendParticles(ParticleTypes.GLOW,pos.getX()+.5f,pos.getY()+2.5f,pos.getZ()+.5f,
                8,0,0,0,.1);
        level.playSound(null,pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS,.5f,.1f);

        if (powered && triggered)
            level.scheduleTick(pos, this, 8);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

    }
}

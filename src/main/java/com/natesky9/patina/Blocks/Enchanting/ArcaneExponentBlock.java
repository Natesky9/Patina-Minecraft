package com.natesky9.patina.Blocks.Enchanting;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.Blocks.PlinthBlock;
import com.natesky9.patina.Blocks.PlinthEntity;
import com.natesky9.patina.init.ModBlocks;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
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

public class ArcaneExponentBlock extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    public static final MapCodec<ArcaneExponentBlock> CODEC = simpleCodec(ArcaneExponentBlock::new);
    public ArcaneExponentBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH)
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
        boolean powered = level.hasNeighborSignal(pos);
        boolean triggered = state.getValue(TRIGGERED);
        Direction direction = state.getValue(FACING);

        BlockPos inputPos = pos.relative(direction.getOpposite());
        BlockPos outputPos = pos.relative(direction);

        boolean inputValid = level.getBlockState(inputPos).getBlock() instanceof PlinthBlock;
        boolean outputValid = level.getBlockState(outputPos).getBlock() instanceof PlinthBlock;
        if (!inputValid || !outputValid) return;

        if (!(level.getBlockEntity(inputPos) instanceof PlinthEntity plinth)) return;
        if (!(level.getBlockEntity(outputPos) instanceof PlinthEntity outputPlinth)) return;

        ItemStack inputStack = plinth.inventory.getStackInSlot(0);
        if (inputStack.isEmpty() || !EnchantmentHelper.hasAnyEnchantments(inputStack)) return;
        if (!outputPlinth.inventory.getStackInSlot(0).is(Items.BOOK)) return;

        if (powered && !triggered)
        {

            level.setBlockAndUpdate(pos, state.setValue(TRIGGERED, true));
            level.scheduleTick(pos, this, 60);
            System.out.println("triggered");
        }
        if (powered && triggered)
        {
            System.out.println("tick");
            level.scheduleTick(pos, this, 60);
        }
        if (!powered && triggered)
        {
            level.setBlock(pos, state.setValue(TRIGGERED, false), 2);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        BlockPos inputPos = pos.relative(direction.getOpposite());
        BlockPos outputPos = pos.relative(direction);
        level.setBlockAndUpdate(pos,state.setValue(TRIGGERED,false));

        boolean inputValid = level.getBlockState(inputPos).getBlock() instanceof PlinthBlock;
        boolean outputValid = level.getBlockState(outputPos).getBlock() instanceof PlinthBlock;
        if (!inputValid || !outputValid) return;

        if (!(level.getBlockEntity(inputPos) instanceof PlinthEntity plinth)) return;
        if (!(level.getBlockEntity(outputPos) instanceof PlinthEntity outputPlinth)) return;

        ItemStack inputStack = plinth.inventory.getStackInSlot(0);
        ItemStack outputStack = outputPlinth.inventory.getStackInSlot(0);

        if (!inputStack.is(Items.ENCHANTED_BOOK) || !outputStack.is(Items.BOOK)) return;

        if (!EnchantmentHelper.hasAnyEnchantments(inputStack)) return;

        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(inputStack);
        Holder<Enchantment> first = enchantments.keySet().stream().findFirst().get();
        if (enchantments.size() > 1 || enchantments.getLevel(first) > 1)
        {
            System.out.println("too many enchants or above level 1!");
            return;
        }

        //get cost
        int enchantmentLevel = EnchantmentHelper.getTagEnchantmentLevel(first, inputStack);
        int cost = first.value().getMaxCost(enchantmentLevel);
        System.out.println("cost to clone: " + cost);
        //int rework = inputStack.getOrDefault(DataComponents.REPAIR_COST,0);
        //cost += rework;

        //find fluid
        BlockPos fluidInput = null;
        Optional<IFluidHandler> handler = Optional.empty();
        for (Direction test:Direction.values())
        {
            if (test.equals(direction) || test.equals(direction.getOpposite()))
                continue;
            handler = FluidUtil.getFluidHandler(level, pos.relative(test), null);
            if (handler.isPresent())
            {
                //we've found our fluid item1
                fluidInput = pos.relative(test);
                break;
            }
        }
        if (fluidInput == null)
        {
            System.out.println("No fluid item1! Check sides");
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
        int repairCost = inputStack.getOrDefault(DataComponents.REPAIR_COST,0);
        repairCost += 1;
        inputStack.set(DataComponents.REPAIR_COST,repairCost);

        //drain the fluid
        handler.get().drain(cost, IFluidHandler.FluidAction.EXECUTE);

        outputPlinth.inventory.setStackInSlot(0, inputStack.copy());
        plinth.setActive(true);
        outputPlinth.setActive(true);
        level.sendBlockUpdated(inputPos, plinth.getBlockState(), plinth.getBlockState(), 3);
        level.sendBlockUpdated(outputPos, outputPlinth.getBlockState(), outputPlinth.getBlockState(), 3);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean triggered = state.getValue(TRIGGERED);
        if (!triggered) return;

        Direction direction = state.getValue(FACING);
        BlockPos inputPos = pos.relative(direction.getOpposite());
        BlockPos outputPos = pos.relative(direction);

        boolean outputValid = level.getBlockState(outputPos).getBlock() instanceof PlinthBlock;
        boolean inputValid = level.getBlockState(inputPos).getBlock() instanceof PlinthBlock;
        if (!inputValid || !outputValid) return;
        if (!(level.getBlockEntity(inputPos) instanceof PlinthEntity)) return;
        if (!(level.getBlockEntity(outputPos) instanceof PlinthEntity)) return;


        //visual stuff
        float xSpeed = inputPos.getX() - outputPos.getX();
        float zSpeed = inputPos.getZ() - outputPos.getZ();
        float x1 = outputPos.getX()+.5f;
        float z1 = outputPos.getZ()+.5f;
        float y1 = outputPos.getY()+2.5f;
        for (int i=0; i<6; i++)
            level.addParticle(ParticleTypes.ENCHANT,x1,y1+ i*.05, z1,xSpeed,-1,zSpeed);
        float pitch = random.nextFloat()*.5f;
        level.playLocalSound(pos, SoundEvents.VILLAGER_WORK_CARTOGRAPHER, SoundSource.BLOCKS,.5f,pitch,false);
    }
}

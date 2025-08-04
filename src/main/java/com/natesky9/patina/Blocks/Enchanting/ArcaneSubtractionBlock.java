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
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
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

public class ArcaneSubtractionBlock extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public static final MapCodec<ArcaneSubtractionBlock> CODEC = simpleCodec(ArcaneSubtractionBlock::new);
    public ArcaneSubtractionBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
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

        BlockPos inputPos = pos.relative(direction.getOpposite());
        BlockPos outputPos = pos.relative(direction);

        boolean inputValid = level.getBlockState(inputPos).getBlock() instanceof PlinthBlock;
        boolean outputValid = FluidUtil.getFluidHandler(level, outputPos,null).isPresent();

        //not needed since fluid API
        //if (level.getBlockState(outputPos).is(Blocks.CAULDRON))
        //{//replace cauldrons with essence
        //    level.setBlock(outputPos, ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get().defaultBlockState(), 3);
        //    return;
        //}
        if (!inputValid || !outputValid) return;
        if (!(level.getBlockEntity(inputPos) instanceof PlinthEntity plinth)) return;

        if (powered && !triggered)
        {
            level.setBlockAndUpdate(pos, state.setValue(TRIGGERED, true).setValue(FACING, direction));
            level.scheduleTick(pos,this,10);
        }
        if (powered && triggered)
        {
            level.scheduleTick(pos, this, 10);
        }
        if (!powered && triggered)
        {
            level.setBlock(pos, state.setValue(TRIGGERED, false).setValue(FACING, direction), 2);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        Direction direction = state.getValue(FACING);

        //if placed next to a vanilla cauldron, convert it
        //no longer needed since fluid API
        //if (!direction.getAxis().isHorizontal()) return;
        //if (level.getBlockState(pos.relative(direction)).is(Blocks.CAULDRON))
        //    level.setBlock(pos.relative(direction), ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get().defaultBlockState(), 3);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        BlockPos inputPos = pos.relative(direction.getOpposite());
        BlockPos outputPos = pos.relative(direction);

        if (!level.hasNeighborSignal(pos))
            level.setBlockAndUpdate(pos,state.setValue(TRIGGERED,false));

        boolean isPlinth = level.getBlockState(inputPos).getBlock() instanceof PlinthBlock;
        Optional<IFluidHandler> handler = FluidUtil.getFluidHandler(level, outputPos, null);
        boolean outputValid = handler.isPresent();

        if (!isPlinth) return;
        if (!(level.getBlockEntity(inputPos) instanceof PlinthEntity plinth)) return;
        if (!outputValid) return;

        ItemStack input = plinth.inventory.getStackInSlot(0);

        if (!EnchantmentHelper.hasAnyEnchantments(input))
        {
            //the item no longer has enchants
            level.setBlock(pos, state.setValue(TRIGGERED, false), 2);
            return;
        }

        ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(input);
        Holder<Enchantment> first = enchants.keySet().stream().findFirst().get();
        int enchantsLevel = enchants.getLevel(first);

        //find the xp value
        int xp = first.value().getMinCost(enchantsLevel);
        //if (enchantsLevel > 1)
        //    xp -= first.value().getMinCost(enchantsLevel - 1);
        System.out.println("xp reclaimed: " + xp);
        //

        //checking if room
        FluidStack fluidStack =  new FluidStack(ModFluids.ESSENCE_SOURCE, xp);
        int moved = handler.get().fill(fluidStack, IFluidHandler.FluidAction.SIMULATE);
        //fluid tank
        if (moved > 0)
        {
            handler.get().fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
            xp -= moved;
            if (xp > 0 && (Math.random() <= (float)xp/100))
                handler.get().fill(new FluidStack(ModFluids.ESSENCE_SOURCE, 100), IFluidHandler.FluidAction.EXECUTE);

        }
        else
        {
            FluidStack cauldronLevel = new FluidStack(ModFluids.ESSENCE_SOURCE, 100);
            if (handler.get().fill(cauldronLevel, IFluidHandler.FluidAction.SIMULATE) > 0)
            {
                if (Math.random() <= (float)xp/100)
                    handler.get().fill(cauldronLevel, IFluidHandler.FluidAction.EXECUTE);
            }
            else
            {
                System.out.println("not enough room to fill cauldron!");
                return;
            }
        }


        //process the enchants
        EnchantmentHelper.updateEnchantments(input, (mutable -> mutable.set(first, enchantsLevel-1)));

        //reduce rework
        int rework = input.getOrDefault(DataComponents.REPAIR_COST,0);
        input.set(DataComponents.REPAIR_COST,Math.max(rework-1,0));

        //convert empty books
        if (input.is(Items.ENCHANTED_BOOK) && !EnchantmentHelper.hasAnyEnchantments(input))
        {
            plinth.inventory.setStackInSlot(0, Items.BOOK.getDefaultInstance());
            level.setBlock(pos, state.setValue(TRIGGERED, false), 2);
        }
        //
        //think this is for updating the BER
        level.sendBlockUpdated(inputPos, plinth.getBlockState(), plinth.getBlockState(), 3);

        if (level.hasNeighborSignal(pos) && EnchantmentHelper.hasAnyEnchantments(input))
            level.scheduleTick(pos, this, 8);
        if (!EnchantmentHelper.hasAnyEnchantments(input))
        {
            level.setBlockAndUpdate(pos,state.setValue(TRIGGERED,false));
            level.playSound(null, pos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, .1f, .5f);
            plinth.setActive(true);
        }
        //ExperienceOrb.award(level, pos.relative(direction).getCenter(), 10);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean triggered = state.getValue(TRIGGERED);
        if (!triggered) return;

        Direction direction = state.getValue(FACING);
        BlockPos inputPos = pos.relative(direction.getOpposite());
        BlockPos outputPos = pos.relative(direction);
        boolean inputValid = level.getBlockState(inputPos).getBlock() instanceof PlinthBlock;
        boolean outputValid = FluidUtil.getFluidHandler(level,outputPos,null).isPresent();
        if (!inputValid || !outputValid) return;

        if (!(level.getBlockEntity(inputPos) instanceof PlinthEntity plinth)) return;
        ItemStack stack = plinth.inventory.getStackInSlot(0);
        if (stack.isEmpty() || !EnchantmentHelper.hasAnyEnchantments(stack)) return;

        float xrand = inputPos.getX()+.4f + random.nextFloat()*.2f;
        float zrand = inputPos.getZ()+.4f + random.nextFloat()*.2f;
        level.addParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR,xrand,inputPos.getY()+1.5,zrand,
                0,0,0);
        level.addParticle(ParticleTypes.GLOW,xrand,inputPos.getY()+1.5,zrand,
                0,.1,0);
        level.playLocalSound(pos,SoundEvents.BOTTLE_FILL_DRAGONBREATH,SoundSource.BLOCKS,.1f,.1f,false);
    }
}

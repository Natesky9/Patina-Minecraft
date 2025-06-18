package com.natesky9.patina.Blocks.Enchanting;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.Blocks.PlinthEntity;
import com.natesky9.patina.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import org.jetbrains.annotations.Nullable;

public class ArcaneMultiplicationBlock extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    public static final MapCodec<ArcaneMultiplicationBlock> CODEC = simpleCodec(ArcaneMultiplicationBlock::new);

    public ArcaneMultiplicationBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TRIGGERED, false));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
        boolean powered = level.hasNeighborSignal(pos);
        boolean triggered = state.getValue(TRIGGERED);
        Direction direction = state.getValue(FACING);

        BlockPos inputOnePos = pos.relative(direction.getClockWise());
        BlockPos inputTwoPos = pos.relative(direction.getCounterClockWise());
        BlockPos outputPos = pos.relative(direction);

        boolean inputOneValid = level.getBlockState(inputOnePos).is(ModBlocks.APPLIANCE_PLINTH.get());
        boolean inputTwoValid = level.getBlockState(inputTwoPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        boolean outputValid = level.getBlockState(outputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!inputOneValid || !inputTwoValid || !outputValid) return;

        if (powered && triggered)
        {
            level.scheduleTick(pos, this, 8);
        }
        if (powered && !triggered)
        {
            level.setBlock(pos, state.setValue(TRIGGERED, true), 2);
        }
        if (!powered && triggered)
        {
            level.setBlock(pos, state.setValue(TRIGGERED, false), 2);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);

        BlockPos inputOnePos = pos.relative(direction.getClockWise());
        BlockPos inputTwoPos = pos.relative(direction.getCounterClockWise());
        BlockPos outputPos = pos.relative(direction);

        boolean inputOneValid = level.getBlockState(inputOnePos).is(ModBlocks.APPLIANCE_PLINTH.get());
        boolean inputTwoValid = level.getBlockState(inputTwoPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        boolean outputValid = level.getBlockState(outputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!inputOneValid || !inputTwoValid || !outputValid) return;

        if (!(level.getBlockEntity(inputOnePos) instanceof PlinthEntity inputPlinthOne)) return;
        if (!(level.getBlockEntity(inputTwoPos) instanceof PlinthEntity inputPlinthTwo)) return;
        if (!(level.getBlockEntity(outputPos) instanceof PlinthEntity outputPlinth)) return;

        ItemStack inputStackOne = inputPlinthOne.inventory.getStackInSlot(0);
        ItemStack inputStackTwo = inputPlinthTwo.inventory.getStackInSlot(0);
        ItemStack outputStack = outputPlinth.inventory.getStackInSlot(0);

        if (!inputStackOne.is(Items.ENCHANTED_BOOK)
                || !inputStackTwo.is(Items.ENCHANTED_BOOK)
                || !outputStack.is(Items.BOOK))
        {
            System.out.println("invalid items for upgrading");
            return;
        }

        ItemEnchantments primaryEnchants = EnchantmentHelper.getEnchantmentsForCrafting(inputStackOne);
        ItemEnchantments secondaryEnchants = EnchantmentHelper.getEnchantmentsForCrafting(inputStackTwo);
        Holder<Enchantment> enchantmentOne = primaryEnchants.keySet().stream().findFirst().get();
        Holder<Enchantment> enchantmentTwo = secondaryEnchants.keySet().stream().findFirst().get();
        int levelOne = primaryEnchants.getLevel(enchantmentOne);
        int levelTwo = secondaryEnchants.getLevel(enchantmentTwo);
        if (primaryEnchants.keySet().size() > 1)
        {
            System.out.println("Only single enchants allowed!");
            return;
        }
        if (!primaryEnchants.keySet().containsAll(secondaryEnchants.keySet())
                || !secondaryEnchants.keySet().containsAll(primaryEnchants.keySet()))
        {
            System.out.println("Enchantments don't match, aborting!");
            return;
        }
        if (levelOne != levelTwo)
        {
            System.out.println("Enchantments are not the same level");
            return;
        }

        if (levelOne >= enchantmentOne.value().getMaxLevel()+1)
        {
            System.out.println("Enchantment is already max!");
            return;
        }

        EnchantmentHelper.updateEnchantments(inputStackOne, mutable ->
        {
            mutable.keySet().forEach(holder ->
            {
                mutable.upgrade(holder, Mth.clamp(primaryEnchants.getLevel(holder)+1, 0, holder.value().getMaxLevel()+1));
            });
        });
        outputPlinth.inventory.setStackInSlot(0, inputStackOne.copy());
        inputPlinthOne.inventory.setStackInSlot(0, Items.BOOK.getDefaultInstance());
        inputPlinthTwo.inventory.setStackInSlot(0, Items.BOOK.getDefaultInstance());

        outputPlinth.setActive(true);
        outputPlinth.setActive(true);
        outputPlinth.setActive(true);

        level.sendBlockUpdated(outputPos, inputPlinthOne.getBlockState(), inputPlinthTwo.getBlockState(), 3);
        level.sendBlockUpdated(outputPos, inputPlinthTwo.getBlockState(), inputPlinthTwo.getBlockState(), 3);
        level.sendBlockUpdated(outputPos, outputPlinth.getBlockState(), outputPlinth.getBlockState(), 3);
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
        return defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection())
                .setValue(TRIGGERED, false);
    }
}

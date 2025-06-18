package com.natesky9.patina.Blocks.Enchanting;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.Blocks.PlinthEntity;
import com.natesky9.patina.init.ModBlocks;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;

public class ArcaneAdditionBlock extends Block {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    public static final MapCodec<ArcaneAdditionBlock> CODEC = simpleCodec(ArcaneAdditionBlock::new);

    public ArcaneAdditionBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TRIGGERED, false));
    }
    //add replaces the old anvil applying of enchantments onto books
    //inputs are a chiseled bookshelf behind, and a plinth in front
    //after application, the books are left behind

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
        boolean powered = level.hasNeighborSignal(pos);
        boolean triggered = state.getValue(TRIGGERED);
        Direction direction = state.getValue(FACING);

        BlockPos inputPos = pos.relative(direction.getOpposite());
        BlockPos outputPos = pos.relative(direction);

        boolean inputValid = level.getBlockState(inputPos).is(Blocks.CHISELED_BOOKSHELF);
        boolean outputValid = level.getBlockState(outputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!inputValid || !outputValid) return;
        if (!(level.getBlockEntity(outputPos) instanceof PlinthEntity output)) return;

        if (powered && !triggered)
        {
            ItemStack outputStack = output.inventory.getStackInSlot(0);
            if (!EnchantmentHelper.hasAnyEnchantments(outputStack))
                level.setBlock(pos, state.setValue(TRIGGERED, true).setValue(FACING, direction),2);
        }
        if (powered && triggered)
        {
            level.scheduleTick(pos, this, 8);
        }
        if (!powered && triggered)
        {
            level.setBlock(pos, state.setValue(TRIGGERED, false).setValue(FACING, direction), 2);
        }
        if (!powered && triggered)
        {
            level.setBlock(pos, state.setValue(TRIGGERED, false).setValue(FACING, direction), 2);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        //optional placement rules here,
        //in case you want to add placing against relevant blocks
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        BlockPos inputPos = pos.relative(direction.getOpposite());
        BlockPos outputPos = pos.relative(direction);

        boolean inputValid = level.getBlockState(inputPos).is(Blocks.CHISELED_BOOKSHELF);
        boolean outputValid = level.getBlockState(outputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!inputValid || !outputValid) return;
        if (!(level.getBlockEntity(outputPos) instanceof PlinthEntity plinth)) return;
        if (!(level.getBlockEntity(inputPos) instanceof ChiseledBookShelfBlockEntity inputShelf)) return;

        //checks passed

        ItemStack outputStack = plinth.inventory.getStackInSlot(0);

        if (outputStack.is(Items.BOOK))
        {
            plinth.inventory.setStackInSlot(0, Items.ENCHANTED_BOOK.getDefaultInstance());
            //have to reset here since it's a new item
            outputStack = plinth.inventory.getStackInSlot(0);
        }

        for (int i=0; i<inputShelf.getContainerSize(); i++)
        {
            ItemStack book = inputShelf.getItem(i);
            if (book.isEmpty()) continue;
            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(book);
            boolean isBook = book.is(Items.ENCHANTED_BOOK);
            boolean canEnchant = outputStack.isBookEnchantable(book);

            if (!isBook)
                System.out.println("Book is not a valid enchanting book!");
            if (!canEnchant)
                System.out.println("Book cannot be applied!");

            if (isBook && canEnchant && EnchantmentHelper.hasAnyEnchantments(book))
            {
                for (Object2IntMap.Entry<Holder<Enchantment>> entry: enchantments.entrySet())
                {
                    if (outputStack.getEnchantments().keySet().contains(entry.getKey()))
                    {
                        System.out.println("Duplicate enchant, skipping!");
                        level.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1, 1);
                        level.sendParticles(ParticleTypes.WHITE_ASH, outputPos.getX()+.5, outputPos.getY()+1, outputPos.getZ()+.5,
                                15, 0, 0, 0, 1);
                        continue;
                    }
                    outputStack.enchant(entry.getKey(), entry.getIntValue());
                    inputShelf.setItem(i, Items.BOOK.getDefaultInstance());
                    //

                    //reworking level
                    int levels =
                            (int) EnchantmentHelper.getEnchantmentsForCrafting(outputStack).entrySet().stream().map(holder -> holder.getIntValue()).count();
                    System.out.println(levels);
                    outputStack.set(DataComponents.REPAIR_COST,levels);
                }
                level.sendBlockUpdated(outputPos, plinth.getBlockState(), plinth.getBlockState(), 3);
                level.setBlock(pos, state.setValue(TRIGGERED,false), 2);
                plinth.setActive(true);
            }
        }
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
}

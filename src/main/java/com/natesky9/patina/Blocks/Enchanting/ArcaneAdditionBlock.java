package com.natesky9.patina.Blocks.Enchanting;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.Blocks.PlinthBlock;
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
        //TODO: change to #updateShape
        boolean powered = level.hasNeighborSignal(pos);
        boolean triggered = state.getValue(TRIGGERED);
        boolean active = level.shouldTickBlocksAt(pos);
        Direction direction = state.getValue(FACING);

        //temporary filter until this method changes to update shape
        if (neighborBlock == Blocks.CHISELED_BOOKSHELF) return;

        if (!(powered || triggered)) return;


        BlockPos inputPos = pos.relative(direction.getOpposite());
        BlockPos outputPos = pos.relative(direction);

        boolean inputValid = level.getBlockState(inputPos).is(Blocks.CHISELED_BOOKSHELF);
        if (!inputValid)
        {
            if (level instanceof ServerLevel server)
            {
                server.sendParticles(ParticleTypes.SMOKE,inputPos.getX()+.5f,outputPos.getY()+.5,outputPos.getZ()+.5,
                        4,0,0,0,.01);
                server.playSound(null,inputPos,SoundEvents.CHISELED_BOOKSHELF_PLACE,SoundSource.BLOCKS,1f,1f);
            }
            return;
        }
        boolean outputValid = level.getBlockState(outputPos).getBlock() instanceof PlinthBlock;
        if (!outputValid)
        {
            //if (level instanceof ServerLevel server)
            //{
            //    server.sendParticles(ParticleTypes.SMOKE,outputPos.getX()+.5f,outputPos.getY()+.5,outputPos.getZ()+.5,
            //            4,0,0,0,.01);
            //    server.playSound(null,outputPos,SoundEvents.ITEM_FRAME_BREAK,SoundSource.BLOCKS,1,1);
            //}
            return;
        }
        if (!(level.getBlockEntity(outputPos) instanceof PlinthEntity output)) return;

        ItemStack outputStack = output.inventory.getStackInSlot(0);
        if (outputStack.isEmpty())
        {
            //if (level instanceof ServerLevel server)
            //{
            //    server.sendParticles(ParticleTypes.SMOKE,outputPos.getX()+.5f,outputPos.getY()+1.5,outputPos.getZ()+.5f,
            //        4,0,0,0,.01);
            //    server.playSound(null,outputPos,SoundEvents.ITEM_PICKUP,SoundSource.BLOCKS,1,1);
            //}
            return;
        }
        if (outputStack.isEnchanted())
            return;


        if (powered && !triggered)
        {
            level.setBlockAndUpdate(pos, state.setValue(TRIGGERED, true).setValue(FACING, direction));
            level.scheduleTick(pos,this,60);
        }
        if (powered && triggered)
        {
            //level.scheduleTick(pos, this, 60);
        }
        if (!powered && triggered)
        {
            if (!active)
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
        level.setBlockAndUpdate(pos,state.setValue(TRIGGERED,false));

        boolean inputValid = level.getBlockState(inputPos).is(Blocks.CHISELED_BOOKSHELF);
        boolean outputValid = level.getBlockState(outputPos).getBlock() instanceof PlinthBlock;
        if (!inputValid || !outputValid) return;
        if (!(level.getBlockEntity(outputPos) instanceof PlinthEntity plinth)) return;
        if (!(level.getBlockEntity(inputPos) instanceof ChiseledBookShelfBlockEntity inputShelf)) return;

        ItemStack outputStack = plinth.inventory.getStackInSlot(0);
        if (outputStack.isEmpty())
        {
            level.sendParticles(ParticleTypes.SMOKE,outputPos.getX()+.5f,outputPos.getY()+1.5,outputPos.getZ()+.5f,
                    4,0,0,0,.01);
            level.playSound(null,pos,SoundEvents.GENERIC_EXTINGUISH_FIRE,SoundSource.BLOCKS);
            return;
        }
        //checks passed


        int repairCost = outputStack.getOrDefault(DataComponents.REPAIR_COST,0);
        int inputCost = 0;

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
            {
                System.out.println("Book is not a valid enchanting book!");
                continue;
            }
            if (!canEnchant)
            {
                System.out.println("Book cannot be applied!");
                continue;
            }

            if (EnchantmentHelper.hasAnyEnchantments(book))
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

                    inputCost += entry.getIntValue()*3;
                    outputStack.enchant(entry.getKey(), entry.getIntValue());
                    inputShelf.setItem(i, Items.BOOK.getDefaultInstance());
                }
            }

            outputStack.set(DataComponents.REPAIR_COST,repairCost+inputCost);
            System.out.println("set cost to: " + repairCost);

            level.sendBlockUpdated(outputPos, plinth.getBlockState(), plinth.getBlockState(), 3);
            level.setBlock(pos, state.setValue(TRIGGERED,false), 2);
            plinth.setActive(true);
        }

        level.playSound(null,pos,SoundEvents.ENCHANTMENT_TABLE_USE,SoundSource.BLOCKS,1,1f);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean triggered = state.getValue(TRIGGERED);
        if (!triggered) return;

        Direction direction = state.getValue(FACING);
        BlockPos inputPos = pos.relative(direction.getOpposite());
        BlockPos outputPos = pos.relative(direction);

        boolean inputValid = level.getBlockState(inputPos).is(Blocks.CHISELED_BOOKSHELF);
        boolean outputValid = level.getBlockState(outputPos).getBlock() instanceof PlinthBlock;
        if (!inputValid || !outputValid) return;
        if (!(level.getBlockEntity(outputPos) instanceof PlinthEntity plinth)) return;
        if (!(level.getBlockEntity(inputPos) instanceof ChiseledBookShelfBlockEntity inputShelf)) return;


        //visual stuff
        for (int i=0; i<6; i++)
        {
            float xrand = random.nextFloat()-.5f;
            float zrand = random.nextFloat()-.5f;
            float xSpeed = inputPos.getX() - outputPos.getX()+xrand;
            float zSpeed = inputPos.getZ() - outputPos.getZ()+zrand;
            float x1 = outputPos.getX()+.5f;
            float z1 = outputPos.getZ()+.5f;
            float y1 = outputPos.getY()+2.5f;
            level.addParticle(ParticleTypes.ENCHANT,x1,y1+i*.1, z1,xSpeed,-1.5-i*.1,zSpeed);
        }
        level.playLocalSound(pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS,.5f,.1f,false);
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

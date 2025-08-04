package com.natesky9.patina.Blocks.Enchanting;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.Blocks.PlinthBlock;
import com.natesky9.patina.Blocks.PlinthEntity;
import com.natesky9.patina.init.ModBlockEntities;
import com.natesky9.patina.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
import org.jetbrains.annotations.Nullable;

public class ArcaneDivisionBlock extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    public static final MapCodec<ArcaneDivisionBlock> CODEC = simpleCodec(ArcaneDivisionBlock::new);

    public ArcaneDivisionBlock(Properties p_49795_) {
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
        boolean outputValid = level.getBlockState(outputPos).is(Blocks.CHISELED_BOOKSHELF);
        if (!inputValid || !outputValid) return;

        if (!(level.getBlockEntity(inputPos) instanceof PlinthEntity plinth)) return;
        ItemStack inputStack = plinth.inventory.getStackInSlot(0);
        if (inputStack.isEmpty() || !EnchantmentHelper.hasAnyEnchantments(inputStack)) return;

        if (powered && !triggered)
        {
            level.setBlockAndUpdate(pos, state.setValue(TRIGGERED, true));
            level.scheduleTick(pos, this, 60);
        }
        if (powered && triggered)
        {
            //level.scheduleTick(pos, this, 60);
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
        boolean outputValid = level.getBlockState(outputPos).is(Blocks.CHISELED_BOOKSHELF);
        if (!inputValid || !outputValid) return;
        if (!(level.getBlockEntity(inputPos) instanceof PlinthEntity plinth)) return;
        if (!(level.getBlockEntity(outputPos) instanceof ChiseledBookShelfBlockEntity outputShelf)) return;

        ItemStack inputStack = plinth.inventory.getStackInSlot(0);
        if (inputStack.isEmpty() || !EnchantmentHelper.hasAnyEnchantments(inputStack)) return;

        for (int i=EnchantmentHelper.getEnchantmentsForCrafting(inputStack).size(); i>0; i--)
        {
            EnchantmentHelper.updateEnchantments(inputStack, mutable ->
            {
                boolean present = mutable.keySet().stream().findFirst().isPresent();
                //don't process curses
                boolean curse = mutable.keySet().stream().findFirst().get().is(EnchantmentTags.CURSE);
                boolean room = shelfHasRoom(outputShelf);

                if (present && room && !curse)
                {
                    Holder<Enchantment> entry = mutable.keySet().stream().findFirst().get();
                    ItemStack book = Items.ENCHANTED_BOOK.getDefaultInstance();
                    book.enchant(entry, mutable.getLevel(entry));

                    //subtract the repair cost onto the book
                    book.set(DataComponents.REPAIR_COST,mutable.getLevel(entry)*3);
                    int repair = inputStack.getOrDefault(DataComponents.REPAIR_COST,0);
                    int subtracted = Mth.clamp(repair - mutable.getLevel(entry)*3,0,repair);
                    inputStack.set(DataComponents.REPAIR_COST,subtracted);

                    mutable.removeIf(key -> key == entry);
                    setBookInShelf(outputShelf, book);
                }
            });
        }
        if (inputStack.is(Items.ENCHANTED_BOOK) && !EnchantmentHelper.hasAnyEnchantments(inputStack))
        {
            //replace blank books with regular ones
            plinth.inventory.setStackInSlot(0, Items.BOOK.getDefaultInstance());
        }

        plinth.setActive(true);
        level.sendBlockUpdated(inputPos, plinth.getBlockState(), plinth.getBlockState(), 3);
        level.playSound(null,pos,SoundEvents.ENCHANTMENT_TABLE_USE,SoundSource.BLOCKS,1,.1f);
    }
    void setBookInShelf(ChiseledBookShelfBlockEntity entity, ItemStack stack)
    {
        for (int i=0; i<entity.getContainerSize(); i++)
        {
            if (entity.getItem(i).is(Items.BOOK))
            {
                entity.setItem(i, stack);
                return;
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean triggered = state.getValue(TRIGGERED);
        if (!triggered) return;

        Direction direction = state.getValue(FACING);
        BlockPos inputPos = pos.relative(direction.getOpposite());
        BlockPos outputPos = pos.relative(direction);

        boolean outputValid = level.getBlockState(outputPos).is(Blocks.CHISELED_BOOKSHELF);
        boolean inputValid = level.getBlockState(inputPos).getBlock() instanceof PlinthBlock;
        if (!inputValid || !outputValid) return;
        if (!(level.getBlockEntity(inputPos) instanceof PlinthEntity)) return;
        if (!(level.getBlockEntity(outputPos) instanceof ChiseledBookShelfBlockEntity)) return;


        //visual stuff
        float xrand = random.nextFloat()-.5f;
        float zrand = random.nextFloat()-.5f;
        float xSpeed = inputPos.getX() - outputPos.getX()+xrand;
        float zSpeed = inputPos.getZ() - outputPos.getZ()+zrand;
        float x1 = outputPos.getX()  -xrand+.5f;
        float z1 = outputPos.getZ()  -zrand+.5f;
        for (int i=0; i<6; i++)
            level.addParticle(ParticleTypes.ENCHANT,x1,outputPos.getY()+2+ i*.05, z1,xSpeed,-.5,zSpeed);
        level.playLocalSound(pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS,.5f,.1f,false);
    }

    boolean shelfHasRoom(ChiseledBookShelfBlockEntity shelf)
    {
        for (int i=0; i<shelf.getContainerSize();i++)
        {
            if (shelf.getItem(i).is(Items.BOOK)) return true;
        }
        return false;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }
}

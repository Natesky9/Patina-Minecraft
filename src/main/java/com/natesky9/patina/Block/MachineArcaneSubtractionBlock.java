package com.natesky9.patina.Block;

import com.natesky9.patina.Block.Plinth.AppliancePlinthEntity;
import com.natesky9.patina.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MachineArcaneSubtractionBlock extends Block {
    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public MachineArcaneSubtractionBlock(Properties p_49795_) {
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

        System.out.println("0");
        BlockPos inputPos = pPos.relative(direction.getOpposite());
        BlockPos outputPos = pPos.relative(Direction.DOWN);

        //input is chiseled bookshelf, output is plinth
        boolean isShelf = pLevel.getBlockState(outputPos).is(Blocks.CHISELED_BOOKSHELF);
        boolean isPlinth = pLevel.getBlockState(inputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!isShelf || !isPlinth) return;

        if (!(pLevel.getBlockEntity(inputPos) instanceof AppliancePlinthEntity inputPlinth)) return;

        System.out.println("A");
        if (powered && !triggered)
        {

            ItemStack input = inputPlinth.getStack();
            if (input.isEmpty() || !input.isEnchanted()) return;

            pLevel.setBlock(pPos,pState.setValue(TRIGGERED,true).setValue(DIRECTION,direction),2);
            System.out.println("initial");
            //pLevel.scheduleTick(pPos,this,4);
        }
        if (powered & triggered)
        {
            System.out.println("trigger");
            pLevel.scheduleTick(pPos, this, 8);
        }
        if (!powered && triggered)
        {
            pLevel.setBlock(pPos,pState.setValue(TRIGGERED,false).setValue(DIRECTION,direction),2);
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
            return defaultBlockState().setValue(DIRECTION,pContext.getClickedFace());
        //place reversed against bookshelves
        if (Direction.Plane.VERTICAL.test(direction) && pContext.getLevel().getBlockState(pContext.getClickedPos()
                .relative(direction)).is(Blocks.CHISELED_BOOKSHELF))
            return defaultBlockState().setValue(DIRECTION,pContext.getClickedFace().getOpposite());
        //otherwise normal facing rules
        return defaultBlockState().setValue(DIRECTION,pContext.getHorizontalDirection());
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        Direction direction = pState.getValue(DIRECTION);
        BlockPos inputPos = pPos.relative(direction.getOpposite());
        BlockPos outputPos = pPos.relative(Direction.DOWN);

        //output is chiseled bookshelf, input is plinth
        boolean isShelf = pLevel.getBlockState(outputPos).is(Blocks.CHISELED_BOOKSHELF);
        boolean isPlinth = pLevel.getBlockState(inputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!isShelf || !isPlinth) return;

        if (!(pLevel.getBlockEntity(inputPos) instanceof AppliancePlinthEntity inputPlinth)) return;
        ItemStack input = inputPlinth.getStack();
        if (input.isEmpty() || !input.isEnchanted()) return;

        if (!(pLevel.getBlockEntity(outputPos) instanceof ChiseledBookShelfBlockEntity outputShelf)) return;

        @NotNull LazyOptional<IItemHandler> handler = outputShelf.getCapability(ForgeCapabilities.ITEM_HANDLER);

        handler.ifPresent(itemHandler ->
        {
            //ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(input);
            EnchantmentHelper.updateEnchantments(input,mutable ->
            {
                boolean present = mutable.keySet().stream().findFirst().isPresent();
                boolean room = hasRoom(itemHandler);
                if (present && room)
                {
                    System.out.println("removing enchant");
                    Holder<Enchantment> entry = mutable.keySet().stream().findFirst().get();
                    ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                    book.enchant(entry,mutable.getLevel(entry));

                    mutable.removeIf((key) -> key == entry);
                    for (int i = 0; i < itemHandler.getSlots();i++)
                    {
                        if (itemHandler.getStackInSlot(i).isEmpty())
                        {
                            itemHandler.insertItem(i,book,false);
                            break;
                        }
                    }
                }

            });

            pLevel.sendBlockUpdated(inputPos,inputPlinth.getBlockState(),inputPlinth.getBlockState(),3);
            //effect
            if (pLevel instanceof ServerLevel server)
                server.sendParticles(ParticleTypes.EFFECT,outputPos.getX()+.5,outputPos.getY()+1,outputPos.getZ()+.5,
                        15,0,0,0,1);
        });
    }
    boolean hasRoom(IItemHandler handler)
    {
        for (int i = 0;i < handler.getSlots();i++)
        {
            if (handler.getStackInSlot(i).isEmpty()) return true;
        }
        return false;
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

package com.natesky9.patina.Block;

import com.natesky9.patina.Block.Plinth.AppliancePlinthEntity;
import com.natesky9.patina.init.ModBlocks;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MachineArcaneAddingBlock extends Block {
    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public MachineArcaneAddingBlock(Properties p_49795_) {
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

        BlockPos inputPos = pPos.relative(Direction.UP);
        BlockPos outputPos = pPos.relative(direction);

        //input is chiseled bookshelf, output is plinth
        boolean isShelf = pLevel.getBlockState(inputPos).is(Blocks.CHISELED_BOOKSHELF);
        boolean isPlinth = pLevel.getBlockState(outputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!isShelf || !isPlinth) return;

        if (!(pLevel.getBlockEntity(outputPos) instanceof AppliancePlinthEntity outputPlinth)) return;

        System.out.println("A");
        if (powered && !triggered)
        {
            ItemStack output = outputPlinth.getStack();
            System.out.println("initial");
            if (!output.isEnchanted())
                pLevel.setBlock(pPos,pState.setValue(TRIGGERED,true).setValue(DIRECTION,pState.getValue(DIRECTION)),2);
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
            return defaultBlockState().setValue(DIRECTION,pContext.getClickedFace().getOpposite());
        //place reversed against bookshelves
        if (Direction.Plane.VERTICAL.test(direction) && pContext.getLevel().getBlockState(pContext.getClickedPos()
                .relative(direction)).is(Blocks.CHISELED_BOOKSHELF))
            return defaultBlockState().setValue(DIRECTION,pContext.getClickedFace());
        //otherwise normal facing rules
        return defaultBlockState().setValue(DIRECTION,pContext.getHorizontalDirection());
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        Direction direction = pState.getValue(DIRECTION);
        BlockPos inputPos = pPos.relative(Direction.UP);
        BlockPos outputPos = pPos.relative(direction);

        //input is chiseled bookshelf, output is plinth
        boolean isShelf = pLevel.getBlockState(inputPos).is(Blocks.CHISELED_BOOKSHELF);
        boolean isPlinth = pLevel.getBlockState(outputPos).is(ModBlocks.APPLIANCE_PLINTH.get());
        if (!isShelf || !isPlinth) return;

        if (!(pLevel.getBlockEntity(outputPos) instanceof AppliancePlinthEntity outputPlinth)) return;
        ItemStack output = outputPlinth.getStack();
        if (output.isEmpty() || !output.getItem().isEnchantable(output)) return;

        if (!(pLevel.getBlockEntity(inputPos) instanceof ChiseledBookShelfBlockEntity inputShelf)) return;


        if (pLevel instanceof ServerLevel server)
            server.sendParticles(ParticleTypes.ENCHANT,outputPos.getX()+.5,outputPos.getY()+1,outputPos.getZ()+.5,
                    15,0,0,0,1);


        @NotNull LazyOptional<IItemHandler> handler = inputShelf.getCapability(ForgeCapabilities.ITEM_HANDLER);

        handler.ifPresent(itemHandler ->
        {
            for (int i=0;i < itemHandler.getSlots();i++)
            {
                System.out.println("run");
                ItemStack book = itemHandler.getStackInSlot(i);
                ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(book);

                boolean enchant = book.is(Items.ENCHANTED_BOOK);
                boolean canEnchant = output.isBookEnchantable(book);

                if (!enchant)
                    System.out.println("Book is not a valid enchanting book!");
                if (!canEnchant)
                    System.out.println("Book cannot be applied!");


                if (enchant && canEnchant && EnchantmentHelper.hasAnyEnchantments(book))
                {
                    for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
                        if (output.getEnchantments().keySet().contains(entry.getKey()))
                        {
                            System.out.println("duplicate enchant, skipping!");
                            if (pLevel instanceof ServerLevel server)
                            {//fail condition
                                server.playSound(null,pPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS,.5f,.5f);
                                server.sendParticles(ParticleTypes.WHITE_ASH,outputPos.getX()+.5,outputPos.getY()+1,outputPos.getZ()+.5,
                                        15,0,0,0,1);
                            }
                            continue;
                        }
                        //success
                        output.enchant(entry.getKey(), entry.getIntValue());
                        itemHandler.extractItem(i,1,false);
                        //update the plinth to show
                        pLevel.sendBlockUpdated(outputPos,outputPlinth.getBlockState(),outputPlinth.getBlockState(),3);
                        //effect
                        //if (pLevel instanceof ServerLevel server)
                        //    server.sendParticles(ParticleTypes.EFFECT,outputPos.getX()+.5,outputPos.getY()+1,outputPos.getZ()+.5,
                        //            15,0,0,0,1);

                        return;
                    }
                }
                //no more books were found
                pLevel.setBlock(pPos,pState.setValue(TRIGGERED,false).setValue(DIRECTION,direction),2);
            }
        });
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

package com.natesky9.patina.Block;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class AppliancePlinthBlock extends BaseEntityBlock {
    public static final MapCodec<AppliancePlinthBlock> CODEC = simpleCodec(AppliancePlinthBlock::new);
    public AppliancePlinthBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult)
    {
        if (pStack.is(ModBlocks.MACHINE_MATRIX.get().asItem()))
        {
            BlockPos pos = pPos.relative(Direction.UP,2);
            if (pLevel.isEmptyBlock(pos))
            {
                pLevel.setBlock(pos,ModBlocks.MACHINE_MATRIX.get().defaultBlockState(), 2);
                pStack.shrink(1);
                return ItemInteractionResult.SUCCESS;
            }
        }

        //return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
        if (!(pLevel.getBlockEntity(pPos) instanceof AppliancePlinthEntity plinth)) return ItemInteractionResult.FAIL;
        ItemStack stack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.isEmpty()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        ItemStack present = plinth.getStack();
        //swap?
        if (!present.isEmpty())
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        plinth.handler.insertItem(0,stack.copyWithCount(1),false);
        stack.shrink(1);
        System.out.println("placed item");
        pLevel.playSound(null,pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1,1);
        return ItemInteractionResult.CONSUME;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!(pLevel.getBlockEntity(pPos) instanceof AppliancePlinthEntity plinth)) return InteractionResult.FAIL;
        ItemStack stack = plinth.getStack();
        if (stack.isEmpty()) return InteractionResult.CONSUME;
        plinth.handler.extractItem(0,1,false);
        if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).isEmpty())
            pPlayer.setItemInHand(InteractionHand.MAIN_HAND,stack);
        else pPlayer.addItem(stack);
        System.out.println("took item");
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AppliancePlinthEntity(blockPos,blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if (entity instanceof  AppliancePlinthEntity plinth)
        {
            Containers.dropContents(pLevel,pPos,new SimpleContainer(plinth.getStack()));
            pLevel.removeBlockEntity(pPos);
        }
    }
}

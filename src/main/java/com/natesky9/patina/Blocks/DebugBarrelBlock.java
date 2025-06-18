package com.natesky9.patina.Blocks;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.Test.ProperFoodData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.event.VanillaGameEvent;
import org.jetbrains.annotations.Nullable;

public class DebugBarrelBlock extends BaseEntityBlock {
    public static final MapCodec<DebugBarrelBlock> CODEC = simpleCodec(DebugBarrelBlock::new);
    public DebugBarrelBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return codec();
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DebugBarrelEntity(blockPos, blockState);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        player.foodData = new ProperFoodData();
        if (player instanceof ServerPlayer server)
        {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof DebugBarrelEntity barrel)
            {
                barrel.itemHandler.setStackInSlot(0, player.getItemInHand(hand));
            }
            else
                throw new IllegalStateException("Not sure how you managed this one, bud");
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!(player instanceof ServerPlayer serverPlayer))
            return super.useWithoutItem(state, level, pos, player, hitResult);
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
}

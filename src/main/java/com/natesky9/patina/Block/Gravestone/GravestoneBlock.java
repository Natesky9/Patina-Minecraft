package com.natesky9.patina.Block.Gravestone;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class GravestoneBlock extends BaseEntityBlock {
    public static final MapCodec<GravestoneBlock> CODEC = simpleCodec(GravestoneBlock::new);
    public GravestoneBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new GravestoneBlockEntity(p_153215_,p_153216_);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState p_60515_, Level level, BlockPos pos, BlockState p_60518_, boolean p_60519_) {
        if (p_60515_.getBlock() != p_60518_.getBlock())
        {
            level.updateNeighbourForOutputSignal(pos, this);
            if (level.getBlockEntity(pos) instanceof GravestoneBlockEntity grave)
                grave.drops();
        }
        super.onRemove(p_60515_, level, pos, p_60518_, p_60519_);
    }

    public static void create(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        Level level = event.getEntity().level();
        BlockPos pos = event.getEntity().blockPosition();
        if (event.getDrops().isEmpty()) return;
        //first height check
        while (!player.level().isInWorldBounds(pos)) {
            boolean above = pos.getY() >= player.level().getMaxBuildHeight();
            boolean below = pos.getY() < player.level().getMinBuildHeight();
            pos = pos.relative(Direction.Axis.Y, (below ? 1 : 0) - (above ? 1 : 0));
        }
        BlockPos search = pos;
        while (pos.getY() < player.level().getMaxBuildHeight()) {
            Iterator<BlockPos.MutableBlockPos> iterator = BlockPos.spiralAround
                    (pos, 8, Direction.NORTH, Direction.EAST).iterator();

            search = iterator.next();

            while ((!player.level().getBlockState(search).canBeReplaced()
                    || player.level().isFluidAtPosition(search, FluidState::isSource))
                    && iterator.hasNext())
                search = iterator.next();
            if (player.level().getBlockState(search).canBeReplaced()
                    && !player.level().isFluidAtPosition(search, FluidState::isSource))
                break;
            pos = pos.above(1);
        }


        level.setBlock(search, ModBlocks.GRAVESTONE.get().defaultBlockState(),3);
        BlockEntity blockEntity = level.getBlockEntity(search);
        if (!(blockEntity instanceof GravestoneBlockEntity grave)) return;

        List<ItemEntity> items = event.getDrops().stream().toList();
        for (ItemEntity entity:items)
        {
            ItemStack stack = entity.getItem();
            grave.add(stack);
            event.getDrops().remove(entity);
        }

        //LazyOptional<IItemHandler> handler = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER);
        //handler.ifPresent(iItemHandler ->
        //{
        //    List<ItemEntity> items = event.getDrops().stream().toList();
        //    for (ItemEntity entity:items)
        //    {
        //        ItemStack stack = entity.getItem();
        //        ItemStack remainder = ItemHandlerHelper.insertItem(iItemHandler,stack,false);
        //        if (remainder == ItemStack.EMPTY)
        //            event.getDrops().remove(entity);
        //    }
        //});
        //everything that doesn't fit should fall to the floor
    }
}

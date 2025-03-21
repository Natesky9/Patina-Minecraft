package com.natesky9.patina.Block.Gravestone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class GravestoneEvent {
    public static void create(LivingDropsEvent event)
    {
        if (!(event.getEntity() instanceof Player player)) return;
        Level level = event.getEntity().level();
        BlockPos pos = event.getEntity().blockPosition();
        //first height check
        while (!player.level().isInWorldBounds(pos))
            {
                boolean above = pos.getY() >= player.level().getMaxBuildHeight();
                boolean below = pos.getY() < player.level().getMinBuildHeight();
                pos = pos.relative(Direction.Axis.Y,(below? 1:0)-(above ? 1:0));
            }
        BlockPos search = pos;
        while (pos.getY() < player.level().getMaxBuildHeight())
        {
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
        //shrink foods by 1
        for (ItemEntity entity:event.getDrops())
        {
            ItemStack stack = entity.getItem();
            if (stack.get(DataComponents.FOOD) != null)
                stack.shrink(1);
            //do tool and armor damage
            if (stack.isDamageableItem())
            {
                stack.setDamageValue(Math.min(stack.getDamageValue()+stack.getMaxDamage()/5,stack.getMaxDamage()-1));
            }
        }

        Optional<ItemEntity> container = event.getDrops().stream().filter(itemEntity -> itemEntity.getItem().is(Items.BARREL)).findFirst();
        //if no barrel, skip
        if (container.isEmpty()) return;

        //remove 1 barrel
        event.getDrops().remove(container.get());
        container.get().getItem().shrink(1);
        event.getDrops().add(container.get());



        level.setBlock(search, Blocks.BARREL.defaultBlockState(),3);
        //POI types
        BlockEntity barrel = level.getBlockEntity(search);
        if (barrel == null) return;

        LazyOptional<IItemHandler> handler = barrel.getCapability(ForgeCapabilities.ITEM_HANDLER);
        handler.ifPresent(iItemHandler ->
        {
            List<ItemEntity> items = event.getDrops().stream().toList();
            for (ItemEntity entity:items)
            {
                ItemStack stack = entity.getItem();
                ItemStack remainder = ItemHandlerHelper.insertItem(iItemHandler,stack,false);
                if (remainder == ItemStack.EMPTY)
                    event.getDrops().remove(entity);
            }
        });
        //everything that doesn't fit should fall to the floor
    }
}

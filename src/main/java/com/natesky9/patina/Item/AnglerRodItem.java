package com.natesky9.patina.Item;

import com.natesky9.patina.entity.Fishing.FishingBobbler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.RandomAccess;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnglerRodItem extends FishingRodItem {
    public AnglerRodItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pLevel.getDayTime() % 1200 == 0 && pLevel instanceof ServerLevel)
        {
            //find a suitable location
            BlockPos pos = pEntity.blockPosition().below(9);
            List<BlockPos> blocks = BlockPos.betweenClosedStream(new AABB(pos).inflate(8 ,4,8)).map(BlockPos::immutable).toList();
            BlockPos test = blocks.get((int)(Math.random()*blocks.size()));
            while (!pLevel.getBlockState(test.above()).is(Blocks.AIR) || pLevel.getFluidState(test.above()).is(Fluids.WATER))
            {
                test = test.above();
            }
            boolean water = pLevel.getFluidState(test).is(Fluids.WATER);

            if (water)
            {
                LodestoneTracker spot =  new LodestoneTracker(Optional.of(GlobalPos.of(pLevel.dimension(), test)), false);
                pStack.set(DataComponents.LODESTONE_TRACKER,spot);
            }
            else
            {
                pStack.remove(DataComponents.LODESTONE_TRACKER);
            }
        }

        if (pStack.has(DataComponents.LODESTONE_TRACKER) && !(pLevel instanceof ServerLevel))
        {
            Random random = new Random();
            LodestoneTracker fishingSpot = pStack.get(DataComponents.LODESTONE_TRACKER);
            BlockPos pos = fishingSpot.target().get().pos();
            float x = random.nextFloat()*4 + pos.getX();
            float y = pos.getY();
            float z = random.nextFloat()*4 + pos.getZ();

            if (pLevel.getFluidState(BlockPos.containing(x,y,z)).is(Fluids.WATER))
                pLevel.addParticle(ParticleTypes.BUBBLE,x,y+.9,z,0,0,0);
            pLevel.addParticle(ParticleTypes.SPLASH,x,y+1,z,0,0,0);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (pPlayer.fishing != null)
        {
            if (!pLevel.isClientSide)
            {
                pPlayer.fishing.retrieve(itemstack);
            }
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F,
                    0.4F / ((float)Math.random() * 0.4F + 0.8F));
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.FISHING_BOBBER_THROW,
                    SoundSource.NEUTRAL, 0.5F, 0.4F / ((float)Math.random() * 0.4F + 0.8F));
            if (pLevel instanceof ServerLevel server) {
                server.addFreshEntity(new FishingBobbler(pPlayer, pLevel));
            }

            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}

package com.natesky9.patina.Blocks;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Predicate;

public class ApplianceEssenceCauldronBlock extends AbstractCauldronBlock {
    public static final IntegerProperty LEVEL = IntegerProperty.create("essence_cauldron_level", 1, 10);
    static final VoxelShape INSIDE = box(2.0, 4.0, 2.0, 14.0, 16, 14.0);
    static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Shapes.or(
            box(0.0, 0.0, 4.0, 16.0, 3.0, 12.0),
            box(4.0, 0.0, 0.0, 12.0, 3.0, 16.0),
            box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), INSIDE), BooleanOp.ONLY_FIRST);
    static final CauldronInteraction.InteractionMap INTERACTION_MAP = CauldronInteraction.newInteractionMap("essence");
    public static final MapCodec<ApplianceEssenceCauldronBlock> CODEC = simpleCodec(ApplianceEssenceCauldronBlock::new);

    public ApplianceEssenceCauldronBlock(Properties p_51403_) {
        super(p_51403_, INTERACTION_MAP);
        //interactions.map().put(ModItems.ESSENCE_BUCKET.get(), ApplianceEssenceCauldronBlock::fillCauldron);
    }

    public void addInteractions()
    {
        //this has to be done after block and item registration I guess?
        //bucket into cauldron
        CauldronInteraction.EMPTY.map().put(ModItems.ESSENCE_BUCKET.get(), ApplianceEssenceCauldronBlock::fillCauldron);
        CauldronInteraction.EMPTY.map().put(Items.EXPERIENCE_BOTTLE, ApplianceEssenceCauldronBlock::fillCauldronWithBottle);
        //interactions.map().put(ModItems.ESSENCE_BUCKET.get(), ApplianceEssenceCauldronBlock::fillCauldron);
        //cauldron to bucket
        INTERACTION_MAP.map().put(Items.BUCKET, ((blockState, level, blockPos, player, interactionHand, itemStack) ->
                fillBucket(blockState, level, blockPos, player, interactionHand, itemStack,
                        ModItems.ESSENCE_BUCKET.toStack(),
                        state -> state.getValue(ApplianceEssenceCauldronBlock.LEVEL) == 10,
                        SoundEvents.BUCKET_FILL)));
        INTERACTION_MAP.map().put(Items.EXPERIENCE_BOTTLE, ApplianceEssenceCauldronBlock::fillCauldronWithBottle);
        INTERACTION_MAP.map().put(Items.GLASS_BOTTLE, ApplianceEssenceCauldronBlock::emptyCauldronWithBottle);
        //golden apple to enchanted
        INTERACTION_MAP.map().put(Items.GOLDEN_APPLE, ((blockState, level, blockPos, player, interactionHand, itemStack) ->
                fillBucket(blockState, level, blockPos, player, interactionHand, itemStack,
                        Items.ENCHANTED_GOLDEN_APPLE.getDefaultInstance(),
                        state -> state.getValue(ApplianceEssenceCauldronBlock.LEVEL) == 10,
                        SoundEvents.BUCKET_FILL)));
    }
    private static InteractionResult fillCauldron(
            BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack filledStack)
    {//for putting a bucket in a cauldron
        if (state.is(Blocks.CAULDRON))
            return CauldronInteraction.emptyBucket(level, pos, player, hand, filledStack, ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get().defaultBlockState().setValue(LEVEL, 10), SoundEvents.BUCKET_EMPTY);
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }
    private static InteractionResult emptyCauldronWithBottle(
            BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack filledStack)
    {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        Item item = filledStack.getItem();
        filledStack.shrink(1);
        player.addItem(Items.EXPERIENCE_BOTTLE.getDefaultInstance());
        player.awardStat(Stats.ITEM_USED.get(item));
        if (true)//Math.random() >= .8f)
        {
            int fill = state.getValue(LEVEL);
            player.awardStat(Stats.USE_CAULDRON);
            //lower, and change to cauldron if empty
            if (fill == 1)
                level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
            else
                level.setBlockAndUpdate(pos, state.setValue(LEVEL, fill - 1));
        }
        level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1, 1);
        return InteractionResult.SUCCESS;
    }

    private static InteractionResult fillCauldronWithBottle(
            BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack filledStack)
    {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if (state.is(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get()))
            if (state.getValue(LEVEL) == 10)
                return InteractionResult.TRY_WITH_EMPTY_HAND;

        Item item = filledStack.getItem();
        filledStack.shrink(1);
        player.addItem(Items.GLASS_BOTTLE.getDefaultInstance());
        player.awardStat(Stats.ITEM_USED.get(item));
        if (true)//Math.random()>=.8f)
        {
            player.awardStat(Stats.FILL_CAULDRON);
            if (state.is(Blocks.CAULDRON))
                level.setBlockAndUpdate(pos, ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get().defaultBlockState());
            else
            {
                int i = state.getValue(LEVEL);
                level.setBlockAndUpdate(pos, state.setValue(LEVEL, i + 1));
            }
        }
        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1, 1);
        level.gameEvent(null, GameEvent.FLUID_PLACE, pos);

        return InteractionResult.SUCCESS;
    }


    static InteractionResult fillBucket(BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, ItemStack emptyStack, ItemStack filledStack,
            Predicate<BlockState> statePredicate, SoundEvent fillSound
    ) {
        if (!statePredicate.test(state)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        } else {
            if (!level.isClientSide) {
                Item item = emptyStack.getItem();
                player.setItemInHand(hand, ItemUtils.createFilledResult(emptyStack, player, filledStack));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(item));
                //empty the cauldron
                level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                level.playSound(null, pos, fillSound, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }

            return InteractionResult.SUCCESS;
        }
    }



    @Override
    public MapCodec<? extends AbstractCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isFull(BlockState blockState) {
        return blockState.getValue(LEVEL) == 10;
    }

    @Override
    protected VoxelShape getShape(BlockState p_151964_, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return SHAPE;
    }

    @Override
    protected boolean canReceiveStalactiteDrip(Fluid fluid) {
        return false;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (isEntityInsideContent(state, pos, entity))
        {
            //only interact with players
            if (!(entity instanceof Player player)) return;
            int xp = state.getValue(LEVEL);
            //only trigger if xp in block
            if (!(xp >= 1)) return;
            player.giveExperiencePoints(100);
            if (xp == 1)
            {
                level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), 3);
            }
            else
                level.setBlock(pos, state.setValue(LEVEL, xp-1),3);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    protected double getContentHeight(BlockState state) {
        int level = state.getValue(LEVEL);
        return (level > 0 ? level+5 : 0);
    }
}

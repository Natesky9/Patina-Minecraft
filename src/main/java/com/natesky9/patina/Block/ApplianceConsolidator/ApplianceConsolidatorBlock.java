package com.natesky9.patina.Block.ApplianceConsolidator;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ApplianceConsolidatorBlock extends BaseEntityBlock {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final EnumProperty<MultiBlockQuad> CORNER = EnumProperty.create("corner", MultiBlockQuad.class);
    public static final MapCodec<ApplianceConsolidatorBlock> CODEC = simpleCodec(ApplianceConsolidatorBlock::new);
    public ApplianceConsolidatorBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVE,false)
                .setValue(CORNER,MultiBlockQuad.NORTHEAST));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE).add(CORNER);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction[] directions = context.getNearestLookingDirections();
        MultiBlockQuad corner = MultiBlockQuad.cornerFromDirections(directions);

        BlockState state = defaultBlockState().setValue(ACTIVE, false)
                .setValue(CORNER, corner);
        return state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {

        MultiBlockQuad corner = state.getValue(CORNER);
        Direction northSouth = MultiBlockQuad.firstDirection(corner);
        Direction eastWest = MultiBlockQuad.secondDirection(corner);

        BlockPos two = pos.relative(northSouth);
        BlockPos three = pos.relative(northSouth).relative(eastWest);
        BlockPos four = pos.relative(eastWest);
        return levelReader.getBlockState(pos).canBeReplaced() && levelReader.getBlockState(two).canBeReplaced()
                && levelReader.getBlockState(three).canBeReplaced() && levelReader.getBlockState(four).canBeReplaced();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);
        assert entity != null;

        double cos = Math.cos(entity.getYRot()*Math.PI/180);
        double sin = Math.sin(entity.getYRot()*Math.PI/180);
        Direction northSouth = Direction.get(cos > 0 ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                Direction.Axis.Z);
        Direction eastWest = Direction.get(sin < 0 ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                Direction.Axis.X);

        //place the others
        MultiBlockQuad twoCorner = MultiBlockQuad.cornerFromDirections(northSouth.getOpposite(), eastWest);
        MultiBlockQuad threeCorner = MultiBlockQuad.cornerFromDirections(northSouth.getOpposite(),eastWest.getOpposite());
        MultiBlockQuad fourCorner = MultiBlockQuad.cornerFromDirections(northSouth, eastWest.getOpposite());
        level.setBlock(pos.relative(northSouth),defaultBlockState().setValue(CORNER,twoCorner),3);
        level.setBlock(pos.relative(northSouth).relative(eastWest),defaultBlockState().setValue(CORNER,threeCorner),3);
        level.setBlock(pos.relative(eastWest),defaultBlockState().setValue(CORNER,fourCorner),3);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newstate, boolean p_60519_) {
        super.onRemove(state, level, pos, newstate, p_60519_);
        if (state.is(newstate.getBlock())) return;
        MultiBlockQuad corner = state.getValue(CORNER);

        //remove all the others
        for (BlockPos blockPos : Arrays.asList(MultiBlockQuad.getTwo(pos, corner),
                MultiBlockQuad.getThree(pos, corner), MultiBlockQuad.getFour(pos, corner)))
        {level.setBlock(blockPos, Blocks.AIR.defaultBlockState(),3);}
    }

    @Override
    public BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_) {
        return super.updateShape(p_60541_, p_60542_, p_60543_, p_60544_, p_60545_, p_60546_);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        MultiBlockQuad corner = state.getValue(CORNER);

        if (!(entity instanceof Player player)) return;

        if (player.experienceLevel <= 0) return;

        if (!state.getValue(ACTIVE))
        {
            //set all 4 to active
            for (BlockPos blockPos : Arrays.asList(pos, MultiBlockQuad.getTwo(pos, corner),
                    MultiBlockQuad.getThree(pos, corner), MultiBlockQuad.getFour(pos, corner)))
            {
                if (!level.getBlockState(blockPos).is(ModBlocks.APPLIANCE_ARCANE_CONSOLIDATOR.get())) return;
                BlockState loop = level.getBlockState(blockPos);
                if (loop.getValue(CORNER) == MultiBlockQuad.NORTHWEST)
                    level.scheduleTick(blockPos, this, 10);
                level.setBlock(blockPos, loop.setValue(ACTIVE,true),3);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {


        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof ApplianceConsolidatorEntity consolidator)) return;
        MultiBlockQuad corner = state.getValue(CORNER);

        if (!state.getValue(ACTIVE))
        {
            consolidator.fire(level, pos);
            return;
        }

        List<Player> list = level.getEntitiesOfClass(Player.class, new AABB(pos.above())
                .expandTowards(-1,0,-1));
        if (list.isEmpty())
        {//if no one is present
            //consolidator.fire(level, items);moved to tick
            //set all 4 to inactive
            for (BlockPos blockPos : Arrays.asList(pos, MultiBlockQuad.getTwo(pos, corner),
                    MultiBlockQuad.getThree(pos, corner), MultiBlockQuad.getFour(pos, corner)))
            {
                if (!level.getBlockState(blockPos).is(ModBlocks.APPLIANCE_ARCANE_CONSOLIDATOR.get())) return;
                level.setBlock(blockPos, level.getBlockState(blockPos).setValue(ACTIVE, false),3);
            }
            //level.setBlock(items, state.setValue(ACTIVE,false),3);
            level.scheduleTick(pos, this, 42);
            return;
        }
        if (!(state.getValue(CORNER) == MultiBlockQuad.NORTHWEST)) return;

        if (list.stream().anyMatch(player -> player.experienceLevel > 0))
        {//someone still has xp, keep ticking
            consolidator.addValue(list.stream().findFirst().get());
            level.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, .1F, level.random.nextFloat());

            level.scheduleTick(pos, this, 4);
        }
        else
        {//no one standing on it has xp
            //consolidator.fire(level, items);
            level.setBlock(pos, state.setValue(ACTIVE, false),3);

            //set all 4 back to inactive
            for (BlockPos blockPos : Arrays.asList(pos, MultiBlockQuad.getTwo(pos, corner),
                    MultiBlockQuad.getThree(pos, corner), MultiBlockQuad.getFour(pos, corner)))
            {level.setBlock(blockPos, level.getBlockState(blockPos).setValue(ACTIVE, false),3);}
            level.scheduleTick(pos, this, 42);
        }


    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        //only the northwest has the block entity
        if (state.getValue(CORNER) == MultiBlockQuad.NORTHWEST)
            return new ApplianceConsolidatorEntity(pos, state);
        else return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }
}

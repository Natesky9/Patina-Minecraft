package com.natesky9.patina.Block;

import com.natesky9.patina.Recipe.MatrixRecipe;
import com.natesky9.patina.Recipe.MatrixRecipeInput;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class MachineArcanaMatrix extends Block {
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    Map<BlockPos,List<BlockPos>> matrices = new HashMap<>();

    public MachineArcanaMatrix(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(TRIGGERED,false));
    }

    @Override
    protected void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        if (!(pLevel instanceof ServerLevel server)) return;
        boolean powered = pLevel.hasNeighborSignal(pPos);
        boolean triggered = pState.getValue(TRIGGERED);

        List<BlockPos> plinths = new ArrayList<>();

        pLevel.updateNeighbourForOutputSignal(pPos,this);
        matrices.remove(pPos);
        matrices.put(pPos,plinths);//spiral?
        Iterable<BlockPos> blocks = BlockPos.betweenClosed(pPos.offset(15,15,15),pPos.offset(-15,-15,-15));

        blocks.forEach(test ->
        {
            if (pLevel.getBlockEntity(test) instanceof AppliancePlinthEntity entity)
            {
                plinths.add(test.immutable());
            }
        });

        if (powered && !triggered)
        {


            if (canCraft(pLevel,pPos))
            {
                pLevel.setBlock(pPos,pState.setValue(TRIGGERED,true),2);
                pLevel.scheduleTick(pPos,this,20);
            }
            else
            {
                server.sendParticles(ParticleTypes.SMOKE,pPos.getX()+.5,pPos.getY()+.5,pPos.getZ()+.5,16,
                        .5,.5,.5,0);
                server.playSound(null,pPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS,.5f,.5f);
            }
        }

        if (!powered && triggered)
        {
            pLevel.setBlock(pPos,pState.setValue(TRIGGERED,false),2);
        }
        //pLevel.sendBlockUpdated(pPos,pState,pState,2);
        pLevel.updateNeighbourForOutputSignal(pPos,this);
        //pLevel.blockUpdated(pPos,this);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(TRIGGERED))
        {
            pLevel.addParticle(ParticleTypes.CRIT,pPos.getX()+pRandom.nextFloat(),pPos.getY()+pRandom.nextFloat(),
                    pPos.getZ()+pRandom.nextFloat(),+pRandom.nextFloat(),+pRandom.nextFloat(),+pRandom.nextFloat());
        }
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pState.getValue(TRIGGERED))
        {//triggered is 2
            return 2;
        }
        if (canCraft(pLevel,pPos))
            return 3;//free is 3
        List<RecipeHolder<MatrixRecipe>> recipes = pLevel.getRecipeManager().getAllRecipesFor(ModRecipeTypes.MATRIX_RECIPE_TYPE.get());
        if (pLevel.getBlockEntity(pPos.below(2)) instanceof AppliancePlinthEntity plinth)
        {
            if (recipes.stream().anyMatch(holder -> holder.value()
                    .getResultItem(pLevel.registryAccess()).is(plinth.getStack().getItem())))
                return 4;//done is 4
        }

        //idle is 1
        return 1;
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        List<BlockPos> list = matrices.get(pPos);
        if (list == null) return;
        if (list.isEmpty()) return;

        if (pLevel.hasNeighborSignal(pPos) && canCraft(pLevel,pPos))
            craft(pLevel,pPos);
        else
        {//craft fail
            pLevel.addParticle(ParticleTypes.EXPLOSION,pPos.getX()+.5,
                    pPos.getY()+2,pPos.getZ()+.5,
                    0,.1,0);
            pLevel.playSound(null,pPos,SoundEvents.GRINDSTONE_USE,SoundSource.BLOCKS,1,.5f);
        }
        //pLevel.updateNeighborsAt(pPos,this);
    }
    boolean canCraft(Level level, BlockPos pos)
    {
        List<BlockPos> list = matrices.get(pos);
        if (list == null || list.isEmpty()) return false;

        //oh dear god what have I done
        ArrayList<ItemStack> items = list.stream().filter(listPos ->
                        level.getBlockEntity(listPos) instanceof AppliancePlinthEntity).map(listPos ->
                        (AppliancePlinthEntity) level.getBlockEntity(listPos)).filter(Objects::nonNull)
                .map(AppliancePlinthEntity::getStack)
                .filter(stack -> !stack.isEmpty()).collect(Collectors.toCollection(ArrayList::new));
        if (items.isEmpty())
            return false;

        RecipeInput container = new MatrixRecipeInput(items);
        Optional<RecipeHolder<MatrixRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.MATRIX_RECIPE_TYPE.get(),container,level);

        System.out.println("recipe " + (recipe.isPresent() ? "exists!" : "does not exist"));
        return recipe.isPresent();
    }

    void craft(Level level, BlockPos pos)
    {
        List<BlockPos> list = matrices.get(pos);
        if (list == null || list.isEmpty()) return;

        ArrayList<ItemStack> items = list.stream().filter(listPos ->
                        level.getBlockEntity(listPos) instanceof AppliancePlinthEntity).map(listPos ->
                        (AppliancePlinthEntity) level.getBlockEntity(listPos)).filter(Objects::nonNull)
                .map(AppliancePlinthEntity::getStack)
                .filter(stack -> !stack.isEmpty()).collect(Collectors.toCollection(ArrayList::new));

        RecipeInput container = new MatrixRecipeInput(items);

        Optional<RecipeHolder<MatrixRecipe>> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.MATRIX_RECIPE_TYPE.get(),container,level);

        recipe.ifPresent(holder ->
        {
            list.stream().filter(listPos -> level.getBlockEntity(listPos) instanceof AppliancePlinthEntity)
                    .map(listPos -> (AppliancePlinthEntity) level.getBlockEntity(listPos)).filter(Objects::nonNull).filter(entity -> !entity.getStack().isEmpty())
                    .forEach(plinth ->
                    {
                        if (pos.below(2).equals(plinth.getBlockPos()))
                        {
                            plinth.setStack(ItemStack.EMPTY);
                            plinth.handler.insertItem(0,holder.value().getResultItem(level.registryAccess()),false);
                            level.setBlock(pos,this.defaultBlockState().setValue(TRIGGERED,false),2);
                        }
                        else
                        {
                            ItemParticleOption particle = new ItemParticleOption(ParticleTypes.ITEM, plinth.getStack());
                            level.addParticle(particle,plinth.getBlockPos().getX()+.5,
                                    plinth.getBlockPos().getY()+2,plinth.getBlockPos().getZ()+.5,
                                    0,.1,0);
                            plinth.setStack(ItemStack.EMPTY);
                        }
                    });
            level.playSound(null,pos,SoundEvents.ENCHANTMENT_TABLE_USE,SoundSource.BLOCKS,1,.5f);
        });
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TRIGGERED);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}

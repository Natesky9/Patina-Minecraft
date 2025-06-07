package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.MinceratorMenu;
import com.natesky9.patina.Recipe.FoundryRecipe;
import com.natesky9.patina.Recipe.MinceratorRecipe;
import com.natesky9.patina.init.ModBlockEntities;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MachineMinceratorEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler handler;
    public MachineMinceratorEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MINCERATOR_ENTITY.get(), pos, blockState);
        handler = new ItemStackHandler(5);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.machine_mixer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MinceratorMenu(i, inventory, this);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineMinceratorEntity mincerator) {

        if (!(level instanceof ServerLevel server)) return;
        ItemStack input = mincerator.handler.getStackInSlot(0);
        ItemStack output = mincerator.handler.getStackInSlot(1);

        RecipeInput recipe = new SingleRecipeInput(input);

        Optional<? extends RecipeHolder<? extends MinceratorRecipe>> valid =
                server.recipeAccess().getRecipeFor(ModRecipeTypes.MINCERATOR_RECIPE.get(), recipe, server);

        if (valid.isPresent())
        {
            ItemStack result = valid.get().value().output();
            if (output.isEmpty() || output.is(result.getItem()) && mincerator.handler.insertItem(1,result, true).isEmpty())
            {
                ItemStack stack = valid.get().value().assemble(recipe, level.registryAccess());
                mincerator.handler.extractItem(0, 1, false);
                mincerator.handler.insertItem(1, stack, false);
            }
        }
    }
}

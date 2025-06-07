package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.FoundryMenu;
import com.natesky9.patina.Recipe.FoundryRecipe;
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
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MachineFoundryEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler handler;
    public final RecipeManager.CachedCheck<RecipeInput, ? extends FoundryRecipe> quickCheck;
    public MachineFoundryEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FOUNDRY_ENTITY.get(), pos, blockState);
        handler = new ItemStackHandler(2)
        {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return super.isItemValid(slot, stack);
            }
        };
        quickCheck = RecipeManager.createCheck(ModRecipeTypes.FOUNDRY_RECIPE.get());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.machine_foundry");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FoundryMenu(i, inventory, this);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineFoundryEntity foundry) {
        if (!(level instanceof ServerLevel server)) return;
        ItemStack input = foundry.handler.getStackInSlot(0);
        ItemStack output = foundry.handler.getStackInSlot(1);

        RecipeInput recipe = new SingleRecipeInput(input);

        Optional<? extends RecipeHolder<? extends FoundryRecipe>> valid =
                server.recipeAccess().getRecipeFor(ModRecipeTypes.FOUNDRY_RECIPE.get(), recipe, server);

        if (valid.isPresent())
        {
            ItemStack result = valid.get().value().output();
            if (output.isEmpty() || output.is(result.getItem()) && foundry.handler.insertItem(1, result, true).isEmpty())
            {
                ItemStack stack = valid.get().value().assemble(recipe, level.registryAccess());
                foundry.handler.extractItem(0, 1, false);
                foundry.handler.insertItem(1, stack, false);
            }
        }
    }
}

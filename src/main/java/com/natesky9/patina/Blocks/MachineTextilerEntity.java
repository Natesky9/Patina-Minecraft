package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.TextilerMenu;
import com.natesky9.patina.Recipe.FoundryRecipe;
import com.natesky9.patina.Recipe.TextilerRecipe;
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
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MachineTextilerEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler handler;
    public MachineTextilerEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TEXTILER_ENTITY.get(), pos, blockState);
        handler = new ItemStackHandler(2)
        {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return super.isItemValid(slot, stack);
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.machine_textiler");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new TextilerMenu(i, inventory, this);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineTextilerEntity textiler) {
        if (!(level instanceof ServerLevel server)) return;
        ItemStack input = textiler.handler.getStackInSlot(0);
        ItemStack output = textiler.handler.getStackInSlot(1);

        RecipeInput recipe = new SingleRecipeInput(input);

        Optional<? extends RecipeHolder<? extends TextilerRecipe>> valid =
                server.recipeAccess().getRecipeFor(ModRecipeTypes.TEXTILER_RECIPE.get(), recipe, server);

        if (valid.isPresent())
        {
            ItemStack result = valid.get().value().output();
            ItemStack stack = valid.get().value().assemble(recipe, level.registryAccess());
            if (output.isEmpty() || output.is(result.getItem()) && textiler.handler.insertItem(1,stack, true).isEmpty())
            {
                textiler.handler.extractItem(0, 1, false);
                textiler.handler.insertItem(1, stack, false);
            }
        }
    }
}

package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.EvaporatorMenu;
import com.natesky9.patina.Recipe.EvaporatorRecipe;
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

public class MachineEvaporatorEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler handler;
    public MachineEvaporatorEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EVAPORATOR_ENTITY.get(), pos, blockState);
        handler = new ItemStackHandler(3)
        {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return super.isItemValid(slot, stack);
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.machine_evaporator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new EvaporatorMenu(i, inventory, this);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineEvaporatorEntity evaporator) {
        if (!(level instanceof ServerLevel server)) return;
        ItemStack input = evaporator.handler.getStackInSlot(0);
        ItemStack output = evaporator.handler.getStackInSlot(1);

        RecipeInput recipe = new SingleRecipeInput(input);

        Optional<? extends RecipeHolder<? extends EvaporatorRecipe>> valid =
                server.recipeAccess().getRecipeFor(ModRecipeTypes.EVAPORATOR_RECIPE.get(), recipe, server);

        if (valid.isPresent())
        {
            ItemStack result = valid.get().value().output();
            if (output.isEmpty() || output.is(result.getItem()) && evaporator.handler.insertItem(1,output, true).isEmpty())
            {
                ItemStack stack = valid.get().value().assemble(recipe, level.registryAccess());
                evaporator.handler.extractItem(0, 1, false);
                evaporator.handler.insertItem(1, stack, false);
            }
        }
    }
}

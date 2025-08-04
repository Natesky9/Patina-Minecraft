package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.FoundryMenu;
import com.natesky9.patina.Recipe.AlloyRecipeInput;
import com.natesky9.patina.Recipe.FoundryRecipe;
import com.natesky9.patina.init.ModBlockEntities;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
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

        boolean alloy = state.getValue(MachineFoundryBlock.MODE);

        ItemStack slot1 = foundry.handler.getStackInSlot(0);
        ItemStack slot2 = foundry.handler.getStackInSlot(1);

        Optional<? extends RecipeHolder<? extends FoundryRecipe>> valid;
        RecipeInput recipe;

        if (alloy)
            recipe = new AlloyRecipeInput(slot1,slot2);
        else
            recipe = new SingleRecipeInput(slot1);
        valid = server.recipeAccess().getRecipeFor(ModRecipeTypes.FOUNDRY_RECIPE.get(),recipe,server);
        //---------------------------------------------
        if (valid.isPresent())
        {
            if (alloy)
            {
                if (!foundry.handler.insertItem(2,valid.get().value().item3(), true).isEmpty())
                    return;

                //we have space, now move it
                foundry.handler.extractItem(0,1,false);
                foundry.handler.extractItem(1,1,false);
                ItemStack output = valid.get().value().item3().copy();
                foundry.handler.insertItem(2,output,false);
            }
            else
            {
                if (!foundry.handler.insertItem(1,valid.get().value().item2(), true).isEmpty()
                || !foundry.handler.insertItem(2,valid.get().value().item3(),true).isEmpty())
                    return;
                //we have space, move it
                foundry.handler.extractItem(0,1,false);
                ItemStack output1 = valid.get().value().item2().copy();
                ItemStack output2 = valid.get().value().item3().copy();
                foundry.handler.insertItem(1,output1,false);
                foundry.handler.insertItem(2,output2,false);
            }
        }
    }
}

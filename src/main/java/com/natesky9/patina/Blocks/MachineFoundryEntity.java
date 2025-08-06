package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.FoundryContainerData;
import com.natesky9.patina.Menu.FoundryMenu;
import com.natesky9.patina.Recipe.AlloyRecipeInput;
import com.natesky9.patina.Recipe.FoundryRecipe;
import com.natesky9.patina.init.ModBlockEntities;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MachineFoundryEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler handler;
    public final FoundryContainerData data;
    public final RecipeManager.CachedCheck<RecipeInput, ? extends FoundryRecipe> quickCheck;
    public int progress;
    public int progressMax = 1000;
    public int heat;
    public int heatMax = 4000;
    public int burn;

    public MachineFoundryEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FOUNDRY_ENTITY.get(), pos, blockState);
        handler = new ItemStackHandler(4)
        {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return super.isItemValid(slot, stack);
            }
        };
        quickCheck = RecipeManager.createCheck(ModRecipeTypes.FOUNDRY_RECIPE.get());
        data = new FoundryContainerData(this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.machine_foundry");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FoundryMenu(i, inventory, this,data);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineFoundryEntity foundry) {
        if (!(level instanceof ServerLevel server)) return;

        boolean alloy = foundry.mode();

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
        if (valid.isPresent() || level.hasNeighborSignal(foundry.getBlockPos()))
            burnFuel(foundry);

        transferHeat(foundry);
        if (valid.isPresent())
        {
            craftProgress(foundry);
            craftRecipe(foundry,valid.get(),alloy);
        }
        else
            foundry.progress = 0;
    }
    public boolean mode()
    {
        return level.getBlockEntity(getBlockPos()).getBlockState().getValue(MachineFoundryBlock.MODE);
    }

    static void transferHeat(MachineFoundryEntity foundry)
    {
        if (foundry.burn > 0 && foundry.heat < foundry.heatMax)
        {
            int transfer = (int) Math.ceil(foundry.burn/100F);
            foundry.burn -= transfer;
            foundry.heat += transfer;
        }
    }
    static void craftProgress(MachineFoundryEntity foundry)
    {
        if (foundry.heat >= 1000 && foundry.progress < foundry.progressMax)
        {
            int transfer = (int)Math.ceil(foundry.heat/1000F);
            foundry.heat -= transfer;
            foundry.progress += transfer;
        }
    }
    static void burnFuel(MachineFoundryEntity foundry)
    {
        Level level = foundry.level;
        ItemStack fuel = foundry.handler.getStackInSlot(3);
        int burnTime = fuel.getBurnTime(ModRecipeTypes.FOUNDRY_RECIPE.get(),level.fuelValues());
        if (burnTime > 0 && foundry.burn == 0)
        {
            foundry.burn += burnTime*4;
            foundry.handler.extractItem(3,1,false);
        }
    }
    static void craftRecipe(MachineFoundryEntity foundry, RecipeHolder<? extends FoundryRecipe> valid, boolean alloy)
    {
        if (foundry.progress < foundry.progressMax) return;

        if (alloy)
        {
            if (!foundry.handler.insertItem(2,valid.value().item3(), true).isEmpty())
                return;

            //we have space, now move it
            foundry.handler.extractItem(0,1,false);
            foundry.handler.extractItem(1,1,false);
            ItemStack output = valid.value().item3().copy();
            foundry.handler.insertItem(2,output,false);
        }
        else
        {
            if (!foundry.handler.insertItem(1,valid.value().item2(), true).isEmpty()
                    || !foundry.handler.insertItem(2,valid.value().item3(),true).isEmpty())
                return;
            //we have space, move it
            foundry.handler.extractItem(0,1,false);
            ItemStack output1 = valid.value().item2().copy();
            ItemStack output2 = valid.value().item3().copy();
            foundry.handler.insertItem(1,output1,false);
            foundry.handler.insertItem(2,output2,false);
        }
        foundry.progress = 0;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        handler.deserializeNBT(registries, tag.getCompound("inventory"));
        if (handler.getSlots() != 4)
        {
            System.out.println("Slots do not match! Correcting now");
            handler.setSize(4);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", handler.serializeNBT(registries));
        super.saveAdditional(tag, registries);
    }

    public void drops()
    {
        SimpleContainer container = new SimpleContainer(handler.getSlots());
        for (int i=0; i<handler.getSlots(); i++)
        {
            container.setItem(i,handler.getStackInSlot(i));
        }
        Containers.dropContents(level,worldPosition,container);
    }
}

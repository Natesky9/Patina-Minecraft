package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.ContainerData.FoundryContainerData;
import com.natesky9.patina.Menu.FoundryMenu;
import com.natesky9.patina.Recipe.AlloyRecipeInput;
import com.natesky9.patina.Recipe.FoundryRecipe;
import com.natesky9.patina.init.ModBlockEntities;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
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

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class MachineFoundryEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler handler;
    public final FoundryContainerData data;
    private RecipeHolder<? extends FoundryRecipe> recipe;

    int mult = 4;
    public int progress;
    public int progressMax = 250*mult;
    public int heat;
    public int heatMax = 1000;
    public int burn;


    public MachineFoundryEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FOUNDRY_ENTITY.get(), pos, blockState);
        handler = new ItemStackHandler(4)
        {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                if (!(level instanceof ServerLevel server)) return false;
                if (slot == 2) return true;
                if (!mode() && slot == 1) return true;
                Collection<RecipeHolder<FoundryRecipe>> recipes = server.recipeAccess().recipeMap().byType(ModRecipeTypes.FOUNDRY_RECIPE.get());
                boolean match = false;
                for (RecipeHolder<FoundryRecipe> check:recipes)
                {
                    match = slot == 0 ? check.value().item1().is(stack.getItem()) : check.value().item2().is(stack.getItem());
                    if (match) break;
                }

                return match;
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                Optional<RecipeHolder<FoundryRecipe>> valid = getRecipe(level);

                if (valid.isPresent() && valid.get() != recipe)
                {
                    recipe = valid.get();
                    return;
                }
                if (valid.isEmpty())
                    recipe = null;
            }
        };

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

    Optional<RecipeHolder<FoundryRecipe>> getRecipe(Level level)
    {
        if (!(level instanceof ServerLevel server)) return Optional.empty();
        boolean alloy = mode();

        ItemStack slot1 = handler.getStackInSlot(0);
        ItemStack slot2 = handler.getStackInSlot(1);

        RecipeInput recipe;

        if (alloy)
            recipe = new AlloyRecipeInput(slot1,slot2);
        else
            recipe = new SingleRecipeInput(slot1);
        return server.recipeAccess().getRecipeFor(ModRecipeTypes.FOUNDRY_RECIPE.get(),recipe,server);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineFoundryEntity foundry) {
        if (!(level instanceof ServerLevel server)) return;
        Optional<RecipeHolder<FoundryRecipe>> valid = foundry.getRecipe(level);
        //---------------------------------------------
        if (valid.isPresent() || level.hasNeighborSignal(foundry.getBlockPos()))
            burnFuel(foundry);

        transferHeat(foundry);
        if (valid.isPresent())
        {
            craftProgress(foundry);
            craftRecipe(foundry,valid.get());
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
            int transfer = foundry.heat/1000;
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
            foundry.burn += burnTime* foundry.mult;
            ItemStack stack = foundry.handler.extractItem(3,1,false);
            if (!stack.getCraftingRemainder().isEmpty())
            {
                //case for fuel like buckets to return the remainder
                foundry.handler.insertItem(3,stack.getCraftingRemainder(),false);
            }

        }
    }
    static void craftRecipe(MachineFoundryEntity foundry, RecipeHolder<FoundryRecipe> valid)
    {
        ServerLevel level = (ServerLevel)foundry.level;
        if (foundry.progress < foundry.progressMax) return;
        ItemStack output1;
        ItemStack output2;
        boolean alloy = valid.value().alloy();

        if (alloy)
        {
            output1 = valid.value().item3().copy();
            if (!foundry.handler.insertItem(2,valid.value().item3(), true).isEmpty())
                return;
            //we have space, now move it
            foundry.handler.extractItem(0,1,false);
            foundry.handler.extractItem(1,1,false);
            foundry.handler.insertItem(2,output1,false);
        }
        else
        {
            output1 = valid.value().item2().copy();
            output2 = valid.value().item3().copy();
            if (!foundry.handler.insertItem(1,valid.value().item2(), true).isEmpty()
                    || !foundry.handler.insertItem(2,valid.value().item3(),true).isEmpty())
                return;
            //we have space, move it

            foundry.handler.extractItem(0,1,false);
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
        heat = tag.getInt("heat");
        burn = tag.getInt("burn");
        progress = tag.getInt("progress");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", handler.serializeNBT(registries));
        tag.putInt("heat",heat);
        tag.putInt("burn",burn);
        tag.putInt("progress",progress);
        super.saveAdditional(tag, registries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
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

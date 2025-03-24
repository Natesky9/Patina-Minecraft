package com.natesky9.patina.Block.MachineTextiler;

import com.natesky9.patina.Block.Template.MachineTemplateEntity;
import com.natesky9.patina.Recipe.TextilerRecipe;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MachineTextilerEntity extends MachineTemplateEntity {
    public static final int slots = 10;
    public static final int dataSlots = 2;

    public static final int input1 = 0;
    public static final int input2 = 1;
    public static final int input3 = 2;
    public static final int input4 = 3;
    public static final int input5 = 4;
    public static final int input6 = 5;
    public static final int input7 = 6;
    public static final int input8 = 7;
    public static final int input9 = 8;
    public static final int output = 9;
    private Optional<RecipeHolder<TextilerRecipe>> recipe = Optional.empty();
    public MachineTextilerEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(pWorldPosition, pBlockState, slots);
    }

    @Override
    protected boolean mySlotValid(int slot, @NotNull ItemStack stack) {
        if (!(level instanceof ServerLevel server)) return false;
        List<RecipeHolder<TextilerRecipe>> recipes = server.recipeAccess()
                .getAllRecipesFor(ModRecipeTypes.TEXTILER_RECIPE_TYPE.get());
        return switch (slot)
                {
                    case input1, input2, input3, input4, input5, input6, input7, input8, input9
                            -> recipes.stream().anyMatch(holder -> holder.value().getIngredients().get(slot).test(stack));
                    case output -> true;
                    default -> false;
                };
    }

    @Override
    protected ContainerData createData() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index)
                        {
                            case 0 -> MachineTextilerEntity.this.progress;
                            case 1 -> MachineTextilerEntity.this.progressMax;
                            default -> 0;
                        };
            }

            @Override
            public void set(int index, int value) {
                switch (index)
                {
                    case 0 -> MachineTextilerEntity.this.progress = value;
                    case 1 -> MachineTextilerEntity.this.progressMax = value;
                }
            }

            @Override
            public int getCount() {
                return dataSlots;
            }
        };
    }

    @Override
    protected int mySlotLimit(int slot) {
        return 1;
    }

    @Override
    protected void myContentsChanged() {
        //clean this up later by making inputs a list
        CraftingInput container = CraftingInput.of(3,3,List.of(itemStackHandler.getStackInSlot(input1),
                itemStackHandler.getStackInSlot(input2),itemStackHandler.getStackInSlot(input3),
                itemStackHandler.getStackInSlot(input4),itemStackHandler.getStackInSlot(input6),
                itemStackHandler.getStackInSlot(input7),itemStackHandler.getStackInSlot(input8),
                itemStackHandler.getStackInSlot(input9)));
        recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.TEXTILER_RECIPE_TYPE.get(),container,level);
        if (recipe.isEmpty())
            resetProgress();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.machine_textiler");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new MachineTextilerMenu(pContainerId,pInventory,this,this.data);
    }

    @Override
    protected void MachineTick() {
        if (hasRecipe() && hasRoom())
        {
            progress++;
            if (progress >= progressMax)
                craftItem();
        }
        else resetProgress();
    }
    private boolean hasRoom()
    {
        if (recipe.isEmpty()) return false;
        ItemStack slot = itemStackHandler.getStackInSlot(output);
        if (slot.isEmpty()) return true;
        ItemStack result = recipe.get().value().getResultItem(level.registryAccess());
        boolean stack = ItemHandlerHelper.canItemStacksStack(slot,result);
        boolean limit = slot.getCount() + result.getCount() <= slot.getMaxStackSize();
        return stack && limit;
    }
    private void craftItem()
    {
        ItemStack stack = recipe.get().value().getResultItem(level.registryAccess());
        itemStackHandler.extractItem(input1,1,false);
        itemStackHandler.extractItem(input2,1,false);
        itemStackHandler.extractItem(input3,1,false);
        itemStackHandler.extractItem(input4,1,false);
        itemStackHandler.extractItem(input5,1,false);
        itemStackHandler.extractItem(input6,1,false);
        itemStackHandler.extractItem(input7,1,false);
        itemStackHandler.extractItem(input8,1,false);
        itemStackHandler.extractItem(input9,1,false);
        itemStackHandler.insertItem(output,stack,false);
    }

    private boolean hasRecipe()
    {
        return recipe.isPresent();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("progress",progress);
        tag.putInt("progressMax",progressMax);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        progress = tag.getInt("progress");
        progressMax = tag.getInt("progressMax");
    }
}

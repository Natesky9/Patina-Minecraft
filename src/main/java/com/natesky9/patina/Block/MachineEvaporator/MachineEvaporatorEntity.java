package com.natesky9.patina.Block.MachineEvaporator;

import com.natesky9.patina.Block.Template.MachineTemplateEntity;
import com.natesky9.patina.Recipe.EvaporatorRecipe;
import com.natesky9.patina.init.ModItems;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class MachineEvaporatorEntity extends MachineTemplateEntity implements MenuProvider {
    public static final int dataSlots = 3;
    private int heat;
    private LazyOptional<IItemHandler> outputHandler = LazyOptional.empty();
    protected final ItemStackHandler outputStackHandler;
    private Optional<RecipeHolder<EvaporatorRecipe>> recipe = Optional.empty();

    public static final int slots = 3;
    final int input = 0;
    final int fuel = 1;
    final int output = 2;

    public MachineEvaporatorEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(pWorldPosition, pBlockState, slots);
        this.progressMax = 180;
        this.outputStackHandler = new ItemStackHandler(slots)
        {
            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (itemStackHandler.isItemValid(slot,stack))
                {
                    itemStackHandler.insertItem(slot,stack,simulate);
                    return super.insertItem(slot,stack,simulate);
                }
                return stack;
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                //allow empty bottles to be extracted
                if (slot == input && !itemStackHandler.getStackInSlot(slot).is(Items.GLASS_BOTTLE))
                    return ItemStack.EMPTY;
                if (slot == fuel) return ItemStack.EMPTY;
                //allow output to be extracted
                return itemStackHandler.extractItem(slot,amount,simulate);
            }
        };
    }

    @Override
    public boolean mySlotValid(int slot, @NotNull ItemStack stack)
    {
        List<RecipeHolder<EvaporatorRecipe>> recipes = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.EVAPORATOR_RECIPE_TYPE.get());
        return switch (slot)
                {
                    case input -> recipes.stream().anyMatch(evaporatorRecipeRecipeHolder ->
                            evaporatorRecipeRecipeHolder.value().getIngredients().get(slot).test(stack));
                    case fuel -> stack.is(ItemTags.LOGS) || stack.is(Items.CHARCOAL)
                            || stack.is(Items.BLAZE_ROD) || stack.is(Items.NETHER_STAR);
                    case output -> stack.is(ModItems.POTION_SALT.get()) || stack.is(Items.GUNPOWDER)
                            || stack.is(ModItems.BISMUTH_NUGGET.get()) || stack.is(Items.GLASS);
                    default -> false;
                };
    }

    @Override
    protected ContainerData createData()
    {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> progressMax;
                    case 2 -> heat;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> progress = pValue;
                    case 1 -> progressMax = pValue;
                    case 2 -> heat = pValue;
                }
            }

            @Override
            public int getCount() {
                return dataSlots;
            }
        };
    }

    @Override
    protected void myContentsChanged()
    {
        if (!(level instanceof ServerLevel server)) return;

        RecipeInput container = new SingleRecipeInput(itemStackHandler.getStackInSlot(input));
        Optional<RecipeHolder<EvaporatorRecipe>> tempRecipe = recipe;
        List<RecipeHolder<EvaporatorRecipe>> recipes = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.EVAPORATOR_RECIPE_TYPE.get());
        recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.EVAPORATOR_RECIPE_TYPE.get(),container,level);
        if (!hasRecipe())
        {
            recipe = Optional.empty();
            resetProgress();
            return;
        }
        if (tempRecipe.isPresent() && recipe.isPresent())
        {
            if (tempRecipe.get() != recipe.get())
            {
                resetProgress();
            }
        }
    }

    @Override
    protected int mySlotLimit(int slot) {
        return switch (slot)
        {
            case input, output -> 1;
            case fuel -> 64;
            default -> 0;
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        outputHandler = LazyOptional.of(() -> outputStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        outputHandler.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.machine_evaporator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new MachineEvaporatorMenu(pContainerId,pInventory,this,this.data);
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
        {
            if (side == Direction.DOWN)
                return outputHandler.cast();
            return itemCapability.cast();
        }
        return super.getCapability(cap,side);
    }


    @Override
    protected void MachineTick()
    {
        if (heat > 0) heat--;
        if (hasRecipe() && heat > 0)
        {
            progress++;
            if (progress >= progressMax)
            {
                craftItem();
                resetProgress();
            }
        }
        else resetProgress();
        //fuel
        burnFuel();

        setChanged();
    }
    void burnFuel()
    {
        if (heat == 0)
        {
            if (!hasRecipe()) return;
            ItemStack item = itemStackHandler.getStackInSlot(fuel);
            if (itemStackHandler.getStackInSlot(fuel).is(ItemTags.LOGS))
            {
                heat += 200;
                itemStackHandler.extractItem(fuel, 1, false);
            }
            if (item.is(Items.BLAZE_ROD))
            {
                heat += 500;
                itemStackHandler.extractItem(fuel,1,false);
            }
            if (item.is(Items.NETHER_STAR))
            {
                heat += 1000;
                //don't extract
            }
        }
    }
    void craftItem()
    {
        if (recipe.isEmpty()) return;
        //we have a recipe, craft it
        itemStackHandler.insertItem(output,recipe.get().value().getResultItem(level.registryAccess()), false);
        itemStackHandler.setStackInSlot(input,new ItemStack(Items.GLASS_BOTTLE));
        //itemStackHandler.extractItem(input,1,false);

        //ItemStack potion = itemStackHandler.extractItem(input,1,false);
        //itemStackHandler.insertItem(input,new ItemStack(Items.GLASS_BOTTLE),false);
        //ItemStack result;
        //if (PotionUtils.getPotion(potion) == Potions.THICK)
        //{result = new ItemStack(Items.BONE_MEAL,9);}
        //else if (PotionUtils.getPotion(potion)== ModPotions.VOLATILE_POTION.get())
        //{result = new ItemStack(Items.GUNPOWDER,9);}
        //else if (PotionUtils.getPotion(potion) == ModPotions.IRIDESCENT_POTION.get())
        //{result = new ItemStack(ModItems.BISMUTH_NUGGET.get());}
        //else
        //{
        //    result = new ItemStack(ModItems.POTION_SALT.get());
        //    PotionUtils.setPotion(result, PotionUtils.getPotion(potion));
        //}
        //itemStackHandler.insertItem(output,result,false);
    }
    private boolean hasNotReachedStackLimit()
    {
        ItemStack stack = itemStackHandler.getStackInSlot(output);
        int count = recipe.get().value().output().getCount();
        return stack.getCount() < stack.getMaxStackSize()-count;
    }

    private boolean hasRecipe()
    {
        return recipe.isPresent() && hasNotReachedStackLimit();
        //boolean hasFirstSlot = itemStackHandler.getStackInSlot(input).getCount() > 0;
        //if (!hasFirstSlot) return false;
        //Potion potion = PotionUtils.getPotion(itemStackHandler.getStackInSlot(input));
        //boolean valid = potion.getEffects().size() == 1
        //        || potion == ModPotions.VOLATILE_POTION.get() || potion == Potions.THICK
        //        || potion == ModPotions.IRIDESCENT_POTION.get();
        //return valid;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("heat", heat);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        heat = tag.getInt("heat");
    }
}

package com.natesky9.patina.Block.Template;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MachineTemplateEntity extends BlockEntity implements MenuProvider {
    protected ItemStackHandler itemStackHandler;


    protected abstract boolean mySlotValid(int slot, @NotNull ItemStack stack);

    protected LazyOptional<IItemHandler> itemCapability = LazyOptional.empty();

    protected final ContainerData data;

    protected int progress = 0;
    protected int progressMax = 20;
    protected int machineSlots = 0;

    public MachineTemplateEntity(BlockPos pWorldPosition, BlockState pBlockState, int slots) {
        super(((MachineTemplateBlock)pBlockState.getBlock()).getBlockEntityType(),pWorldPosition, pBlockState);
        machineSlots = slots;
        //create the handler
        itemStackHandler = new ItemStackHandler(slots)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                setChanged();
                myContentsChanged();
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                return super.extractItem(slot, amount, simulate);
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return mySlotValid(slot,stack);
            }
        };
        data = createData();
    }
    //creates the data slots to read/write
    protected abstract ContainerData createData();

    protected abstract void myContentsChanged();

    @Override
    public abstract Component getDisplayName();

    @Nullable
    @Override
    public abstract AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer);


    @Override
    public void onLoad() {
        super.onLoad();
        itemCapability = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemCapability.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemStackHandler.serializeNBT());
        pTag.putInt("progress",progress);
        super.saveAdditional(pTag);
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemStackHandler.deserializeNBT(tag.getCompound("inventory"));
        if (itemStackHandler.getSlots() != machineSlots)
        {
            System.out.println("Slots do not match! setting to correct now!");
            itemStackHandler.setSize(machineSlots);
        }
        progress = tag.getInt("progress");
    }

    public void drops()
    {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i=0;i < itemStackHandler.getSlots();i++)
        {
            inventory.setItem(i,itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, MachineTemplateEntity pBlockEntity)
    {
        pBlockEntity.MachineTick();
    }
    protected abstract void MachineTick();
    public void resetProgress()
    {
        this.progress = 0;
    }

}

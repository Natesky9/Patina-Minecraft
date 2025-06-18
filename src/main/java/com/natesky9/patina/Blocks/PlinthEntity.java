package com.natesky9.patina.Blocks;

import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class PlinthEntity extends BlockEntity {
    protected boolean active;
    public final ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide)
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            active = false;
            return super.extractItem(slot, amount, simulate);
        }
    };

    public PlinthEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.PLINTH_ENTITY.get(), pos, blockState);
    }
    public PlinthEntity(BlockEntityType type, BlockPos pos, BlockState state)
    {
        //constructor for future blocks as a passthrough
        super(type, pos, state);
    }

    public void setActive(boolean active) {
        //basic plinth does not have automation
    }
    public boolean getActive()
    {
        return false;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putBoolean("active",active);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        active = tag.getBoolean("active");
    }

    public void drops()
    {
        Containers.dropContents(this.level, this.worldPosition, new SimpleContainer(inventory.getStackInSlot(0)));
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}

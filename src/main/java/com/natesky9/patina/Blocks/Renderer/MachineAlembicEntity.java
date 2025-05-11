package com.natesky9.patina.Blocks.Renderer;

import com.natesky9.patina.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class MachineAlembicEntity extends BlockEntity {
    private ItemStackHandler itemHandler = new ItemStackHandler(2)
    {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide)
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(),3);
        }
    };
    private ItemStackHandler automationHandler;

    private Item reagent;
    private int leftover;
    private int progress;
    public MachineAlembicEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ALEMBIC_ENTITY.get(), pos, blockState);
        reagent = Items.AIR;
        leftover = 0;
        progress = 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemHandler.serializeNBT(registries));
        ItemStack stack = new ItemStack(reagent);
        tag.put("reagent", stack.save(registries));
        tag.putInt("reagentCount", leftover);
        tag.putInt("progress", progress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag);
        reagent = ItemStack.parse(registries, tag).get().getItem();
        leftover = tag.getInt("reagentCount");
        progress = tag.getInt("progress");
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

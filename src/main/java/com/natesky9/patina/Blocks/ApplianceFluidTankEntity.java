package com.natesky9.patina.Blocks;

import com.natesky9.patina.init.ModBlockEntities;
import com.natesky9.patina.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public class ApplianceFluidTankEntity extends BlockEntity {
    public FluidTank fluidHandler;
    public int fluidLevel = 0;
    public boolean dataHolder;
    public ApplianceFluidTankEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FLUID_TANK_ENTITY.get(), pos, blockState);
        //dataholder stores whether this entity is the logic controller
        //or simply just a mirror. since we don't have level access yet,
        //the fluid handler is set in #setLevel
        if (blockState.getValue(ApplianceFluidTank.HALF) == DoubleBlockHalf.LOWER)
        {
            fluidHandler = new FluidTank(4000)
            {
            @Override
            public boolean isFluidValid(FluidStack stack) {
                return stack.is(getFluid().getFluidType())
                        || isEmpty();
            }

            @Override
            protected void onContentsChanged() {
                level.sendBlockUpdated(pos, blockState, blockState, 3);
                setChanged(level, pos, blockState);
            }
        };
            dataHolder = true;
        }
        else
        {
            fluidHandler = null;
            dataHolder = false;
        }
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        if (fluidHandler == null)
        {
            if (level.getBlockEntity(getBlockPos().below()) instanceof ApplianceFluidTankEntity bottom)
            {
                fluidHandler = bottom.fluidHandler;
            }
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (dataHolder)
            fluidHandler.readFromNBT(registries, tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (dataHolder)
            fluidHandler.writeToNBT(registries, tag);
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

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(ModDataComponents.FLUID, SimpleFluidContent.copyOf(fluidHandler.getFluid()));
        System.out.println("collected: " + fluidHandler.getFluid());
    }
}

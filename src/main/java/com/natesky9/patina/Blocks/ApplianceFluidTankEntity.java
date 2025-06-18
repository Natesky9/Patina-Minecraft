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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public class ApplianceFluidTankEntity extends BlockEntity {
    public final FluidTank fluidHandler;
    public ApplianceFluidTankEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FLUID_TANK_ENTITY.get(), pos, blockState);
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
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        fluidHandler.readFromNBT(registries, tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
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

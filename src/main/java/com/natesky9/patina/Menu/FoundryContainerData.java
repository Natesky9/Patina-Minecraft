package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.MachineFoundryEntity;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class FoundryContainerData implements ContainerData {
    MachineFoundryEntity foundry;
    public FoundryContainerData(MachineFoundryEntity machineFoundryEntity) {
        foundry = machineFoundryEntity;
    }

    @Override
    public int get(int index) {
        return switch (index)
        {
            case 0 -> foundry.heat;
            case 1 -> foundry.heatMax;
            case 2 -> foundry.progress;
            case 3 -> foundry.progressMax;
            default -> 0;
        };
    }

    @Override
    public void set(int index, int value) {
        switch (index)
        {
            case 0 -> foundry.heat = value;
            case 1 -> foundry.heatMax = value;
            case 2 -> foundry.progress = value;
            case 3 -> foundry.progressMax = value;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

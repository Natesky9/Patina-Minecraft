package com.natesky9.patina.Menu.ContainerData;

import com.natesky9.patina.Blocks.MachineKwernEntity;
import net.minecraft.world.inventory.ContainerData;

public class KwernContainerData implements ContainerData {
    MachineKwernEntity kwern;
    public KwernContainerData(MachineKwernEntity machineKwernEntity) {
        kwern = machineKwernEntity;
    }

    @Override
    public int get(int index) {
        return switch (index)
        {
            case 0 -> kwern.heat;
            case 1 -> kwern.heatMax;
            case 2 -> kwern.progress;
            case 3 -> kwern.progressMax;
            default -> 0;
        };
    }

    @Override
    public void set(int index, int value) {
        switch (index)
        {
            case 0 -> kwern.heat = value;
            case 1 -> kwern.heatMax = value;
            case 2 -> kwern.progress = value;
            case 3 -> kwern.progressMax = value;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

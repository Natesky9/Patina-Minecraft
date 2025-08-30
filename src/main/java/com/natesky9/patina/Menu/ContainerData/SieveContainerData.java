package com.natesky9.patina.Menu.ContainerData;

import com.natesky9.patina.Blocks.MachineSieveEntity;
import net.minecraft.world.inventory.ContainerData;

public class SieveContainerData implements ContainerData {
    MachineSieveEntity sieve;
    public SieveContainerData(MachineSieveEntity machineSieveEntity) {
        sieve = machineSieveEntity;
    }

    @Override
    public int get(int index) {
        return switch (index)
        {
            case 0 -> sieve.heat;
            case 1 -> sieve.heatMax;
            case 2 -> sieve.progress;
            case 3 -> sieve.progressMax;
            default -> 0;
        };
    }

    @Override
    public void set(int index, int value) {
        switch (index)
        {
            case 0 -> sieve.heat = value;
            case 1 -> sieve.heatMax = value;
            case 2 -> sieve.progress = value;
            case 3 -> sieve.progressMax = value;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

package com.natesky9.patina.datagen.loot;

import com.natesky9.patina.init.ModBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot {
    @Override
    protected void addTables()
    {
        this.dropSelf(ModBlocks.CUSTOM_BLOCK.get());
        this.dropSelf(ModBlocks.TEST_BLOCK.get());
        this.dropSelf(ModBlocks.TEST_STAIRS.get());
        this.dropSelf(ModBlocks.TEST_WALL.get());

        this.dropSelf(ModBlocks.CHORUS_CABLE.get());

        this.add(ModBlocks.HONEY_PUDDLE.get(), noDrop());

        this.dropSelf(ModBlocks.TELECHORUS.get());
        this.dropSelf(ModBlocks.MACHINE_CAULDRON_BREWING.get());
        this.dropSelf(ModBlocks.MACHINE_BLAST_CAULDRON.get());
        this.dropSelf(ModBlocks.MACHINE_SMOKER_GRINDSTONE.get());

        //special blocks
        this.add(ModBlocks.TEST_SLAB.get(), BlockLoot::createSlabItemTable);
    }
    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}

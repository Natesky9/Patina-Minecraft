package com.natesky9.patina.DataGen;

import com.natesky9.patina.Blocks.ApplianceWardrobeBlock;
import com.natesky9.patina.init.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        //dropself adds the item version of the block to the loot table
        //any new block added has to have a loot table or else
        add(ModBlocks.APPLIANCE_WARDROBE.get(), createSinglePropConditionTable(ModBlocks.APPLIANCE_WARDROBE.get(),
                ApplianceWardrobeBlock.HALF, DoubleBlockHalf.LOWER));
        add(ModBlocks.APPLIANCE_ICEBOX.get(), createSinglePropConditionTable(ModBlocks.APPLIANCE_ICEBOX.get(),
                ApplianceWardrobeBlock.HALF, DoubleBlockHalf.LOWER));
        dropSelf(ModBlocks.ADDON_ALEMBIC.get());
        dropSelf(ModBlocks.ADDON_FOUNDRY.get());
        dropSelf(ModBlocks.APPLIANCE_ARCANE_CONSOLIDATOR.get());
        dropSelf(ModBlocks.APPLIANCE_BENCHMARK.get());
        dropSelf(ModBlocks.APPLIANCE_CHORUS_TELEPORTER.get());
        dropSelf(ModBlocks.APPLIANCE_PLINTH.get());
        dropSelf(ModBlocks.APPLIANCE_REINFORCED_PLINTH.get());
        dropSelf(ModBlocks.APPLIANCE_RESEARCH_DESK.get());
        dropSelf(ModBlocks.CHORUS_CABLE.get());
        dropSelf(ModBlocks.FLUUD_PIPE.get());
        dropSelf(ModBlocks.WYRE_CABLE.get());
        dropSelf(ModBlocks.MACHINE_ABSTRACTOR.get());
        dropSelf(ModBlocks.MACHINE_ALEMBIC.get());
        dropSelf(ModBlocks.MACHINE_ARBITRATOR.get());
        dropSelf(ModBlocks.MACHINE_AUGMENTOR.get());
        dropSelf(ModBlocks.MACHINE_REPLICATOR.get());
        dropSelf(ModBlocks.MACHINE_UNIFIER.get());
        dropSelf(ModBlocks.MACHINE_EXTRACTOR.get());
        dropSelf(ModBlocks.MACHINE_TEXTILER.get());
        dropSelf(ModBlocks.MACHINE_FOUNDRY.get());
        dropSelf(ModBlocks.MACHINE_KWERN.get());
        dropSelf(ModBlocks.MACHINE_MATRIX.get());
        dropSelf(ModBlocks.MACHINE_MINCERATOR.get());
        dropSelf(ModBlocks.MACHINE_ARBITRATOR.get());
        dropSelf(ModBlocks.APPLIANCE_ARCANE_CONSOLIDATOR.get());
        dropSelf(ModBlocks.MACHINE_ABSTRACTOR.get());
        dropSelf(ModBlocks.MACHINE_EVAPORATOR.get());
        dropSelf(ModBlocks.MACHINE_SIEVE.get());
        dropSelf(ModBlocks.ADDON_SIEVE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value).toList();
    }
}

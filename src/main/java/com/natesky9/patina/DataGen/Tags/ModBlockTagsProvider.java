package com.natesky9.patina.DataGen.Tags;

import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Patina.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.PRISMATIC_ORE.get())
                .add(ModBlocks.DELTITE_ORE.get())
                .add(ModBlocks.CHROMATIC_ORE.get());
        tag(BlockTags.CAULDRONS)
                .add(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get());
        tag(Tags.Blocks.ORES)
                .add(ModBlocks.PRISMATIC_ORE.get())
                .add(ModBlocks.DELTITE_ORE.get())
                .add(ModBlocks.CHROMATIC_ORE.get());


        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.CHROMATIC_ORE.get())
                .add(ModBlocks.PRISMATIC_ORE.get())
                .add(ModBlocks.DELTITE_ORE.get());

        tag(BlockTags.NEEDS_STONE_TOOL).remove(Blocks.IRON_ORE);
        tag(BlockTags.INCORRECT_FOR_STONE_TOOL).add(Blocks.IRON_ORE);
        tag(Tags.Blocks.NEEDS_GOLD_TOOL).add(Blocks.IRON_ORE);
    }
}

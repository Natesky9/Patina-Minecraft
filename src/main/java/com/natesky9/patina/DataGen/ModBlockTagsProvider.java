package com.natesky9.patina.DataGen;

import com.natesky9.patina.Patina;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Patina.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}

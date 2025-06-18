package com.natesky9.patina.DataGen;

import com.natesky9.patina.init.ModEnchantments;
import com.natesky9.patina.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagsProvider extends EnchantmentTagsProvider {
    public ModEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.CURSE_EXCLUSIVE).add(ModEnchantments.CURSE_ENVY)
                .add(ModEnchantments.CURSE_GLUTTONY)
                .add(ModEnchantments.CURSE_GREED)
                .add(ModEnchantments.CURSE_LUST)
                .add(ModEnchantments.CURSE_SLOTH)
                .add(ModEnchantments.CURSE_PRIDE)
                .add(ModEnchantments.CURSE_WRATH)
                .add(Enchantments.BINDING_CURSE)
                .add(Enchantments.VANISHING_CURSE);

        tag(ModTags.VANISHING_EXCLUSIVE)
                .add(Enchantments.VANISHING_CURSE)
                .add(ModEnchantments.BLESSING_VANISHING);
    }
}

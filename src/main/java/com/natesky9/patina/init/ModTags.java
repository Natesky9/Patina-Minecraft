package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModTags {
    public static TagKey<Enchantment> CURSE_EXCLUSIVE = tagEnchantment("exclusive_set/curse");


    //

    private static TagKey<Enchantment> tagEnchantment(String name)
    {
        return TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Patina.MODID, name));
    }
}

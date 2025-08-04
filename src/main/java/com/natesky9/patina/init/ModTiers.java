package com.natesky9.patina.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;

public record ModTiers(TagKey<Block> incorrectBlocksForDrops, int durability, float speed, float attackDamageBonus, int enchantmentValue, TagKey<Item> repairItems) {
    public static final ToolMaterial COPPER;
    public static final ToolMaterial CRYSTAL;
    static
    {
        COPPER = new ToolMaterial(BlockTags.INCORRECT_FOR_GOLD_TOOL,
                200, 5.0F, 1.0F,
                1, ItemTags.IRON_TOOL_MATERIALS);
    }
    static
    {
        CRYSTAL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
                999, 9.0F, 4.0F,
                15, ItemTags.DIAMOND_TOOL_MATERIALS);
    }

}

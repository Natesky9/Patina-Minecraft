package com.natesky9.patina.init;

import com.natesky9.patina.Blocks.PlinthBlock;
import com.natesky9.patina.Patina;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Patina.MODID);
    //TODO: add in blockitems
    //TODO: add loot tables
    //region arcane machines
    public static final DeferredBlock<Block> MACHINE_UNIFIER = BLOCKS.registerSimpleBlock("machine_unifier",
            BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion());
    public static final DeferredBlock<Block> MACHINE_ABSTRACTOR = BLOCKS.registerSimpleBlock("machine_abstractor",
            BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion());
    public static final DeferredBlock<Block> MACHINE_REPLICATOR = BLOCKS.registerSimpleBlock("machine_replicator",
            BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion());
    public static final DeferredBlock<Block> MACHINE_EXTRACTOR = BLOCKS.registerSimpleBlock("machine_extractor",
            BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion());
    public static final DeferredBlock<Block> MACHINE_AUGMENTOR = BLOCKS.registerSimpleBlock("machine_augmentor",
            BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion());
    public static final DeferredBlock<Block> MACHINE_ARBITRATOR = BLOCKS.registerSimpleBlock("machine_arbitrator",
            BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion());
    public static final DeferredBlock<Block> MACHINE_MATRIX = BLOCKS.registerSimpleBlock("machine_matrix",
            BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion());
    //endregion arcane machines

    public static final DeferredBlock<Block> APPLIANCE_PLINTH = registerBlock("appliance_plinth",
            () -> new PlinthBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("appliance_plinth"))));
    public static final DeferredBlock<Block> APPLIANCE_REINFORCED_PLINTH = BLOCKS.registerSimpleBlock("machine_reinforced_plinth",
            BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion());

    //region tech machines
    public static final DeferredBlock<Block> MACHINE_FOUNDRY = BLOCKS.registerSimpleBlock("machine_foundry",
            BlockBehaviour.Properties.of()
                    .strength(3F));
    public static final DeferredBlock<Block> ADDON_FOUNDRY = BLOCKS.registerSimpleBlock("addon_foundry",
            BlockBehaviour.Properties.of()
                    .strength(3F));
    public static final DeferredBlock<Block> MACHINE_ALEMBIC = BLOCKS.registerSimpleBlock("machine_alembic",
            BlockBehaviour.Properties.of()
                    .strength(3F));
    public static final DeferredBlock<Block> ADDON_ALEMBIC = BLOCKS.registerSimpleBlock("addon_alembic",
            BlockBehaviour.Properties.of()
                    .strength(3F));
    public static final DeferredBlock<Block> MACHINE_MINCERATOR = BLOCKS.registerSimpleBlock("machine_mixer",
            BlockBehaviour.Properties.of()
                    .strength(3F));
    public static final DeferredBlock<Block> MACHINE_KWERN = BLOCKS.registerSimpleBlock("machine_grinder",
            BlockBehaviour.Properties.of()
                    .strength(3F));
    public static final DeferredBlock<Block> MACHINE_TEXTILER = BLOCKS.registerSimpleBlock("machine_textiler",
            BlockBehaviour.Properties.of()
                    .strength(3F));
    public static final DeferredBlock<Block> MACHINE_EVAPORATOR = BLOCKS.registerSimpleBlock("machine_evaporator",
            BlockBehaviour.Properties.of()
                    .strength(3F));
    //endregion tech machines

    //region appliances
    public static final DeferredBlock<Block> APPLIANCE_WARDROBE = BLOCKS.registerSimpleBlock("appliance_wardrobe",
            BlockBehaviour.Properties.of()
                    .strength(2F));
    public static final DeferredBlock<Block> APPLIANCE_ICEBOX = BLOCKS.registerSimpleBlock("appliance_icebox",
            BlockBehaviour.Properties.of()
                    .strength(2F));
    public static final DeferredBlock<Block> APPLIANCE_RESEARCH_DESK = BLOCKS.registerSimpleBlock("appliance_research_desk",
            BlockBehaviour.Properties.of()
                    .strength(2F));
    public static final DeferredBlock<Block> APPLIANCE_BENCHMARK = BLOCKS.registerSimpleBlock("appliance_benchmark",
            BlockBehaviour.Properties.of()
                    .strength(2F));
    public static final DeferredBlock<Block> APPLIANCE_ARCANE_CONSOLIDATOR = BLOCKS.registerSimpleBlock("appliance_arcane_consolidator",
            BlockBehaviour.Properties.of()
                    .strength(2F));
    public static final DeferredBlock<Block> APPLIANCE_CHORUS_TELEPORTER = BLOCKS.registerSimpleBlock("appliance_chorus_teleporter",
            BlockBehaviour.Properties.of()
                    .strength(2f));
    //endregion appliances
    //region pipes and cables
    public static final DeferredBlock<Block> CHORUS_CABLE = BLOCKS.registerSimpleBlock("cable_chorus",
            BlockBehaviour.Properties.of()
                    .strength(1F));
    public static final DeferredBlock<Block> WYRE_CABLE = BLOCKS.registerSimpleBlock("cable_wyre",
            BlockBehaviour.Properties.of()
                    .strength(1F));
    public static final DeferredBlock<Block> FLUUD_PIPE = BLOCKS.registerSimpleBlock("pipe_fluud",
            BlockBehaviour.Properties.of()
                    .strength(1F));
    //endregion pipes and cables
    //
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block)
    {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static ResourceKey<Block> createBlockKey(String string)
    {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Patina.MODID, string));
    }

    private static ResourceKey<Item> createItemKey(String string)
    {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(string));
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block)
    {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().setId(createItemKey(name))));
    }
    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}

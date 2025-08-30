package com.natesky9.patina.init;

import com.natesky9.patina.Blocks.*;
import com.natesky9.patina.Blocks.Enchanting.*;
import com.natesky9.patina.Blocks.MachineSieveBlock;
import com.natesky9.patina.Blocks.Research.ApplianceResearchDeskBlock;
import com.natesky9.patina.Patina;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.WaterloggedTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Patina.MODID);
    //TODO: add in blockitems
    //TODO: add loot tables
    public static final DeferredBlock<Block> DEBUG_BARREL = BLOCKS.register("debug_barrel",
            () -> new DebugBarrelBlock(BlockBehaviour.Properties.of()
                    .setId(createBlockKey("debug_barrel"))));
    //region fluids
    public static final DeferredBlock<LiquidBlock> ESSENCE_FLUID = BLOCKS.register("essence_block",
            () -> new LiquidBlock(ModFluids.ESSENCE_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
                    .noOcclusion()
                    .lightLevel((state) -> state.getValue(LiquidBlock.LEVEL))
                    .setId(createBlockKey("essence_fluid"))));
    //endregion fluids
    //region ores
    public static final DeferredBlock<Block> PRISMATIC_ORE = BLOCKS.register("prismatic_ore",
            () -> new WaterloggedTransparentBlock(BlockBehaviour.Properties.of()
                    .strength(4F).lightLevel(state -> 9)
                    .requiresCorrectToolForDrops()
                    .setId(createBlockKey("prismatic_ore"))));//prismarine
    public static final DeferredBlock<Block> DELTITE_ORE = BLOCKS.register("deltite_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5F).lightLevel(state -> 5)
                    .requiresCorrectToolForDrops()
                    .setId(createBlockKey("deltite_ore"))));//uranium
    public static final DeferredBlock<Block> CHROMATIC_ORE = BLOCKS.register("chromatic_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5F).requiresCorrectToolForDrops()
                    .setId(createBlockKey("chromatic_ore"))));//bismuth
    //endregion ores
    //region arcane machines
    public static final DeferredBlock<Block> MACHINE_UNIFIER =registerBlock("arcane_unifier",
            () -> new ArcaneAdditionBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("arcane_unifier"))));//adding
    public static final DeferredBlock<Block> MACHINE_ABSTRACTOR = registerBlock("arcane_abstractor",
            () -> new ArcaneSubtractionBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("arcane_abstractor"))));//subtracting
    public static final DeferredBlock<Block> MACHINE_AUGMENTOR = registerBlock("arcane_augmentor",
            () -> new ArcaneMultiplicationBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("arcane_augmentor"))));//upgrading
    public static final DeferredBlock<Block> MACHINE_EXTRACTOR = registerBlock("arcane_extractor",
            () -> new ArcaneDivisionBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("arcane_extractor"))));//extracting
    public static final DeferredBlock<Block> MACHINE_REPLICATOR = registerBlock("arcane_replicator",
            () -> new ArcaneExponentBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("arcane_replicator"))));//copying
    public static final DeferredBlock<Block> MACHINE_ARBITRATOR = registerBlock("arcane_arbitrator",
            () -> new ArcaneRadicalBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("arcane_arbitrator"))));//reducing
    public static final DeferredBlock<Block> MACHINE_MATRIX = registerBlock("arcane_matrix",
            () -> new ArcaneMatrixBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("arcane_matrix"))));
    //endregion arcane machines
    //replaced by block and blockitem
    //public static final DeferredBlock<Block> APPLIANCE_FLUID_TANK = registerBlock("appliance_fluid_tank",
    //        () -> new ApplianceFluidTank(BlockBehaviour.Properties.of()
    //                .noOcclusion().strength(4F)
    //                .setId(createBlockKey("appliance_fluid_tank"))));

    //region tech machines
    public static final DeferredBlock<Block> MACHINE_FOUNDRY = registerBlock("machine_foundry",
            () -> new MachineFoundryBlock(BlockBehaviour.Properties.of()
                    .strength(3F)
                    .setId(createBlockKey("machine_foundry"))));
    public static final DeferredBlock<Block> ADDON_FOUNDRY = registerBlock("addon_foundry",
            () -> new Block(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("addon_foundry"))));
    public static final DeferredBlock<Block> MACHINE_ALEMBIC = registerBlock("machine_alembic",
            () -> new MachineAlembicBlock(BlockBehaviour.Properties.of()
                    .strength(3F)
                    .setId(createBlockKey("machine_alembic"))));
    public static final DeferredBlock<Block> ADDON_ALEMBIC = registerBlock("addon_alembic",
            () -> new Block(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("addon_alembic"))));
    public static final DeferredBlock<Block> MACHINE_MINCERATOR = registerBlock("machine_mincerator",
            () -> new MachineMinceratorBlock(BlockBehaviour.Properties.of()
                    .strength(3F)
                    .setId(createBlockKey("machine_mincerator"))));
    public static final DeferredBlock<Block> MACHINE_KWERN = registerBlock("machine_kwern",
            () -> new MachineKwernBlock(BlockBehaviour.Properties.of()
                    .strength(3F)
                    .setId(createBlockKey("machine_kwern"))));
    public static final DeferredBlock<Block> MACHINE_TEXTILER = registerBlock("machine_textiler",
            () -> new MachineTextilerBlock(BlockBehaviour.Properties.of()
                    .strength(3F)
                    .setId(createBlockKey("machine_textiler"))));
    public static final DeferredBlock<Block> MACHINE_EVAPORATOR = registerBlock("machine_evaporator",
            () -> new MachineEvaporatorBlock(BlockBehaviour.Properties.of()
                    .strength(3F)
                    .setId(createBlockKey("machine_evaporator"))));
    public static final DeferredBlock<Block> MACHINE_SIEVE = registerBlock("machine_sieve",
            () -> new MachineSieveBlock(BlockBehaviour.Properties.of()
                    .strength(3F)
                    .setId(createBlockKey("machine_sieve"))));
    public static final DeferredBlock<Block> ADDON_SIEVE = registerBlock("addon_sieve",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3F)
                    .setId(createBlockKey("addon_sieve"))));
    //endregion tech machines

    //region appliances
    public static final DeferredBlock<Block> APPLIANCE_WARDROBE = registerBlock("appliance_wardrobe",
            () -> new ApplianceWardrobeBlock(BlockBehaviour.Properties.of()
                    .strength(2F)
                    .setId(createBlockKey("appliance_wardrobe"))));
    public static final DeferredBlock<Block> APPLIANCE_ICEBOX = registerBlock("appliance_icebox",
            () -> new ApplianceIceboxBlock(BlockBehaviour.Properties.of()
                    .strength(2F)
                    .setId(createBlockKey("appliance_icebox"))));
    public static final DeferredBlock<Block> APPLIANCE_RESEARCH_DESK = registerBlock("appliance_research_desk",
            () -> new ApplianceResearchDeskBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("appliance_research_desk"))));
    public static final DeferredBlock<Block> APPLIANCE_BENCHMARK = registerBlock("appliance_benchmark",
            () -> new Block(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("appliance_benchmark"))));
    public static final DeferredBlock<Block> APPLIANCE_ARCANE_CONSOLIDATOR = registerBlock("appliance_arcane_consolidator",
            () -> new Block(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("appliance_arcane_consolidator"))));
    public static final DeferredBlock<Block> APPLIANCE_CHORUS_TELEPORTER = registerBlock("appliance_chorus_teleporter",
            () -> new Block(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("appliance_chorus_teleporter"))));
    public static final DeferredBlock<Block> APPLIANCE_FLUID_TANK = registerBlock("appliance_fluid_tank",
            () -> new ApplianceFluidTank(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("appliance_fluid_tank"))));
    public static final DeferredBlock<Block> APPLIANCE_PLINTH = registerBlock("appliance_plinth",
            () -> new PlinthBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("appliance_plinth"))));
    public static final DeferredBlock<Block> APPLIANCE_PEDESTAL = registerBlock("appliance_pedestal",
            () -> new PedestalBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("appliance_pedestal"))));
    public static final DeferredBlock<Block> APPLIANCE_ESSENCE_CAULDRON = BLOCKS.register("appliance_essence_cauldron",
            () -> new ApplianceEssenceCauldronBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)
                    .setId(createBlockKey("appliance_essence_cauldron"))));
    //endregion appliances
    //region pipes and cables
    public static final DeferredBlock<Block> CHORUS_CABLE = registerBlock("cable_chorus",
            () -> new Block(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("cable_chorus"))));
    public static final DeferredBlock<Block> WYRE_CABLE = registerBlock("cable_wyre",
            () -> new Block(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("cable_wyre"))));
    public static final DeferredBlock<Block> FLUUD_PIPE = registerBlock("pipe_fluud",
            () -> new Block(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(3F)
                    .setId(createBlockKey("pipe_fluud"))));
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
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Patina.MODID, string));
    }

    private static <T extends Block> DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<T> block)
    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().setId(createItemKey(name))));
    }
    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}

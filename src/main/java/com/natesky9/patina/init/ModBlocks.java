package com.natesky9.patina.init;

import com.natesky9.patina.Block.ApplianceConsolidator.ApplianceConsolidatorBlock;
import com.natesky9.patina.Block.ApplianceIcebox.ApplianceIceboxBlock;
import com.natesky9.patina.Block.*;
import com.natesky9.patina.Block.ApplianceResearchDesk.ApplianceResearchDeskBlock;
import com.natesky9.patina.Block.ApplianceWardrobe.ApplianceWardrobeBlock;
import com.natesky9.patina.Block.Benchmark.ApplianceBenchmarkBlock;
import com.natesky9.patina.Block.Gravestone.GravestoneBlock;
import com.natesky9.patina.Block.MachineAlembic.AddonAlembicBlock;
import com.natesky9.patina.Block.MachineAlembic.MachineAlembicBlock;
import com.natesky9.patina.Block.MachineEnchanter.MachineEnchanterBlock;
import com.natesky9.patina.Block.MachineEvaporator.MachineEvaporatorBlock;
import com.natesky9.patina.Block.MachineFoundry.AddonFoundryBlock;
import com.natesky9.patina.Block.MachineFoundry.MachineFoundryBlock;
import com.natesky9.patina.Block.MachineMincerator.MachineMinceratorBlock;
import com.natesky9.patina.Block.MachineTextiler.MachineTextilerBlock;
import com.natesky9.patina.Block.Plinth.AppliancePlinthBlock;
import com.natesky9.patina.Patina;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Patina.MODID);
    //--------------------//
    public static final RegistryObject<Block> CHORUS_CABLE = registerBlock("chorus_cable",
            () -> new ChorusCableBlock(BlockBehaviour.Properties.of()
                    .strength(1F)
                    .randomTicks()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .noOcclusion()
            ));
    public static final RegistryObject<Block> CHARGE_CABLE = registerBlock("charge_cable",
            () -> new CopperCableBlock(BlockBehaviour.Properties.of()
                    .randomTicks()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(1F)
                    .noOcclusion()));
    public static final RegistryObject<Block> CHORUS_TELEPORTER = registerBlock("chorus_teleporter",
            () -> new ChorusTeleporter(BlockBehaviour.Properties.of()
                    .strength(1F)
                    .mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block> TEST_GENERATOR = registerBlock("test_generator",
            () -> new GeneratorBlock(BlockBehaviour.Properties.of()
                    .strength(1000)));
    //region machines
    //liquid to solid
    public static final RegistryObject<Block> MACHINE_EVAPORATOR = registerBlock("machine_evaporator",
            () -> new MachineEvaporatorBlock(BlockBehaviour.Properties.of()
                    .strength(1f)));
    //solid to solid
    //public static final RegistryObject<Block> MACHINE_SAWMILL = registerBlock("machine_sawmill",
    //        () -> new MachineSawmillBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
    //                .strength(4f)));
    //solid to liquid
    public static final RegistryObject<Block> MACHINE_FOUNDRY = registerBlock("machine_foundry",
            () -> new MachineFoundryBlock(BlockBehaviour.Properties.of()
                    .strength(1f)));
    public static final RegistryObject<Block> ADDON_FOUNDRY = registerBlock("addon_foundry",
            () -> new AddonFoundryBlock(BlockBehaviour.Properties.of()
                    .strength(1f)));
    public static final RegistryObject<Block> MACHINE_MINCERATOR = registerBlock("machine_mincerator",
            () -> new MachineMinceratorBlock(BlockBehaviour.Properties.of()
                    .strength(1f)));
    //liquid to liquid
    public static final RegistryObject<Block> MACHINE_ALEMBIC = registerBlock("machine_alembic",
            () -> new MachineAlembicBlock(BlockBehaviour.Properties.of()
                    .strength(1f)));
    public static final RegistryObject<Block> ADDON_ALEMBIC = registerBlock("addon_alembic",
            () -> new AddonAlembicBlock(BlockBehaviour.Properties.of()
                    .strength(1f)));
    //enchanting
    public static final RegistryObject<Block> MACHINE_ENCHANTER = registerBlock("machine_enchanter",
            () -> new MachineEnchanterBlock(BlockBehaviour.Properties.of()
                    .strength(1f)));
    //loom
    public static final RegistryObject<Block> MACHINE_TEXTILER = registerBlock("machine_textiler",
            () -> new MachineTextilerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LOOM).strength(1)));
    //appliances
    public static final RegistryObject<Block> APPLIANCE_WARDROBE = registerBlock("appliance_wardrobe",
            () -> new ApplianceWardrobeBlock(BlockBehaviour.Properties.of()
                    .strength(3f)));
    public static final RegistryObject<Block> APPLIANCE_BENCHMARK = registerBlock("appliance_benchmark",
            () -> new ApplianceBenchmarkBlock(BlockBehaviour.Properties.of()
                    .strength(3f)));
    public static final RegistryObject<Block> APPLIANCE_ICEBOX = registerBlock("appliance_icebox",
            () -> new ApplianceIceboxBlock(BlockBehaviour.Properties.of()
                    .strength(3f)));
    public static final RegistryObject<Block> APPLIANCE_ARCANE_CONSOLIDATOR = registerBlock("appliance_arcane_consolidator",
            () -> new ApplianceConsolidatorBlock(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .speedFactor(0.5F)));
    public static final RegistryObject<Block> APPLIANCE_RESEARCH_DESK = registerBlock("appliance_research_desk",
            () -> new ApplianceResearchDeskBlock(BlockBehaviour.Properties.of()
                    .strength(3f)));
    //
    public static final RegistryObject<Block> APPLIANCE_PLINTH = registerBlock("appliance_plinth",
            () -> new AppliancePlinthBlock(BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion()));
    public static final RegistryObject<Block> APPLIANCE_REINFORCED_PLINTH = registerBlock("appliance_upgraded_plinth",
            () -> new AppliancePlinthBlock(BlockBehaviour.Properties.of()
                    .strength(4f).noOcclusion()));

    public static final RegistryObject<Block> MACHINE_UNIFIER = registerBlock("machine_unifier",//adding
            () -> new MachineArcaneAddingBlock(BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion().lightLevel((light) -> 5)));
    public static final RegistryObject<Block> MACHINE_ABSTRACTOR = registerBlock("machine_abstractor",//subtracting
            () -> new MachineArcaneSubtractionBlock(BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion().lightLevel((light) -> 5)));
    public static final RegistryObject<Block> MACHINE_REPLICATOR = registerBlock("machine_copier",//multiplying
            () -> new MachineArcaneMultiplierBlock(BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion().lightLevel((light) ->  5)));
    public static final RegistryObject<Block> MACHINE_EXTRACTOR = registerBlock("machine_extractor",//diving
            () -> new MachineArcaneDivisionBlock(BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion().lightLevel((light) ->  5)));
    public static final RegistryObject<Block> MACHINE_AUGMENTOR = registerBlock("machine_augmentor",//exponent
            () -> new MachineArcaneExponentBlock(BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion().lightLevel((light) ->  5)));
    public static final RegistryObject<Block> MACHINE_ARBITRATOR = registerBlock("machine_arbitrator",//radical
            () -> new MachineArcaneRadicalBlock(BlockBehaviour.Properties.of()
                    .strength(3f).noOcclusion().lightLevel((light) -> 5)));

    public static final RegistryObject<Block> ESSENCE_CAULDRON = registerBlock("essence_cauldron",
            () -> new EssenceCauldronBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    CauldronInteraction.EMPTY));

    public static final RegistryObject<Block> MACHINE_MATRIX = registerBlock("machine_matrix",
            () -> new MachineArcanaMatrix(BlockBehaviour.Properties.of()
                    .strength(4f).noOcclusion().lightLevel((light) -> 15)));
    //public static final RegistryObject<Block> APPLIANCE_TOOLRACK = registerBlock("appliance_toolrack",
    //        () -> new ApplianceToolrackBlock(BlockBehaviour.Properties.of(Material.WOOD)
    //                .strength(3f)));
    //endregion
    public static final RegistryObject<Block> PRISMATIC_ORE = registerBlock("prismatic_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).strength(3F)));

    //register block only
    public static final RegistryObject<Block> BISMUTH_ORE = BLOCKS.register("bismuth_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> GRAVESTONE = BLOCKS.register("gravestone",
            () -> new GravestoneBlock(BlockBehaviour.Properties.of()
                    .strength(1F)));
    //-----------------------//
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> registeredBlock = BLOCKS.register(name,block);
        registerBlockItem(name,registeredBlock);
        return registeredBlock;
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block)
    {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }
    public static void register(IEventBus eventBus)
    {BLOCKS.register(eventBus);}
}

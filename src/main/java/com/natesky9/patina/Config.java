package com.natesky9.patina;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Patina.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    //enable/disable items
    private static final ModConfigSpec.BooleanValue COPPER_TOOLS = BUILDER
            .comment("Enable copper tools")
            .define("enableCopperTools", true);
    private static final ModConfigSpec.BooleanValue COPPER_ARMOR = BUILDER
            .comment("Enable copper armor")
            .define("enableCopperArmor",true);
    private static final ModConfigSpec.BooleanValue CRYSTAL_TOOLS = BUILDER
            .comment("Enable crystal tools")
            .define("enableCrystalTools", true);
    private static final ModConfigSpec.BooleanValue CRYSTAL_ARMOR = BUILDER
            .comment("Enable crystal armor")
            .comment("This disables Crystal, Anima, Ferus, and Fortis armor recipes")
            .comment("but does not remove them from the world")
            .define("enableCrystalArmor", true);
    private static final ModConfigSpec.BooleanValue FLASKS = BUILDER
            .comment("Enable potion flasks")
            .define("enableFlasks", true);
    private static final ModConfigSpec.BooleanValue CLAWS = BUILDER
            .comment("Enable Crab Claws")
            .define("enableClaws", true);

    //
    //private static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
    //        .comment("Whether to log the dirt block on common setup")
    //        .define("logDirtBlock", true);

    //private static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER
    //        .comment("A magic number")
    //        .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    //public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
    //        .comment("What you want the introduction message to be for the magic number")
    //        .define("magicNumberIntroduction", "The magic number is... ");


    static final ModConfigSpec SPEC = BUILDER.build();
    //
    public static boolean copperTools;
    public static boolean copperArmor;
    public static boolean crystalTools;
    public static boolean crystalArmor;
    public static boolean flasks;
    public static boolean claws;

    //public static boolean logDirtBlock;
    //public static int magicNumber;
    //public static String magicNumberIntroduction;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        copperTools = COPPER_TOOLS.get();
        copperArmor = COPPER_ARMOR.get();
        crystalTools = CRYSTAL_TOOLS.get();
        crystalArmor = CRYSTAL_ARMOR.get();
        flasks = FLASKS.get();
        claws = CLAWS.get();
        //
        //logDirtBlock = LOG_DIRT_BLOCK.get();
        //magicNumber = MAGIC_NUMBER.get();
        //magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
    }
}

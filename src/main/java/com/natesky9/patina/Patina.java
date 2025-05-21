package com.natesky9.patina;

import com.natesky9.patina.Event.EventsMod;
import com.natesky9.patina.Recipe.ModRecipeSerializers;
import com.natesky9.patina.Recipe.ModRecipeTypes;
import com.natesky9.patina.init.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Patina.MODID)
public class Patina
{
    public static final String MODID = "patina";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);//:(

    public Patina(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);//:)
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);
        ModRecipeTypes.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(EventsMod::Creative);

        //BLOCKS.register(modEventBus);

        //NeoForge.EVENT_BUS.register(this);

        //mod event bus next

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }



    //@SubscribeEvent
    //public void onServerStarting(ServerStartingEvent event)
    //{
    //    LOGGER.info("HELLO from server starting");
    //}

    //@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    //public static class ClientModEvents
    //{
    //    @SubscribeEvent
    //    public static void onClientSetup(FMLClientSetupEvent event)
    //    {
    //        LOGGER.info("HELLO FROM CLIENT SETUP");
    //        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    //    }
    //}
}

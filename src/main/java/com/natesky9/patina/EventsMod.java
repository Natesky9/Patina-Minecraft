package com.natesky9.patina;

import com.natesky9.patina.entity.MiscModels.BEWLR;
import com.natesky9.patina.init.*;
import com.natesky9.patina.item.BoltComponent;
import com.natesky9.patina.item.CopperItem;
import com.natesky9.patina.recipe.FletchingRecipe;
import com.natesky9.patina.recipe.ToolRecipe;
import com.natesky9.patina.recipe.SmokerGrindstoneRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nonnull;

import static java.lang.Math.min;

@Mod.EventBusSubscriber(modid = Patina.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventsMod {
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event)
    {
        event.getRegistry().registerAll(
                new GreedCurseSubtractionModifier.Serializer().setRegistryName(
                        new ResourceLocation(Patina.MOD_ID,"greed_curse"))

        );
    }
    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event)
    {
        Registry.register(Registry.RECIPE_TYPE, SmokerGrindstoneRecipe.Type.ID, SmokerGrindstoneRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, ToolRecipe.Type.ID, ToolRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, FletchingRecipe.Type.ID,FletchingRecipe.Type.INSTANCE);
    }
    @SubscribeEvent
    public static void ColorHandlerEvent(final ColorHandlerEvent.Item event)
    {
        //color bolt tips
        event.getItemColors().register((item,hue) -> {return BoltComponent.getColor(item);},
                ModItems.BOLT_TIPS.get());
        //color unfinished bolts
        event.getItemColors().register((item,hue) -> {return BoltComponent.getColor(item);},
                ModItems.UNFINISHED_BOLTS.get());
        event.getItemColors().register((item,hue) ->
                {return hue > 0 ? -1 : PotionUtils.getColor(item);}
                ,ModItems.MAGIC_SALT.get());
        event.getItemColors().register((item,hue) ->
        {return hue > 0 ? -1 : CopperItem.getRustColor(item);},
            ModItems.COPPER_HELMET.get(),ModItems.COPPER_CHESTPLATE.get(),
                ModItems.COPPER_LEGGINGS.get(),ModItems.COPPER_BOOTS.get());
    }
    @SubscribeEvent
    public static void doCommonStuff(final FMLCommonSetupEvent event)
    {
        ModPotions.addSaltPotions();
        ModPotions.addVoidPotions();
    }
    @SubscribeEvent
    public static void doClientStuff(FMLClientSetupEvent event)
    {
        Patina.bewlr = new BEWLR(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
        //Render types
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.HERB_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.HONEY_PUDDLE.get(), RenderType.translucentNoCrumbling());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MACHINE_BEACON_GRINDSTONE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WATER_VALVE.get(),RenderType.cutout());
        //screen stuff

        //IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //ModMenuTypes.register(eventBus);
        ModScreens.register();


        ItemProperties.register(ModItems.LUXOMETER.get(),
                new ResourceLocation(Patina.MOD_ID,"toggle"),(stack, world, entity, number) ->
                {
                    CompoundTag nbt = stack.getOrCreateTag();
                    return nbt.getBoolean("toggle") ? 1F:0F;
                });
    }
}

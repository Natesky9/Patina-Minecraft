package com.natesky9.patina.Event;

import com.natesky9.patina.Blocks.ApplianceEssenceCauldronBlock;
import com.natesky9.patina.Blocks.Renderer.FluidTankRenderer;
import com.natesky9.patina.Blocks.Renderer.PedestalEntityRenderer;
import com.natesky9.patina.Blocks.Renderer.PlinthEntityRenderer;
import com.natesky9.patina.DataGen.DataGenerators;
import com.natesky9.patina.Entity.armor.CopperArmorModel;
import com.natesky9.patina.Fluids.EssenceFluid;
import com.natesky9.patina.Patina;
import com.natesky9.patina.Screen.*;
import com.natesky9.patina.Test.ProperFoodRenderer;
import com.natesky9.patina.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.GuiLayerManager;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;
import org.jetbrains.annotations.Nullable;


@EventBusSubscriber(modid = Patina.MODID, bus = EventBusSubscriber.Bus.MOD)
public class EventsMod {
    //
    @SubscribeEvent
    public static void CreateAttribute(EntityAttributeModificationEvent event)
    {
        event.add(EntityType.PLAYER, ModAttributes.GLUTTONY, 1);

        event.add(EntityType.PLAYER, ModAttributes.GLUTTONY_BLESSING, 1);

    }
    @SubscribeEvent
    public static void renderEvent(RegisterGuiLayersEvent event)
    {
        ResourceLocation extended_hunger = ResourceLocation.fromNamespaceAndPath(Patina.MODID, "hunger");
        GuiLayerManager hungerDraw = new GuiLayerManager().add(extended_hunger, (graphics, partial) ->
                ProperFoodRenderer.renderHunger(graphics));
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL,extended_hunger,(graphics, partial) ->
                new GuiLayerManager().add(hungerDraw, ProperFoodRenderer::shouldRender));
    }
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event)
    {
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.FLUID_TANK_ENTITY.get(),
                (tank, side) -> tank.fluidHandler);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.PLINTH_ENTITY.get(),
                (plinth, side) -> plinth.inventory);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.PEDESTAL_ENTITY.get(),
                (pedestal, side) ->
                        switch (side)
                        {
                            case DOWN -> pedestal.getActive() ?
                            pedestal.inventory : null;
                            case UP, NORTH, SOUTH, WEST, EAST -> pedestal.inventory;
                            case null -> pedestal.inventory;
                        });
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DEBUG_BARREL.get(),
                (barrel, side) ->
                barrel.itemHandler);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.DEBUG_BARREL.get(),
                (barrel, side) ->
                barrel.fluidHandler);
    }
    @SubscribeEvent
    public static void Creative(BuildCreativeModeTabContentsEvent event)
    {
        //for adding stuff to vanilla tabs
    }
    @SubscribeEvent
    public static void gatherDataEvent(GatherDataEvent.Client event)
    {
        DataGenerators.gatherData(event);
    }
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntities.PLINTH_ENTITY.get(), PlinthEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL_ENTITY.get(), PedestalEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.FLUID_TANK_ENTITY.get(), FluidTankRenderer::new);
    }
    @SubscribeEvent
    public static void renderScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModMenuTypes.ALEMBIC_MENU.get(), AlembicScreen::new);
        event.register(ModMenuTypes.MINCERATOR_MENU.get(), MinceratorScreen::new);
        event.register(ModMenuTypes.FOUNDRY_MENU.get(), FoundryScreen::new);
        event.register(ModMenuTypes.TEXTILER_MENU.get(), TextilerScreen::new);
        event.register(ModMenuTypes.KWERN_MENU.get(), KwernScreen::new);
        event.register(ModMenuTypes.EVAPORATOR_MENU.get(), EvaporatorScreen::new);
        event.register(ModMenuTypes.SIEVE_MENU.get(), SieveScreen::new);

        event.register(ModMenuTypes.ICEBOX_MENU.get(), IceboxScreen::new);
        event.register(ModMenuTypes.WARDROBE_MENU.get(), WardrobeScreen::new);
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        ItemBlockRenderTypes.setRenderLayer(ModFluids.ESSENCE_SOURCE.get(), RenderType.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.ESSENCE_FLOWING.get(), RenderType.TRANSLUCENT);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.APPLIANCE_FLUID_TANK.get(), RenderType.CUTOUT);
    }

    @SubscribeEvent
    public static void common(FMLCommonSetupEvent event)
    {
        ApplianceEssenceCauldronBlock cauldron = (ApplianceEssenceCauldronBlock) ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get();
        cauldron.addInteractions();
    }
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event)
    {
        event.registerFluidType(new IClientFluidTypeExtensions() {

            @Override
            public ResourceLocation getStillTexture() {
                return EssenceFluid.ESSENCE_STILL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return EssenceFluid.ESSENCE_FLOWING;
            }

            @Override
            public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                return EssenceFluid.ESSENCE_OVERLAY;
            }
        }, ModFluidTypes.ESSENCE);
        //
        event.registerItem(new IClientItemExtensions() {
            @Override
            public Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
                return ModModels.copperArmorModel;
            }
        },ModItems.COPPER_HELMET,ModItems.COPPER_CHESTPLATE,ModItems.COPPER_LEGGINGS,ModItems.COPPER_BOOTS);
    }
    @SubscribeEvent
    public static void registerCauldronFluidContentEvent(RegisterCauldronFluidContentEvent event)
    {
        event.register(ModBlocks.APPLIANCE_ESSENCE_CAULDRON.get(), ModFluids.ESSENCE_SOURCE.get(), 1000, ApplianceEssenceCauldronBlock.LEVEL);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(CopperArmorModel.LAYER_LOCATION, CopperArmorModel::createBodyLayer);
    }
}

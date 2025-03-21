package com.natesky9.patina.event;

import com.natesky9.datagen.DataGenerators;
import com.natesky9.patina.Block.Plinth.AppliancePlinthRenderer;
import com.natesky9.patina.Item.RustableItem;
import com.natesky9.patina.Item.flasks.PotionFlaskItem;
import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.ModRecipeBookType;
import com.natesky9.patina.entity.Armor.*;
import com.natesky9.patina.entity.BearPrince.BearPrince;
import com.natesky9.patina.entity.BearPrince.BearPrinceModel;
import com.natesky9.patina.entity.BeePrincess.BeePrincess;
import com.natesky9.patina.entity.BeePrincess.BeePrincessModel;
import com.natesky9.patina.entity.PiglinBaron;
import com.natesky9.patina.entity.SandwormKing;
import com.natesky9.patina.entity.SinisterApothecary;
import com.natesky9.patina.entity.SlimeKnight;
import com.natesky9.patina.entity.SpiderNest.SpiderNestModel;
import com.natesky9.patina.entity.SpiderNest.Spidernest;
import com.natesky9.patina.entity.SpiderQueen.SpiderQueen;
import com.natesky9.patina.entity.SpiderQueen.SpiderQueenModel;
import com.natesky9.patina.init.*;
import com.natesky9.patina.networking.ModPackets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = Patina.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventsMod {
    @SubscribeEvent
    public static void RegisterBER(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntities.APPLIANCE_PLINTH_ENTITY.get(), AppliancePlinthRenderer::new);
    }
    @SubscribeEvent
    public static void RegisterRecipeBookCategoriesEvent(RegisterRecipeBookCategoriesEvent event)
    {
        event.registerRecipeCategoryFinder(ModRecipeTypes.FOUNDRY_RECIPE_TYPE.get(),recipe -> ModRecipeBookType.FOUNDRY_CATEGORY);
        event.registerBookCategories(ModRecipeBookType.FOUNDRY, List.of(ModRecipeBookType.FOUNDRY_CATEGORY));

        event.registerRecipeCategoryFinder(ModRecipeTypes.MINCERATOR_RECIPE_TYPE.get(),recipe -> ModRecipeBookType.MINCERATOR_CATEGORY);
        event.registerBookCategories(ModRecipeBookType.MINCERATOR, List.of(ModRecipeBookType.MINCERATOR_CATEGORY));

        event.registerRecipeCategoryFinder(ModRecipeTypes.TEXTILER_RECIPE_TYPE.get(), recipe -> ModRecipeBookType.TEXTILER_CATEGORY);
        event.registerBookCategories(ModRecipeBookType.TEXTILER, List.of(ModRecipeBookType.TEXTILER_CATEGORY));

        event.registerRecipeCategoryFinder(ModRecipeTypes.EVAPORATOR_RECIPE_TYPE.get(), recipe -> ModRecipeBookType.EVAPORATOR_CATEGORY);
        event.registerBookCategories(ModRecipeBookType.EVAPORATOR, List.of(ModRecipeBookType.EVAPORATOR_CATEGORY));

    }
    @SubscribeEvent
    public static void ColorHandlerEvent(final RegisterColorHandlersEvent.Item event)
    {
        //potion flask
        event.register((pStack, pTintIndex) ->
                pTintIndex == 1 ? pStack.get(DataComponents.POTION_CONTENTS).getColor() : -1
            ,ModItems.POTION_FLASK.get(),ModItems.IMPETUS_FLASK.get(),
                ModItems.VITA_FLASK.get(),ModItems.MAGNA_FLASK.get(),
                ModItems.ETERNA_FLASK.get());
        //salts
        event.register((pStack, pTintIndex) ->
                pTintIndex == 0 ? pStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor() : -1,ModItems.POTION_SALT.get());
        //tint on layer 1
        //TODO:do rust, eventually?
        event.register(((pStack, pTintIndex) -> pTintIndex == 1 ? RustableItem.getTint(pStack):-1),
                ModItems.COPPER_AXE.get(),ModItems.COPPER_HOE.get(),ModItems.COPPER_SWORD.get(),
                ModItems.COPPER_PICK.get(),ModItems.COPPER_SHOVEL.get(),
                ModItems.COPPER_HELMET.get(),ModItems.COPPER_CHESTPLATE.get(),
                ModItems.COPPER_LEGGINGS.get(),ModItems.COPPER_BOOTS.get());
        event.register(((pStack, pTintIndex) ->
                {return pTintIndex == 1 ? RustableItem.getSheen(Minecraft.getInstance().level) : -1;}),
                ModItems.BRONZE_AXE.get(),ModItems.BRONZE_HOE.get(),ModItems.BRONZE_SWORD.get(),
                ModItems.BRONZE_PICK.get(),ModItems.BRONZE_SHOVEL.get());
        //tint on layer 0
        //event.register(((pStack, pTintIndex) -> pTintIndex == 0 ? RustableItem.getRust(pStack) : -1),
        //        ModItems.COPPER_HELMET.get(),ModItems.COPPER_CHESTPLATE.get(),
        //        ModItems.COPPER_LEGGINGS.get(),ModItems.COPPER_BOOTS.get());
        event.register(((pStack, pTintIndex) ->
        {return RustableItem.getSheen(Minecraft.getInstance().level);}),
                ModItems.BRONZE_INGOT.get(),ModItems.BRONZE_HELMET.get(),ModItems.BRONZE_CHESTPLATE.get(),
                ModItems.BRONZE_LEGGINGS.get(),ModItems.BRONZE_BOOTS.get());
    }
    @SubscribeEvent
    public static void gatherDataEvent(GatherDataEvent event)
    {
        DataGenerators.gatherData(event);
    }
    @SubscribeEvent
    public static void doCommonStuff(final FMLCommonSetupEvent event)
    {
        ModPackets.register();
        //potions moved into their own event in 1.21
        //ModPotions.removePotions();
        //ModPotions.addNormalPotions();
        //ModPotions.addSaltPotions();
        //ModPotions.addVoidPotions();
    }

    @SubscribeEvent
    public static void doClientStuff(FMLClientSetupEvent event)
    {

        ModScreens.register();
        ItemProperties.register(ModItems.PIG_CROSSBOW.get(),ResourceLocation.withDefaultNamespace("pull"),
                (itemStack, clientLevel, livingEntity, i) -> {
                    if (livingEntity == null) {return 0.0F;}
                    else {
                        return CrossbowItem.isCharged(itemStack) ? 0.0F : (float)(itemStack.getUseDuration(livingEntity) -
                                livingEntity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(itemStack,livingEntity);
                    }});

        ItemProperties.register(ModItems.PIG_CROSSBOW.get(),ResourceLocation.withDefaultNamespace("pulling"),
                (itemStack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack && !CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.PIG_CROSSBOW.get(),ResourceLocation.withDefaultNamespace("charged"),
                (itemStack, level, entity, seed) -> CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);

        registerPotionCapacityProperty(ModItems.POTION_FLASK.get());
        registerPotionCapacityProperty(ModItems.VITA_FLASK.get());
        registerPotionCapacityProperty(ModItems.IMPETUS_FLASK.get());
        registerPotionCapacityProperty(ModItems.MAGNA_FLASK.get());
        registerPotionCapacityProperty(ModItems.ETERNA_FLASK.get());

        //we got rid of crude essence
        //ItemProperties.register(ModItems.ESSENCE.get(),ResourceLocation.withDefaultNamespace("crude"),
        //        (itemstack, level, entity, seed) -> EssenceItem.isCrude(itemstack) ? 0.0F : 1.0F);
    }
    static void registerPotionCapacityProperty(Item item)
    {
        ItemProperties.register(item,ResourceLocation.withDefaultNamespace("capacity"),
                ((pStack, pLevel, pEntity, pSeed) -> PotionFlaskItem.percentFull(pStack)));
    }
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntityTypes.BEAR_BOSS.get(), BearPrince.createAttributes().build());
        event.put(ModEntityTypes.BEE_BOSS.get(), BeePrincess.createAttributes().build());
        event.put(ModEntityTypes.PIGLIN_BOSS.get(), PiglinBaron.createAttributes().build());
        event.put(ModEntityTypes.SLIME_BOSS.get(), SlimeKnight.createAttributes().build());
        event.put(ModEntityTypes.SANDWORM_BOSS.get(), SandwormKing.createAttributes().build());
        event.put(ModEntityTypes.SPIDER_BOSS.get(), SpiderQueen.createAttributes().build());
        event.put(ModEntityTypes.SPIDER_NEST.get(), Spidernest.createAttributes().build());
        event.put(ModEntityTypes.WITCH_BOSS.get(), SinisterApothecary.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(BeePrincessModel.LAYER_LOCATION, BeePrincessModel::createBodyLayer);
        event.registerLayerDefinition(BearPrinceModel.LAYER_LOCATION, BearPrinceModel::createBodyLayer);
        event.registerLayerDefinition(SpiderQueenModel.LAYER_LOCATION, SpiderQueenModel::createBodyLayer);
        event.registerLayerDefinition(SpiderNestModel.LAYER_LOCATION, SpiderNestModel::createBodyLayer);

        //armor stuff
        event.registerLayerDefinition(TemplateArmorModel.LAYER_LOCATION, TemplateArmorModel::createBodyLayer);
        event.registerLayerDefinition(UmbraArmorModel.LAYER_LOCATION, UmbraArmorModel::createBodyLayer);
        event.registerLayerDefinition(CrystalPrimeArmorModel.LAYER_LOCATION, CrystalPrimeArmorModel::createBodyLayer);
        event.registerLayerDefinition(CrystalAnimaArmorModel.LAYER_LOCATION, CrystalAnimaArmorModel::createBodyLayer);
        event.registerLayerDefinition(CrystalFerusArmorModel.LAYER_LOCATION, CrystalFerusArmorModel::createBodyLayer);
        event.registerLayerDefinition(CrystalFortisArmorModel.LAYER_LOCATION, CrystalFortisArmorModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        ModEntityRenderers.register(event);
    }
}

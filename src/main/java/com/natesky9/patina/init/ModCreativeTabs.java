package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Patina.MODID);

    //region global tab
    public static final RegistryObject<CreativeModeTab> TAB_1 = TABS.register("main_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.main_tab"))
                    .icon(Items.COPPER_INGOT::getDefaultInstance)
                    .withTabsAfter(ModCreativeTabs.TOOL_TAB.getId(),
                            ModCreativeTabs.MACHINE_TAB.getId(),
                            ModCreativeTabs.POTION_TAB.getId())
                    .displayItems((params, output) ->
                    {
                        for (RegistryObject<Item> item: ModItems.ITEMS.getEntries())
                        {
                            output.accept(item.get().getDefaultInstance());
                        }
                    })
                    .build());
    //endregion
    //region Tool Tab
    public static final RegistryObject<CreativeModeTab> TOOL_TAB = TABS.register("tool_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tool_tab"))
                    .icon(ModItems.BEE_SWORD.get()::getDefaultInstance)
                    .displayItems((params, output) ->
                    {
                        output.accept(ModItems.BEE_SWORD.get());
                        output.accept(ModItems.BEE_SHIELD.get());
                        output.accept(ModItems.PIG_CROSSBOW.get());
                        output.accept(ModItems.PIG_SWORD.get());
                        output.accept(ModItems.WITHER_STAFF.get());
                        output.accept(ModItems.WITHER_WINGS.get());

                        output.accept(ModItems.CLAW.get());
                        output.accept(ModItems.COPPER_CLAW.get());
                        output.accept(ModItems.CRYSTAL_CLAW.get());
                        output.accept(ModItems.DRAGON_CLAW.get());

                        output.accept(ModItems.BRONZE_HELMET.get());
                        output.accept(ModItems.BRONZE_CHESTPLATE.get());
                        output.accept(ModItems.BRONZE_LEGGINGS.get());
                        output.accept(ModItems.BRONZE_BOOTS.get());
                        output.accept(ModItems.BRONZE_AXE.get());
                        output.accept(ModItems.BRONZE_PICK.get());
                        output.accept(ModItems.BRONZE_SHOVEL.get());
                        output.accept(ModItems.BRONZE_SWORD.get());
                        output.accept(ModItems.BRONZE_HOE.get());

                        output.accept(ModItems.COPPER_HELMET.get());
                        output.accept(ModItems.COPPER_CHESTPLATE.get());
                        output.accept(ModItems.COPPER_LEGGINGS.get());
                        output.accept(ModItems.COPPER_BOOTS.get());
                        output.accept(ModItems.COPPER_AXE.get());
                        output.accept(ModItems.COPPER_PICK.get());
                        output.accept(ModItems.COPPER_SHOVEL.get());
                        output.accept(ModItems.COPPER_SWORD.get());
                        output.accept(ModItems.COPPER_HOE.get());

                        output.accept(ModItems.DRAGON_HELMET.get());
                        output.accept(ModItems.DRAGON_CHESTPLATE.get());
                        output.accept(ModItems.DRAGON_LEGGINGS.get());
                        //output.accept(ModItems.DRAGON_BOOTS.get());

                        output.accept(ModItems.CLOTH_BOOTS.get());
                        output.accept(ModItems.UMBRA_HAT.get());
                        output.accept(ModItems.UMBRA_TOP.get());
                        output.accept(ModItems.UMBRA_BOTTOM.get());

                        output.accept(ModItems.CHARM_ALCHEMY.get());
                        output.accept(ModItems.CHARM_AMBUSH.get());
                        output.accept(ModItems.CHARM_CONTRABAND.get());
                        output.accept(ModItems.CHARM_DETONATION.get());
                        output.accept(ModItems.CHARM_FERTILITY.get());
                        output.accept(ModItems.CHARM_EXPERIENCE.get());
                        output.accept(ModItems.CHARM_VITALITY.get());
                        output.accept(ModItems.CHARM_WARDING.get());
                        output.accept(ModItems.CHARM_FRAGMENT.get());
                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> MACHINE_TAB = TABS.register("machine_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.machine_tab"))
                    .icon(ModBlocks.MACHINE_ALEMBIC.get().asItem()::getDefaultInstance)
                    .displayItems((params, output) ->
                    {
                        output.accept(ModBlocks.MACHINE_ALEMBIC.get());
                        output.accept(ModBlocks.ADDON_ALEMBIC.get());
                        output.accept(ModBlocks.MACHINE_EVAPORATOR.get());
                        output.accept(ModBlocks.MACHINE_FOUNDRY.get());
                        output.accept(ModBlocks.ADDON_FOUNDRY.get());
                        output.accept(ModBlocks.MACHINE_MINCERATOR.get());
                        output.accept(ModBlocks.MACHINE_TEXTILER.get());
                        output.accept(ModBlocks.MACHINE_ENCHANTER.get());
                        output.accept(ModBlocks.APPLIANCE_ICEBOX.get());
                        output.accept(ModBlocks.APPLIANCE_WARDROBE.get());
                        output.accept(ModBlocks.APPLIANCE_ARCANE_CONSOLIDATOR.get());

                        output.accept(ModBlocks.APPLIANCE_PLINTH.get());
                        output.accept(ModBlocks.APPLIANCE_REINFORCED_PLINTH.get());
                        output.accept(ModBlocks.MACHINE_UNIFIER.get());
                        output.accept(ModBlocks.MACHINE_ABSTRACTOR.get());
                        output.accept(ModBlocks.MACHINE_REPLICATOR.get());
                        output.accept(ModBlocks.MACHINE_EXTRACTOR.get());
                        output.accept(ModBlocks.MACHINE_AUGMENTOR.get());
                        output.accept(ModBlocks.MACHINE_ARBITRATOR.get());
                        output.accept(ModBlocks.MACHINE_MATRIX.get());

                        output.accept(ModBlocks.CHORUS_TELEPORTER.get());
                        output.accept(ModBlocks.CHORUS_CABLE.get());

                        output.accept(ModItems.BISMUTH_INGOT.get());
                        output.accept(ModItems.BRONZE_INGOT.get());
                        output.accept(ModItems.MALACHITE.get());
                        output.accept(ModItems.PRIME_GLASS.get());
                        output.accept(ModItems.ANIMA_GLASS.get());
                        output.accept(ModItems.FERUS_GLASS.get());
                        output.accept(ModItems.FORTIS_GLASS.get());
                        output.accept(ModItems.COPPER_NUGGET.get());
                        output.accept(ModItems.NETHERITE_NUGGET.get());

                        output.accept(ModItems.SMITHING_FLUX.get());
                        output.accept(ModItems.SILK.get());
                        output.accept(ModItems.UMBRA.get());

                        output.accept(ModItems.VOID_SALT.get());
                        //potion salts
                        params.holders().lookup(Registries.POTION).ifPresent(
                                lookup -> {
                                    lookup.listElements().filter(potion -> potion.value().getEffects().size() == 1)
                                            .map(potion -> PotionContents.createItemStack(ModItems.POTION_SALT.get(),potion))
                                            .forEach(output::accept);
                                        }
                                );
                    })
                    .build());
    //endregion
    //region salt
    public static final RegistryObject<CreativeModeTab> POTION_TAB = TABS.register("potion_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.potion_tab"))
                    .icon(ModItems.POTION_FLASK.get()::getDefaultInstance)
                    .withSearchBar()
                    .displayItems( (params, output) ->
                    {
                        List<Item> flasks = List.of(ModItems.POTION_FLASK.get(),ModItems.IMPETUS_FLASK.get(),
                            ModItems.MAGNA_FLASK.get(),ModItems.VITA_FLASK.get(),ModItems.ETERNA_FLASK.get());
                        for (Item item:flasks)
                        {
                            for (Potion potion: BuiltInRegistries.POTION)
                            {
                                if (potion.getEffects().isEmpty()) continue;
                                ItemStack stack = item.getDefaultInstance();
                                stack.set(DataComponents.POTION_CONTENTS,new PotionContents(Holder.direct(potion)));//PotionUtils.setPotion(item.getDefaultInstance(),potion);
                                stack.setDamageValue(stack.getMaxDamage());
                                output.accept(stack);
                            }
                        }
                    })
                    .build());
    //endregion


    public static void register(IEventBus eventBus)
    {
        TABS.register(eventBus);
    }
}

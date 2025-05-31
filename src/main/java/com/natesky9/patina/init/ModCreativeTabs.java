package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Patina.MODID);

    //region global tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_1 = TABS.register("patina_main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemgroup.patina.main"))
                    .icon(Items.COPPER_INGOT::getDefaultInstance)
                    .displayItems((params, output) ->
                    {
                        //put all items into this tab
                        for (DeferredHolder<Item, ? extends Item> item: ModItems.ITEMS.getEntries())
                        {
                            output.accept(item.get().getDefaultInstance());
                        }
                    })
                    .build());
    //endregion global tab
    //region tool tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TOOLS = TABS.register("patina_tools",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemgroup.patina.tools"))
                    .icon(Items.IRON_PICKAXE::getDefaultInstance)
                    .displayItems(((params, output) ->
                    {
                        output.accept(ModItems.COPPER_AXE);
                        output.accept(ModItems.COPPER_PICK);
                        output.accept(ModItems.COPPER_SHOVEL);
                        output.accept(ModItems.COPPER_HOE);
                        output.accept(ModItems.CRYSTAL_AXE);
                        output.accept(ModItems.CRYSTAL_PICK);
                        output.accept(ModItems.CRYSTAL_SHOVEL);
                        output.accept(ModItems.CRYSTAL_HOE);

                        output.accept(ModItems.FLASK_CRYSTAL);
                        output.accept(ModItems.FLASK_VITA);
                        output.accept(ModItems.FLASK_MAGNA);
                        output.accept(ModItems.FLASK_ETERNA);
                        output.accept(ModItems.FLASK_PLUVIA);
                        output.accept(ModItems.FLASK_PUGNA);

                        output.accept(ModItems.CRAB_CLAW);
                        output.accept(ModItems.COPPER_CLAW);
                        output.accept(ModItems.DRAGON_CLAW);
                    }))
                    .build());
    //endregion tool tab
    //region combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COMBAT = TABS.register("patina_combat",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemgroup.patina.combat"))
                    .icon(Items.GOLDEN_SWORD::getDefaultInstance)
                    .displayItems(((params, output) ->
                    {
                        output.accept(ModItems.COPPER_SWORD.get());
                        output.accept(ModItems.COPPER_HELMET);
                        output.accept(ModItems.COPPER_CHESTPLATE);
                        output.accept(ModItems.COPPER_LEGGINGS);
                        output.accept(ModItems.COPPER_BOOTS);

                        output.accept(ModItems.CRYSTAL_SWORD);
                        output.accept(ModItems.PRIME_HELMET);
                        output.accept(ModItems.PRIME_CHESTPLATE);
                        output.accept(ModItems.PRIME_LEGGINGS);
                        output.accept(ModItems.ANIMA_HELMET);
                        output.accept(ModItems.ANIMA_CHESTPLATE);
                        output.accept(ModItems.ANIMA_LEGGINGS);
                        output.accept(ModItems.FERUS_HELMET);
                        output.accept(ModItems.FERUS_CHESTPLATE);
                        output.accept(ModItems.FERUS_LEGGINGS);
                        output.accept(ModItems.FORTIS_HELMET);
                        output.accept(ModItems.FORTIS_CHESTPLATE);
                        output.accept(ModItems.FORTIS_LEGGINGS);
                        output.accept(ModItems.IMPERIUM_HELMET);
                        output.accept(ModItems.IMPERIUM_CHESTPLATE);
                        output.accept(ModItems.IMPERIUM_LEGGINGS);
                    }))
                    .build());
    //endregion combat tab

    //region material tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MATERIAL = TABS.register("patina_material",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemgroup.patina.material"))
                    .icon(Items.GLOWSTONE_DUST::getDefaultInstance)
                    .displayItems(((params, output) ->
                    {
                        output.accept(ModItems.COPPER_SWORD.get());
                        output.accept(ModItems.POTION_SALT);
                        output.accept(ModItems.VOID_SALT);
                        output.accept(ModItems.KERATIN);
                        output.accept(ModItems.SILK);
                        output.accept(ModItems.UMBRA);

                        output.accept(ModItems.ANIMA_CRYSTAL);
                        output.accept(ModItems.FERUS_CRYSTAL);
                        output.accept(ModItems.FORTIS_CRYSTAL);
                        output.accept(ModItems.PRIME_CRYSTAL);
                        output.accept(ModItems.REGIMA_CRYSTAL);
                        output.accept(ModItems.PERPETUUM_CRYSTAL);
                        output.accept(ModItems.MALACHITE);

                        output.accept(ModItems.BISMUTH_NUGGET);
                        output.accept(ModItems.BISMUTH_INGOT);
                        output.accept(ModItems.BRON_INGOT);
                    }))
                    .build());
    //endregion material tab
    //
    public static void register(IEventBus eventBus)
    {
        TABS.register(eventBus);
        //eventBus.register(TABS);
    }
}

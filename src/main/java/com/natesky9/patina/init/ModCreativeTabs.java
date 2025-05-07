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
                    }))
                    .build());
    //endregion combat tab
    //
    public static void register(IEventBus eventBus)
    {
        TABS.register(eventBus);
        //eventBus.register(TABS);
    }
}

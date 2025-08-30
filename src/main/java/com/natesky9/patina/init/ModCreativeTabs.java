package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

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

                        for (DeferredHolder<Item, ? extends Item> item: ModItems.ITEMS.getEntries())
                        {
                            if (item.get() instanceof DiggerItem)
                                output.accept(item.get().getDefaultInstance());
                        }

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
    //region ore

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PROCESSING = TABS.register("patina_processing",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemgroup.patina.processing"))
                    .icon(Items.IRON_ORE::getDefaultInstance)
                    .displayItems(((params, output) ->
                    {
                        //region items
                        List<Holder<Item>> copper = List.of(ModItems.COPPER_COBBLE,ModItems.COPPER_FLAKE,
                                ModItems.COPPER_PEBBLE,ModItems.COPPER_BLEND,ModItems.COPPER_CHUNK,ModItems.COPPER_CLUMP,
                                ModItems.COPPER_GRAVEL,ModItems.COPPER_GRIT,ModItems.COPPER_HUNK,ModItems.COPPER_MIX,
                                ModItems.COPPER_SLAG,ModItems.COPPER_LUMP);
                        List<Holder<Item>> iron = List.of(ModItems.IRON_COBBLE,ModItems.IRON_FLAKE,
                                ModItems.IRON_PEBBLE,ModItems.IRON_BLEND,ModItems.IRON_CHUNK,ModItems.IRON_CLUMP,
                                ModItems.IRON_GRAVEL,ModItems.IRON_GRIT,ModItems.IRON_HUNK,ModItems.IRON_MIX,
                                ModItems.IRON_SLAG,ModItems.IRON_LUMP);
                        List<Holder<Item>> gold = List.of(ModItems.GOLD_COBBLE,ModItems.GOLD_FLAKE,
                                ModItems.GOLD_PEBBLE,ModItems.GOLD_BLEND,ModItems.GOLD_CHUNK,ModItems.GOLD_CLUMP,
                                ModItems.GOLD_GRAVEL,ModItems.GOLD_GRIT,ModItems.GOLD_HUNK,ModItems.GOLD_MIX,
                                ModItems.GOLD_SLAG,ModItems.GOLD_LUMP);
                        //endregion items
                        for (Holder<Item> item:copper)
                            output.accept(item.value());
                        for (Holder<Item> item:iron)
                            output.accept(item.value());
                        for (Holder<Item> item:gold)
                            output.accept(item.value());
                    }))
                    .build());
    //endregion ore

    //region material tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MATERIAL = TABS.register("patina_material",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemgroup.patina.material"))
                    .icon(ModItems.COPPER_AXE.get()::getDefaultInstance)
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
                        output.accept(ModItems.COPPER_NUGGET);
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

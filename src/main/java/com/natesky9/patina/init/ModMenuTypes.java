package com.natesky9.patina.init;

import com.natesky9.patina.Menu.*;
import com.natesky9.patina.Patina;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(BuiltInRegistries.MENU, Patina.MODID);
    //
    public static final Supplier<MenuType<MinceratorMenu>> MINCERATOR_MENU = MENU_TYPES.register("mincerator",
            () -> IMenuTypeExtension.create(MinceratorMenu::new));
    public static final Supplier<MenuType<FoundryMenu>> FOUNDRY_MENU = MENU_TYPES.register("foundry",
            () -> IMenuTypeExtension.create(FoundryMenu::new));
    public static final Supplier<MenuType<EvaporatorMenu>> EVAPORATOR_MENU = MENU_TYPES.register("evaporator",
            () -> IMenuTypeExtension.create(EvaporatorMenu::new));
    public static final Supplier<MenuType<TextilerMenu>> TEXTILER_MENU = MENU_TYPES.register("textiler",
            () -> IMenuTypeExtension.create(TextilerMenu::new));
    public static final Supplier<MenuType<AlembicMenu>> ALEMBIC_MENU = MENU_TYPES.register("alembic",
            () -> IMenuTypeExtension.create(AlembicMenu::new));
    public static final Supplier<MenuType<KwernMenu>> KWERN_MENU = MENU_TYPES.register("kwern",
            () -> IMenuTypeExtension.create(KwernMenu::new));
    public static final Supplier<MenuType<SieveMenu>> SIEVE_MENU = MENU_TYPES.register("sieve",
            () -> IMenuTypeExtension.create(SieveMenu::new));

    public static final Supplier<MenuType<IceboxMenu>> ICEBOX_MENU = MENU_TYPES.register("icebox",
            () -> IMenuTypeExtension.create(IceboxMenu::new));
    public static final Supplier<MenuType<WardrobeMenu>> WARDROBE_MENU = MENU_TYPES.register("wardrobe",
            () -> IMenuTypeExtension.create(WardrobeMenu::new));
    //
    public static void register(IEventBus eventBus)
    {
        MENU_TYPES.register(eventBus);
    }
}

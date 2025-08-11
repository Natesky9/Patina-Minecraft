package com.natesky9.patina.misc;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.natesky9.patina.Items.OreProcessItem;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jetbrains.annotations.Nullable;

public record ItemColorTintSource(int defaultColor) implements ItemTintSource {
    public static final MapCodec<ItemColorTintSource> MAP_CODEC = RecordCodecBuilder.mapCodec((p_386972_) ->
            p_386972_.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default")
            .forGetter(ItemColorTintSource::defaultColor)).apply(p_386972_, ItemColorTintSource::new));
    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
        if (!(itemStack.getItem() instanceof OreProcessItem)) return 0;
        ItemContainerContents contents = itemStack.get(DataComponents.CONTAINER);

        if (contents == null || contents.stream().findFirst().isEmpty()) return 0;
        ItemStack stack = contents.stream().findFirst().get();


        if (stack.is(Items.RAW_COPPER))
            return ARGB.opaque(13196853);
        if (stack.is(Items.RAW_IRON))
            return ARGB.opaque(11505271);
        if (stack.is(Items.RAW_GOLD))
            return ARGB.opaque(16237617);

        return 0;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}

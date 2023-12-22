package com.natesky9.patina.Item;

import com.natesky9.patina.init.ModArmorMaterials;
import com.natesky9.patina.init.ModModels;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class UmbraArmorItem extends CustomArmorItem{
    public UmbraArmorItem(Type p_266831_, Properties p_40388_, Attribute attribute, AttributeModifier modifier) {
        super(ModArmorMaterials.UMBRA,p_266831_, p_40388_, attribute, modifier);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
                            @Override
                            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                                return ModModels.umbraArmorModel;
                            }
                        }
        );
    }
}
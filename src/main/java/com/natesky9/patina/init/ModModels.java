package com.natesky9.patina.init;

import com.natesky9.patina.Entity.armor.CopperArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class ModModels {
    public static final CopperArmorModel<HumanoidRenderState> copperArmorModel =
            new CopperArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(CopperArmorModel.LAYER_LOCATION));
}

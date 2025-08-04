package com.natesky9.patina.Blocks.Renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.natesky9.patina.Blocks.PlinthEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PlinthEntityRenderer implements BlockEntityRenderer<PlinthEntity> {
    public PlinthEntityRenderer(BlockEntityRendererProvider.Context context)
    {

    }
    @Override
    public void render(PlinthEntity entity, float partial, PoseStack poseStack,
                       MultiBufferSource multiBufferSource, int packedLight, int packedOverlay)
    {
        //render a floating item
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = entity.inventory.getStackInSlot(0);
        if (stack.isEmpty()) return;

        poseStack.translate(.5f, 1.5f, .5f);
        poseStack.scale(.5f, .5f, .5f);
        //how come this changed between versions?
        float rot = entity.getLevel().getTimeOfDay(partial)*100000;
        poseStack.mulPose(Axis.YP.rotationDegrees(rot));

        renderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay,
                poseStack, multiBufferSource, entity.getLevel(), 0);

    }
}

package com.natesky9.patina.Block.Plinth;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class AppliancePlinthRenderer implements BlockEntityRenderer<AppliancePlinthEntity> {
    private final ItemRenderer itemRenderer;
    public AppliancePlinthRenderer(BlockEntityRendererProvider.Context pContext)
    {
        this.itemRenderer = pContext.getItemRenderer();
    }
    @Override
    public void render(AppliancePlinthEntity plinth, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemStack stack = plinth.getStack();
        pPoseStack.translate(.5,1,.5);
        pPoseStack.scale(.5f,.5f,.5f);
        pPoseStack.translate(0,.5,0);
        pPoseStack.mulPose(Axis.YP.rotation(plinth.getLevel().getTimeOfDay(pPartialTick)*1000));

        if (!stack.isEmpty())
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED,pPackedLight,pPackedOverlay,pPoseStack,pBufferSource,plinth.getLevel(),0);
    }
}

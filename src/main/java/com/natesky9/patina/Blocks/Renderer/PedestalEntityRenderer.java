package com.natesky9.patina.Blocks.Renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.natesky9.patina.Blocks.PlinthEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PedestalEntityRenderer extends PlinthEntityRenderer {
    public PedestalEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(PlinthEntity entity, float partial, PoseStack poseStack,
                       MultiBufferSource multiBufferSource, int packedLight, int packedOverlay)
    {
        Entity cameraEntity = Minecraft.getInstance().cameraEntity;
        if (cameraEntity == null) return;
        //render a floating item
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = entity.inventory.getStackInSlot(0);
        if (stack.isEmpty()) return;
        int rework = stack.getOrDefault(DataComponents.REPAIR_COST,0);
        //System.out.println("rework: " + rework);

        //render the item
        poseStack.pushPose();
        poseStack.translate(.5f, 1.5f, .5f);
        poseStack.scale(.5f, .5f, .5f);
        //how come this changed between versions?
        float rot = entity.getLevel().getTimeOfDay(partial)*100000;
        poseStack.mulPose(Axis.YP.rotationDegrees(rot));

        renderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay,
                poseStack, multiBufferSource, entity.getLevel(), 0);

        //render the rework penalty
        if (rework == 0)
        {
            poseStack.popPose();
            return;
        }

        int cameraRot = (int) ((-(Mth.wrapDegrees(cameraEntity.getYRot())+180)+180+360+45+90) / 90);
        poseStack.popPose();
        poseStack.translate(.5,1.1,.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(cameraRot * 90));
        poseStack.translate(.4f,0,0);
        poseStack.scale(.1f, .1f, .1f);

        poseStack.mulPose(Axis.YP.rotationDegrees(90));

        //this gets the amount of "bills" it would take to reach the number
        //counting 10s, 5s, and 1s
        int base10 = (rework / 10) + ((rework / 5) % 2) + rework % 10 - ((rework / 5) % 2)*5;
        //translate it left as far as half the width
        poseStack.translate(-(float)base10/2*.8 -.4,0,0);

        int count = rework;
        for (int i=0; i<base10; i++)
        {
            poseStack.translate(.8,0,0);
            if (count/10 >= 1)
            {
                renderer.renderStatic(Items.LAPIS_BLOCK.getDefaultInstance(),ItemDisplayContext.FIXED, packedLight, packedOverlay,
                    poseStack, multiBufferSource, entity.getLevel(), 0);
                count -= 10;
                continue;
            }
            if (count / 5 >= 1)
            {
                renderer.renderStatic(Items.LAPIS_ORE.getDefaultInstance(),ItemDisplayContext.FIXED, packedLight, packedOverlay,
                        poseStack, multiBufferSource, entity.getLevel(), 0);
                count -= 5;
                continue;
            }
            renderer.renderStatic(Items.LAPIS_LAZULI.getDefaultInstance(),ItemDisplayContext.FIXED, packedLight, packedOverlay,
                    poseStack, multiBufferSource, entity.getLevel(), 0);
            count -= 1;
        }
    }
}

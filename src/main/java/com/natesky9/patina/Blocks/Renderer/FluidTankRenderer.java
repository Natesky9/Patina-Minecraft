package com.natesky9.patina.Blocks.Renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.natesky9.patina.Blocks.ApplianceFluidTankEntity;
import com.natesky9.patina.Patina;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.textures.FluidSpriteCache;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidTankRenderer implements BlockEntityRenderer<ApplianceFluidTankEntity> {
    //TODO:renderers are single instanced! replace lerpFluid with the entity's own lerp
    private int lerpFluid = 0;
    public FluidTankRenderer(BlockEntityRendererProvider.Context context)
    {

    }
    @Override
    public void render(ApplianceFluidTankEntity applianceFluidTankEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int i1) {
        if (applianceFluidTankEntity.getLevel() == null) return;

        FluidStack fluid = applianceFluidTankEntity.fluidHandler.getFluid();
        int fluidAmount = applianceFluidTankEntity.fluidHandler.getFluidAmount();
        if (fluid.isEmpty())
        {
            this.lerpFluid = 0;
            return;
        }
        FluidState state = fluid.getFluid().defaultFluidState();
        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluid.getFluid());


        TextureAtlasSprite[] atextureatlassprite = FluidSpriteCache.getFluidSprites(applianceFluidTankEntity.getLevel(),
                applianceFluidTankEntity.getBlockPos(), fluid.getFluid().defaultFluidState());
        TextureAtlasSprite sprite = atextureatlassprite[0];

        int tintColor = fluidTypeExtensions.getTintColor();
        VertexConsumer builder = multiBufferSource.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));

        this.lerpFluid = (int) Mth.lerp(.1F, this.lerpFluid, fluidAmount);
        float fill = ((float) this.lerpFluid /applianceFluidTankEntity.fluidHandler.getCapacity());

        //p is padding, all around the fluid render
        float p = 1/8F;
        //height is the fluid fill
        float height = 7/8F*fill*2 + p;

        float u = sprite.getU(0);
        float v = sprite.getV(0);

        Entity camera = Minecraft.getInstance().cameraEntity;
        BlockPos pos = applianceFluidTankEntity.getBlockPos();

        boolean eastOf = camera.getX() < pos.getX();
        boolean southOf = camera.getZ() < pos.getZ();
        boolean westOf = camera.getZ()-1 > pos.getZ();
        boolean northOf = camera.getX()-1 > pos.getX();

        //top
        drawVertex(builder, poseStack, 1-p, height,0+p,sprite.getU(.5F),v, packedLight, tintColor);
        drawVertex(builder, poseStack, 0+p, height,0+p,u,v, packedLight, tintColor);
        drawVertex(builder, poseStack, 0+p, height,1-p,u,sprite.getV(.5F), packedLight, tintColor);
        drawVertex(builder, poseStack, 1-p, height,1-p,sprite.getU(.5F),sprite.getV(.5F), packedLight, tintColor);

        //viewed facing south
        if (southOf)
        {
            drawVertex(builder, poseStack, 1-p, p, 0+p, sprite.getU(.5F), v, packedLight, tintColor);
            drawVertex(builder, poseStack, 0+p, p, 0+p, u, v, packedLight, tintColor);
            drawVertex(builder, poseStack, 0+p, height, 0+p, u, sprite.getV(fill), packedLight, tintColor);
            drawVertex(builder, poseStack, 1-p, height, 0+p, sprite.getU(.5F), sprite.getV(fill), packedLight, tintColor);
        }
        //right
        if (northOf)
        {
            drawVertex(builder, poseStack, 1-p, p, 1-p, sprite.getU(.5F), v, packedLight, tintColor);
            drawVertex(builder, poseStack, 1-p, p, 0+p, u, v, packedLight, tintColor);
            drawVertex(builder, poseStack, 1-p, height, 0+p, u, sprite.getV(fill), packedLight, tintColor);
            drawVertex(builder, poseStack, 1-p, height, 1-p, sprite.getU(.5F), sprite.getV(fill), packedLight, tintColor);
        }
        //left
        if(eastOf)
        {
            drawVertex(builder, poseStack, 0+p, p, 0+p, sprite.getU(.5F), v, packedLight, tintColor);
            drawVertex(builder, poseStack, 0+p, p, 1-p, u, v, packedLight, tintColor);
            drawVertex(builder, poseStack, 0+p, height, 1-p, u, sprite.getV(fill), packedLight, tintColor);
            drawVertex(builder, poseStack, 0+p, height, 0+p, sprite.getU(.5F), sprite.getV(fill), packedLight, tintColor);
        }
        //back
        if (westOf)
        {
            drawVertex(builder, poseStack, 0+p, p, 1-p, sprite.getU(.5F), v, packedLight, tintColor);
            drawVertex(builder, poseStack, 1-p, p, 1-p, u, v, packedLight, tintColor);
            drawVertex(builder, poseStack, 1-p, height, 1-p, u, sprite.getV(fill), packedLight, tintColor);
            drawVertex(builder, poseStack, 0+p, height, 1-p, sprite.getU(.5F), sprite.getV(fill), packedLight, tintColor);
        }
    }
    private static void drawVertex(VertexConsumer builder, PoseStack pose, float x, float y, float z,
                                   float u, float v, int light,int tint)
    {
        builder.addVertex(pose.last().pose(), x, y, z)
                .setColor(tint).setUv(u,v)
                .setLight(light)
                .setNormal(1,0,0);
    }

    @Override
    public boolean shouldRender(ApplianceFluidTankEntity blockEntity, Vec3 cameraPos) {
        return BlockEntityRenderer.super.shouldRender(blockEntity, cameraPos);
    }

    @Override
    public int getViewDistance() {
        return 128;
    }
}

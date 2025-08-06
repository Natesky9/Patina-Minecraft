package com.natesky9.patina.Entity.armor;

import com.natesky9.patina.Patina;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;

public class CopperArmorModel <T extends HumanoidRenderState> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Patina.MODID,"copper_armor"),"main");

    public CopperArmorModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(28, 26).addBox(-5.0F, -6.0F, -6.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-1.0F, -10.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(28, 20).addBox(-6.0F, -1.0F, 2.0F, 12.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(28, 30).addBox(-5.0F, -27.0F, -6.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 32).addBox(-5.0F, -26.0F, -6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-2.0F, -26.0F, -6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 34).addBox(1.0F, -26.0F, -6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 32).addBox(4.0F, -26.0F, -6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 28).addBox(-5.0F, -24.0F, -6.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition Chestplate_r1 = body.addOrReplaceChild("Chestplate_r1", CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, -6.0F, 0.0F, 4.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -4.0F, 0.0F, -0.2618F, 0.0F));
        PartDefinition Chestplate_r2 = body.addOrReplaceChild("Chestplate_r2", CubeListBuilder.create().texOffs(34, 23).addBox(0.0F, -6.0F, -1.0F, 4.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 4.0F, 0.0F, 0.2618F, 0.0F));
        PartDefinition Chestplate_r3 = body.addOrReplaceChild("Chestplate_r3", CubeListBuilder.create().texOffs(10, 25).addBox(-4.0F, -6.0F, -1.0F, 4.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 4.0F, 0.0F, -0.2618F, 0.0F));
        PartDefinition Chestplate_r4 = body.addOrReplaceChild("Chestplate_r4", CubeListBuilder.create().texOffs(24, 23).addBox(-4.0F, -6.0F, 0.0F, 4.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -4.0F, 0.0F, 0.2618F, 0.0F));
        PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
        PartDefinition RightArmArmor_r1 = rightArm.addOrReplaceChild("RightArmArmor_r1", CubeListBuilder.create().texOffs(24, 9).addBox(-4.0F, 0.0F, -3.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1225F, -2.9306F, 0.0F, 0.0F, 0.0F, -0.7854F));
        PartDefinition RightArmArmor_r2 = rightArm.addOrReplaceChild("RightArmArmor_r2", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -1.0F, -4.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
        PartDefinition LeftArmArmor_r1 = leftArm.addOrReplaceChild("LeftArmArmor_r1", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, 0.0F, -3.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.1225F, -2.9306F, 0.0F, 0.0F, 0.0F, 0.7854F));
        PartDefinition LeftArmArmor_r2 = leftArm.addOrReplaceChild("LeftArmArmor_r2", CubeListBuilder.create().texOffs(24, 0).addBox(0.0F, -1.0F, -4.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

}

package com.example.examplemod.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;

public class FurryModel<T extends LivingEntity> extends EntityModel<T> {
    // 自定义参数
    private final ModelPart head;
    private final ModelPart horns;
    private final ModelPart tail;
    private final ModelPart legs;

    public FurryModel(ModelPart root) {
        this.head = root.getChild("head");
        this.horns = root.getChild("horns");
        this.tail = root.getChild("tail");
        this.legs = root.getChild("legs");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // 头部基础结构
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // 角（可配置位置）
        PartDefinition horns = root.addOrReplaceChild("horns",
                CubeListBuilder.create()
                        .texOffs(32, 0).addBox(-2.0F, -10.0F, -2.0F, 4.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // 尾巴（可配置类型）
        PartDefinition tail = root.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 4.0F, 4.0F, 0.3491F, 0.0F, 0.0F));

        // 腿部（直腿/曲腿）
        PartDefinition legs = root.addOrReplaceChild("legs",
                CubeListBuilder.create()
                        .texOffs(16, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(0.0F, 12.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // 动画逻辑
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
    }

    @Override
    public void renderToBuffer(com.mojang.blaze3d.vertex.PoseStack poseStack, com.mojang.blaze3d.vertex.VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        horns.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    // 自定义参数设置方法
    public void setHorns(boolean hasHorns) {
        this.horns.visible = hasHorns;
    }

    public void setTailType(int type) {
        // 根据类型调整尾巴模型
    }

    public void setLegType(boolean isBent) {
        // 调整腿部旋转角度
    }
}
package io.github.xsmalldeadguyx.elementalcreepers.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CelebrationCreeperModel extends EntityModel<CreeperRenderState> {
	private final ModelPart head;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;

	public CelebrationCreeperModel(ModelPart p_170524_) {
        super(p_170524_);
		this.head = p_170524_.getChild("head");
		this.rightHindLeg = p_170524_.getChild("right_hind_leg");
		this.leftHindLeg = p_170524_.getChild("left_hind_leg");
		this.rightFrontLeg = p_170524_.getChild("right_front_leg");
		this.leftFrontLeg = p_170524_.getChild("left_front_leg");
	}

	public static LayerDefinition createBodyLayer() {
		CubeDeformation deformation = CubeDeformation.NONE;
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation)
						.texOffs(24, 0).addBox(-1F, -14F, -1F, 2F, 6F, 2F, deformation).texOffs(32, 0)
						.addBox(-0.5F, -15F, -0.5F, 1F, 1F, 1F, deformation),
				PartPose.offset(0.0F, 6.0F, 0.0F));

		partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation),
				PartPose.offset(0.0F, 6.0F, 0.0F));

		CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F,
				4.0F, deformation);
		partdefinition.addOrReplaceChild("right_hind_leg", cubelistbuilder, PartPose.offset(-2.0F, 18.0F, 4.0F));
		partdefinition.addOrReplaceChild("left_hind_leg", cubelistbuilder, PartPose.offset(2.0F, 18.0F, 4.0F));
		partdefinition.addOrReplaceChild("right_front_leg", cubelistbuilder, PartPose.offset(-2.0F, 18.0F, -4.0F));
		partdefinition.addOrReplaceChild("left_front_leg", cubelistbuilder, PartPose.offset(2.0F, 18.0F, -4.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(CreeperRenderState p_361525_) {
        super.setupAnim(p_361525_);
        this.head.yRot = p_361525_.yRot * (float) (Math.PI / 180.0);
        this.head.xRot = p_361525_.xRot * (float) (Math.PI / 180.0);
        float f = p_361525_.walkAnimationSpeed;
        float f1 = p_361525_.walkAnimationPos;
        this.rightHindLeg.xRot = Mth.cos(f1 * 0.6662F) * 1.4F * f;
        this.leftHindLeg.xRot = Mth.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f;
        this.rightFrontLeg.xRot = Mth.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f;
        this.leftFrontLeg.xRot = Mth.cos(f1 * 0.6662F) * 1.4F * f;
	}
}
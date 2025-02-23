package io.github.xsmalldeadguyx.elementalcreepers.client.model;

import java.util.Set;

import io.github.xsmalldeadguyx.elementalcreepers.client.renderstate.FriendlyCreeperRenderState;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FriendlyCreeperModel extends EntityModel<FriendlyCreeperRenderState> {
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(Set.of("head"));
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;

	public FriendlyCreeperModel(ModelPart p_170524_) {
		super(p_170524_);
		
		this.head = p_170524_.getChild("head");
		this.body = p_170524_.getChild("body");
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
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation),
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
	public void setupAnim(FriendlyCreeperRenderState p_102463_) {
        float f = p_102463_.walkAnimationPos;
        float f1 = p_102463_.walkAnimationSpeed;
        
        float f2 = p_102463_.ageScale;
        
		if (p_102463_.isSitting) {
			this.head.setPos(0F, 12F * f2, 0F);
			this.body.setPos(0F, 12F, 0F);
			this.leftHindLeg.setPos(2F, 22F, 2F);
			this.rightHindLeg.setPos(-2F, 22F, 2F);
			this.leftFrontLeg.setPos(2F, 22F, -2F);
			this.rightFrontLeg.setPos(-2F, 22F, -2F);

			this.leftHindLeg.xRot = (float) Math.PI / 2F;
			this.rightHindLeg.xRot = (float) Math.PI / 2F;
			this.leftFrontLeg.xRot = 3F * (float) Math.PI / 2F;
			this.rightFrontLeg.xRot = 3F * (float) Math.PI / 2F;

			this.leftHindLeg.yRot = (float) Math.PI / 18F;
			this.rightHindLeg.yRot = (float) -Math.PI / 18F;
			this.leftFrontLeg.yRot = (float) -Math.PI / 18F;
			this.rightFrontLeg.yRot = (float) Math.PI / 18F;
		} else {
			this.head.setPos(0F, 6F * f2, 0F);
			this.body.setPos(0F, 6F, 0F);
			this.leftHindLeg.setPos(2F, 18F, 4F);
			this.rightHindLeg.setPos(-2F, 18F, 4F);
			this.leftFrontLeg.setPos(2F, 18F, -4F);
			this.rightFrontLeg.setPos(-2F, 18F, -4F);

			this.leftHindLeg.xRot = Mth.cos(f * 0.6662F) * 1.4F * f1;
			this.rightHindLeg.xRot = Mth.cos(f * 0.6662F + 2 * (float) Math.PI / 3.0F) * 1.4F * f1;
			this.leftFrontLeg.xRot = Mth.cos(f * 0.6662F + 4 * (float) Math.PI / 3.0F) * 1.4F * f1;
			this.rightFrontLeg.xRot = Mth.cos(f * 0.6662F) * 1.4F * f1;

			this.leftHindLeg.yRot = 0F;
			this.rightHindLeg.yRot = 0F;
			this.leftFrontLeg.yRot = 0F;
			this.rightFrontLeg.yRot = 0F;
		}

		this.head.xRot = p_102463_.xRot * ((float) Math.PI / 180F);
		this.head.yRot = p_102463_.yRot * ((float) Math.PI / 180F);
	}
}
package io.github.xsmalldeadguyx.elementalcreepers.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiderCreeperModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightSideLeg;
	private final ModelPart leftSideLeg;

	public SpiderCreeperModel(ModelPart p_170524_) {
		this.root = p_170524_;
		this.head = p_170524_.getChild("head");
		this.rightHindLeg = p_170524_.getChild("right_hind_leg");
		this.leftHindLeg = p_170524_.getChild("left_hind_leg");
		this.rightFrontLeg = p_170524_.getChild("right_front_leg");
		this.leftFrontLeg = p_170524_.getChild("left_front_leg");

		this.rightSideLeg = p_170524_.getChild("right_side_leg");
		this.leftSideLeg = p_170524_.getChild("left_side_leg");
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
		partdefinition.addOrReplaceChild("right_side_leg", cubelistbuilder, PartPose.offset(-6.0F, 18.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_side_leg", cubelistbuilder, PartPose.offset(6.0F, 18.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	public ModelPart root() {
		return this.root;
	}

	public void setupAnim(T p_102463_, float p_102464_, float p_102465_, float p_102466_, float p_102467_,
			float p_102468_) {
		this.head.yRot = p_102467_ * ((float) Math.PI / 180F);
		this.head.xRot = p_102468_ * ((float) Math.PI / 180F);
		this.leftHindLeg.xRot = Mth.cos(p_102464_ * 0.6662F) * 1.4F * p_102465_;
		this.rightHindLeg.xRot = Mth.cos(p_102464_ * 0.6662F + 2 * (float) Math.PI / 3.0F) * 1.4F * p_102465_;
		this.leftFrontLeg.xRot = Mth.cos(p_102464_ * 0.6662F + 4 * (float) Math.PI / 3.0F) * 1.4F * p_102465_;
		this.rightFrontLeg.xRot = Mth.cos(p_102464_ * 0.6662F) * 1.4F * p_102465_;

		this.leftSideLeg.xRot = Mth.cos(p_102464_ * 0.6662F + 2 * (float) Math.PI / 3.0F) * 1.4F * p_102465_;
		this.rightSideLeg.xRot = Mth.cos(p_102464_ * 0.6662F + 4 * (float) Math.PI / 3.0F) * 1.4F * p_102465_;

	}
}
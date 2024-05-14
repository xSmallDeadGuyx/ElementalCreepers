package io.github.xsmalldeadguyx.elementalcreepers.client.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiderCreeperModel<T extends Entity> extends SegmentedModel<T> {
	private final ModelRenderer head;
	private final ModelRenderer hair;
	private final ModelRenderer body;
	private final ModelRenderer rightHindLeg;
	private final ModelRenderer leftHindLeg;
	private final ModelRenderer rightFrontLeg;
	private final ModelRenderer leftFrontLeg;
	private final ModelRenderer rightSideLeg;
	private final ModelRenderer leftSideLeg;

	public SpiderCreeperModel() {
		this(0.0F);
	}

	public SpiderCreeperModel(float p_i46366_1_) {
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i46366_1_);
		this.head.setPos(0.0F, 6.0F, 0.0F);
		this.hair = new ModelRenderer(this, 32, 0);
		this.hair.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i46366_1_ + 0.5F);
		this.hair.setPos(0.0F, 6.0F, 0.0F);
		this.body = new ModelRenderer(this, 16, 16);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_i46366_1_);
		this.body.setPos(0.0F, 6.0F, 0.0F);
		this.rightHindLeg = new ModelRenderer(this, 0, 16);
		this.rightHindLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
		this.rightHindLeg.setPos(-2.0F, 18.0F, 4.0F);
		this.leftHindLeg = new ModelRenderer(this, 0, 16);
		this.leftHindLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
		this.leftHindLeg.setPos(2.0F, 18.0F, 4.0F);
		this.rightFrontLeg = new ModelRenderer(this, 0, 16);
		this.rightFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
		this.rightFrontLeg.setPos(-2.0F, 18.0F, -4.0F);
		this.leftFrontLeg = new ModelRenderer(this, 0, 16);
		this.leftFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
		this.leftFrontLeg.setPos(2.0F, 18.0F, -4.0F);
		this.rightSideLeg = new ModelRenderer(this, 0, 16);
		this.rightSideLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
		this.rightSideLeg.setPos(-6.0F, 18.0F, 0.0F);
		this.leftSideLeg = new ModelRenderer(this, 0, 16);
		this.leftSideLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
		this.leftSideLeg.setPos(6.0F, 18.0F, 0.0F);
	}

	@Override
	public void setupAnim(T p_102463_, float p_102464_, float p_102465_, float p_102466_, float p_102467_,
			float p_102468_) {
		this.head.yRot = p_102467_ * ((float) Math.PI / 180F);
		this.head.xRot = p_102468_ * ((float) Math.PI / 180F);
		this.leftHindLeg.xRot = MathHelper.cos(p_102464_ * 0.6662F) * 1.4F * p_102465_;
		this.rightHindLeg.xRot = MathHelper.cos(p_102464_ * 0.6662F + 2 * (float) Math.PI / 3.0F) * 1.4F * p_102465_;
		this.leftFrontLeg.xRot = MathHelper.cos(p_102464_ * 0.6662F + 4 * (float) Math.PI / 3.0F) * 1.4F * p_102465_;
		this.rightFrontLeg.xRot = MathHelper.cos(p_102464_ * 0.6662F) * 1.4F * p_102465_;

		this.leftSideLeg.xRot = MathHelper.cos(p_102464_ * 0.6662F + 2 * (float) Math.PI / 3.0F) * 1.4F * p_102465_;
		this.rightSideLeg.xRot = MathHelper.cos(p_102464_ * 0.6662F + 4 * (float) Math.PI / 3.0F) * 1.4F * p_102465_;

	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(this.head, this.hair, this.body, this.rightHindLeg, this.leftHindLeg,
				this.rightFrontLeg, this.leftFrontLeg, this.rightSideLeg, this.leftSideLeg);
	}
}
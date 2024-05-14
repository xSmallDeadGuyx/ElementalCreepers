package io.github.xsmalldeadguyx.elementalcreepers.client.model;

import com.google.common.collect.ImmutableList;

import io.github.xsmalldeadguyx.elementalcreepers.common.entity.FriendlyCreeper;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FriendlyCreeperModel<T extends FriendlyCreeper> extends AgeableModel<T> {
	private final ModelRenderer head;
	private final ModelRenderer hair;
	private final ModelRenderer body;
	private final ModelRenderer leg0;
	private final ModelRenderer leg1;
	private final ModelRenderer leg2;
	private final ModelRenderer leg3;

	public FriendlyCreeperModel() {
		this(0.0F);
	}

	public FriendlyCreeperModel(float p_i46366_1_) {
		super(false, 12F, 0F, 2F, 2F, 24F);

		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i46366_1_);
		this.head.setPos(0.0F, 6.0F, 0.0F);
		this.hair = new ModelRenderer(this, 32, 0);
		this.hair.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i46366_1_ + 0.5F);
		this.hair.setPos(0.0F, 6.0F, 0.0F);
		this.body = new ModelRenderer(this, 16, 16);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_i46366_1_);
		this.body.setPos(0.0F, 6.0F, 0.0F);
		this.leg0 = new ModelRenderer(this, 0, 16);
		this.leg0.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
		this.leg0.setPos(-2.0F, 18.0F, 4.0F);
		this.leg1 = new ModelRenderer(this, 0, 16);
		this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
		this.leg1.setPos(2.0F, 18.0F, 4.0F);
		this.leg2 = new ModelRenderer(this, 0, 16);
		this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
		this.leg2.setPos(-2.0F, 18.0F, -4.0F);
		this.leg3 = new ModelRenderer(this, 0, 16);
		this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
		this.leg3.setPos(2.0F, 18.0F, -4.0F);
	}

	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_,
			float p_225597_6_) {
		if (p_225597_1_.isInSittingPose()) {
			this.head.setPos(0F, p_225597_1_.isBaby() ? 6F : 12F, 0F);
			this.body.setPos(0F, 12F, 0F);
			this.leg1.setPos(2F, 22F, 2F);
			this.leg0.setPos(-2F, 22F, 2F);
			this.leg3.setPos(2F, 22F, -2F);
			this.leg2.setPos(-2F, 22F, -2F);

			this.leg1.xRot = (float) Math.PI / 2F;
			this.leg0.xRot = (float) Math.PI / 2F;
			this.leg3.xRot = 3F * (float) Math.PI / 2F;
			this.leg2.xRot = 3F * (float) Math.PI / 2F;

			this.leg1.yRot = (float) Math.PI / 18F;
			this.leg0.yRot = (float) -Math.PI / 18F;
			this.leg3.yRot = (float) -Math.PI / 18F;
			this.leg2.yRot = (float) Math.PI / 18F;
		} else {
			this.head.setPos(0F, p_225597_1_.isBaby() ? 3F : 6F, 0F);
			this.body.setPos(0F, 6F, 0F);
			this.leg1.setPos(2F, 18F, 4F);
			this.leg0.setPos(-2F, 18F, 4F);
			this.leg3.setPos(2F, 18F, -4F);
			this.leg2.setPos(-2F, 18F, -4F);

			this.leg0.xRot = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
			this.leg1.xRot = MathHelper.cos(p_225597_2_ * 0.6662F + (float) Math.PI) * 1.4F * p_225597_3_;
			this.leg2.xRot = MathHelper.cos(p_225597_2_ * 0.6662F + (float) Math.PI) * 1.4F * p_225597_3_;
			this.leg3.xRot = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;

			this.leg1.yRot = 0F;
			this.leg0.yRot = 0F;
			this.leg3.yRot = 0F;
			this.leg2.yRot = 0F;
		}

		this.head.yRot = p_225597_5_ * ((float) Math.PI / 180F);
		this.head.xRot = p_225597_6_ * ((float) Math.PI / 180F);
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.leg0, this.leg1, this.leg2, this.leg3);
	}
}
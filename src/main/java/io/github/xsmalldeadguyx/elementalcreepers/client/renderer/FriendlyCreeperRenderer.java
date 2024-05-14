package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.xsmalldeadguyx.elementalcreepers.client.model.FriendlyCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.FriendlyCreeper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class FriendlyCreeperRenderer extends MobRenderer<FriendlyCreeper, FriendlyCreeperModel<FriendlyCreeper>> {
	private static ResourceLocation NORMAL_TEXTURE = new ResourceLocation(ElementalCreepers.MODID,
			"textures/entity/friendlycreeper0.png");
	private static ResourceLocation TAME_TEXTURE = new ResourceLocation(ElementalCreepers.MODID,
			"textures/entity/friendlycreeper1.png");

	public FriendlyCreeperRenderer(EntityRendererManager p_173958_) {
		super(p_173958_, new FriendlyCreeperModel<>(), 0.5F);
		this.addLayer(new FriendlyCreeperPowerLayer(this));
	}

	@Override
	protected void scale(FriendlyCreeper p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
		float f = p_225620_1_.getSwelling(p_225620_3_);
		float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		f = f * f;
		f = f * f;
		float f2 = (1.0F + f * 0.4F) * f1;
		float f3 = (1.0F + f * 0.1F) / f1;
		p_225620_2_.scale(f2, f3, f2);
	}

	@Override
	protected float getWhiteOverlayProgress(FriendlyCreeper p_225625_1_, float p_225625_2_) {
		float f = p_225625_1_.getSwelling(p_225625_2_);
		return (int) (f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(FriendlyCreeper p_114482_) {
		return p_114482_.isTame() ? TAME_TEXTURE : NORMAL_TEXTURE;
	}

}

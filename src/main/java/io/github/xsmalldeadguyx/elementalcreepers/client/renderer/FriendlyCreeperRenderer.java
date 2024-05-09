package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.xsmalldeadguyx.elementalcreepers.client.model.FriendlyCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.FriendlyCreeper;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FriendlyCreeperRenderer extends MobRenderer<FriendlyCreeper, FriendlyCreeperModel<FriendlyCreeper>> {
	private static ResourceLocation NORMAL_TEXTURE = new ResourceLocation(ElementalCreepers.MODID,
			"textures/entity/friendlycreeper0.png");
	private static ResourceLocation TAME_TEXTURE = new ResourceLocation(ElementalCreepers.MODID,
			"textures/entity/friendlycreeper1.png");

	public FriendlyCreeperRenderer(EntityRendererProvider.Context p_173958_) {
		super(p_173958_, new FriendlyCreeperModel<>(p_173958_.bakeLayer(ModelLayers.CREEPER)), 0.5F);
		this.addLayer(new FriendlyCreeperPowerLayer(this, p_173958_.getModelSet()));
	}

	protected void scale(FriendlyCreeper p_114046_, PoseStack p_114047_, float p_114048_) {
		float f = p_114046_.getSwelling(p_114048_);
		float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
		f = Mth.clamp(f, 0.0F, 1.0F);
		f *= f;
		f *= f;
		float f2 = (1.0F + f * 0.4F) * f1;
		float f3 = (1.0F + f * 0.1F) / f1;
		p_114047_.scale(f2, f3, f2);
	}

	protected float getWhiteOverlayProgress(FriendlyCreeper p_114043_, float p_114044_) {
		float f = p_114043_.getSwelling(p_114044_);
		return (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(FriendlyCreeper p_114482_) {
		return p_114482_.isTame() ? TAME_TEXTURE : NORMAL_TEXTURE;
	}

}

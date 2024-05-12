package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.xsmalldeadguyx.elementalcreepers.client.ElementalCreepersClient;
import io.github.xsmalldeadguyx.elementalcreepers.client.model.CelebrationCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.CelebrationCreeper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CelebrationCreeperRenderer
		extends MobRenderer<CelebrationCreeper, CelebrationCreeperModel<CelebrationCreeper>> {

	public CelebrationCreeperRenderer(EntityRendererProvider.Context p_173958_) {
		super(p_173958_, new CelebrationCreeperModel<>(
				p_173958_.bakeLayer(ElementalCreepersClient.CELEBRATION_CREEPER_MODEL_LAYER_LOCATION)), 0.5F);
		this.addLayer(new CelebrationCreeperPowerLayer(this, p_173958_.getModelSet()));
	}

	protected void scale(CelebrationCreeper p_114046_, PoseStack p_114047_, float p_114048_) {
		float f = p_114046_.getSwelling(p_114048_);
		float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
		f = Mth.clamp(f, 0.0F, 1.0F);
		f *= f;
		f *= f;
		float f2 = (1.0F + f * 0.4F) * f1;
		float f3 = (1.0F + f * 0.1F) / f1;
		p_114047_.scale(f2, f3, f2);
	}

	protected float getWhiteOverlayProgress(CelebrationCreeper p_114043_, float p_114044_) {
		float f = p_114043_.getSwelling(p_114044_);
		return (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(CelebrationCreeper p_114482_) {
		return new ResourceLocation(ElementalCreepers.MODID, "textures/entity/celebrationcreeper.png");
	}

}

package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.xsmalldeadguyx.elementalcreepers.client.model.GhostCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.GhostCreeper;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GhostCreeperRenderer extends MobRenderer<GhostCreeper, GhostCreeperModel<GhostCreeper>> {
	public GhostCreeperRenderer(EntityRendererProvider.Context p_173958_) {
		super(p_173958_, new GhostCreeperModel<>(p_173958_.bakeLayer(ModelLayers.CREEPER)), 0.5F);
		this.addLayer(new GhostCreeperPowerLayer(this, p_173958_.getModelSet()));
	}

	protected void scale(Creeper p_114046_, PoseStack p_114047_, float p_114048_) {
		float f = p_114046_.getSwelling(p_114048_);
		float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
		f = Mth.clamp(f, 0.0F, 1.0F);
		f *= f;
		f *= f;
		float f2 = (1.0F + f * 0.4F) * f1;
		float f3 = (1.0F + f * 0.1F) / f1;
		p_114047_.scale(f2, f3, f2);
	}

	@Override
	protected float getWhiteOverlayProgress(GhostCreeper p_114043_, float p_114044_) {
		float f = p_114043_.getSwelling(p_114044_);
		return (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(GhostCreeper p_114041_) {
		return new ResourceLocation("textures/entity/creeper/creeper.png");
	}

	@Override
	protected RenderType getRenderType(GhostCreeper p_115322_, boolean p_115323_, boolean p_115324_,
			boolean p_115325_) {
		return RenderType.entityTranslucentEmissive(new ResourceLocation("textures/entity/creeper/creeper.png"));
	}
}

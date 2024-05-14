package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.xsmalldeadguyx.elementalcreepers.client.model.SpiderCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.SpiderCreeper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiderCreeperRenderer extends MobRenderer<SpiderCreeper, SpiderCreeperModel<SpiderCreeper>> {
	public SpiderCreeperRenderer(EntityRendererManager p_173958_) {
		super(p_173958_, new SpiderCreeperModel<>(), 0.5F);
		this.addLayer(new SpiderCreeperPowerLayer(this));
	}

	@Override
	protected void scale(SpiderCreeper p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
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
	protected float getWhiteOverlayProgress(SpiderCreeper p_225625_1_, float p_225625_2_) {
		float f = p_225625_1_.getSwelling(p_225625_2_);
		return (int) (f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(SpiderCreeper p_114041_) {
		return new ResourceLocation(ElementalCreepers.MODID, "textures/entity/spidercreeper.png");
	}
}

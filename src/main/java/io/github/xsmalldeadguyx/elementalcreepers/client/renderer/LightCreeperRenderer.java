package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LightCreeperRenderer extends CreeperRenderer {
	public LightCreeperRenderer(EntityRendererManager p_173958_) {
		super(p_173958_);
	}

	@Override
	public ResourceLocation getTextureLocation(CreeperEntity p_114041_) {
		return new ResourceLocation(ElementalCreepers.MODID, "textures/entity/lightcreeper.png");
	}

	@Override
	protected RenderType getRenderType(CreeperEntity p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
		return RenderType.entityTranslucent(getTextureLocation(p_115322_));
	}
}

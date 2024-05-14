package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LightCreeperRenderer extends CreeperRenderer {
	public LightCreeperRenderer(Context p_173958_) {
		super(p_173958_);
	}

	@Override
	public ResourceLocation getTextureLocation(Creeper p_114041_) {
		return new ResourceLocation(ElementalCreepers.MODID, "textures/entity/lightcreeper.png");
	}

	@Override
	protected RenderType getRenderType(Creeper p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
		return RenderType.entityTranslucent(getTextureLocation(p_115322_));
	}
}

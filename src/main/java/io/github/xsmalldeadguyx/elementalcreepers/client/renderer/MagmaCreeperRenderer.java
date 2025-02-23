package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MagmaCreeperRenderer extends CreeperRenderer {
	public MagmaCreeperRenderer(Context p_173958_) {
		super(p_173958_);
	}

	@Override
	public ResourceLocation getTextureLocation(CreeperRenderState p_114041_) {
		return ResourceLocation.tryBuild(ElementalCreepers.MODID, "textures/entity/magmacreeper.png");
	}
}

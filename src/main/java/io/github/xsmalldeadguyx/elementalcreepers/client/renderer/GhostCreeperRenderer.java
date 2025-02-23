package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GhostCreeperRenderer extends CreeperRenderer {

	public GhostCreeperRenderer(Context p_173958_) {
		super(p_173958_);
	}
	
	@Override
	protected RenderType getRenderType(CreeperRenderState p_369777_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
		return RenderType.entityTranslucentEmissive(ResourceLocation.withDefaultNamespace("textures/entity/creeper/creeper.png"));
	}
	
	@Override
	protected int getModelTint(CreeperRenderState p_361319_) {
		return ARGB.color(128, 255, 255, 255);
	}

}

package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import io.github.xsmalldeadguyx.elementalcreepers.client.model.FriendlyCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderstate.FriendlyCreeperRenderState;
import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.FriendlyCreeper;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FriendlyCreeperRenderer extends MobRenderer<FriendlyCreeper, FriendlyCreeperRenderState, FriendlyCreeperModel> {
	private static ResourceLocation NORMAL_TEXTURE = ResourceLocation.tryBuild(ElementalCreepers.MODID,
			"textures/entity/friendlycreeper0.png");
	private static ResourceLocation TAME_TEXTURE = ResourceLocation.tryBuild(ElementalCreepers.MODID,
			"textures/entity/friendlycreeper1.png");

	public FriendlyCreeperRenderer(EntityRendererProvider.Context p_173958_) {
		super(p_173958_, new FriendlyCreeperModel(p_173958_.bakeLayer(ModelLayers.CREEPER)), 0.5F);
		this.addLayer(new FriendlyCreeperPowerLayer(this, p_173958_.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(FriendlyCreeperRenderState p_114482_) {
		return p_114482_.isTame ? TAME_TEXTURE : NORMAL_TEXTURE;
	}

	@Override
    public FriendlyCreeperRenderState createRenderState() {
        return new FriendlyCreeperRenderState();
    }

	@Override
    public void extractRenderState(FriendlyCreeper p_362379_, FriendlyCreeperRenderState p_361845_, float p_368662_) {
        super.extractRenderState(p_362379_, p_361845_, p_368662_);
        p_361845_.isSitting = p_362379_.isInSittingPose();
        p_361845_.isTame = p_362379_.isTame();
        p_361845_.swelling = p_362379_.getSwelling(p_368662_);
        p_361845_.isPowered = p_362379_.isPowered();
    }

}

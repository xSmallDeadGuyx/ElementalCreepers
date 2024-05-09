package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import io.github.xsmalldeadguyx.elementalcreepers.client.model.FriendlyCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.FriendlyCreeper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FriendlyCreeperPowerLayer extends EnergySwirlLayer<FriendlyCreeper, FriendlyCreeperModel<FriendlyCreeper>> {
	private static final ResourceLocation POWER_LOCATION = new ResourceLocation(
			"textures/entity/creeper/creeper_armor.png");
	private final FriendlyCreeperModel<FriendlyCreeper> model;

	public FriendlyCreeperPowerLayer(RenderLayerParent<FriendlyCreeper, FriendlyCreeperModel<FriendlyCreeper>> p_174471_,
			EntityModelSet p_174472_) {
		super(p_174471_);
		this.model = new FriendlyCreeperModel<>(p_174472_.bakeLayer(ModelLayers.CREEPER_ARMOR));
	}

	protected float xOffset(float p_116683_) {
		return p_116683_ * 0.01F;
	}

	protected ResourceLocation getTextureLocation() {
		return POWER_LOCATION;
	}

	protected EntityModel<FriendlyCreeper> model() {
		return this.model;
	}
}

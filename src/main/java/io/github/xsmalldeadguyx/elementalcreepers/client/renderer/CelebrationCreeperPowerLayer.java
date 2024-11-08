package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import io.github.xsmalldeadguyx.elementalcreepers.client.model.CelebrationCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.CelebrationCreeper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CelebrationCreeperPowerLayer extends EnergySwirlLayer<CelebrationCreeper, CelebrationCreeperModel<CelebrationCreeper>> {
	private static final ResourceLocation POWER_LOCATION = ResourceLocation.withDefaultNamespace(
			"textures/entity/creeper/creeper_armor.png");
	private final CelebrationCreeperModel<CelebrationCreeper> model;

	public CelebrationCreeperPowerLayer(RenderLayerParent<CelebrationCreeper, CelebrationCreeperModel<CelebrationCreeper>> p_174471_,
			EntityModelSet p_174472_) {
		super(p_174471_);
		this.model = new CelebrationCreeperModel<>(p_174472_.bakeLayer(ModelLayers.CREEPER_ARMOR));
	}

	protected float xOffset(float p_116683_) {
		return p_116683_ * 0.01F;
	}

	protected ResourceLocation getTextureLocation() {
		return POWER_LOCATION;
	}

	protected EntityModel<CelebrationCreeper> model() {
		return this.model;
	}
}

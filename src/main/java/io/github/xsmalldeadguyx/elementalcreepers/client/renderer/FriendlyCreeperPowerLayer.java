package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import io.github.xsmalldeadguyx.elementalcreepers.client.model.FriendlyCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.FriendlyCreeper;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FriendlyCreeperPowerLayer extends EnergyLayer<FriendlyCreeper, FriendlyCreeperModel<FriendlyCreeper>> {
	private static final ResourceLocation POWER_LOCATION = new ResourceLocation(
			"textures/entity/creeper/creeper_armor.png");
	private final FriendlyCreeperModel<FriendlyCreeper> model = new FriendlyCreeperModel<>(2.0F);

	public FriendlyCreeperPowerLayer(
			IEntityRenderer<FriendlyCreeper, FriendlyCreeperModel<FriendlyCreeper>> p_174471_) {
		super(p_174471_);
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

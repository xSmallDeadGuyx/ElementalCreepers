package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import io.github.xsmalldeadguyx.elementalcreepers.client.model.GhostCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.GhostCreeper;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GhostCreeperPowerLayer extends EnergyLayer<GhostCreeper, GhostCreeperModel<GhostCreeper>> {
	private static final ResourceLocation POWER_LOCATION = new ResourceLocation(
			"textures/entity/creeper/creeper_armor.png");
	private final GhostCreeperModel<GhostCreeper> model = new GhostCreeperModel<>(2.0F);

	public GhostCreeperPowerLayer(IEntityRenderer<GhostCreeper, GhostCreeperModel<GhostCreeper>> p_174471_) {
		super(p_174471_);
	}

	protected float xOffset(float p_116683_) {
		return p_116683_ * 0.01F;
	}

	protected ResourceLocation getTextureLocation() {
		return POWER_LOCATION;
	}

	protected EntityModel<GhostCreeper> model() {
		return this.model;
	}
}

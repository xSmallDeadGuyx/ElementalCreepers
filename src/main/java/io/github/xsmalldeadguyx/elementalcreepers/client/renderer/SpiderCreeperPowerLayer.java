package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import io.github.xsmalldeadguyx.elementalcreepers.client.model.SpiderCreeperModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiderCreeperPowerLayer extends EnergySwirlLayer<CreeperRenderState, SpiderCreeperModel> {
    private static final ResourceLocation POWER_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/creeper/creeper_armor.png");
    private final SpiderCreeperModel model;

    public SpiderCreeperPowerLayer(RenderLayerParent<CreeperRenderState, SpiderCreeperModel> p_174471_, EntityModelSet p_174472_) {
        super(p_174471_);
        this.model = new SpiderCreeperModel(p_174472_.bakeLayer(ModelLayers.CREEPER_ARMOR));
    }

    protected boolean isPowered(CreeperRenderState p_367950_) {
        return p_367950_.isPowered;
    }

    @Override
    protected float xOffset(float p_116683_) {
        return p_116683_ * 0.01F;
    }

    @Override
    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

    protected SpiderCreeperModel model() {
        return this.model;
    }
}

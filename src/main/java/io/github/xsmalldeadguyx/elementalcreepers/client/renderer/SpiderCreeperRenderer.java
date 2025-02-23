package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.xsmalldeadguyx.elementalcreepers.client.ElementalCreepersClient;
import io.github.xsmalldeadguyx.elementalcreepers.client.model.SpiderCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.SpiderCreeper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiderCreeperRenderer extends MobRenderer<SpiderCreeper, CreeperRenderState, SpiderCreeperModel> {
	public SpiderCreeperRenderer(EntityRendererProvider.Context p_173958_) {
		super(p_173958_, new SpiderCreeperModel(
				p_173958_.bakeLayer(ElementalCreepersClient.SPIDER_CREEPER_MODEL_LAYER_LOCATION)), 0.5F);
		this.addLayer(new SpiderCreeperPowerLayer(this, p_173958_.getModelSet()));
	}

	@Override
    protected void scale(CreeperRenderState p_366533_, PoseStack p_114047_) {
        float f = p_366533_.swelling;
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f *= f;
        f *= f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        p_114047_.scale(f2, f3, f2);
    }

	@Override
    protected float getWhiteOverlayProgress(CreeperRenderState p_364355_) {
        float f = p_364355_.swelling;
        return (int)(f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

	@Override
	public ResourceLocation getTextureLocation(CreeperRenderState p_114041_) {
		return ResourceLocation.tryBuild(ElementalCreepers.MODID, "textures/entity/spidercreeper.png");
	}

	@Override
    public CreeperRenderState createRenderState() {
        return new CreeperRenderState();
    }

	@Override
    public void extractRenderState(SpiderCreeper p_366316_, CreeperRenderState p_369319_, float p_368319_) {
        super.extractRenderState(p_366316_, p_369319_, p_368319_);
        p_369319_.swelling = p_366316_.getSwelling(p_368319_);
        p_369319_.isPowered = p_366316_.isPowered();
    }
}

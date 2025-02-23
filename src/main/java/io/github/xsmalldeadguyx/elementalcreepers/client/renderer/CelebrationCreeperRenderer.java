package io.github.xsmalldeadguyx.elementalcreepers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.xsmalldeadguyx.elementalcreepers.client.ElementalCreepersClient;
import io.github.xsmalldeadguyx.elementalcreepers.client.model.CelebrationCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.CelebrationCreeper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CelebrationCreeperRenderer extends MobRenderer<CelebrationCreeper, CreeperRenderState, CelebrationCreeperModel> {
    private static final ResourceLocation CREEPER_LOCATION = ResourceLocation.tryBuild(ElementalCreepers.MODID, "textures/entity/celebrationcreeper.png");

    public CelebrationCreeperRenderer(EntityRendererProvider.Context p_173958_) {
        super(p_173958_, new CelebrationCreeperModel(p_173958_.bakeLayer(ElementalCreepersClient.CELEBRATION_CREEPER_MODEL_LAYER_LOCATION)), 0.5F);
        this.addLayer(new CelebrationCreeperPowerLayer(this, p_173958_.getModelSet()));
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
    public ResourceLocation getTextureLocation(CreeperRenderState p_368131_) {
        return CREEPER_LOCATION;
    }

    @Override
    public CreeperRenderState createRenderState() {
        return new CreeperRenderState();
    }

    @Override
    public void extractRenderState(CelebrationCreeper p_366316_, CreeperRenderState p_369319_, float p_368319_) {
        super.extractRenderState(p_366316_, p_369319_, p_368319_);
        p_369319_.swelling = p_366316_.getSwelling(p_368319_);
        p_369319_.isPowered = p_366316_.isPowered();
    }
}

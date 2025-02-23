package io.github.xsmalldeadguyx.elementalcreepers.client.renderstate;

import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FriendlyCreeperRenderState extends CreeperRenderState {
    public boolean isSitting;
    public boolean isTame;
}

package io.github.xsmalldeadguyx.elementalcreepers.client.model;

import java.awt.Color;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GhostCreeperModel<T extends Entity> extends CreeperModel<T> {
	public GhostCreeperModel(ModelPart p_170524_) {
		super(p_170524_);
	}
	
	@Override
	public void renderToBuffer(PoseStack p_170625_, VertexConsumer p_170626_, int p_170627_, int p_170628_,
			int p_342246_) {
		Color c = new Color(p_342246_);
		float[] components = c.getColorComponents(null);
		Color translucent = new Color(components[0], components[1], components[2], 0.5f);
		
		// TODO Auto-generated method stub
		super.renderToBuffer(p_170625_, p_170626_, p_170627_, p_170628_, translucent.getRGB());
	}
}

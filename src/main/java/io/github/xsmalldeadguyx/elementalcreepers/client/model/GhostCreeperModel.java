package io.github.xsmalldeadguyx.elementalcreepers.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GhostCreeperModel<T extends Entity> extends CreeperModel<T> {
	public GhostCreeperModel() {
		super();
	}

	public GhostCreeperModel(float f) {
		super(f);
	}

	@Override
	public void renderToBuffer(MatrixStack p_170625_, IVertexBuilder p_170626_, int p_170627_, int p_170628_,
			float p_170629_, float p_170630_, float p_170631_, float p_170632_) {
		super.renderToBuffer(p_170625_, p_170626_, p_170627_, p_170628_, p_170629_, p_170630_, p_170631_,
				p_170632_ * 0.5f);
	}
}

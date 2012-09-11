package smalldeadguy.elementalcreepers;

import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelCreeper;
import net.minecraft.src.RenderLiving;

import org.lwjgl.opengl.GL11;

public class RenderGhostCreeper extends RenderLiving {
	private ModelBase field_27008_a = new ModelCreeper(2.0F);

	public RenderGhostCreeper() {
		super(new ModelCreeper(), 0.5F);
	}
	
	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
		super.doRenderLiving(par1EntityLiving, par2, par4, par6, par8, par9);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected void updateCreeperScale(EntityCreeper par1EntityCreeper, float par2) {
		float var4 = par1EntityCreeper.setCreeperFlashTime(par2);
		float var5 = 1.0F + MathHelper.sin(var4 * 100.0F) * var4 * 0.01F;

		if (var4 < 0.0F)
			var4 = 0.0F;

		if (var4 > 1.0F)
			var4 = 1.0F;

		var4 *= var4;
		var4 *= var4;
		float var6 = (1.0F + var4 * 0.4F) * var5;
		float var7 = (1.0F + var4 * 0.1F) / var5;
		GL11.glScalef(var6, var7, var6);
	}

	protected int updateCreeperColorMultiplier(EntityCreeper par1EntityCreeper, float par2, float par3) {
		float var5 = par1EntityCreeper.setCreeperFlashTime(par3);

		if ((int)(var5 * 10.0F) % 2 == 0)
			return 0;
		else {
			int var6 = (int)(var5 * 0.2F * 255.0F);

			if (var6 < 0)
				var6 = 0;

			if (var6 > 255)
				var6 = 255;

			short var7 = 255;
			short var8 = 255;
			short var9 = 255;
			return var6 << 24 | var7 << 16 | var8 << 8 | var9;
		}
	}

	protected int func_27006_a(EntityCreeper par1EntityCreeper, int par2, float par3) {
		if (par1EntityCreeper.getPowered()) {
			if (par2 == 1) {
				float var4 = (float)par1EntityCreeper.ticksExisted + par3;
				loadTexture("/armor/power.png");
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				float var5 = var4 * 0.01F;
				float var6 = var4 * 0.01F;
				GL11.glTranslatef(var5, var6, 0.0F);
				setRenderPassModel(field_27008_a);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_BLEND);
				float var7 = 0.5F;
				GL11.glColor4f(var7, var7, var7, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
				return 1;
			}

			if (par2 == 2) {
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}

		return -1;
	}

	protected int func_27007_b(EntityCreeper par1EntityCreeper, int par2, float par3) {
		return -1;
	}

	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {
		updateCreeperScale((EntityCreeper)par1EntityLiving, par2);
	}

	protected int getColorMultiplier(EntityLiving par1EntityLiving, float par2, float par3) {
		return updateCreeperColorMultiplier((EntityCreeper)par1EntityLiving, par2, par3);
	}

	protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3) {
		return func_27006_a((EntityCreeper)par1EntityLiving, par2, par3);
	}

	protected int inheritRenderPass(EntityLiving par1EntityLiving, int par2, float par3) {
		return func_27007_b((EntityCreeper)par1EntityLiving, par2, par3);
	}
}

package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class EC_GhostExplosion {
	public boolean isFlaming = false;
	private Random explosionRNG = new Random();
	private World worldObj;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Entity exploder;
	public float explosionSize;

	public EC_GhostExplosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9) {
		worldObj = par1World;
		exploder = par2Entity;
		explosionSize = par9;
		explosionX = par3;
		explosionY = par5;
		explosionZ = par7;
	}

	public void doExplosionA()
	{
		float var1 = explosionSize;
		byte var2 = 16;
		int var3;
		int var4;
		int var5;
		double var15;
		double var17;
		double var19;

		explosionSize *= 2.0F;
		var3 = MathHelper.floor_double(explosionX - (double)explosionSize - 1.0D);
		var4 = MathHelper.floor_double(explosionX + (double)explosionSize + 1.0D);
		var5 = MathHelper.floor_double(explosionY - (double)explosionSize - 1.0D);
		int var29 = MathHelper.floor_double(explosionY + (double)explosionSize + 1.0D);
		int var7 = MathHelper.floor_double(explosionZ - (double)explosionSize - 1.0D);
		int var30 = MathHelper.floor_double(explosionZ + (double)explosionSize + 1.0D);
		List var9 = worldObj.getEntitiesWithinAABBExcludingEntity(exploder, AxisAlignedBB.getBoundingBoxFromPool((double)var3, (double)var5, (double)var7, (double)var4, (double)var29, (double)var30));
		Vec3D var31 = Vec3D.createVector(explosionX, explosionY, explosionZ);

		for (int var11 = 0; var11 < var9.size(); var11++) {
			Entity var32 = (Entity)var9.get(var11);
			double var13 = var32.getDistance(explosionX, explosionY, explosionZ) / (double)explosionSize;

			if (var13 <= 1.0D) {
				var15 = var32.posX - explosionX;
				var17 = var32.posY - explosionY;
				var19 = var32.posZ - explosionZ;
				double var35 = (double)MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);
				var15 /= var35;
				var17 /= var35;
				var19 /= var35;
				double var34 = (double)worldObj.getBlockDensity(var31, var32.boundingBox);
				double var36 = (1.0D - var13) * var34;
				var32.attackEntityFrom(DamageSource.explosion, (int)((var36 * var36 + var36) / 2.0D * 8.0D * (double)explosionSize + 1.0D));
				var32.motionX += var15 * var36;
				var32.motionY += var17 * var36;
				var32.motionZ += var19 * var36;
			}
		}

		explosionSize = var1;
	}

	public void doExplosionB(boolean par1) {
		worldObj.playSoundEffect(explosionX, explosionY, explosionZ, "random.explode", 4.0F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		worldObj.spawnParticle("hugeexplosion", explosionX, explosionY, explosionZ, 0.0D, 0.0D, 0.0D);
		ArrayList var2 = new ArrayList();
		int var3;
		ChunkPosition var4;
		int var5;
		int var6;
		int var7;
		int var8;

		for (var3 = var2.size() - 1; var3 >= 0; var3--) {
			var4 = (ChunkPosition)var2.get(var3);
			var5 = var4.x;
			var6 = var4.y;
			var7 = var4.z;
			var8 = worldObj.getBlockId(var5, var6, var7);

			if (par1) {
				double var9 = (double)((float)var5 + worldObj.rand.nextFloat());
				double var11 = (double)((float)var6 + worldObj.rand.nextFloat());
				double var13 = (double)((float)var7 + worldObj.rand.nextFloat());
				double var15 = var9 - explosionX;
				double var17 = var11 - explosionY;
				double var19 = var13 - explosionZ;
				double var21 = (double)MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);
				var15 /= var21;
				var17 /= var21;
				var19 /= var21;
				double var23 = 0.5D / (var21 / (double)explosionSize + 0.1D);
				var23 *= (double)(worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3F);
				var15 *= var23;
				var17 *= var23;
				var19 *= var23;
				worldObj.spawnParticle("explode", (var9 + explosionX * 1.0D) / 2.0D, (var11 + explosionY * 1.0D) / 2.0D, (var13 + explosionZ * 1.0D) / 2.0D, var15, var17, var19);
				worldObj.spawnParticle("smoke", var9, var11, var13, var15, var17, var19);
			}

			if (var8 > 0) {
				Block.blocksList[var8].dropBlockAsItemWithChance(worldObj, var5, var6, var7, worldObj.getBlockMetadata(var5, var6, var7), 0.3F, 0);
				worldObj.setBlockWithNotify(var5, var6, var7, 0);
				Block.blocksList[var8].onBlockDestroyedByExplosion(worldObj, var5, var6, var7);
			}
		}
	}
}

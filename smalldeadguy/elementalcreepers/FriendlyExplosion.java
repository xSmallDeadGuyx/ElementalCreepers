package smalldeadguy.elementalcreepers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

public class FriendlyExplosion {
	private Random explosionRNG = new Random();
	private World worldObj;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Entity exploder;
	public float explosionSize;

	public FriendlyExplosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9) {
		worldObj = par1World;
		exploder = par2Entity;
		explosionSize = par9;
		explosionX = par3;
		explosionY = par5;
		explosionZ = par7;
	}

	/**
	 * Does the first part of explosion (destroy blocks)
	 */
	public void doExplosionA(EntityFriendlyCreeper me)
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
		List var9 = worldObj.getEntitiesWithinAABBExcludingEntity(exploder, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)var3, (double)var5, (double)var7, (double)var4, (double)var29, (double)var30));
		Vec3 var31 = Vec3.getVec3Pool().getVecFromPool(explosionX, explosionY, explosionZ);

		for (int var11 = 0; var11 < var9.size(); ++var11) {
			Entity var32 = (Entity)var9.get(var11);
			if(var32.equals(me) || (var32 instanceof EntityPlayer && ((EntityPlayer)var32).username.equalsIgnoreCase(me.getOwnerName()))) continue;
			
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
	}
}

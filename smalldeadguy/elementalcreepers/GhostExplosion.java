package smalldeadguy.elementalcreepers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class GhostExplosion extends Explosion {
	/** whether or not the explosion sets fire to blocks around it */
	public boolean isFlaming = false;

	/** whether or not this explosion spawns smoke particles */
	public boolean isSmoking = true;
	private int field_77289_h = 16;
	private Random explosionRNG = new Random();
	private World worldObj;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Entity exploder;
	public float explosionSize;

	private Map field_77288_k = new HashMap();

	public GhostExplosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9) {
		super(par1World, par2Entity, par3, par5, par7, par9);
		this.worldObj = par1World;
		this.exploder = par2Entity;
		this.explosionSize = par9;
		this.explosionX = par3;
		this.explosionY = par5;
		this.explosionZ = par7;
	}

	public void doExplosionA() {
		float f = this.explosionSize;
		HashSet hashset = new HashSet();
		int i;
		int j;
		int k;
		double d0;
		double d1;
		double d2;

		this.explosionSize *= 2.0F;
		i = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
		j = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
		k = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
		int l1 = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0D);
		int i2 = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0D);
		int j2 = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0D);
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getAABBPool().getAABB((double)i, (double)k, (double)i2, (double)j, (double)l1, (double)j2));
		Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.explosionX, this.explosionY, this.explosionZ);

		for(int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = (Entity)list.get(k2);

			double d7 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;

			if(d7 <= 1.0D) {
				d0 = entity.posX - this.explosionX;
				d1 = entity.posY + (double)entity.getEyeHeight() - this.explosionY;
				d2 = entity.posZ - this.explosionZ;
				double d8 = (double)MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);

				if(d8 != 0.0D) {
					d0 /= d8;
					d1 /= d8;
					d2 /= d8;
					double d9 = (double)this.worldObj.getBlockDensity(vec3, entity.boundingBox);
					double d10 = (1.0D - d7) * d9;
					entity.attackEntityFrom(DamageSource.setExplosionSource(this), (int)((d10 * d10 + d10) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D));
					double d11 = EnchantmentProtection.func_92092_a(entity, d10);
					entity.motionX += d0 * d11;
					entity.motionY += d1 * d11;
					entity.motionZ += d2 * d11;

					if(entity instanceof EntityPlayer)
						this.field_77288_k.put((EntityPlayer)entity, this.worldObj.getWorldVec3Pool().getVecFromPool(d0 * d10, d1 * d10, d2 * d10));
				}
			}
		}

		this.explosionSize = f;
	}

	/**
	 * Does the second part of the explosion (sound, particles, drop spawn)
	 */
	public void doExplosionB(boolean par1) {
		this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

		if(this.explosionSize >= 2.0F && this.isSmoking)
			this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
		else
			this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
	}

	public Map func_77277_b() {
		return this.field_77288_k;
	}

	public EntityLiving func_94613_c() {
		return (EntityLiving) (this.exploder instanceof EntityLiving || this.exploder instanceof EntityTNTPrimed ? this.exploder : null);
	}
}

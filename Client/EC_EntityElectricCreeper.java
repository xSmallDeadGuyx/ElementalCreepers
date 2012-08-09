package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EC_EntityElectricCreeper extends EC_EntityElementalCreeper {
	public EC_EntityElectricCreeper(World world) {
		super(world);
		texture = "/mob/electriccreeper.png";
	}

	@Override
	public void creeperEffect() {
		int radius = getPowered() ? (int) (mod_ElementalCreepers.electricCreeperRadius * 1.5F) : mod_ElementalCreepers.electricCreeperRadius;
		List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(radius, radius, radius));
		for(int j = 0; j < list.size(); j++)
		{
			EntityLiving entityliving = (EntityLiving)list.get(j);
			if(entityliving == null) {
				continue;
			}
			else if(entityliving instanceof EntityMob) {
				continue;
			}
			worldObj.spawnEntityInWorld(new EntityLightningBolt(worldObj, entityliving.posX, entityliving.posY, entityliving.posZ));
		}
		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		spawnExplosionParticle();
	}
}
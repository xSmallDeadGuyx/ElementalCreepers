package net.minecraft.src;

import java.util.Random;

public class EC_EntityCookieCreeper extends EC_EntityElementalCreeper {
	public EC_EntityCookieCreeper(World world) {
		super(world);
		texture = "/mob/cookiecreeper.png";
	}

	@Override
	public void creeperEffect() {
		int amount = getPowered() ? (int) (mod_ElementalCreepers.cookieCreeperAmount * 1.5F) : mod_ElementalCreepers.cookieCreeperAmount;
		for(int x = 0; x < amount; x++) {
			float f1 = 0.7F;
			double d = (double)(worldObj.rand.nextFloat() * f1) + (double)(1.0F + f1) * 0.5D;
			double d1 = (double)(worldObj.rand.nextFloat() * f1) + (double)(1.0F + f1) * 0.5D;
			double d2 = (double)(worldObj.rand.nextFloat() * f1) + (double)(1.0F + f1) * 0.5D;
			
			EntityItem entityitem = new EntityItem(worldObj, (double)posX + d, (double)posY + d1, (double)posZ + d2, new ItemStack(Item.cookie, 1));
			entityitem.delayBeforeCanPickup = 10;
			worldObj.spawnEntityInWorld(entityitem);
		}
		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		spawnExplosionParticle();
	}
}

package smalldeadguy.elementalcreepers;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityCookieCreeper extends EntityElementalCreeper {
	public EntityCookieCreeper(World world) {
		super(world);
	}

	protected ResourceLocation Texture;

	@Override
	public void creeperEffect() {
		int amount = getPowered() ? (int) (ElementalCreepers.cookieCreeperAmount * 1.5F)
				: ElementalCreepers.cookieCreeperAmount;
		for (int x = 0; x < amount; x++) {
			float f1 = 0.7F;
			double d = (double) (worldObj.rand.nextFloat() * f1) + (double) (1.0F + f1) * 0.5D;
			double d1 = (double) (worldObj.rand.nextFloat() * f1) + (double) (1.0F + f1) * 0.5D;
			double d2 = (double) (worldObj.rand.nextFloat() * f1) + (double) (1.0F + f1) * 0.5D;

			EntityItem entityitem = new EntityItem(worldObj, (double) posX + d, (double) posY + d1, (double) posZ + d2,
					new ItemStack(Item.getItemById(357), 1));
			entityitem.delayBeforeCanPickup = 10;
			worldObj.spawnEntityInWorld(entityitem);
		}
		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F,
				(1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		spawnExplosionParticle();
	}

	protected void setEntityTexture()
    {
        Texture = new ResourceLocation("ElementalCreepers:mob/cookiecreeper.png");
    }

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless
	 * you call Render.bindEntityTexture.
	 */
//	@Override
//	protected ResourceLocation getEntityTexture(Entity par1Entity) {
//		return Texture;
//	}
}

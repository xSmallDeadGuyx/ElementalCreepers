package smalldeadguy.elementalcreepers;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;


public class EntitySpiderCreeper extends EntityElementalCreeper {
	public EntitySpiderCreeper(World world) {
		super(world);
		texture = "/mob/spidercreeper.png";
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(20, new Byte((byte)0));
	}

	public boolean isBesideClimbableBlock() {
		return (this.dataWatcher.getWatchableObjectByte(20) & 1) != 0;
	}

	public void setBesideClimbableBlock(boolean par1) {
		byte b0 = this.dataWatcher.getWatchableObjectByte(20);

		if (par1)
			b0 = (byte)(b0 | 1);
		else
			b0 &= -2;

		this.dataWatcher.updateObject(20, Byte.valueOf(b0));
	}

	public boolean isOnLadder() {
		return this.isBesideClimbableBlock();
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(!this.worldObj.isRemote)
			this.setBesideClimbableBlock(this.isCollidedHorizontally);
	}

	@Override
	public void creeperEffect() {
		double radius = getPowered() ? (int) (ElementalCreepers.spiderCreeperRadius * 1.5F) : ElementalCreepers.spiderCreeperRadius;
		for(int x = (int) -radius - 1; x <= radius; x++) for(int y = (int) -radius - 1; y <= radius; y++) for(int z = (int) -radius - 1; z <= radius; z++)
			if(Block.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z) && Math.sqrt(Math.pow(x,  2) + Math.pow(y,  2) + Math.pow(z, 2)) <= radius)
				if(rand.nextInt(100) < 2)
					worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Block.web.blockID);

		List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getAABBPool().getAABB(posX - radius, posY - radius, posZ - radius, posX + radius, posY + radius, posZ + radius));

		byte b0 = 0;
		if(this.worldObj.difficultySetting == 1)
			b0 = 7;
		else if(this.worldObj.difficultySetting == 2)
			b0 = 10;
		else if(this.worldObj.difficultySetting == 3)
			b0 = 15;

		if(b0 > 0)
			for(Entity e : list)
				if(e instanceof EntityLiving) {
					EntityLiving el = (EntityLiving) e;
					if(el.getDistanceToEntity(this) <= radius)
						el.addPotionEffect(new PotionEffect(Potion.poison.id, b0 * 20, 0));
				}

		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		this.spawnExplosionParticle();
	}
}
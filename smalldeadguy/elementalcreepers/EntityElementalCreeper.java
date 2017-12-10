package smalldeadguy.elementalcreepers;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityElementalCreeper extends EntityCreeper {

	int timeSinceIgnited;
	int lastActiveTime;

	public EntityElementalCreeper(World par1World) {
		super(par1World);
	}
	
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if(worldObj.rand.nextInt(100) < ElementalCreepers.ghostCreeperChance && !worldObj.isRemote && !(this instanceof EntityGhostCreeper)) {
			EntityGhostCreeper gc = new EntityGhostCreeper(worldObj);
			gc.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			worldObj.spawnEntityInWorld(gc);
		}
	}

	public void onUpdate() {
		if(isEntityAlive()) {
			lastActiveTime = timeSinceIgnited;
			int var1 = getCreeperState();

			if(var1 > 0 && timeSinceIgnited == 0)
				worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);

			timeSinceIgnited += var1;

			if(timeSinceIgnited < 0)
				timeSinceIgnited = 0;

			if(timeSinceIgnited >= 30) {
				timeSinceIgnited = 30;

				if (!worldObj.isRemote) {
					creeperEffect();
					setDead();
				}
			}
		}

		super.onUpdate();
	}

	public void creeperEffect() {
		worldObj.createExplosion(this, posX, posY, posZ, getPowered() ? 6.0F : 3.0F, false);
	}
}

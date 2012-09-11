package smalldeadguy.elementalcreepers;

import java.util.List;
import java.util.Random;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class EntityIllusionCreeper extends EntityElementalCreeper {
	boolean split = false;
	
	public EntityIllusionCreeper(World world) {
		super(world);
		texture = "/mob/illusioncreeper.png";
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		EntityPlayer ep = worldObj.getClosestPlayerToEntity(this, 8.0F);
		if(!split && ep != null && !worldObj.isRemote) {
			createFakeCreepersAndLaunchSelf();
			split = true;
		}
	}

	private void createFakeCreepersAndLaunchSelf() {
		for(int i = 0; i < 4; i++) {
			EntityFakeIllusionCreeper entity = new EntityFakeIllusionCreeper(worldObj);
			if(entity != null) {
				entity.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
				entity.motionY = 0.5;
				worldObj.spawnEntityInWorld(entity);
			}
		}
		motionY = 0.5;
	}
}
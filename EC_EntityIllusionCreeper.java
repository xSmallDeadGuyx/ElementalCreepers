package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EC_EntityIllusionCreeper extends EC_EntityElementalCreeper {
	boolean split = false;
	
	public EC_EntityIllusionCreeper(World world) {
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
			EC_EntityFakeIllusionCreeper entity = new EC_EntityFakeIllusionCreeper(worldObj);
			if(entity != null) {
				entity.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
				entity.motionY = 0.5;
				worldObj.spawnEntityInWorld(entity);
			}
		}
		motionY = 0.5;
	}
}
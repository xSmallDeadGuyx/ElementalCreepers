package smalldeadguy.elementalcreepers;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;

public class EntityAIFriendlyCreeperSwell extends EntityAIBase {
	EntityFriendlyCreeper swellingCreeper;

	EntityLiving creeperAttackTarget;

	public EntityAIFriendlyCreeperSwell(EntityFriendlyCreeper par1EntityCreeper) {
		swellingCreeper = par1EntityCreeper;
		setMutexBits(1);
	}

	public boolean shouldExecute() {
		EntityLiving var1 = (EntityLiving) swellingCreeper.getAttackTarget();
		return swellingCreeper.getCreeperState() > 0 || var1 != null && swellingCreeper.getDistanceSqToEntity(var1) < 9.0D;
	}

	public void startExecuting() {
		//swellingCreeper.getNavigator().clearPathEntity();
		creeperAttackTarget = (EntityLiving) swellingCreeper.getAttackTarget();
	}

	public void resetTask() {
		creeperAttackTarget = null;
	}

	public void updateTask() {
		creeperAttackTarget = (EntityLiving) swellingCreeper.getAttackTarget();
		if(creeperAttackTarget == null)
			swellingCreeper.setCreeperState(-1);
		else if(swellingCreeper.getDistanceSqToEntity(creeperAttackTarget) > 49.0D)
			swellingCreeper.setCreeperState(-1);
		else if(!swellingCreeper.getEntitySenses().canSee(creeperAttackTarget))
			swellingCreeper.setCreeperState(-1);
		else
			swellingCreeper.setCreeperState(1);
	}
}

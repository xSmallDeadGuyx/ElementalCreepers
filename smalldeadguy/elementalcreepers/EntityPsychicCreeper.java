package ElementalCreepers.smalldeadguy.elementalcreepers;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPsychicCreeper extends EntityElementalCreeper {
	public EntityPsychicCreeper(World world) {
		super(world);
		texture = "/mob/psychiccreeper.png";
	}

	@Override
	public void creeperEffect() {
		float power = getPowered() ? (ElementalCreepers.psychicCreeperPower * 1.5F) : ElementalCreepers.psychicCreeperPower;
		createPsychicCreeperLauncher(this, posX, posY, posZ, power);
	}
	
	public PsychicCreeperLauncher createPsychicCreeperLauncher(Entity entity, double d, double d1, double d2, float f) {
		return newPsychicCreeperLauncher(entity, d, d1, d2, f, false);
	}

	public PsychicCreeperLauncher newPsychicCreeperLauncher(Entity entity, double d, double d1, double d2, float f, boolean flag) {
		PsychicCreeperLauncher explosion = new PsychicCreeperLauncher(worldObj, entity, d, d1, d2, f);
		explosion.doExplosionA();
		explosion.doExplosionB(true);
		return explosion;
	}
}
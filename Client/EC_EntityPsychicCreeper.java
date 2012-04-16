package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EC_EntityPsychicCreeper extends EC_EntityElementalCreeper {
	public EC_EntityPsychicCreeper(World world) {
		super(world);
		texture = "/mob/psychiccreeper.png";
	}

	@Override
	public void creeperEffect() {
		float power = getPowered() ? (mod_ElementalCreepers.psychicCreeperPower * 1.5F) : mod_ElementalCreepers.psychicCreeperPower;
		createPsychicCreeperLauncher(this, posX, posY, posZ, power);
	}
	
	public EC_PsychicCreeperLauncher createPsychicCreeperLauncher(Entity entity, double d, double d1, double d2, float f) {
		return newPsychicCreeperLauncher(entity, d, d1, d2, f, false);
	}

	public EC_PsychicCreeperLauncher newPsychicCreeperLauncher(Entity entity, double d, double d1, double d2, float f, boolean flag) {
		EC_PsychicCreeperLauncher explosion = new EC_PsychicCreeperLauncher(worldObj, entity, d, d1, d2, f);
		explosion.doExplosionA();
		explosion.doExplosionB(true);
		return explosion;
	}
}
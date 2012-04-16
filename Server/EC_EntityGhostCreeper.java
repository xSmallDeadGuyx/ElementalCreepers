package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EC_EntityGhostCreeper extends EC_EntityElementalCreeper {
	public EC_EntityGhostCreeper(World world) {
		super(world);
		texture = "/mob/creeper.png";
	}

	@Override
	public void creeperEffect() {
		float radius = getPowered() ? mod_ElementalCreepers.ghostCreeperRadius * 1.5F : mod_ElementalCreepers.ghostCreeperRadius;
		createGhostExplosion(this, posX, posY, posZ, radius);
	}
	
	public EC_GhostExplosion createGhostExplosion(Entity entity, double d, double d1, double d2, float f) {
		return newGhostExplosion(entity, d, d1, d2, f, false);
	}

	public EC_GhostExplosion newGhostExplosion(Entity entity, double d, double d1, double d2, float f, boolean flag) {
		EC_GhostExplosion explosion = new EC_GhostExplosion(worldObj, entity, d, d1, d2, f);
		explosion.isFlaming = flag;
		explosion.doExplosionA();
		explosion.doExplosionB(true);
		return explosion;
	}
}

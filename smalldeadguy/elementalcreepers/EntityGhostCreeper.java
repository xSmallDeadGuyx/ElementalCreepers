package smalldeadguy.elementalcreepers;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;


public class EntityGhostCreeper extends EntityElementalCreeper {
	public EntityGhostCreeper(World world) {
		super(world);
//		texture = "/mob/creeper.png";
	}

	@Override
	public void creeperEffect() {
		float radius = getPowered() ? ElementalCreepers.ghostCreeperRadius * 1.5F : ElementalCreepers.ghostCreeperRadius;
		createGhostExplosion(this, posX, posY, posZ, radius);
	}
	
	public GhostExplosion createGhostExplosion(Entity entity, double d, double d1, double d2, float f) {
		return newGhostExplosion(entity, d, d1, d2, f, false);
	}

	public GhostExplosion newGhostExplosion(Entity entity, double d, double d1, double d2, float f, boolean flag) {
		GhostExplosion explosion = new GhostExplosion(worldObj, entity, d, d1, d2, f);
		explosion.isFlaming = flag;
		explosion.doExplosionA();
		explosion.doExplosionB(true);
		return explosion;
	}
}

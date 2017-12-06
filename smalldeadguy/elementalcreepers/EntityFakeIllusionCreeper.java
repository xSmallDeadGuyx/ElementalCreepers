package ElementalCreepers.smalldeadguy.elementalcreepers;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;

public class EntityFakeIllusionCreeper extends EntityElementalCreeper {
	public EntityFakeIllusionCreeper(World world) {
		super(world);
		texture = "/mob/illusioncreeper.png";
	}

	@Override
	public void creeperEffect() {
		spawnExplosionParticle();
	}
}
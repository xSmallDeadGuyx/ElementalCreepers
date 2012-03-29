package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EC_EntityFakeIllusionCreeper extends EC_EntityElementalCreeper {
	public EC_EntityFakeIllusionCreeper(World world) {
		super(world);
		texture = "/mob/illusioncreeper.png";
	}

	@Override
	public void creeperEffect() {
		spawnExplosionParticle();
	}
}
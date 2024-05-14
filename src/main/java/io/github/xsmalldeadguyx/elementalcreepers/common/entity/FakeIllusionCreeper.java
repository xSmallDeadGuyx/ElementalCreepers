package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class FakeIllusionCreeper extends ElementalCreeper {

	public FakeIllusionCreeper(EntityType<? extends CreeperEntity> type, World level) {
		super(type, level);
	}

	@Override
	public void creeperEffect() {
		handleNetworkedExplosionEffects(5.d, SoundEvents.GENERIC_EXTINGUISH_FIRE);
	}
}
package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;

public class FakeIllusionCreeper extends ElementalCreeper {

	public FakeIllusionCreeper(EntityType<? extends Creeper> type, Level level) {
		super(type, level);
	}

	@Override
	public void creeperEffect() {
		handleNetworkedExplosionEffects(5.d, SoundEvents.GENERIC_EXTINGUISH_FIRE);
	}
}
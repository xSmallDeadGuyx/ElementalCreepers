package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import java.util.Map;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import io.github.xsmalldeadguyx.elementalcreepers.common.misc.EntityOnlyExplosion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PsychicCreeper extends ElementalCreeper {

	public PsychicCreeper(EntityType<? extends CreeperEntity> type, World level) {
		super(type, level);
	}

	@Override
	public void creeperEffect() {
		double radius = Config.psychicCreeperExplosionRadius;
		if (this.isPowered()) {
			radius *= 1.5d;
		}
		Map<PlayerEntity, Vector3d> hitPlayers = EntityOnlyExplosion.explodeAt(this.level, this, this.getX(),
				this.getY(), this.getZ(), radius, 0, Config.psychicCreeperLaunchMultiplier);

		handleNetworkedExplosionEffects(radius, hitPlayers, SoundEvents.ENDER_PEARL_THROW);
	}
}
package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import java.util.Map;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import io.github.xsmalldeadguyx.elementalcreepers.common.misc.EntityOnlyExplosion;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GhostCreeper extends ElementalCreeper {

	public GhostCreeper(EntityType<? extends Creeper> type, Level level) {
		super(type, level);
	}

	@Override
	public void creeperEffect() {
		double radius = Config.ghostCreeperExplosionRadius;
		if (this.isPowered()) {
			radius *= 1.5d;
		}

		Map<Player, Vec3> hitPlayers = EntityOnlyExplosion.explodeAt(this.level, this, this.getX(), this.getY(),
				this.getZ(), Config.ghostCreeperExplosionRadius, Config.ghostCreeperDamageMultiplier, 0);

		handleNetworkedExplosionEffects(radius, hitPlayers, SoundEvents.GENERIC_EXPLODE);
	}
}
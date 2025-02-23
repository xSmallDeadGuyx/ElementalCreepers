package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import java.util.List;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ElectricCreeper extends ElementalCreeper {

	public ElectricCreeper(EntityType<? extends Creeper> type, Level level) {
		super(type, level);

	}

	@Override
	public void creeperEffect() {
		double radius = Config.electricCreeperExplosionRadius;
		if (this.isPowered()) {
			radius *= 1.5;
		}

		List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class,
				AABB.ofSize(this.position(), radius, radius, radius));

		for (LivingEntity entity : entities) {
			if (entity == this) {
				continue;
			}

			LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(this.level(), EntitySpawnReason.TRIGGERED);
			if (bolt != null) {
				bolt.moveTo(Vec3.atBottomCenterOf(entity.blockPosition()));
				this.level().addFreshEntity(bolt);
			}
		}

		handleNetworkedExplosionEffects(radius, SoundEvents.LIGHTNING_BOLT_THUNDER);
	}
}
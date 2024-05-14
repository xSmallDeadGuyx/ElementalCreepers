package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import java.util.List;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ElectricCreeper extends ElementalCreeper {

	public ElectricCreeper(EntityType<? extends CreeperEntity> type, World level) {
		super(type, level);

	}

	@Override
	public void creeperEffect() {
		double radius = Config.electricCreeperExplosionRadius;
		if (this.isPowered()) {
			radius *= 1.5;
		}

		List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class,
				AxisAlignedBB.ofSize(radius, radius, radius).move(this.position()));

		for (LivingEntity entity : entities) {
			if (entity == this) {
				continue;
			}

			LightningBoltEntity bolt = EntityType.LIGHTNING_BOLT.create(this.level);
			if (bolt != null) {
				bolt.moveTo(Vector3d.atBottomCenterOf(entity.blockPosition()));
				this.level.addFreshEntity(bolt);
			}
		}

		handleNetworkedExplosionEffects(radius, SoundEvents.LIGHTNING_BOLT_THUNDER);
	}
}
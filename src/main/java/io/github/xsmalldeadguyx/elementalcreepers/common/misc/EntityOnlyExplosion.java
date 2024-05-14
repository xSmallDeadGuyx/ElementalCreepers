package io.github.xsmalldeadguyx.elementalcreepers.common.misc;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityOnlyExplosion {
	public static Map<Player, Vec3> explodeAt(Level level, Entity source, double x, double y, double z, double radius,
			double damageMulti, double launchMulti) {
		double diameter = radius * 2;
		int k1 = Mth.floor(x - diameter - 1.0D);
		int l1 = Mth.floor(x + diameter + 1.0D);
		int i2 = Mth.floor(y - diameter - 1.0D);
		int i1 = Mth.floor(y + diameter + 1.0D);
		int j2 = Mth.floor(z - diameter - 1.0D);
		int j1 = Mth.floor(z + diameter + 1.0D);
		List<Entity> list = level.getEntities(source,
				new AABB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		Vec3 vec3 = new Vec3(x, y, z);

		Map<Player, Vec3> hitPlayers = Maps.newHashMap();

		for (Entity entity : list) {
			if (source instanceof TamableAnimal) {
				TamableAnimal tamable = (TamableAnimal) source;
				if (tamable.isTame()) {
					// Don't harm our owner or players which cannot be harmed by our owner.
					LivingEntity owner = tamable.getOwner();
					if (owner == entity) {
						continue;
					}

					if (owner instanceof Player && entity instanceof Player) {
						if (!((Player) owner).canHarmPlayer((Player) entity)) {
							continue;
						}
					}
				}
			}

			if (!entity.ignoreExplosion()) {
				double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / diameter;
				if (d12 <= 1.0D) {
					double d5 = entity.getX() - x;
					double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - y;
					double d9 = entity.getZ() - z;
					double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
					if (d13 != 0.0D) {
						d5 /= d13;
						d7 /= d13;
						d9 /= d13;
						double d14 = (double) Explosion.getSeenPercent(vec3, entity);
						double d10 = (1.0D - d12) * d14;
						if (damageMulti > 0) {
							entity.hurt(
									DamageSource
											.explosion(source instanceof LivingEntity ? (LivingEntity) source : null),
									(float) ((int) (damageMulti
											* ((d10 * d10 + d10) / 2.0D * 7.0D * diameter + 1.0D))));
						}
						double d11;
						if (entity instanceof LivingEntity) {
							LivingEntity livingentity = (LivingEntity) entity;
							d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener(livingentity, d10);
						} else {
							d11 = d10;
						}

						d5 *= d11 * launchMulti;
						if (launchMulti > 1) {
							d7 = Math.min(0.5, d7) * d11 * launchMulti;
						} else {
							d7 *= d11 * launchMulti;
						}
						d9 *= d11 * launchMulti;
						Vec3 vec31 = new Vec3(d5, d7, d9);
						entity.setDeltaMovement(entity.getDeltaMovement().add(vec31));
						if (entity instanceof Player) {
							Player player = (Player) entity;
							if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
								hitPlayers.put(player, vec31);
							}
						}
					}
				}
			}
		}

		return hitPlayers;
	}
}

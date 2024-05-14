package io.github.xsmalldeadguyx.elementalcreepers.common.misc;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityOnlyExplosion {
	public static Map<PlayerEntity, Vector3d> explodeAt(World World, Entity source, double x, double y, double z, double radius,
			double damageMulti, double launchMulti) {
		double diameter = radius * 2;
		int k1 = MathHelper.floor(x - diameter - 1.0D);
		int l1 = MathHelper.floor(x + diameter + 1.0D);
		int i2 = MathHelper.floor(y - diameter - 1.0D);
		int i1 = MathHelper.floor(y + diameter + 1.0D);
		int j2 = MathHelper.floor(z - diameter - 1.0D);
		int j1 = MathHelper.floor(z + diameter + 1.0D);
		List<Entity> list = World.getEntities(source,
				new AxisAlignedBB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		Vector3d Vector3d = new Vector3d(x, y, z);

		Map<PlayerEntity, Vector3d> hitPlayers = Maps.newHashMap();

		for (Entity entity : list) {
			if (source instanceof TameableEntity) {
				TameableEntity tamable = (TameableEntity) source;
				if (tamable.isTame()) {
					// Don't harm our owner or players which cannot be harmed by our owner.
					LivingEntity owner = tamable.getOwner();
					if (owner == entity) {
						continue;
					}

					if (owner instanceof PlayerEntity && entity instanceof PlayerEntity) {
						if (!((PlayerEntity) owner).canHarmPlayer((PlayerEntity) entity)) {
							continue;
						}
					}
				}
			}

			if (!entity.ignoreExplosion()) {
				double d12 = Math.sqrt(entity.distanceToSqr(Vector3d)) / diameter;
				if (d12 <= 1.0D) {
					double d5 = entity.getX() - x;
					double d7 = entity.getEyeY() - y;
					double d9 = entity.getZ() - z;
					double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
					if (d13 != 0.0D) {
						d5 /= d13;
						d7 /= d13;
						d9 /= d13;
						double d14 = (double) Explosion.getSeenPercent(Vector3d, entity);
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
						Vector3d Vector3d1 = new Vector3d(d5, d7, d9);
						entity.setDeltaMovement(entity.getDeltaMovement().add(Vector3d1));
						if (entity instanceof PlayerEntity) {
							PlayerEntity PlayerEntity = (PlayerEntity) entity;
							if (!PlayerEntity.isSpectator() && (!PlayerEntity.isCreative() || !PlayerEntity.abilities.flying)) {
								hitPlayers.put(PlayerEntity, Vector3d1);
							}
						}
					}
				}
			}
		}

		return hitPlayers;
	}
}

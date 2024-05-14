package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ElementalCreeper extends Creeper {

	protected int oldSwell;
	protected int swell;
	protected int maxSwell = 30;

	public ElementalCreeper(EntityType<? extends Creeper> type, Level level) {
		super(type, level);
	}

	public boolean causeFallDamage(float p_149687_, float p_149688_, DamageSource source) {
		boolean flag = super.causeFallDamage(p_149687_, p_149688_, source);
		this.swell += (int) (p_149687_ * 1.5F);
		if (this.swell > this.maxSwell - 5) {
			this.swell = this.maxSwell - 5;
		}

		return flag;
	}

	public float getSwelling(float lerp) {
		return Mth.lerp(lerp, (float) this.oldSwell, (float) this.swell) / (float) (this.maxSwell - 2);
	}

	public void addAdditionalSaveData(CompoundTag p_32304_) {
		super.addAdditionalSaveData(p_32304_);
		p_32304_.putShort("Fuse", (short) this.maxSwell);
	}

	public void readAdditionalSaveData(CompoundTag p_32296_) {
		super.readAdditionalSaveData(p_32296_);
		if (p_32296_.contains("Fuse", 99)) {
			this.maxSwell = p_32296_.getShort("Fuse");
		}
	}

	public void tick() {
		if (this.isAlive()) {
			this.oldSwell = this.swell;
			if (this.isIgnited()) {
				this.setSwellDir(1);
			}

			int i = this.getSwellDir();
			if (i > 0 && this.swell == 0) {
				this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
				this.gameEvent(GameEvent.PRIME_FUSE);
			}

			this.swell += i;
			if (this.swell < 0) {
				this.swell = 0;
			}

			Level level = this.level;
			if (this.swell >= this.maxSwell) {
				this.swell = this.maxSwell;
				if (!level.isClientSide()) {
					this.dead = true;
					creeperEffect();
					this.discard();
				}

			}
		}

		super.tick();
	}

	protected void creeperEffect() {
		// float f = this.isPowered() ? 2.0F : 1.0F;
		// this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)
		// this.explosionRadius * f,
		// Level.ExplosionInteraction.MOB);
		// this.spawnLingeringCloud();
	}

	protected void spawnLingeringCloud() {
		Collection<MobEffectInstance> collection = this.getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
			areaeffectcloud.setRadius(2.5F);
			areaeffectcloud.setRadiusOnUse(-0.5F);
			areaeffectcloud.setWaitTime(10);
			areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
			areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());

			for (MobEffectInstance mobeffectinstance : collection) {
				areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
			}

			this.level.addFreshEntity(areaeffectcloud);
		}
	}

	protected void handleNetworkedExplosionEffects(double radius, SoundEvent soundEvent) {
		handleNetworkedExplosionEffects(radius, null, soundEvent);
	}

	protected void handleNetworkedExplosionEffects(double radius, @Nullable Map<Player, Vec3> hitPlayers,
			SoundEvent soundEvent) {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();

		Level level = this.level;
		if (this.level.isClientSide()) {
			this.level.playLocalSound(x, y, z, soundEvent, SoundSource.BLOCKS, 4.0F,
					(1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.2F) * 0.7F, false);
		}

		if (this.level instanceof ServerLevel) {
			ServerLevel serverLevel = (ServerLevel) this.level;
			for (ServerPlayer serverPlayer : serverLevel.players()) {
				if (serverPlayer.distanceToSqr(x, y, z) < 4096.0D) {
					serverPlayer.connection.send(new ClientboundExplodePacket(x, y, z, (float) radius,
							new ArrayList<BlockPos>(), hitPlayers != null ? hitPlayers.get(serverPlayer) : Vec3.ZERO));
				}
			}
		}

		this.spawnLingeringCloud();
	}

	@Override
	public void die(DamageSource p_21014_) {
		super.die(p_21014_);

		Level level = this.level;
		if (level instanceof ServerLevel && level.random.nextDouble() < Config.ghostCreeperSpawnChance
				&& !(this instanceof GhostCreeper)) {
			ElementalCreeper ghost = ElementalCreepers.GHOST_CREEPER.get().create(this.level);
			if (ghost != null) {
				ghost.moveTo(this.blockPosition(), this.getYRot(), this.getXRot());
				this.level.addFreshEntity(ghost);
			}
		}
	}
}

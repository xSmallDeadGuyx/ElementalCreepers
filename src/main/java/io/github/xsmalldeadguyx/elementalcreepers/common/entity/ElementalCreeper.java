package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SExplosionPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ElementalCreeper extends CreeperEntity {

	protected int oldSwell;
	protected int swell;
	protected int maxSwell = 30;

	public ElementalCreeper(EntityType<? extends CreeperEntity> type, World level) {
		super(type, level);
	}

	@Override
	public boolean causeFallDamage(float p_149687_, float p_149688_) {
		boolean flag = super.causeFallDamage(p_149687_, p_149688_);
		this.swell += (int) (p_149687_ * 1.5F);
		if (this.swell > this.maxSwell - 5) {
			this.swell = this.maxSwell - 5;
		}

		return flag;
	}

	@OnlyIn(Dist.CLIENT)
	public float getSwelling(float p_70831_1_) {
		return MathHelper.lerp(p_70831_1_, (float) this.oldSwell, (float) this.swell) / (float) (this.maxSwell - 2);
	}

	public void addAdditionalSaveData(CompoundNBT p_32304_) {
		super.addAdditionalSaveData(p_32304_);
		p_32304_.putShort("Fuse", (short) this.maxSwell);
	}

	public void readAdditionalSaveData(CompoundNBT p_32296_) {
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
			}

			this.swell += i;
			if (this.swell < 0) {
				this.swell = 0;
			}

			World level = this.level;
			if (this.swell >= this.maxSwell) {
				this.swell = this.maxSwell;
				if (!level.isClientSide()) {
					this.dead = true;
					creeperEffect();
					this.remove();
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
		Collection<EffectInstance> collection = this.getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloudEntity areaeffectcloud = new AreaEffectCloudEntity(this.level, this.getX(), this.getY(),
					this.getZ());
			areaeffectcloud.setRadius(2.5F);
			areaeffectcloud.setRadiusOnUse(-0.5F);
			areaeffectcloud.setWaitTime(10);
			areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
			areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());

			for (EffectInstance mobeffectinstance : collection) {
				areaeffectcloud.addEffect(new EffectInstance(mobeffectinstance));
			}

			this.level.addFreshEntity(areaeffectcloud);
		}
	}

	protected void handleNetworkedExplosionEffects(double radius, SoundEvent soundEvent) {
		handleNetworkedExplosionEffects(radius, null, soundEvent);
	}

	protected void handleNetworkedExplosionEffects(double radius, @Nullable Map<PlayerEntity, Vector3d> hitPlayers,
			SoundEvent soundEvent) {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();

		World level = this.level;
		if (this.level.isClientSide()) {
			this.level.playLocalSound(x, y, z, soundEvent, SoundCategory.BLOCKS, 4.0F,
					(1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.2F) * 0.7F, false);
		}

		if (this.level instanceof ServerWorld) {
			ServerWorld serverLevel = (ServerWorld) this.level;
			for (ServerPlayerEntity serverPlayer : serverLevel.players()) {
				if (serverPlayer.distanceToSqr(x, y, z) < 4096.0D) {
					serverPlayer.connection
							.send(new SExplosionPacket(x, y, z, (float) radius, new ArrayList<BlockPos>(),
									hitPlayers != null ? hitPlayers.get(serverPlayer) : Vector3d.ZERO));
				}
			}
		}

		this.spawnLingeringCloud();
	}

	@Override
	public void die(DamageSource p_21014_) {
		super.die(p_21014_);

		World level = this.level;
		if (level instanceof ServerWorld && level.random.nextDouble() < Config.ghostCreeperSpawnChance
				&& !(this instanceof GhostCreeper)) {
			ElementalCreeper ghost = ElementalCreepers.GHOST_CREEPER.get().create(this.level);
			if (ghost != null) {
				ghost.moveTo(this.blockPosition(), this.yRot, this.xRot);
				this.level.addFreshEntity(ghost);
			}
		}
	}
}

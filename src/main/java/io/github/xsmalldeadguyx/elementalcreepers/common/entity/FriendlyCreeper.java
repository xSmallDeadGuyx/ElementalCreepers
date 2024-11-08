package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import io.github.xsmalldeadguyx.elementalcreepers.common.misc.EntityOnlyExplosion;
import io.github.xsmalldeadguyx.elementalcreepers.common.misc.FriendlyCreeperSwellGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class FriendlyCreeper extends TamableAnimal implements NeutralMob, PowerableMob {

	private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData
			.defineId(FriendlyCreeper.class, EntityDataSerializers.INT);
	private static final float START_HEALTH = 8.0F;
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

	private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(FriendlyCreeper.class,
			EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(FriendlyCreeper.class,
			EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(FriendlyCreeper.class,
			EntityDataSerializers.BOOLEAN);
	private int oldSwell;
	private int swell;
	private int maxSwell = 30;
	private int cooldown;
	private int maxCooldown = 80;
	private int explosionRadius = 3;

	@Nullable
	private UUID persistentAngerTarget;

	public FriendlyCreeper(EntityType<? extends FriendlyCreeper> p_30369_, Level p_30370_) {
		super(p_30369_, p_30370_);
		this.setTame(false, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(3, new FriendlyCreeperSwellGoal(this));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Ocelot.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Cat.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
		this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(4,
				new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Creeper.createAttributes().add(Attributes.MAX_HEALTH, START_HEALTH);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder p_332186_) {
		super.defineSynchedData(p_332186_);
		p_332186_.define(DATA_REMAINING_ANGER_TIME, 0);
		p_332186_.define(DATA_SWELL_DIR, -1);
		p_332186_.define(DATA_IS_POWERED, false);
		p_332186_.define(DATA_IS_IGNITED, false);
	}

	public void addAdditionalSaveData(CompoundTag p_30418_) {
		super.addAdditionalSaveData(p_30418_);
		this.addPersistentAngerSaveData(p_30418_);
		if (this.entityData.get(DATA_IS_POWERED)) {
			p_30418_.putBoolean("powered", true);
		}

		p_30418_.putShort("Fuse", (short) this.maxSwell);
		p_30418_.putByte("ExplosionRadius", (byte) this.explosionRadius);
		p_30418_.putBoolean("ignited", this.isIgnited());
	}

	public void readAdditionalSaveData(CompoundTag p_30402_) {
		super.readAdditionalSaveData(p_30402_);
		this.readPersistentAngerSaveData(this.level(), p_30402_);
		this.entityData.set(DATA_IS_POWERED, p_30402_.getBoolean("powered"));
		if (p_30402_.contains("Fuse", 99)) {
			this.maxSwell = p_30402_.getShort("Fuse");
		}

		if (p_30402_.contains("ExplosionRadius", 99)) {
			this.explosionRadius = p_30402_.getByte("ExplosionRadius");
		}

		if (p_30402_.getBoolean("ignited")) {
			this.ignite();
		}
	}

	public void tick() {
		if (this.isAlive()) {
			this.oldSwell = this.swell;
			if (this.cooldown > 0) {
				this.cooldown -= 1;

				if (this.cooldown < 0) {
					this.cooldown = 0;
				}
			} else {
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

				if (this.swell >= this.maxSwell) {
					this.swell = 0;
					this.cooldown = this.maxCooldown;
					this.setSwellDir(-1);
					this.explodeCreeper();
				}
			}
		}

		super.tick();
	}

	public int getMaxFallDistance() {
		return this.getTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
	}

	public boolean causeFallDamage(float p_149687_, float p_149688_, DamageSource p_149689_) {
		boolean flag = super.causeFallDamage(p_149687_, p_149688_, p_149689_);
		this.swell += (int) (p_149687_ * 1.5F);
		if (this.swell > this.maxSwell - 5) {
			this.swell = this.maxSwell - 5;
		}

		return flag;
	}

	protected SoundEvent getHurtSound(DamageSource p_30424_) {
		return SoundEvents.CREEPER_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.CREEPER_DEATH;
	}

	public boolean isPowered() {
		return this.entityData.get(DATA_IS_POWERED);
	}

	public float getSwelling(float p_32321_) {
		return Mth.lerp(p_32321_, (float) this.oldSwell, (float) this.swell) / (float) (this.maxSwell - 2);
	}

	public int getSwellDir() {
		return this.entityData.get(DATA_SWELL_DIR);
	}

	public void setSwellDir(int p_32284_) {
		this.entityData.set(DATA_SWELL_DIR, p_32284_);
	}

	public void thunderHit(ServerLevel p_32286_, LightningBolt p_32287_) {
		super.thunderHit(p_32286_, p_32287_);
		this.entityData.set(DATA_IS_POWERED, true);
	}

	private void explodeCreeper() {
		Level level = this.level();
		if (!level.isClientSide()) {
			double radius = this.explosionRadius;
			if (this.isPowered()) {
				radius *= 1.5d;
			}

			Map<Player, Vec3> hitPlayers = EntityOnlyExplosion.explodeAt(level, this, this.getX(), this.getY(),
					this.getZ(), radius, 0.8d, 1.d);
			handleNetworkedExplosionEffects(radius, hitPlayers, SoundEvents.GENERIC_EXPLODE.get());
		}
	}

	protected void handleNetworkedExplosionEffects(double radius, @Nullable Map<Player, Vec3> hitPlayers,
			SoundEvent genericExplode) {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();

		Level level = this.level();
		if (this.level().isClientSide()) {
			this.level().playLocalSound(x, y, z, genericExplode, SoundSource.BLOCKS, 4.0F,
					(1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.2F) * 0.7F, false);
		}

		if (this.level() instanceof ServerLevel) {
			ServerLevel serverLevel = (ServerLevel) this.level();
			for (ServerPlayer serverPlayer : serverLevel.players()) {
				if (serverPlayer.distanceToSqr(x, y, z) < 4096.0D) {
					serverPlayer.connection.send(new ClientboundExplodePacket(x, y, z, (float) radius,
							new ArrayList<BlockPos>(), hitPlayers != null ? hitPlayers.get(serverPlayer) : Vec3.ZERO,
							Explosion.BlockInteraction.KEEP, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER,
							Holder.direct(genericExplode)));
				}
			}
		}

		this.spawnLingeringCloud();
	}

	private void spawnLingeringCloud() {
		Collection<MobEffectInstance> collection = this.getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
			areaeffectcloud.setRadius(2.5F);
			areaeffectcloud.setRadiusOnUse(-0.5F);
			areaeffectcloud.setWaitTime(10);
			areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
			areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());

			for (MobEffectInstance mobeffectinstance : collection) {
				areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
			}

			this.level().addFreshEntity(areaeffectcloud);
		}

	}

	public boolean isIgnited() {
		return this.entityData.get(DATA_IS_IGNITED);
	}

	public void ignite() {
		this.entityData.set(DATA_IS_IGNITED, true);
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	public void aiStep() {
		super.aiStep();
		Level level = this.level();
		if (!level.isClientSide()) {
			this.updatePersistentAnger((ServerLevel) level, true);
		}
	}

	public boolean hurt(DamageSource p_30386_, float p_30387_) {
		if (this.isInvulnerableTo(p_30386_)) {
			return false;
		} else {
			Level level = this.level();
			if (!level.isClientSide()) {
				this.setOrderedToSit(false);
			}

			return super.hurt(p_30386_, p_30387_);
		}
	}

	public InteractionResult mobInteract(Player p_30412_, InteractionHand p_30413_) {
		ItemStack itemstack = p_30412_.getItemInHand(p_30413_);
		Level level = this.level();
		if (level.isClientSide()) {
			boolean flag = this.isOwnedBy(p_30412_) || this.isTame()
					|| itemstack.is(Items.GUNPOWDER) && !this.isTame() && !this.isAngry();
			return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
		} else if (this.isTame()) {
			if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
				this.heal(10);
				if (!p_30412_.getAbilities().instabuild) {
					itemstack.shrink(1);
				}

				this.gameEvent(GameEvent.EAT, this);
				return InteractionResult.SUCCESS;
			} else {
				InteractionResult interactionresult = super.mobInteract(p_30412_, p_30413_);
				if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(p_30412_)) {
					this.setOrderedToSit(!this.isOrderedToSit());
					this.jumping = false;
					this.navigation.stop();
					this.setTarget((LivingEntity) null);
					return InteractionResult.SUCCESS;
				} else {
					return interactionresult;
				}
			}
		} else if (itemstack.is(Items.GUNPOWDER) && !this.isAngry()) {
			if (!p_30412_.getAbilities().instabuild) {
				itemstack.shrink(1);
			}

			if (this.random.nextInt(3) == 0
					&& !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_30412_)) {
				this.tame(p_30412_);
				this.navigation.stop();
				this.setTarget((LivingEntity) null);
				this.setOrderedToSit(true);
				this.level().broadcastEntityEvent(this, (byte) 7);
			} else {
				this.level().broadcastEntityEvent(this, (byte) 6);
			}

			return InteractionResult.SUCCESS;
		} else {
			return super.mobInteract(p_30412_, p_30413_);
		}
	}

	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(DATA_REMAINING_ANGER_TIME);
	}

	public void setRemainingPersistentAngerTime(int p_30404_) {
		this.entityData.set(DATA_REMAINING_ANGER_TIME, p_30404_);
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	public void setPersistentAngerTarget(@Nullable UUID p_30400_) {
		this.persistentAngerTarget = p_30400_;
	}

	@Override
	public boolean isFood(ItemStack p_27600_) {
		return p_27600_.is(Items.GUNPOWDER);
	}

	@Nullable
	public FriendlyCreeper getBreedOffspring(ServerLevel p_149088_, AgeableMob p_149089_) {
		FriendlyCreeper baby = ElementalCreepers.FRIENDLY_CREEPER.get().create(p_149088_);
		if (baby != null) {
			UUID uuid = this.getOwnerUUID();
			if (uuid != null) {
				baby.setOwnerUUID(uuid);
				baby.setTame(true, true);
			}
		}

		return baby;
	}

	public boolean canMate(Animal p_30392_) {
		if (p_30392_ == this) {
			return false;
		} else if (!this.isTame()) {
			return false;
		} else if (!(p_30392_ instanceof FriendlyCreeper)) {
			return false;
		} else {
			FriendlyCreeper other = (FriendlyCreeper) p_30392_;
			if (!other.isTame()) {
				return false;
			} else if (other.isInSittingPose()) {
				return false;
			} else {
				return this.isInLove() && other.isInLove();
			}
		}
	}

	public boolean wantsToAttack(LivingEntity p_30389_, LivingEntity p_30390_) {
		if (!(p_30389_ instanceof Creeper) && !(p_30389_ instanceof Ghast)) {
			if (p_30389_ instanceof TamableAnimal) {
				TamableAnimal tamable = (TamableAnimal) p_30389_;
				return !tamable.isTame() || tamable.getOwner() != p_30390_;
			} else if (p_30389_ instanceof Player && p_30390_ instanceof Player
					&& !((Player) p_30390_).canHarmPlayer((Player) p_30389_)) {
				return false;
			} else if (p_30389_ instanceof AbstractHorse && ((AbstractHorse) p_30389_).isTamed()) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean canBeLeashed() {
		return !this.isAngry();
	}
}

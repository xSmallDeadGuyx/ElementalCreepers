package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import io.github.xsmalldeadguyx.elementalcreepers.common.misc.EntityOnlyExplosion;
import io.github.xsmalldeadguyx.elementalcreepers.common.misc.FriendlyCreeperSwellGoal;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.IChargeableMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SExplosionPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FriendlyCreeper extends TameableEntity implements IAngerable, IChargeableMob {

	private static final DataParameter<Integer> DATA_REMAINING_ANGER_TIME = EntityDataManager
			.defineId(FriendlyCreeper.class, DataSerializers.INT);
	private static final float START_HEALTH = 8.0F;
	private static final float TAME_HEALTH = 40.0F;
	private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);

	private static final DataParameter<Integer> DATA_SWELL_DIR = EntityDataManager.defineId(FriendlyCreeper.class,
			DataSerializers.INT);
	private static final DataParameter<Boolean> DATA_IS_POWERED = EntityDataManager.defineId(FriendlyCreeper.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> DATA_IS_IGNITED = EntityDataManager.defineId(FriendlyCreeper.class,
			DataSerializers.BOOLEAN);
	private int oldSwell;
	private int swell;
	private int maxSwell = 30;
	private int cooldown;
	private int maxCooldown = 80;
	private int explosionRadius = 3;

	@Nullable
	private UUID persistentAngerTarget;

	public FriendlyCreeper(EntityType<? extends FriendlyCreeper> p_30369_, World p_30370_) {
		super(p_30369_, p_30370_);
		this.setTame(false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new SitGoal(this));
		this.goalSelector.addGoal(3, new FriendlyCreeperSwellGoal(this));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, CatEntity.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
		this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(4,
				new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return CreeperEntity.createAttributes().add(Attributes.MAX_HEALTH, START_HEALTH);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
		this.entityData.define(DATA_SWELL_DIR, -1);
		this.entityData.define(DATA_IS_POWERED, false);
		this.entityData.define(DATA_IS_IGNITED, false);
	}

	public void addAdditionalSaveData(CompoundNBT p_30418_) {
		super.addAdditionalSaveData(p_30418_);
		this.addPersistentAngerSaveData(p_30418_);
		if (this.entityData.get(DATA_IS_POWERED)) {
			p_30418_.putBoolean("powered", true);
		}

		p_30418_.putShort("Fuse", (short) this.maxSwell);
		p_30418_.putByte("ExplosionRadius", (byte) this.explosionRadius);
		p_30418_.putBoolean("ignited", this.isIgnited());
	}

	public void readAdditionalSaveData(CompoundNBT p_30402_) {
		super.readAdditionalSaveData(p_30402_);

		if (!level.isClientSide) // FORGE: allow this entity to be read from nbt on client. (Fixes MC-189565)
			this.readPersistentAngerSaveData((ServerWorld) this.level, p_30402_);

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

	public boolean causeFallDamage(float p_149687_, float p_149688_) {
		boolean flag = super.causeFallDamage(p_149687_, p_149688_);
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
		return MathHelper.lerp(p_32321_, (float) this.oldSwell, (float) this.swell) / (float) (this.maxSwell - 2);
	}

	public int getSwellDir() {
		return this.entityData.get(DATA_SWELL_DIR);
	}

	public void setSwellDir(int p_32284_) {
		this.entityData.set(DATA_SWELL_DIR, p_32284_);
	}

	public void thunderHit(ServerWorld p_32286_, LightningBoltEntity p_32287_) {
		super.thunderHit(p_32286_, p_32287_);
		this.entityData.set(DATA_IS_POWERED, true);
	}

	private void explodeCreeper() {
		World level = this.level;
		if (!level.isClientSide()) {
			double radius = this.explosionRadius;
			if (this.isPowered()) {
				radius *= 1.5d;
			}

			Map<PlayerEntity, Vector3d> hitPlayers = EntityOnlyExplosion.explodeAt(level, this, this.getX(),
					this.getY(), this.getZ(), radius, 0.8d, 1.d);
			handleNetworkedExplosionEffects(radius, hitPlayers, SoundEvents.GENERIC_EXPLODE);
		}
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

	private void spawnLingeringCloud() {
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
		World level = this.level;
		if (!level.isClientSide()) {
			this.updatePersistentAnger((ServerWorld) level, true);
		}
	}

	public boolean hurt(DamageSource p_30386_, float p_30387_) {
		if (this.isInvulnerableTo(p_30386_)) {
			return false;
		} else {
			World level = this.level;
			if (!level.isClientSide()) {
				this.setOrderedToSit(false);
			}

			return super.hurt(p_30386_, p_30387_);
		}
	}

	@Override
	public void setTame(boolean p_21836_) {
		byte b0 = this.entityData.get(DATA_FLAGS_ID);
		if (p_21836_) {
			this.entityData.set(DATA_FLAGS_ID, (byte) (b0 | 4));
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(TAME_HEALTH);
			this.setHealth(40.0F);
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte) (b0 & -5));
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(START_HEALTH);
		}
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity p_30412_, Hand p_30413_) {
		ItemStack itemstack = p_30412_.getItemInHand(p_30413_);
		Item item = itemstack.getItem();
		World level = this.level;
		if (level.isClientSide()) {
			boolean flag = this.isOwnedBy(p_30412_) || this.isTame()
					|| item == Items.GUNPOWDER && !this.isTame() && !this.isAngry();
			return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
		} else if (this.isTame()) {
			if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
				this.heal(10);
				if (!p_30412_.abilities.instabuild) {
					itemstack.shrink(1);
				}

				return ActionResultType.SUCCESS;
			} else {
				ActionResultType interactionresult = super.mobInteract(p_30412_, p_30413_);
				if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(p_30412_)) {
					this.setOrderedToSit(!this.isOrderedToSit());
					this.jumping = false;
					this.navigation.stop();
					this.setTarget((LivingEntity) null);
					return ActionResultType.SUCCESS;
				} else {
					return interactionresult;
				}
			}
		} else if (item == Items.GUNPOWDER && !this.isAngry()) {
			if (!p_30412_.abilities.instabuild) {
				itemstack.shrink(1);
			}

			if (this.random.nextInt(3) == 0
					&& !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_30412_)) {
				this.tame(p_30412_);
				this.navigation.stop();
				this.setTarget((LivingEntity) null);
				this.setOrderedToSit(true);
				this.level.broadcastEntityEvent(this, (byte) 7);
			} else {
				this.level.broadcastEntityEvent(this, (byte) 6);
			}

			return ActionResultType.SUCCESS;
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
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
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
		return p_27600_.getItem() == Items.GUNPOWDER;
	}

	@Nullable
	public FriendlyCreeper getBreedOffspring(ServerWorld p_149088_, AgeableEntity p_149089_) {
		FriendlyCreeper baby = ElementalCreepers.FRIENDLY_CREEPER.get().create(p_149088_);
		if (baby != null) {
			UUID uuid = this.getOwnerUUID();
			if (uuid != null) {
				baby.setOwnerUUID(uuid);
				baby.setTame(true);
			}
		}

		return baby;
	}

	public boolean canMate(AnimalEntity p_30392_) {
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
		if (!(p_30389_ instanceof CreeperEntity) && !(p_30389_ instanceof GhastEntity)) {
			if (p_30389_ instanceof TameableEntity) {
				TameableEntity tamable = (TameableEntity) p_30389_;
				return !tamable.isTame() || tamable.getOwner() != p_30390_;
			} else if (p_30389_ instanceof PlayerEntity && p_30390_ instanceof PlayerEntity
					&& !((PlayerEntity) p_30390_).canHarmPlayer((PlayerEntity) p_30389_)) {
				return false;
			} else if (p_30389_ instanceof AbstractHorseEntity && ((AbstractHorseEntity) p_30389_).isTamed()) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public boolean canBeLeashed(PlayerEntity p_30396_) {
		return !this.isAngry() && super.canBeLeashed(p_30396_);
	}
}

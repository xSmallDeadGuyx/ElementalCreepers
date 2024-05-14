package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import java.util.List;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SpiderCreeper extends ElementalCreeper {
	private static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.defineId(SpiderCreeper.class,
			DataSerializers.BYTE);

	public SpiderCreeper(EntityType<? extends CreeperEntity> type, World level) {
		super(type, level);

	}

	@Override
	public void creeperEffect() {
		double radius = Config.spiderCreeperExplosionRadius;
		if (this.isPowered()) {
			radius *= 1.5;
		}

		double rSqr = Math.pow(radius, 2);

		for (int x = (int) -radius - 1; x <= radius; x++)
			for (int y = (int) -radius - 1; y <= radius; y++)
				for (int z = (int) -radius - 1; z <= radius; z++) {
					double distSqr = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
					BlockPos blockPos = new BlockPos((int) this.getX() + x, (int) this.getY() + y,
							(int) this.getZ() + z);
					if (this.level.getBlockState(blockPos).isAir() && distSqr <= rSqr
							&& this.level.random.nextFloat() < 0.05f) {
						this.level.setBlockAndUpdate(blockPos, Blocks.COBWEB.defaultBlockState());
					}
				}

		List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class,
				AxisAlignedBB.ofSize(radius, radius, radius).move(this.position()));

		double poisonTime = Config.spiderCreeperPoisonTimeMedium;
		switch (this.level.getDifficulty()) {
		case EASY:
			poisonTime *= 0.66;
			break;
		case HARD:
			poisonTime *= 1.5;
			break;
		case PEACEFUL:
			poisonTime = 0;
			break;
		case NORMAL:
		default:
			break;
		}

		if (radius > 0) {
			for (LivingEntity entity : entities) {
				if (entity == this) {
					continue;
				}

				double strength = Math.min(0.5, 1 - (entity.distanceTo(this) / radius));
				if (strength <= 1) {
					entity.addEffect(new EffectInstance(Effects.POISON, (int) (60 * poisonTime * strength), 1));
				}
			}
		}

		handleNetworkedExplosionEffects(radius, SoundEvents.SPIDER_DEATH);
	}

	@Override
	protected PathNavigator createNavigation(World p_33802_) {
		return new ClimberPathNavigator(this, p_33802_);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_FLAGS_ID, (byte) 0);
	}

	@Override
	public void tick() {
		super.tick();
		World level = this.level;
		if (!level.isClientSide) {
			this.setClimbing(this.horizontalCollision);
		}

	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SPIDER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_33814_) {
		return SoundEvents.CREEPER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SPIDER_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos p_33804_, BlockState p_33805_) {
		this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean onClimbable() {
		return this.isClimbing();
	}

	@Override
	public void makeStuckInBlock(BlockState p_33796_, Vector3d p_33797_) {
		if (!p_33796_.is(Blocks.COBWEB)) {
			super.makeStuckInBlock(p_33796_, p_33797_);
		}
	}

	@Override
	public boolean canBeAffected(EffectInstance p_33809_) {
		if (p_33809_.getEffect() == Effects.POISON) {
			net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(
					this, p_33809_);
			net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
			return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
		}
		return super.canBeAffected(p_33809_);
	}

	public boolean isClimbing() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	public void setClimbing(boolean p_33820_) {
		byte b0 = this.entityData.get(DATA_FLAGS_ID);
		if (p_33820_) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 = (byte) (b0 & -2);
		}

		this.entityData.set(DATA_FLAGS_ID, b0);
	}
}
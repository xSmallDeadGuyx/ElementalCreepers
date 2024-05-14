package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import java.util.Random;

import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class IllusionCreeper extends ElementalCreeper {

	private boolean hasSplit = false;

	public IllusionCreeper(EntityType<? extends CreeperEntity> type, World level) {
		super(type, level);
	}

	@Override
	public void tick() {
		super.tick();

		World level = this.level;
		if (!hasSplit && !level.isClientSide()) {
			PlayerEntity player = level.getNearestPlayer(this, 8.d);
			if (player != null) {

				Random random = level.random;

				for (int i = 0; i < 4; ++i) {
					FakeIllusionCreeper fake = ElementalCreepers.FAKE_ILLUSION_CREEPER.get().create(level);
					if (fake != null) {
						fake.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);

						double launchX = random.nextDouble() * 0.5 - 0.25;
						double launchZ = random.nextDouble() * 0.5 - 0.25;
						fake.setDeltaMovement(new Vector3d(launchX, 0.5, launchZ));

						level.addFreshEntity(fake);
					}
				}
				double launchX = random.nextDouble() * 0.5 - 0.25;
				double launchZ = random.nextDouble() * 0.5 - 0.25;

				this.setDeltaMovement(new Vector3d(launchX, 0.5, launchZ));

				hasSplit = true;
			}
		}
	}

	@Override
	public void creeperEffect() {
		float f = this.isPowered() ? 1.5f : 1;
		Explosion.Mode explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory
				.getMobGriefingEvent(this.level, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
		this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3f * f, explosion$blockinteraction);

		this.spawnLingeringCloud();
	}
}
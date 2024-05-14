package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class IllusionCreeper extends ElementalCreeper {

	private boolean hasSplit = false;

	public IllusionCreeper(EntityType<? extends Creeper> type, Level level) {
		super(type, level);
	}

	@Override
	public void tick() {
		super.tick();

		Level level = this.level;
		if (!hasSplit && !level.isClientSide()) {
			Player player = level.getNearestPlayer(this, 8.d);
			if (player != null) {

				RandomSource random = level.random;

				for (int i = 0; i < 4; ++i) {
					FakeIllusionCreeper fake = ElementalCreepers.FAKE_ILLUSION_CREEPER.get().create(level);
					if (fake != null) {
						fake.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());

						double launchX = random.nextDouble() * 0.5 - 0.25;
						double launchZ = random.nextDouble() * 0.5 - 0.25;
						fake.setDeltaMovement(new Vec3(launchX, 0.5, launchZ));

						level.addFreshEntity(fake);
					}
				}
				double launchX = random.nextDouble() * 0.5 - 0.25;
				double launchZ = random.nextDouble() * 0.5 - 0.25;

				this.setDeltaMovement(new Vec3(launchX, 0.5, launchZ));

				hasSplit = true;
			}
		}
	}

	@Override
	public void creeperEffect() {
		float f = this.isPowered() ? 1.5f : 1;
        Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
		this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3f * f, explosion$blockinteraction);

		this.spawnLingeringCloud();
	}
}
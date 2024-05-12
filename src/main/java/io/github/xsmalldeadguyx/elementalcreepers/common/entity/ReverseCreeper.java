package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ReverseCreeper extends ElementalCreeper {

	public ReverseCreeper(EntityType<? extends Creeper> type, Level level) {
		super(type, level);
	}

	@Override
	public void creeperEffect() {
		double radius = Config.reverseCreeperExplosionRadius;
		if (this.isPowered()) {
			radius *= 1.5;
		}

		double rSqr = Math.pow(radius, 2);

		Level level = this.level();
		for (int x = (int) -radius - 1; x <= radius; x++)
			for (int y = 1; y <= radius; y++)
				for (int z = (int) -radius - 1; z <= radius; z++) {
					double distSqr = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
					if (distSqr <= rSqr) {
						BlockPos posA = new BlockPos((int) this.getX() + x, (int) this.getY() + y,
								(int) this.getZ() + z);

						BlockPos posB = new BlockPos((int) this.getX() + x, (int) this.getY() - (y - 1),
								(int) this.getZ() + z);

						// Prevent flipping blocks out of build height.
						if (level.isOutsideBuildHeight(posA) || level.isOutsideBuildHeight(posB))
							continue;

						BlockState stateA = level.getBlockState(posA);
						BlockState stateB = level.getBlockState(posB);

						// Prevent flipping blocks which can't be destroyed/pushed.
						if (stateA.getDestroySpeed(this.level(), posA) == -1.0F
								|| stateB.getDestroySpeed(this.level(), posB) == -1.0F) {
							continue;
						}

						// Prevent flipping entities which we can't preserve properly.
						if (stateA.hasBlockEntity() || stateB.hasBlockEntity()) {
							continue;
						}

						level.setBlockAndUpdate(posA, stateB);
						level.setBlockAndUpdate(posB, stateA);
					}
				}

		handleNetworkedExplosionEffects(radius, SoundEvents.PISTON_EXTEND);
	}
}

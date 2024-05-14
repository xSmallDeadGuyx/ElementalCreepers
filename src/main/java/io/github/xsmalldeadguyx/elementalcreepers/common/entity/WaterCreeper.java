package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class WaterCreeper extends ElementalCreeper {

	public WaterCreeper(EntityType<? extends Creeper> type, Level level) {
		super(type, level);

	}

	@Override
	public void creeperEffect() {
		FluidState flowingState = Fluids.FLOWING_WATER.getFlowing(4, false);

		double radius = Config.waterCreeperExplosionRadius;
		double fullRadius = Config.waterCreeperPermanentRadius;
		if (this.isPowered()) {
			radius *= 1.5;
			fullRadius *= 1.5;
		}

		double rSqr = Math.pow(radius, 2);
		double fullSqr = Math.pow(fullRadius, 2);

		for (int x = (int) -radius - 1; x <= radius; x++)
			for (int y = (int) -radius - 1; y <= radius; y++) {
				// Only place one permanent block of water per x/y coord, so it's a puddle on
				// the ground.
				boolean placedPermanentAtThisXY = false;
				for (int z = (int) -radius - 1; z <= radius; z++) {
					BlockPos blockPos = new BlockPos((int) this.getX() + x, (int) this.getY() + y,
							(int) this.getZ() + z);

					double distSqr = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
					if (this.level.getBlockState(blockPos).isAir() && distSqr <= rSqr) {
						BlockState blockState;

						if (distSqr <= fullSqr && !placedPermanentAtThisXY) {
							blockState = Blocks.WATER.defaultBlockState();
							placedPermanentAtThisXY = true;
						} else {
							blockState = flowingState.createLegacyBlock();
						}

						this.level.setBlock(blockPos, blockState, 2);
					}
				}
			}

		handleNetworkedExplosionEffects(radius, SoundEvents.HOSTILE_SPLASH);
	}
}
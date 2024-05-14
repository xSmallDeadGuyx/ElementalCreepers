package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WaterCreeper extends ElementalCreeper {

	public WaterCreeper(EntityType<? extends CreeperEntity> type, World level) {
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
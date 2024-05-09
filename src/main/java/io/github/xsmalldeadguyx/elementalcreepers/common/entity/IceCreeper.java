package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class IceCreeper extends ElementalCreeper {

	public IceCreeper(EntityType<? extends Creeper> type, Level level) {
		super(type, level);
	}

	@Override
	public void creeperEffect() {
		double radius = Config.iceCreeperExplosionRadius;
		if (this.isPowered()) {
			radius *= 1.5;
		}

		double rSqr = Math.pow(radius, 2);

		Level level = this.level();
		for (int x = (int) -radius - 1; x <= radius; x++)
			for (int y = (int) -radius - 1; y <= radius; y++)
				for (int z = (int) -radius - 1; z <= radius; z++) {
					double distSqr = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
					if (distSqr <= rSqr) {
						BlockPos blockPos = new BlockPos((int) this.getX() + x, (int) this.getY() + y,
								(int) this.getZ() + z);

						BlockState blockState = level.getBlockState(blockPos);
						FluidState fluidState = blockState.getFluidState();

						if (fluidState != null) {
							// Freeze water and lava.
							if (fluidState.is(Fluids.WATER) || fluidState.is(Fluids.FLOWING_WATER)) {
								level.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
								continue;
							} else if (fluidState.is(Fluids.FLOWING_LAVA)) {
								level.setBlockAndUpdate(blockPos, Blocks.COBBLESTONE.defaultBlockState());
								continue;
							} else if (fluidState.is(Fluids.LAVA)) {
								level.setBlockAndUpdate(blockPos, Blocks.OBSIDIAN.defaultBlockState());
								continue;
							}
						}

						BlockPos belowPos = blockPos.offset(0, -1, 0);
						if (blockState.isAir()
								&& level.getBlockState(belowPos).isFaceSturdy(level, belowPos, Direction.UP)
								&& level.random.nextFloat() < 0.8) {

							// Try place snow on top of a solid block.
							level.setBlockAndUpdate(blockPos, Blocks.SNOW.defaultBlockState());
							BlockState snowState = level.getBlockState(blockPos);

							if (snowState.is(Blocks.SNOW)) {
								// Placed successfully, grow closer to explosion centre with a little variance
								int snowSize = Math.min(8, (int) (8
										* (radius - Math.sqrt(distSqr) - 1 + 2 * level.random.nextDouble()) / radius));

								if (snowSize > 1) {
									BlockState grownState = snowState.setValue(SnowLayerBlock.LAYERS, snowSize);
									Block.pushEntitiesUp(snowState, grownState, level, blockPos);
									level.setBlockAndUpdate(blockPos, grownState);
								}
							}
						}
					}
				}

		handleNetworkedExplosionEffects(radius, SoundEvents.PLAYER_HURT_FREEZE);
	}
}
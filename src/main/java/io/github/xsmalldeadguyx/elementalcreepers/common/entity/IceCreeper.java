package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IceCreeper extends ElementalCreeper {

	public IceCreeper(EntityType<? extends CreeperEntity> type, World level) {
		super(type, level);
	}

	@Override
	public void creeperEffect() {
		double radius = Config.iceCreeperExplosionRadius;
		if (this.isPowered()) {
			radius *= 1.5;
		}

		double rSqr = Math.pow(radius, 2);

		World level = this.level;
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
							if (fluidState.is(FluidTags.WATER)) {
								level.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
								continue;
							} else if (fluidState.getType() == Fluids.FLOWING_LAVA) {
								level.setBlockAndUpdate(blockPos, Blocks.COBBLESTONE.defaultBlockState());
								continue;
							} else if (fluidState.is(FluidTags.LAVA)) {
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
									BlockState grownState = snowState.setValue(SnowBlock.LAYERS, snowSize);
									Block.pushEntitiesUp(snowState, grownState, level, blockPos);
									level.setBlockAndUpdate(blockPos, grownState);
								}
							}
						}
					}
				}

		handleNetworkedExplosionEffects(radius, SoundEvents.SNOW_PLACE);
	}
}
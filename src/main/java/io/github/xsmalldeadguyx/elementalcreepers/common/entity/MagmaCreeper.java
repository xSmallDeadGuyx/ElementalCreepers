package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagmaCreeper extends ElementalCreeper {

	public MagmaCreeper(EntityType<? extends CreeperEntity> type, World level) {
		super(type, level);

	}

	@Override
	public void creeperEffect() {
		FluidState flowingState = Fluids.FLOWING_LAVA.getFlowing(4, false);

		double radius = Config.waterCreeperExplosionRadius;
		if (this.isPowered()) {
			radius *= 1.5;
		}

		double rSqr = Math.pow(radius, 2);

		World level = this.level;
		for (int x = (int) -radius - 1; x <= radius; x++)
			for (int y = (int) -radius - 1; y <= radius; y++)
				for (int z = (int) -radius - 1; z <= radius; z++) {
					BlockPos blockPos = new BlockPos((int) this.getX() + x, (int) this.getY() + y,
							(int) this.getZ() + z);

					double distSqr = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
					if (level.getBlockState(blockPos).isAir() && distSqr <= rSqr) {
						level.setBlock(blockPos, flowingState.createLegacyBlock(), 2);
					}
				}

		handleNetworkedExplosionEffects(radius, SoundEvents.LAVA_POP);
	}
}
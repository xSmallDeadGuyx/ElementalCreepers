package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EarthCreeper extends ElementalCreeper {

	public EarthCreeper(EntityType<? extends CreeperEntity> type, World level) {
		super(type, level);

	}

	@Override
	public void creeperEffect() {
		double radius = Config.earthCreeperExplosionRadius;
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
							&& this.level.random.nextFloat() < 0.75f) {
						this.level.setBlockAndUpdate(blockPos, Blocks.DIRT.defaultBlockState());
					}
				}

		handleNetworkedExplosionEffects(radius, SoundEvents.GRASS_PLACE);
	}
}
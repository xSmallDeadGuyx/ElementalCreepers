package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class EarthCreeper extends ElementalCreeper {

	public EarthCreeper(EntityType<? extends Creeper> type, Level level) {
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

		handleNetworkedExplosionEffects(radius, SoundEvents.ROOTED_DIRT_PLACE);
	}
}
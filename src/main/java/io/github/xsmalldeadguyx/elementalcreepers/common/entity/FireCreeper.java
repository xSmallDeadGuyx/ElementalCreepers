package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class FireCreeper extends ElementalCreeper {

	public FireCreeper(EntityType<? extends Creeper> type, Level level) {
		super(type, level);
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public void creeperEffect() {
		double radius = Config.fireCreeperExplosionRadius;
		if (this.isPowered()) {
			radius *= 1.5;
		}

		double rSqr = Math.pow(radius, 2);

		for (int x = (int) -radius - 1; x <= radius; x++)
			for (int y = (int) -radius - 1; y <= radius; y++)
				for (int z = (int) -radius - 1; z <= radius; z++) {
					double distSqr = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
					if (distSqr <= rSqr) {
						BlockPos blockPos = new BlockPos((int) this.getX() + x, (int) this.getY() + y,
								(int) this.getZ() + z);
						BlockPos belowPos = blockPos.offset(0, -1, 0);

						if (this.level().getBlockState(blockPos).isAir() && this.level().getBlockState(belowPos)
								.isFaceSturdy(this.level(), belowPos, Direction.UP)
								&& this.level().random.nextBoolean()) {
							this.level().setBlockAndUpdate(blockPos, Blocks.FIRE.defaultBlockState());
						}
					}
				}

		handleNetworkedExplosionEffects(radius, SoundEvents.FIRE_AMBIENT);
	}
}
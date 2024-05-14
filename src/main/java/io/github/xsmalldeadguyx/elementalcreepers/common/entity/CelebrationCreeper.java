package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CelebrationCreeper extends ElementalCreeper {

	public CelebrationCreeper(EntityType<? extends CreeperEntity> type, World level) {
		super(type, level);

	}

	@Override
	public void creeperEffect() {
		// List<BlockPos> candles = new ArrayList<BlockPos>();

		for (int x = -3; x < 4; ++x)
			for (int z = -3; z < 4; ++z)
				for (int y = 0; y < 3 && y > -2; y = y > 0 ? -y : -y + 1) {
					BlockPos blockPos = new BlockPos((int) this.getX() + x, (int) this.getY() + y,
							(int) this.getZ() + z);

					BlockState blockState = this.level.getBlockState(blockPos);
					boolean isAir = blockState.isAir();
					boolean isReplaceable = blockState.getMaterial().isReplaceable();

					if (x == 0 && z == 0) {
						// Try and place a cake.
						if (isAir || isReplaceable)
							if (this.level.setBlockAndUpdate(blockPos, Blocks.CAKE.defaultBlockState())) {
								break;
							}
					} else if (Math.abs(x) < 2 && Math.abs(z) < 2) {
						// CANDLES DON'T EXIST IN 1.16.5
						// Try and place candles.
						// if (isAir || isReplaceable)
						// if (this.level.setBlockAndUpdate(blockPos,
						// Blocks.LIME_CANDLE.defaultBlockState())) {
						// candles.add(blockPos);
						// break;
						// }
					} else {
						// Shoot fireworks!
						if (isAir || isReplaceable) {
							FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(this.level,
									this.getX() + x, this.getY() + y + 0.15D, this.getZ() + z,
									new ItemStack(Items.FIREWORK_ROCKET));
							this.level.addFreshEntity(fireworkrocketentity);
							break;
						}
					}
				}

// CANDLES DON'T EXIST IN 1.16.5
//		Collections.shuffle(candles);
//
//		int candlesPerPos = 11 / candles.size();
//		int extraCandles = 11 % candles.size();
//
//		for (int i = 0; i < candles.size(); ++i) {
//			int numCandles = candlesPerPos;
//			if (i < extraCandles)
//				numCandles++;
//
//			numCandles = Math.max(1, Math.min(4, numCandles));
//			BlockState newState = this.level.getBlockState(candles.get(i)).setValue(CandleBlock.CANDLES, numCandles)
//					.setValue(CandleBlock.LIT, true);
//
//			this.level.setBlockAndUpdate(candles.get(i), newState);
//		}

		handleNetworkedExplosionEffects(3F, SoundEvents.FIREWORK_ROCKET_LAUNCH);
	}
}
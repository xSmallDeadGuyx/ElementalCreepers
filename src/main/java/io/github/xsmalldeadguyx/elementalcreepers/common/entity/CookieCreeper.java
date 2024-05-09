package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CookieCreeper extends ElementalCreeper {

	public CookieCreeper(EntityType<? extends Creeper> type, Level level) {
		super(type, level);
	}

	@Override
	protected void creeperEffect() {
		int amount = Config.cookieCreeperQuantity;
		if (this.isPowered()) {
			amount = (int) (amount * 1.5);
		}

		Level level = this.level();
		for (int i = 0; i < amount; ++i) {
			double dx = level.random.nextDouble() - 0.5;
			double dy = level.random.nextDouble() + 0.5;
			double dz = level.random.nextDouble() - 0.5;

			ItemEntity cookieEntity = new ItemEntity(level, this.getX() + dx, this.getY() + dy, this.getZ() + dz,
					new ItemStack(Items.COOKIE, 1));
			cookieEntity.setDefaultPickUpDelay();

			if (captureDrops() != null)
				captureDrops().add(cookieEntity);
			else
				this.level().addFreshEntity(cookieEntity);
		}

		handleNetworkedExplosionEffects(5.d, SoundEvents.DECORATED_POT_BREAK);
	}
}

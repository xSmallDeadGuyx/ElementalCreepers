package io.github.xsmalldeadguyx.elementalcreepers.common.entity;

import io.github.xsmalldeadguyx.elementalcreepers.common.Config;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class CookieCreeper extends ElementalCreeper {

	public CookieCreeper(EntityType<? extends CreeperEntity> type, World level) {
		super(type, level);
	}

	@Override
	protected void creeperEffect() {
		int amount = Config.cookieCreeperQuantity;
		if (this.isPowered()) {
			amount = (int) (amount * 1.5);
		}

		World level = this.level;
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
				this.level.addFreshEntity(cookieEntity);
		}

		handleNetworkedExplosionEffects(5.d, SoundEvents.VILLAGER_CELEBRATE);
	}
}

package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EC_EntityFireCreeper extends EC_EntityElementalCreeper {
	public EC_EntityFireCreeper(World world) {
		super(world);
		texture = "/mob/firecreeper.png";
		isImmuneToFire = true;
	}

	@Override
	public void creeperEffect() {
		int radius = getPowered() ? (int) (mod_ElementalCreepers.fireCreeperRadius * 1.5F) : mod_ElementalCreepers.fireCreeperRadius;
		for(int x = -radius; x <= radius; x++) for(int y = -radius; y <= radius; y++) for(int z = -radius; z <= radius; z++)
			if(worldObj.canBlockBePlacedAt(Block.waterStill.blockID, (int) posX + x, (int) posY + y, (int) posZ + z, false, 0) && !worldObj.canBlockBePlacedAt(Block.waterStill.blockID, (int) posX + x, (int) posY + y - 1, (int) posZ + z, false, 0))
				if(rand.nextBoolean())
					worldObj.setBlockWithNotify((int) posX + x, (int) posY + y, (int) posZ + z, Block.fire.blockID);
		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		spawnExplosionParticle();
	}
}

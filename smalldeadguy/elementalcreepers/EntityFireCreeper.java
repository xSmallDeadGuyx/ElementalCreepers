package smalldeadguy.elementalcreepers;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;


public class EntityFireCreeper extends EntityElementalCreeper {
	public EntityFireCreeper(World world) {
		super(world);
//		texture = "/mob/firecreeper.png";
		isImmuneToFire = true;
	}

	@Override
	public void creeperEffect() {
		int radius = getPowered() ? (int) (ElementalCreepers.fireCreeperRadius * 1.5F) : ElementalCreepers.fireCreeperRadius;
		for(int x = -radius; x <= radius; x++) for(int y = -radius; y <= radius; y++) for(int z = -radius; z <= radius; z++)
			if(Blocks.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z) && !Blocks.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y - 1, (int) posZ + z))
				if(rand.nextBoolean())
					worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Blocks.fire);
		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		spawnExplosionParticle();
	}
}

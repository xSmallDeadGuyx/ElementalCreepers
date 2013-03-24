package smalldeadguy.elementalcreepers;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class EntityLightCreeper extends EntityElementalCreeper {
	public EntityLightCreeper(World world) {
		super(world);
		texture = "/mob/lightcreeper.png";
	}

	@Override
	public void creeperEffect() {
		double radius = getPowered() ? (int) (ElementalCreepers.lightCreeperRadius * 1.5F) : ElementalCreepers.lightCreeperRadius;
		for(int x = (int) -radius - 1; x <= radius; x++) for(int y = (int) -radius - 1; y <= radius; y++) for(int z = (int) -radius - 1; z <= radius; z++)
			if(Block.glowStone.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z) && Math.sqrt(Math.pow(x,  2) + Math.pow(y,  2) + Math.pow(z, 2)) <= radius) 
				if(rand.nextInt(4) < 3)
					worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Block.glowStone.blockID);
		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		spawnExplosionParticle();
	}
}


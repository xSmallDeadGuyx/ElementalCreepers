package smalldeadguy.elementalcreepers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;


public class EntityEarthCreeper extends EntityElementalCreeper {
	public EntityEarthCreeper(World world) {
		super(world);
		texture = "/mob/earthcreeper.png";
	}

	@Override
	public void creeperEffect() {
		double radius = getPowered() ? (int) (ElementalCreepers.earthCreeperRadius * 1.5F) : ElementalCreepers.earthCreeperRadius;
		for(int x = (int) -radius - 1; x <= radius; x++) for(int y = (int) -radius - 1; y <= radius; y++) for(int z = (int) -radius - 1; z <= radius; z++)
			if(Block.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z) && Math.sqrt(Math.pow(x,  2) + Math.pow(y,  2) + Math.pow(z, 2)) <= radius)
				if(rand.nextInt(4) < 3)
					worldObj.func_94575_c((int) posX + x, (int) posY + y, (int) posZ + z, Block.dirt.blockID);
		
		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		this.spawnExplosionParticle();
	}
}
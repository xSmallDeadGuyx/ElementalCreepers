package ElementalCreepers.smalldeadguy.elementalcreepers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;


public class EntityDarkCreeper extends EntityElementalCreeper {
	public EntityDarkCreeper(World world) {
		super(world);
		texture = "/mob/darkcreeper.png";
	}

	@Override
	public void creeperEffect() {
		double radius = getPowered() ? (int) (ElementalCreepers.darkCreeperRadius * 1.5F) : ElementalCreepers.darkCreeperRadius;
		for(int x = (int) -radius - 1; x <= radius; x++) for(int y = (int) -radius - 1; y <= radius; y++) for(int z = (int) -radius - 1; z <= radius; z++) {
			int id = worldObj.getBlockId((int) posX + x, (int) posY + y, (int) posZ + z);
			if(Block.blocksList[id] != null && Block.lightValue[id] > 0.5F && Math.sqrt(Math.pow(x,  2) + Math.pow(y,  2) + Math.pow(z, 2)) <= radius) {
				Block.blocksList[id].dropBlockAsItem(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z, worldObj.getBlockMetadata((int) posX + x, (int) posY + y, (int) posZ + z), 0);
				worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, 0);
				Block.blocksList[id].onBlockDestroyedByExplosion(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z, new Explosion(worldObj, this, 0.0D, 0.0D, 0.0D, 0.0F));
			}
		}
		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		spawnExplosionParticle();
	}
}
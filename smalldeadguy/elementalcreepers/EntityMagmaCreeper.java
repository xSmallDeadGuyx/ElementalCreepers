package smalldeadguy.elementalcreepers;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;


public class EntityMagmaCreeper extends EntityElementalCreeper {
	public EntityMagmaCreeper(World world) {
		super(world);
		texture = "/mob/magmacreeper.png";
		isImmuneToFire = true;
	}

	@Override
	public void onUpdate() {
		if((int) Math.round(posX + 0.5F) != (int) Math.round(prevPosX + 0.5F) || (int) Math.round(posY) != (int) Math.round(prevPosY) || (int) Math.round(posZ + 0.5F) != (int) Math.round(prevPosZ + 0.5F))
			worldObj.func_94575_c((int) Math.round(prevPosX), (int) Math.round(prevPosY), (int) Math.round(prevPosZ), Block.fire.blockID);
		super.onUpdate();
	}

	@Override
	public void creeperEffect() {
		double radius = getPowered() ? (int) (ElementalCreepers.magmaCreeperRadius * 1.5) : ElementalCreepers.magmaCreeperRadius;
		for(int x = (int) -radius - 1; x <= radius; x++) for(int y = (int) -radius - 1; y <= radius; y++) for(int z = (int) -radius - 1; z <= radius; z++)
			if(Block.lavaStill.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z) && Math.sqrt(Math.pow(x,  2) + Math.pow(y,  2) + Math.pow(z, 2)) <= radius)
				worldObj.setBlockAndMetadataWithNotify((int) posX + x, (int) posY + y, (int) posZ + z, Block.lavaStill.blockID, 3, 3);
		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		spawnExplosionParticle();
	}
}
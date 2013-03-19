package smalldeadguy.elementalcreepers;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;


public class EntityIceCreeper extends EntityElementalCreeper {
	public EntityIceCreeper(World world) {
		super(world);
		texture = "/mob/icecreeper.png";
	}

	@Override
	public void creeperEffect() {
		double radius = getPowered() ? (int) (ElementalCreepers.iceCreeperRadius * 1.5F) : ElementalCreepers.iceCreeperRadius;
		for(int x = (int) -radius - 1; x <= radius; x++) for(int y = (int) -radius - 1; y <= radius; y++) for(int z = (int) -radius - 1; z <= radius; z++)
			if(Math.sqrt(Math.pow(x,  2) + Math.pow(y,  2)) <= radius) {
				if(worldObj.getBlockMaterial((int) posX + x, (int) posY + y, (int) posZ + z) == Material.water && worldObj.getBlockMetadata((int) posX + x, (int) posY + y, (int) posZ + z) == 0)
					worldObj.func_94575_c((int) posX + x, (int) posY + y, (int) posZ + z, Block.ice.blockID);
				else if(worldObj.getBlockMaterial((int) posX + x, (int) posY + y, (int) posZ + z) == Material.lava && worldObj.getBlockMetadata((int) posX + x, (int) posY + y, (int) posZ + z) == 0)
					worldObj.func_94575_c((int) posX + x, (int) posY + y, (int) posZ + z, Block.obsidian.blockID);
				else if(Block.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z) && !Block.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y - 1, (int) posZ + z))
					worldObj.func_94575_c((int) posX + x, (int) posY + y, (int) posZ + z, Block.snow.blockID);
			}

		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
	}
}
package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EC_EntityReverseCreeper extends EC_EntityElementalCreeper {
	public EC_EntityReverseCreeper(World world) {
		super(world);
		texture = "/mob/reversecreeper.png";
	}

	@Override
	public void creeperEffect() {
		double radius = getPowered() ? (int) (mod_ElementalCreepers.reverseCreeperRadius * 1.5F) : mod_ElementalCreepers.reverseCreeperRadius;
		int[][][] blocks = new int[((int) radius) * 2 + 2][((int) radius) * 2 + 2][((int) radius) * 2 + 2];
		int[][][] metas = new int[((int) radius) * 2 + 2][((int) radius) * 2 + 2][((int) radius) * 2 + 2];
		TileEntity[][][] TEs = new TileEntity[((int) radius) * 2 + 2][((int) radius) * 2 + 2][((int) radius) * 2 + 2];
		for(int x = (int) -radius - 1; x <= radius; x++) {
			for(int y = (int) -radius - 1; y <= radius; y++) {
				for(int z = (int) -radius - 1; z <= radius; z++) {
					int id = worldObj.getBlockId((int) posX + x, (int) posY + y, (int) posZ + z);
					blocks[x + (int) radius + 1][y + (int) radius + 1][z + (int) radius + 1] = -1;
					if(id > -1 && Math.sqrt(Math.pow(x,  2) + Math.pow(y,  2) + Math.pow(z, 2)) <= radius) {
						blocks[x + (int) radius + 1][y + (int) radius + 1][z + (int) radius + 1] = id;
						metas[x + (int) radius + 1][y + (int) radius + 1][z + (int) radius + 1] = worldObj.getBlockMetadata((int) posX + x, (int) posY + y, (int) posZ + z);
						TEs[x + (int) radius + 1][y + (int) radius + 1][z + (int) radius + 1] = worldObj.getBlockTileEntity((int) posX + x, (int) posY + y, (int) posZ + z);
					}
				}
			}
		}
		for(int x = (int) -radius - 1; x <= radius; x++) {
			for(int y = (int) -radius - 1; y <= radius; y++) {
				for(int z = (int) -radius - 1; z <= radius; z++) {
					int id = blocks[x + (int) radius + 1][(2 * (int) radius) - (y + (int) radius)][z + (int) radius + 1];
					int meta = metas[x + (int) radius + 1][(2 * (int) radius) - (y + (int) radius)][z + (int) radius + 1];
					TileEntity TE = TEs[x + (int) radius + 1][(2 * (int) radius) - (y + (int) radius)][z + (int) radius + 1];
					if(id > -1) {
						worldObj.setBlockAndMetadataWithNotify((int) posX + x, (int) posY + y, (int) posZ + z, id, meta);
						if(TE != null)
							worldObj.setBlockTileEntity((int) posX + x, (int) posY + y, (int) posZ + z, TE);
					}
				}
			}
		}
		worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		spawnExplosionParticle();
	}
}
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
					int ax = x + (int) radius + 1;
					int ay = y + (int) radius + 1;
					int az = z + (int) radius + 1;
					int ex = (int) posX + x;
					int ey = (int) posY + y;
					int ez = (int) posZ + z;
					int id = worldObj.getBlockId(ex, ey, ez);
					blocks[ax][ay][az] = -1;

					if(id > -1 && Math.sqrt(Math.pow(x,  2) + Math.pow(y,  2) + Math.pow(z, 2)) <= radius && ey > -1) {
						blocks[ax][ay][az] = id;
						metas[ax][ay][az] = worldObj.getBlockMetadata(ex, ey, ez);
						TEs[ax][ay][az] = worldObj.getBlockTileEntity(ex, ey, ez);
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
package net.minecraft.src;

public class EC_EntityIceCreeper extends EC_EntityElementalCreeper
{
    public EC_EntityIceCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/icecreeper.png";
    }

    public void creeperEffect()
    {
        double var1 = this.isPowered() ? (double)((int)((float)mod_ElementalCreepers.iceCreeperRadius * 1.5F)) : (double)mod_ElementalCreepers.iceCreeperRadius;

        for (int var3 = (int)(-var1) - 1; (double)var3 <= var1; ++var3)
        {
            for (int var4 = (int)(-var1) - 1; (double)var4 <= var1; ++var4)
            {
                for (int var5 = (int)(-var1) - 1; (double)var5 <= var1; ++var5)
                {
                    if (Math.sqrt(Math.pow((double)var3, 2.0D) + Math.pow((double)var4, 2.0D)) <= var1)
                    {
                        if (this.world.getMaterial((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5) == Material.WATER && this.world.getData((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5) == 0)
                        {
                            this.world.setTypeId((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5, Block.ICE.id);
                        }
                        else if (this.world.getMaterial((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5) == Material.LAVA && this.world.getData((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5) == 0)
                        {
                            this.world.setTypeId((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5, Block.OBSIDIAN.id);
                        }
                        else if (this.world.mayPlace(Block.SNOW.id, (int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5, false, 0) && !this.world.mayPlace(Block.SNOW.id, (int)this.locX + var3, (int)this.locY + var4 - 1, (int)this.locZ + var5, false, 0))
                        {
                            this.world.setTypeId((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5, Block.SNOW.id);
                        }
                    }
                }
            }
        }

        this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
    }
}

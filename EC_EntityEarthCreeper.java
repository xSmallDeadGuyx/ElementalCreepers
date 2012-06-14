package net.minecraft.server;

public class EC_EntityEarthCreeper extends EC_EntityElementalCreeper
{
    public EC_EntityEarthCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/earthcreeper.png";
    }

    public void creeperEffect()
    {
        double var1 = this.isPowered() ? (double)((int)((float)mod_ElementalCreepers.earthCreeperRadius * 1.5F)) : (double)mod_ElementalCreepers.earthCreeperRadius;

        for (int var3 = (int)(-var1) - 1; (double)var3 <= var1; ++var3)
        {
            for (int var4 = (int)(-var1) - 1; (double)var4 <= var1; ++var4)
            {
                for (int var5 = (int)(-var1) - 1; (double)var5 <= var1; ++var5)
                {
                    if (this.world.mayPlace(Block.DIRT.id, (int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5, false, 0) && Math.sqrt(Math.pow((double)var3, 2.0D) + Math.pow((double)var4, 2.0D) + Math.pow((double)var5, 2.0D)) <= var1 && this.random.nextInt(4) < 3)
                    {
                        this.world.setTypeId((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5, Block.DIRT.id);
                    }
                }
            }
        }

        this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
        this.aC();
    }
}

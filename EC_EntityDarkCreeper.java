package net.minecraft.src;

public class EC_EntityDarkCreeper extends EC_EntityElementalCreeper
{
    public EC_EntityDarkCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/darkcreeper.png";
    }

    public void creeperEffect()
    {
        double var1 = this.isPowered() ? (double)((int)((float)mod_ElementalCreepers.darkCreeperRadius * 1.5F)) : (double)mod_ElementalCreepers.darkCreeperRadius;

        for (int var3 = (int)(-var1) - 1; (double)var3 <= var1; ++var3)
        {
            for (int var4 = (int)(-var1) - 1; (double)var4 <= var1; ++var4)
            {
                for (int var5 = (int)(-var1) - 1; (double)var5 <= var1; ++var5)
                {
                    int var6 = this.world.getTypeId((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5);

                    if ((float)Block.lightEmission[var6] > 0.5F && Math.sqrt(Math.pow((double)var3, 2.0D) + Math.pow((double)var4, 2.0D) + Math.pow((double)var5, 2.0D)) <= var1)
                    {
                        Block.byId[var6].b(this.world, (int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5, this.world.getData((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5), 0);
                        this.world.setTypeId((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5, 0);
                        Block.byId[var6].wasExploded(this.world, (int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5);
                    }
                }
            }
        }

        this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
        this.aC();
    }
}

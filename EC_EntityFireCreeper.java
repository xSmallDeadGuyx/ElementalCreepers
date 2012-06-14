package net.minecraft.src;

public class EC_EntityFireCreeper extends EC_EntityElementalCreeper
{
    public EC_EntityFireCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/firecreeper.png";
        this.fireProof = true;
    }

    public void creeperEffect()
    {
        int var1 = this.isPowered() ? (int)((float)mod_ElementalCreepers.fireCreeperRadius * 1.5F) : mod_ElementalCreepers.fireCreeperRadius;

        for (int var2 = -var1; var2 <= var1; ++var2)
        {
            for (int var3 = -var1; var3 <= var1; ++var3)
            {
                for (int var4 = -var1; var4 <= var1; ++var4)
                {
                    if (this.world.mayPlace(Block.STATIONARY_WATER.id, (int)this.locX + var2, (int)this.locY + var3, (int)this.locZ + var4, false, 0) && !this.world.mayPlace(Block.STATIONARY_WATER.id, (int)this.locX + var2, (int)this.locY + var3 - 1, (int)this.locZ + var4, false, 0) && this.random.nextBoolean())
                    {
                        this.world.setTypeId((int)this.locX + var2, (int)this.locY + var3, (int)this.locZ + var4, Block.FIRE.id);
                    }
                }
            }
        }

        this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
        this.aC();
    }
}

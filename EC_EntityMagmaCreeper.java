package net.minecraft.src;

public class EC_EntityMagmaCreeper extends EC_EntityElementalCreeper
{
    public EC_EntityMagmaCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/magmacreeper.png";
        this.fireProof = true;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void F_()
    {
        if ((int)Math.round(this.locX + 0.5D) != (int)Math.round(this.lastX + 0.5D) || (int)Math.round(this.locY) != (int)Math.round(this.lastY) || (int)Math.round(this.locZ + 0.5D) != (int)Math.round(this.lastZ + 0.5D))
        {
            this.world.setTypeId((int)Math.round(this.lastX), (int)Math.round(this.lastY), (int)Math.round(this.lastZ), Block.FIRE.id);
        }

        super.F_();
    }

    public void creeperEffect()
    {
        double var1 = this.isPowered() ? (double)((int)((double)mod_ElementalCreepers.magmaCreeperRadius * 1.5D)) : (double)mod_ElementalCreepers.magmaCreeperRadius;

        for (int var3 = (int)(-var1) - 1; (double)var3 <= var1; ++var3)
        {
            for (int var4 = (int)(-var1) - 1; (double)var4 <= var1; ++var4)
            {
                for (int var5 = (int)(-var1) - 1; (double)var5 <= var1; ++var5)
                {
                    if (this.world.mayPlace(Block.STATIONARY_LAVA.id, (int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5, false, 0) && Math.sqrt(Math.pow((double)var3, 2.0D) + Math.pow((double)var4, 2.0D) + Math.pow((double)var5, 2.0D)) <= var1)
                    {
                        this.world.setTypeIdAndData((int)this.locX + var3, (int)this.locY + var4, (int)this.locZ + var5, Block.STATIONARY_LAVA.id, 3);
                    }
                }
            }
        }

        this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
        this.aC();
    }
}

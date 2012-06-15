package net.minecraft.server;

import java.util.List;

public class EC_EntityElectricCreeper extends EC_EntityElementalCreeper
{
    public EC_EntityElectricCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/electriccreeper.png";
    }

    public void creeperEffect()
    {
        int var1 = this.isPowered() ? (int)((float)mod_ElementalCreepers.electricCreeperRadius * 1.5F) : mod_ElementalCreepers.electricCreeperRadius;
        List var2 = this.world.a(EntityLiving.class, AxisAlignedBB.b(this.locX, this.locY, this.locZ, this.locX + 1.0D, this.locY + 1.0D, this.locZ + 1.0D).grow((double)var1, (double)var1, (double)var1));

        for (int var3 = 0; var3 < var2.size(); ++var3)
        {
            EntityLiving var4 = (EntityLiving)var2.get(var3);

            if (var4 != null && !(var4 instanceof EntityMonster))
            {
                this.world.addEntity(new EntityWeatherLighting(this.world, var4.locX, var4.locY, var4.locZ));
            }
        }

        this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
        this.aC();
    }
}

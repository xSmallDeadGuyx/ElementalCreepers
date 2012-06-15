package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class EC_FriendlyExplosion
{
    private Random explosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;

    public EC_FriendlyExplosion(World var1, Entity var2, double var3, double var5, double var7, float var9)
    {
        this.worldObj = var1;
        this.exploder = var2;
        this.explosionSize = var9;
        this.explosionX = var3;
        this.explosionY = var5;
        this.explosionZ = var7;
    }

    public void doExplosionA(EC_EntityFriendlyCreeper var1)
    {
        float var2 = this.explosionSize;
        boolean var3 = true;
        this.explosionSize *= 2.0F;
        int var4 = MathHelper.floor(this.explosionX - (double)this.explosionSize - 1.0D);
        int var5 = MathHelper.floor(this.explosionX + (double)this.explosionSize + 1.0D);
        int var6 = MathHelper.floor(this.explosionY - (double)this.explosionSize - 1.0D);
        int var13 = MathHelper.floor(this.explosionY + (double)this.explosionSize + 1.0D);
        int var14 = MathHelper.floor(this.explosionZ - (double)this.explosionSize - 1.0D);
        int var15 = MathHelper.floor(this.explosionZ + (double)this.explosionSize + 1.0D);
        List var16 = this.worldObj.getEntities(this.exploder, AxisAlignedBB.b((double)var4, (double)var6, (double)var14, (double)var5, (double)var13, (double)var15));
        Vec3D var17 = Vec3D.create(this.explosionX, this.explosionY, this.explosionZ);

        for (int var18 = 0; var18 < var16.size(); ++var18)
        {
            Entity var19 = (Entity)var16.get(var18);

            if (!var19.equals(var1) && (!(var19 instanceof EntityHuman) || !((EntityHuman)var19).name.equalsIgnoreCase(var1.getOwnerName())))
            {
                double var20 = var19.f(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;

                if (var20 <= 1.0D)
                {
                    double var7 = var19.locX - this.explosionX;
                    double var9 = var19.locY - this.explosionY;
                    double var11 = var19.locZ - this.explosionZ;
                    double var22 = (double)MathHelper.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
                    var7 /= var22;
                    var9 /= var22;
                    var11 /= var22;
                    double var24 = (double)this.worldObj.a(var17, var19.boundingBox);
                    double var26 = (1.0D - var20) * var24;
                    var19.damageEntity(DamageSource.EXPLOSION, (int)((var26 * var26 + var26) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D));
                    var19.motX += var7 * var26;
                    var19.motY += var9 * var26;
                    var19.motZ += var11 * var26;
                }
            }
        }

        this.explosionSize = var2;
    }

    public void doExplosionB(boolean var1)
    {
        this.worldObj.makeSound(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.random.nextFloat() - this.worldObj.random.nextFloat()) * 0.2F) * 0.7F);
        this.worldObj.a("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 0.0D, 0.0D, 0.0D);
    }
}

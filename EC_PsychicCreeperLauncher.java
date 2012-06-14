package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EC_PsychicCreeperLauncher
{
    private Random explosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;

    public EC_PsychicCreeperLauncher(World var1, Entity var2, double var3, double var5, double var7, float var9)
    {
        this.worldObj = var1;
        this.exploder = var2;
        this.explosionSize = var9;
        this.explosionX = var3;
        this.explosionY = var5;
        this.explosionZ = var7;
    }

    public void doExplosionA()
    {
        float var1 = this.explosionSize;
        boolean var2 = true;
        this.explosionSize *= 2.0F;
        int var3 = MathHelper.floor(this.explosionX - (double)this.explosionSize - 1.0D);
        int var4 = MathHelper.floor(this.explosionX + (double)this.explosionSize + 1.0D);
        int var5 = MathHelper.floor(this.explosionY - (double)this.explosionSize - 1.0D);
        int var12 = MathHelper.floor(this.explosionY + (double)this.explosionSize + 1.0D);
        int var13 = MathHelper.floor(this.explosionZ - (double)this.explosionSize - 1.0D);
        int var14 = MathHelper.floor(this.explosionZ + (double)this.explosionSize + 1.0D);
        List var15 = this.worldObj.getEntities(this.exploder, AxisAlignedBB.b((double)var3, (double)var5, (double)var13, (double)var4, (double)var12, (double)var14));
        Vec3D var16 = Vec3D.create(this.explosionX, this.explosionY, this.explosionZ);

        for (int var17 = 0; var17 < var15.size(); ++var17)
        {
            Entity var18 = (Entity)var15.get(var17);
            double var19 = var18.f(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;

            if (var19 <= 1.0D)
            {
                double var6 = var18.locX - this.explosionX;
                double var8 = var18.locY - this.explosionY;
                double var10 = var18.locZ - this.explosionZ;
                double var21 = (double)MathHelper.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
                var6 /= var21;
                var8 /= var21;
                var10 /= var21;
                double var23 = (double)this.worldObj.a(var16, var18.boundingBox);
                double var25 = (1.0D - var19) * var23 * 5.0D;
                var18.motX += var6 * var25;
                var18.motY += var8 * var25;
                var18.motZ += var10 * var25;
            }
        }

        this.explosionSize = var1;
    }

    public void doExplosionB(boolean var1)
    {
        this.worldObj.makeSound(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.random.nextFloat() - this.worldObj.random.nextFloat()) * 0.2F) * 0.7F);
        this.worldObj.a("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 0.0D, 0.0D, 0.0D);
        ArrayList var2 = new ArrayList();

        for (int var3 = var2.size() - 1; var3 >= 0; --var3)
        {
            ChunkPosition var4 = (ChunkPosition)var2.get(var3);
            int var5 = var4.x;
            int var6 = var4.y;
            int var7 = var4.z;
            this.worldObj.getTypeId(var5, var6, var7);

            if (var1)
            {
                double var9 = (double)((float)var5 + this.worldObj.random.nextFloat());
                double var11 = (double)((float)var6 + this.worldObj.random.nextFloat());
                double var13 = (double)((float)var7 + this.worldObj.random.nextFloat());
                double var15 = var9 - this.explosionX;
                double var17 = var11 - this.explosionY;
                double var19 = var13 - this.explosionZ;
                double var21 = (double)MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);
                var15 /= var21;
                var17 /= var21;
                var19 /= var21;
                double var23 = 0.5D / (var21 / (double)this.explosionSize + 0.1D);
                var23 *= (double)(this.worldObj.random.nextFloat() * this.worldObj.random.nextFloat() + 0.3F);
                var15 *= var23;
                var17 *= var23;
                var19 *= var23;
                this.worldObj.a("explode", (var9 + this.explosionX * 1.0D) / 2.0D, (var11 + this.explosionY * 1.0D) / 2.0D, (var13 + this.explosionZ * 1.0D) / 2.0D, var15, var17, var19);
                this.worldObj.a("smoke", var9, var11, var13, var15, var17, var19);
            }
        }
    }
}

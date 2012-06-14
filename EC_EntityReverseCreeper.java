package net.minecraft.src;

public class EC_EntityReverseCreeper extends EC_EntityElementalCreeper
{
    public EC_EntityReverseCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/reversecreeper.png";
    }

    public void creeperEffect()
    {
        double var1 = this.isPowered() ? (double)((int)((float)mod_ElementalCreepers.reverseCreeperRadius * 1.5F)) : (double)mod_ElementalCreepers.reverseCreeperRadius;
        int[][][] var3 = new int[(int)var1 * 2 + 2][(int)var1 * 2 + 2][(int)var1 * 2 + 2];
        int[][][] var4 = new int[(int)var1 * 2 + 2][(int)var1 * 2 + 2][(int)var1 * 2 + 2];
        TileEntity[][][] var5 = new TileEntity[(int)var1 * 2 + 2][(int)var1 * 2 + 2][(int)var1 * 2 + 2];
        int var6;
        int var7;
        int var8;
        int var9;
        int var10;

        for (var6 = (int)(-var1) - 1; (double)var6 <= var1; ++var6)
        {
            for (var7 = (int)(-var1) - 1; (double)var7 <= var1; ++var7)
            {
                for (var8 = (int)(-var1) - 1; (double)var8 <= var1; ++var8)
                {
                    var9 = var6 + (int)var1 + 1;
                    var10 = var7 + (int)var1 + 1;
                    int var11 = var8 + (int)var1 + 1;
                    int var12 = (int)this.locX + var6;
                    int var13 = (int)this.locY + var7;
                    int var14 = (int)this.locZ + var8;
                    int var15 = this.world.getTypeId(var12, var13, var14);
                    var3[var9][var10][var11] = -1;

                    if (var15 > -1 && Math.sqrt(Math.pow((double)var6, 2.0D) + Math.pow((double)var7, 2.0D) + Math.pow((double)var8, 2.0D)) <= var1 && var13 > -1)
                    {
                        var3[var9][var10][var11] = var15;
                        var4[var9][var10][var11] = this.world.getData(var12, var13, var14);
                        var5[var9][var10][var11] = this.world.getTileEntity(var12, var13, var14);
                    }
                }
            }
        }

        for (var6 = (int)(-var1) - 1; (double)var6 <= var1; ++var6)
        {
            for (var7 = (int)(-var1) - 1; (double)var7 <= var1; ++var7)
            {
                for (var8 = (int)(-var1) - 1; (double)var8 <= var1; ++var8)
                {
                    var9 = var3[var6 + (int)var1 + 1][2 * (int)var1 - (var7 + (int)var1)][var8 + (int)var1 + 1];
                    var10 = var4[var6 + (int)var1 + 1][2 * (int)var1 - (var7 + (int)var1)][var8 + (int)var1 + 1];
                    TileEntity var16 = var5[var6 + (int)var1 + 1][2 * (int)var1 - (var7 + (int)var1)][var8 + (int)var1 + 1];

                    if (var9 > -1)
                    {
                        this.world.setTypeIdAndData((int)this.locX + var6, (int)this.locY + var7, (int)this.locZ + var8, var9, var10);

                        if (var16 != null)
                        {
                            this.world.setTileEntity((int)this.locX + var6, (int)this.locY + var7, (int)this.locZ + var8, var16);
                        }
                    }
                }
            }
        }

        this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
        this.aC();
    }
}

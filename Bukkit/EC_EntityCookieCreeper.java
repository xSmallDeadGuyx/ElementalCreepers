package net.minecraft.server;

public class EC_EntityCookieCreeper extends EC_EntityElementalCreeper
{
    public EC_EntityCookieCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/cookiecreeper.png";
    }

    public void creeperEffect()
    {
        int var1 = this.isPowered() ? (int)((float)mod_ElementalCreepers.cookieCreeperAmount * 1.5F) : mod_ElementalCreepers.cookieCreeperAmount;

        for (int var2 = 0; var2 < var1; ++var2)
        {
            float var3 = 0.7F;
            double var4 = (double)(this.world.random.nextFloat() * var3) + (double)(1.0F + var3) * 0.5D;
            double var6 = (double)(this.world.random.nextFloat() * var3) + (double)(1.0F + var3) * 0.5D;
            double var8 = (double)(this.world.random.nextFloat() * var3) + (double)(1.0F + var3) * 0.5D;
            EntityItem var10 = new EntityItem(this.world, this.locX + var4, this.locY + var6, this.locZ + var8, new ItemStack(Item.COOKIE, 1));
            var10.pickupDelay = 10;
            this.world.addEntity(var10);
        }

        this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
        this.aC();
    }
}

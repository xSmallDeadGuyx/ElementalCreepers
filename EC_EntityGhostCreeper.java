package net.minecraft.server;

public class EC_EntityGhostCreeper extends EC_EntityElementalCreeper
{
    public EC_EntityGhostCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/creeper.png";
    }

    public void creeperEffect()
    {
        float var1 = this.isPowered() ? (float)mod_ElementalCreepers.ghostCreeperRadius * 1.5F : (float)mod_ElementalCreepers.ghostCreeperRadius;
        this.createGhostExplosion(this, this.locX, this.locY, this.locZ, var1);
    }

    public EC_GhostExplosion createGhostExplosion(Entity var1, double var2, double var4, double var6, float var8)
    {
        return this.newGhostExplosion(var1, var2, var4, var6, var8, false);
    }

    public EC_GhostExplosion newGhostExplosion(Entity var1, double var2, double var4, double var6, float var8, boolean var9)
    {
        EC_GhostExplosion var10 = new EC_GhostExplosion(this.world, var1, var2, var4, var6, var8);
        var10.isFlaming = var9;
        var10.doExplosionA();
        var10.doExplosionB(true);
        return var10;
    }
}

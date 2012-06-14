package net.minecraft.server;

public class EC_EntityPsychicCreeper extends EC_EntityElementalCreeper
{
    public EC_EntityPsychicCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/psychiccreeper.png";
    }

    public void creeperEffect()
    {
        float var1 = this.isPowered() ? (float)mod_ElementalCreepers.psychicCreeperPower * 1.5F : (float)mod_ElementalCreepers.psychicCreeperPower;
        this.createPsychicCreeperLauncher(this, this.locX, this.locY, this.locZ, var1);
    }

    public EC_PsychicCreeperLauncher createPsychicCreeperLauncher(Entity var1, double var2, double var4, double var6, float var8)
    {
        return this.newPsychicCreeperLauncher(var1, var2, var4, var6, var8, false);
    }

    public EC_PsychicCreeperLauncher newPsychicCreeperLauncher(Entity var1, double var2, double var4, double var6, float var8, boolean var9)
    {
        EC_PsychicCreeperLauncher var10 = new EC_PsychicCreeperLauncher(this.world, var1, var2, var4, var6, var8);
        var10.doExplosionA();
        var10.doExplosionB(true);
        return var10;
    }
}

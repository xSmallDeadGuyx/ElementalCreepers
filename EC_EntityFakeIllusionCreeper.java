package net.minecraft.src;

public class EC_EntityFakeIllusionCreeper extends EC_EntityElementalCreeper
{
    public EC_EntityFakeIllusionCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/illusioncreeper.png";
    }

    public void creeperEffect()
    {
        this.aC();
    }
}

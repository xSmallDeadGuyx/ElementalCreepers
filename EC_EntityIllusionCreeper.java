package net.minecraft.src;

public class EC_EntityIllusionCreeper extends EC_EntityElementalCreeper
{
    boolean split = false;

    public EC_EntityIllusionCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/illusioncreeper.png";
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void F_()
    {
        super.F_();
        EntityHuman var1 = this.world.findNearbyPlayer(this, 8.0D);

        if (!this.split && var1 != null && !this.world.isStatic)
        {
            this.createFakeCreepersAndLaunchSelf();
            this.split = true;
        }
    }

    private void createFakeCreepersAndLaunchSelf()
    {
        for (int var1 = 0; var1 < 4; ++var1)
        {
            EC_EntityFakeIllusionCreeper var2 = new EC_EntityFakeIllusionCreeper(this.world);

            if (var2 != null)
            {
                var2.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
                var2.motY = 0.5D;
                this.world.addEntity(var2);
            }
        }

        this.motY = 0.5D;
    }
}

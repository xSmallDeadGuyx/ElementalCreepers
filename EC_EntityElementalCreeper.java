package net.minecraft.src;

public class EC_EntityElementalCreeper extends EntityCreeper
{
    /**
     * The amount of time since the creeper was close enough to the player to ignite
     */
    int fuseTicks;

    /**
     * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
     * weird)
     */
    int b;

    public EC_EntityElementalCreeper(World var1)
    {
        super(var1);
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource var1)
    {
        super.die(var1);

        if (this.world.random.nextInt(100) < mod_ElementalCreepers.ghostCreeperChance && !this.world.isStatic)
        {
            EC_EntityGhostCreeper var2 = new EC_EntityGhostCreeper(this.world);
            var2.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            this.world.addEntity(var2);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void F_()
    {
        if (this.isAlive())
        {
            this.b = this.fuseTicks;
            int var1 = this.A();

            if (var1 > 0 && this.fuseTicks == 0)
            {
                this.world.makeSound(this, "random.fuse", 1.0F, 0.5F);
            }

            this.fuseTicks += var1;

            if (this.fuseTicks < 0)
            {
                this.fuseTicks = 0;
            }

            if (this.fuseTicks >= 30)
            {
                this.fuseTicks = 30;

                if (!this.world.isStatic)
                {
                    this.creeperEffect();
                    this.die();
                }
            }
        }

        super.F_();
    }

    public void creeperEffect()
    {
        this.world.explode(this, this.locX, this.locY, this.locZ, this.isPowered() ? 6.0F : 3.0F);
    }
}

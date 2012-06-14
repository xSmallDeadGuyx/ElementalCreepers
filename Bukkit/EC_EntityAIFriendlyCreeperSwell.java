package net.minecraft.server;

public class EC_EntityAIFriendlyCreeperSwell extends PathfinderGoal
{
    EC_EntityFriendlyCreeper swellingCreeper;
    EntityLiving creeperAttackTarget;

    public EC_EntityAIFriendlyCreeperSwell(EC_EntityFriendlyCreeper var1)
    {
        this.swellingCreeper = var1;
        this.a(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean a()
    {
        EntityLiving var1 = this.swellingCreeper.at();
        return this.swellingCreeper.getCreeperState() > 0 || var1 != null && this.swellingCreeper.j(var1) < 9.0D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void c()
    {
        this.swellingCreeper.al().f();
        this.creeperAttackTarget = this.swellingCreeper.at();
    }

    /**
     * Resets the task
     */
    public void d()
    {
        this.creeperAttackTarget = null;
    }

    /**
     * Updates the task
     */
    public void e()
    {
        if (this.creeperAttackTarget == null)
        {
            this.swellingCreeper.setCreeperState(-1);
        }
        else if (this.swellingCreeper.j(this.creeperAttackTarget) > 49.0D)
        {
            this.swellingCreeper.setCreeperState(-1);
        }
        else if (!this.swellingCreeper.am().canSee(this.creeperAttackTarget))
        {
            this.swellingCreeper.setCreeperState(-1);
        }
        else
        {
            this.swellingCreeper.setCreeperState(1);
        }
    }
}

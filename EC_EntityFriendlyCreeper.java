package net.minecraft.src;

public class EC_EntityFriendlyCreeper extends EntityTameableAnimal
{
    private float field_25048_b;
    private float field_25054_c;
    private boolean field_25052_g;
    int timeSinceIgnited;
    int lastActiveTime;

    public EC_EntityFriendlyCreeper(World var1)
    {
        super(var1);
        this.texture = "/mob/friendlycreeper0.png";
        this.bb = 0.3F;
        this.al().a(true);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, this.a);
        this.goalSelector.a(3, new EC_EntityAIFriendlyCreeperSwell(this));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, this.bb, true));
        this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, this.bb, 10.0F, 2.0F));
        this.goalSelector.a(6, new PathfinderGoalBreed(this, this.bb));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, this.bb));
        this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalOwnerHurtByTarget(this));
        this.targetSelector.a(2, new PathfinderGoalOwnerHurtTarget(this));
        this.targetSelector.a(3, new PathfinderGoalHurtByTarget(this, true));
        this.goalSelector.a(10, new PathfinderGoalAvoidPlayer(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean c_()
    {
        return true;
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void b(EntityLiving var1)
    {
        super.b(var1);

        if (var1 instanceof EntityHuman)
        {
            this.setAngry(true);
        }
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void g()
    {
        this.datawatcher.watch(18, Integer.valueOf(this.getHealth()));
    }

    public int getMaxHealth()
    {
        return this.isTamed() ? 20 : 8;
    }

    protected void b()
    {
        super.b();
        this.datawatcher.a(18, new Integer(this.getHealth()));
        this.datawatcher.a(19, Byte.valueOf((byte) - 1));
        this.datawatcher.a(20, Byte.valueOf((byte)0));
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean g_()
    {
        return false;
    }

    public String getTexture()
    {
        return this.isTamed() ? "/mob/friendlycreeper1.png" : "/mob/friendlycreeper0.png";
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void b(NBTTagCompound var1)
    {
        super.b(var1);
        var1.setBoolean("Angry", this.isAngry());

        if (this.datawatcher.getByte(20) == 1)
        {
            var1.setBoolean("powered", true);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void a(NBTTagCompound var1)
    {
        super.a(var1);
        this.setAngry(var1.getBoolean("Angry"));
        this.datawatcher.watch(20, Byte.valueOf((byte)(var1.getBoolean("powered") ? 1 : 0)));
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean n()
    {
        return this.isAngry();
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String j()
    {
        return "mob.creeper";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String k()
    {
        return "mob.creeperdeath";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float p()
    {
        return 0.4F;
    }

    public float getHeadHeight()
    {
        return this.length * 0.8F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int D()
    {
        return this.isSitting() ? 20 : super.D();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean damageEntity(DamageSource var1, int var2)
    {
        Entity var3 = var1.getEntity();
        this.a.func_48210_a(false);

        if (var3 != null && !(var3 instanceof EntityHuman) && !(var3 instanceof EntityArrow))
        {
            var2 = (var2 + 1) / 2;
        }

        return super.damageEntity(var1, var2);
    }

    public boolean a(Entity var1)
    {
        return false;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean b(EntityHuman var1)
    {
        ItemStack var2 = var1.inventory.getItemInHand();

        if (!this.isTamed())
        {
            if (var2 != null && var2.id == Item.SULPHUR.id && !this.isAngry() && !this.isTamed())
            {
                --var2.count;

                if (var2.count <= 0)
                {
                    var1.inventory.setItem(var1.inventory.itemInHandIndex, (ItemStack)null);
                }

                if (!this.world.isStatic)
                {
                    if (this.random.nextInt(3) == 0)
                    {
                        this.setTamed(true);
                        this.setPathEntity((PathEntity)null);
                        this.b((EntityLiving)null);
                        this.a.func_48210_a(true);
                        this.setHealth(20);
                        this.setOwnerName(var1.name);
                        this.func_48370_a(true);
                        this.world.broadcastEntityEffect(this, (byte)7);
                    }
                    else
                    {
                        this.func_48370_a(false);
                        this.world.broadcastEntityEffect(this, (byte)6);
                    }
                }

                return true;
            }
        }
        else
        {
            if (var2 != null && (var2.id == Block.RED_ROSE.id || var2.id == Block.YELLOW_FLOWER.id) && this.datawatcher.getInt(18) < 20)
            {
                --var2.count;
                this.heal(1);

                if (var2.count <= 0)
                {
                    var1.inventory.setItem(var1.inventory.itemInHandIndex, (ItemStack)null);
                }

                return true;
            }

            if (var1.name.equalsIgnoreCase(this.getOwnerName()) && !this.world.isStatic && !this.a(var2))
            {
                this.a.func_48210_a(!this.isSitting());
                this.aZ = false;
                this.setPathEntity((PathEntity)null);
            }
        }

        return super.b(var1);
    }

    /**
     * Checks if the parameter is an wheat item.
     */
    public boolean a(ItemStack var1)
    {
        return var1 == null ? false : var1.id == Item.WHEAT.id;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int q()
    {
        return 8;
    }

    public boolean isAngry()
    {
        return (this.datawatcher.getByte(16) & 2) != 0;
    }

    public void setAngry(boolean var1)
    {
        byte var2 = this.datawatcher.getByte(16);

        if (var1)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(var2 | 2)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(var2 & -3)));
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal createChild(EntityAnimal var1)
    {
        EC_EntityFriendlyCreeper var2 = new EC_EntityFriendlyCreeper(this.world);
        var2.setOwnerName(this.getOwnerName());
        var2.setTamed(true);
        return var2;
    }

    public boolean func_48135_b(EntityAnimal var1)
    {
        if (var1 == this)
        {
            return false;
        }
        else if (!this.isTamed())
        {
            return false;
        }
        else if (!(var1 instanceof EntityWolf))
        {
            return false;
        }
        else
        {
            EntityWolf var2 = (EntityWolf)var1;
            return !var2.isTamed() ? false : (var2.isSitting() ? false : this.r_() && var2.r_());
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void F_()
    {
        if (this.isAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;
            int var1 = this.getCreeperState();

            if (var1 > 0 && this.timeSinceIgnited == 0)
            {
                this.world.makeSound(this, "random.fuse", 1.0F, 0.5F);
            }

            this.timeSinceIgnited += var1;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= 30)
            {
                this.timeSinceIgnited = 0;

                if (!this.world.isStatic)
                {
                    this.createFriendlyExplosion(this, this.locX, this.locY, this.locZ, this.getPowered() ? 6.0F : 3.0F);
                }
            }
        }

        super.F_();
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void die(DamageSource var1)
    {
        super.die(var1);

        if (var1.getEntity() instanceof EntitySkeleton)
        {
            this.b(Item.RECORD_1.id + this.random.nextInt(10), 1);
        }
    }

    public boolean getPowered()
    {
        return this.datawatcher.getByte(20) == 1;
    }

    public float setCreeperFlashTime(float var1)
    {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * var1) / 28.0F;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getLootId()
    {
        return Item.SULPHUR.id;
    }

    public int getCreeperState()
    {
        return this.datawatcher.getByte(19);
    }

    public void setCreeperState(int var1)
    {
        this.datawatcher.watch(19, Byte.valueOf((byte)var1));
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void a(EntityWeatherLighting var1)
    {
        super.a(var1);
        this.datawatcher.watch(20, Byte.valueOf((byte)1));
    }

    public EC_FriendlyExplosion createFriendlyExplosion(Entity var1, double var2, double var4, double var6, float var8)
    {
        return this.newFriendlyExplosion(var1, var2, var4, var6, var8, false);
    }

    public EC_FriendlyExplosion newFriendlyExplosion(Entity var1, double var2, double var4, double var6, float var8, boolean var9)
    {
        EC_FriendlyExplosion var10 = new EC_FriendlyExplosion(this.world, var1, var2, var4, var6, var8);
        var10.doExplosionA(this);
        var10.doExplosionB(true);
        return var10;
    }
}

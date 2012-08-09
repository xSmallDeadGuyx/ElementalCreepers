package net.minecraft.src;

import java.util.List;


public class EC_EntityFriendlyCreeper extends EntityTameable {

	private float field_25048_b;
	private float field_25054_c;

	private boolean field_25052_g;

	public EC_EntityFriendlyCreeper(World par1World) {
		super(par1World);
		texture = "/mob/friendlycreeper0.png";
		getNavigator().setAvoidsWater(true);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, aiSit);
		tasks.addTask(3, new EC_EntityAIFriendlyCreeperSwell(this));
		tasks.addTask(4, new EntityAIAttackOnCollide(this, moveSpeed, true));
		tasks.addTask(5, new EntityAIFollowOwner(this, moveSpeed, 10.0F, 2.0F));
		tasks.addTask(6, new EntityAIMate(this, moveSpeed));
		tasks.addTask(7, new EntityAIWander(this, moveSpeed));
		tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(10, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		tasks.addTask(11, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
	}

	public boolean isAIEnabled() {
		return true;
	}

	public void setAttackTarget(EntityLiving par1EntityLiving) {
		super.setAttackTarget(par1EntityLiving);

		if (par1EntityLiving instanceof EntityPlayer)
			setAngry(true);
	}

	protected void updateAITick() {
		dataWatcher.updateObject(18, Integer.valueOf(getHealth()));
	}

	public int getMaxHealth() {
		return isTamed() ? 20 : 8;
	}

	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, new Integer(getHealth()));
		dataWatcher.addObject(19, Byte.valueOf((byte) - 1));
		dataWatcher.addObject(20, Byte.valueOf((byte)0));
	}

	protected boolean canTriggerWalking() {
		return true;
	}

	public String getTexture() {
		return isTamed() ? "/mob/friendlycreeper1.png" : "/mob/friendlycreeper0.png";
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Angry", isAngry());
		if (dataWatcher.getWatchableObjectByte(20) == 1)
			par1NBTTagCompound.setBoolean("powered", true);
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
		setAngry(par1NBTTagCompound.getBoolean("Angry"));
		dataWatcher.updateObject(20, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));
	}

	protected boolean canDespawn() {
		return isAngry();
	}

	protected String getHurtSound() {
		return "mob.creeper";
	}

	protected String getDeathSound() {
		return "mob.creeperdeath";
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	public float getEyeHeight() {
		return height * 0.8F;
	}

	public int getVerticalFaceSpeed() {
		return isSitting() ? 20 : super.getVerticalFaceSpeed();
	}

	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		Entity var3 = par1DamageSource.getEntity();
		aiSit.setSitting(false);

		if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow))
			par2 = (par2 + 1) / 2;

		return super.attackEntityFrom(par1DamageSource, par2);
	}

	public boolean attackEntityAsMob(Entity par1Entity) {
		return false;
	}

	public boolean interact(EntityPlayer par1EntityPlayer) {
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

		if (!isTamed()) {
			if (var2 != null && var2.itemID == Item.gunpowder.shiftedIndex && !isAngry() && !isTamed()) {
				var2.stackSize--;

				if (var2.stackSize <= 0)
					par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);

				if (!worldObj.isRemote) {
					if (rand.nextInt(3) == 0) {
						setTamed(true);
						setPathToEntity((PathEntity)null);
						setAttackTarget((EntityLiving)null);
						aiSit.setSitting(true);
						setEntityHealth(20);
						setOwner(par1EntityPlayer.username);
						playTameEffect(true);
						worldObj.setEntityState(this, (byte)7);
					}
					else {
						playTameEffect(false);
						worldObj.setEntityState(this, (byte)6);
					}
				}

				return true;
			}
		}
		else {
			if (var2 != null && (var2.itemID == Block.plantRed.blockID || var2.itemID == Block.plantYellow.blockID)) {
				if (dataWatcher.getWatchableObjectInt(18) < 20) {
					var2.stackSize--;
					heal(1);

					if (var2.stackSize <= 0)
						par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);

					return true;
				}
			}

			if (par1EntityPlayer.username.equalsIgnoreCase(getOwnerName()) && !worldObj.isRemote && !isWheat(var2)) {
				aiSit.setSitting(!isSitting());
				isJumping = false;
				setPathToEntity((PathEntity)null);
			}
		}

		return super.interact(par1EntityPlayer);
	}

	public boolean isWheat(ItemStack par1ItemStack) {
		return par1ItemStack == null ? false : par1ItemStack.itemID == Item.wheat.shiftedIndex;
	}

	public int getMaxSpawnedInChunk() {
		return 8;
	}

	public boolean isAngry() {
		return (dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	}

	public void setAngry(boolean par1) {
		byte var2 = dataWatcher.getWatchableObjectByte(16);

		if (par1)
			dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
		else
			dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
	}

	public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal) {
		EC_EntityFriendlyCreeper var2 = new EC_EntityFriendlyCreeper(worldObj);
		var2.setOwner(getOwnerName());
		var2.setTamed(true);
		return var2;
	}

	public boolean func_48135_b(EntityAnimal par1EntityAnimal) {
		if (par1EntityAnimal == this)
			return false;
		else if (!isTamed())
			return false;
		else if (!(par1EntityAnimal instanceof EntityWolf))
			return false;
		else {
			EntityWolf var2 = (EntityWolf)par1EntityAnimal;
			return !var2.isTamed() ? false : (var2.isSitting() ? false : isInLove() && var2.isInLove());
		}
	}

	int timeSinceIgnited;
	int lastActiveTime;

	@Override
	public void onUpdate() {
		if(isEntityAlive()) {
			lastActiveTime = timeSinceIgnited;
			int var1 = getCreeperState();

			if (var1 > 0 && timeSinceIgnited == 0)
				worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);

			timeSinceIgnited += var1;

			if (timeSinceIgnited < 0)
				timeSinceIgnited = 0;

			if (timeSinceIgnited >= 30) {
				timeSinceIgnited = 0;

				if (!worldObj.isRemote)
					createFriendlyExplosion(this, posX, posY, posZ, getPowered() ? 6.0F : 3.0F);
			}
		}

		super.onUpdate();
		if(isSitting()) this.rotationPitch = 45.0F;
	}

	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);

		if (par1DamageSource.getEntity() instanceof EntitySkeleton)
			dropItem(Item.record13.shiftedIndex + rand.nextInt(10), 1);
	}

	public boolean getPowered() {
		return dataWatcher.getWatchableObjectByte(20) == 1;
	}

	public float setCreeperFlashTime(float par1) {
		return ((float)lastActiveTime + (float)(timeSinceIgnited - lastActiveTime) * par1) / 28.0F;
	}

	protected int getDropItemId() {
		return Item.gunpowder.shiftedIndex;
	}

	public int getCreeperState() {
		return dataWatcher.getWatchableObjectByte(19);
	}

	public void setCreeperState(int par1) {
		dataWatcher.updateObject(19, Byte.valueOf((byte)par1));
	}

	public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
		super.onStruckByLightning(par1EntityLightningBolt);
		dataWatcher.updateObject(20, Byte.valueOf((byte)1));
	}
	
	public EC_FriendlyExplosion createFriendlyExplosion(Entity entity, double d, double d1, double d2, float f) {
		return newFriendlyExplosion(entity, d, d1, d2, f, false);
	}

	public EC_FriendlyExplosion newFriendlyExplosion(Entity entity, double d, double d1, double d2, float f, boolean flag) {
		EC_FriendlyExplosion explosion = new EC_FriendlyExplosion(worldObj, entity, d, d1, d2, f);
		explosion.doExplosionA(this);
		explosion.doExplosionB(true);
		return explosion;
	}
}

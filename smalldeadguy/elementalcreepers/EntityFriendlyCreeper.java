package ElementalCreepers.smalldeadguy.elementalcreepers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCloth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityTameable;

public class EntityFriendlyCreeper extends EntityTameable {
	private float field_70926_e;
	private float field_70924_f;

	private int lastActiveTime;

	private int timeSinceIgnited;
	private int fuseTime = 30;
	private int explosionRadius = 3;

	public EntityFriendlyCreeper(World par1World) {
		super(par1World);
		this.texture = "/mob/friendlycreeper0.png";
		this.moveSpeed = 0.2F;
		this.setSize(1.0F, 2.0F);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, new EntityAIFriendlyCreeperSwell(this));
		this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
		this.tasks.addTask(5, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
		this.tasks.addTask(6, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
		this.tasks.addTask(7, new EntityAIMate(this, this.moveSpeed));
		this.tasks.addTask(8, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(10, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
	}

	public boolean isAIEnabled() {
		return true;
	}

	protected void fall(float par1) {
		super.fall(par1);
		this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + par1 * 1.5F);

		if (this.timeSinceIgnited > this.fuseTime - 5)
			this.timeSinceIgnited = this.fuseTime - 5;
	}

	public void setAttackTarget(EntityLiving par1EntityLiving) {
		super.setAttackTarget(par1EntityLiving);
		if (par1EntityLiving instanceof EntityPlayer)
			this.setAngry(true);
	}

	protected void updateAITick() {
		this.dataWatcher.updateObject(18, Integer.valueOf(this.getHealth()));
	}

	public int getMaxHealth() {
		return 20;
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(20, Byte.valueOf((byte) - 1));
		this.dataWatcher.addObject(21, Byte.valueOf((byte)0));
		this.dataWatcher.addObject(18, new Integer(this.getHealth()));
		this.dataWatcher.addObject(19, new Byte((byte)0));
	}

	@SideOnly(Side.CLIENT)
	public String getTexture() {
		return this.isTamed() ? "/mob/friendlycreeper1.png" : "/mob/friendlycreeper0.png";
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Angry", this.isAngry());

		if(this.dataWatcher.getWatchableObjectByte(21) == 1)
			par1NBTTagCompound.setBoolean("powered", true);

		par1NBTTagCompound.setShort("Fuse", (short)this.fuseTime);
		par1NBTTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
		this.setAngry(par1NBTTagCompound.getBoolean("Angry"));

		this.dataWatcher.updateObject(21, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));

		if(par1NBTTagCompound.hasKey("Fuse"))
			this.fuseTime = par1NBTTagCompound.getShort("Fuse");

		if(par1NBTTagCompound.hasKey("ExplosionRadius"))
			this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
	}

	protected boolean canDespawn() {
		return this.isAngry();
	}

	protected String getHurtSound() {
		return "mob.creeper.say";
	}

	protected String getDeathSound() {
		return "mob.creeper.death";
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	protected int getDropItemId() {
		return Item.gunpowder.itemID;
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	public void onUpdate() {
		if(this.isEntityAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;
			int i = this.getCreeperState();

			if(i > 0 && this.timeSinceIgnited == 0)
				this.playSound("random.fuse", 1.0F, 0.5F);

			this.timeSinceIgnited += i;

			if(this.timeSinceIgnited < 0)
				this.timeSinceIgnited = 0;

			if(this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;

				if(!this.worldObj.isRemote) {
					boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

					if(this.getPowered())
						createFriendlyExplosion(this, this.posX, this.posY, this.posZ, (float)(this.explosionRadius * 2), flag);
					else
						createFriendlyExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, flag);
				}
				
				this.timeSinceIgnited = 0;
				this.lastActiveTime = 0;
				this.setCreeperState(-1);
			}
		}
		if(isSitting()) this.rotationPitch = 45.0F;

		super.onUpdate();
		this.field_70924_f = this.field_70926_e;

		if (this.func_70922_bv())
			this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
		else
			this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;

		if (this.func_70922_bv())
			this.numTicksToChaseTarget = 10;
	}

	public boolean getPowered() {
		return this.dataWatcher.getWatchableObjectByte(21) == 1;
	}

	@SideOnly(Side.CLIENT)
	public float getCreeperFlashIntensity(float par1) {
		return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * par1) / (float)(this.fuseTime - 2);
	}

	public int getCreeperState() {
		return this.dataWatcher.getWatchableObjectByte(20);
	}

	public void setCreeperState(int par1) {
		this.dataWatcher.updateObject(20, Byte.valueOf((byte)par1));
	}

	public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
		super.onStruckByLightning(par1EntityLightningBolt);
		this.dataWatcher.updateObject(21, Byte.valueOf((byte)1));
	}

	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);

		if (par1DamageSource.getEntity() instanceof EntitySkeleton) {
			int i = Item.record13.itemID + this.rand.nextInt(Item.recordWait.itemID - Item.record13.itemID + 1);
			this.dropItem(i, 1);
		}
	}

	@SideOnly(Side.CLIENT)
	public float getInterestedAngle(float par1) {
		return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * par1) * 0.15F * (float)Math.PI;
	}

	public float getEyeHeight() {
		return this.height * 0.8F;
	}

	public int getVerticalFaceSpeed() {
		return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
	}

	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		if (this.isEntityInvulnerable())
			return false;
		else {
			Entity entity = par1DamageSource.getEntity();
			this.aiSit.setSitting(false);

			if(entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
				par2 = (par2 + 1) / 2;

			return super.attackEntityFrom(par1DamageSource, par2);
		}
	}

	public boolean attackEntityAsMob(Entity par1Entity) {
		int i = this.isTamed() ? 4 : 2;
		return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
	}

	public boolean interact(EntityPlayer par1EntityPlayer) {
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

		if(this.isTamed()) {
			if(itemstack != null) {
				if(Item.itemsList[itemstack.itemID] instanceof ItemFood) {
					ItemFood itemfood = (ItemFood)Item.itemsList[itemstack.itemID];
					if(this.dataWatcher.getWatchableObjectInt(18) < 20) {
						if(!par1EntityPlayer.capabilities.isCreativeMode)
							--itemstack.stackSize;

						this.heal(itemfood.getHealAmount());

						if (itemstack.stackSize <= 0)
							par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);

						return true;
					}
				}
			}

			if (par1EntityPlayer.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack)) {
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.setPathToEntity((PathEntity)null);
			}
		}
		else if(itemstack != null && itemstack.itemID == Item.gunpowder.itemID && !this.isAngry()) {
			if(!par1EntityPlayer.capabilities.isCreativeMode)
				--itemstack.stackSize;

			if(itemstack.stackSize <= 0)
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);

			if(!this.worldObj.isRemote) {
				if(this.rand.nextInt(3) == 0) {
					this.setTamed(true);
					this.setPathToEntity((PathEntity)null);
					this.setAttackTarget((EntityLiving)null);
					this.aiSit.setSitting(true);
					this.setEntityHealth(20);
					this.setOwner(par1EntityPlayer.username);
					this.playTameEffect(true);
					this.worldObj.setEntityState(this, (byte)7);
				}
				else {
					this.playTameEffect(false);
					this.worldObj.setEntityState(this, (byte)6);
				}
			}

			return true;
		}

		return super.interact(par1EntityPlayer);
	}

	public boolean isBreedingItem(ItemStack par1ItemStack) {
		return par1ItemStack != null && (par1ItemStack.getItem().itemID == Block.plantRed.blockID || par1ItemStack.getItem().itemID == Block.plantYellow.blockID);
	}

	public int getMaxSpawnedInChunk() {
		return 3;
	}

	public boolean isAngry() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	}

	public void setAngry(boolean par1) {
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);

		if (par1)
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 2)));
		else
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -3)));
	}

	public EntityFriendlyCreeper spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
		EntityFriendlyCreeper entity = new EntityFriendlyCreeper(this.worldObj);
		String s = this.getOwnerName();

		if (s != null && s.trim().length() > 0) {
			entity.setOwner(s);
			entity.setTamed(true);
		}

		return entity;
	}

	public void func_70918_i(boolean par1) {
		byte b0 = this.dataWatcher.getWatchableObjectByte(19);

		if (par1)
			this.dataWatcher.updateObject(19, Byte.valueOf((byte)1));
		else
			this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
	}

	public boolean canMateWith(EntityAnimal par1EntityAnimal) {
		if (par1EntityAnimal == this)
			return false;
		else if (!this.isTamed())
			return false;
		else if (!(par1EntityAnimal instanceof EntityFriendlyCreeper))
			return false;
		else {
			EntityFriendlyCreeper entity = (EntityFriendlyCreeper)par1EntityAnimal;
			return !entity.isTamed() ? false : (entity.isSitting() ? false : this.isInLove() && entity.isInLove());
		}
	}

	public boolean func_70922_bv() {
		return this.dataWatcher.getWatchableObjectByte(19) == 1;
	}

	public EntityAgeable createChild(EntityAgeable par1EntityAgeable) {
		return this.spawnBabyAnimal(par1EntityAgeable);
	}

	public FriendlyExplosion createFriendlyExplosion(EntityFriendlyCreeper entity, double x, double y, double z, float radius, boolean flag) {
		FriendlyExplosion explosion = new FriendlyExplosion(this.worldObj, this, x, y, z, radius);
		explosion.isSmoking = true;
		explosion.doExplosionA();
		explosion.doExplosionB(true);
		return explosion;
	}
}

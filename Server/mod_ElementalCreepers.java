package net.minecraft.src;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.Map;

public class mod_ElementalCreepers extends BaseModMp {

	@MLProp public static int waterCreeperSpawn = 5;
	@MLProp public static int fireCreeperSpawn = 5;
	@MLProp public static int iceCreeperSpawn = 5;
	@MLProp public static int electricCreeperSpawn = 5;
	@MLProp public static int earthCreeperSpawn = 5;
	@MLProp public static int psychicCreeperSpawn = 5;
	@MLProp public static int cookieCreeperSpawn = 1;
	@MLProp public static int magmaCreeperSpawn = 3;
	@MLProp public static int friendlyCreeperSpawn = 1;
	@MLProp public static int illusionCreeperSpawn = 3;
	@MLProp public static int lightCreeperSpawn = 2;
	@MLProp public static int darkCreeperSpawn = 5;
	@MLProp public static int reverseCreeperSpawn = 3;
	
	@MLProp public static int waterCreeperRadius = 4;
	@MLProp public static int fireCreeperRadius = 6;
	@MLProp public static int iceCreeperRadius = 12;
	@MLProp public static int electricCreeperRadius = 5;
	@MLProp public static int earthCreeperRadius = 8;
	@MLProp public static int psychicCreeperRadius = 5;
	@MLProp public static int psychicCreeperPower = 8;
	@MLProp public static int cookieCreeperAmount = 25;
	@MLProp public static int magmaCreeperRadius = 3;
	@MLProp public static int ghostCreeperRadius = 5;
	@MLProp public static int ghostCreeperChance = 35;
	@MLProp public static int lightCreeperRadius = 4;
	@MLProp public static int darkCreeperRadius = 12;
	@MLProp public static int reverseCreeperRadius = 8;
	
	@MLProp public static int waterCreeperId = 12;
	@MLProp public static int fireCreeperId = 13;
	@MLProp public static int iceCreeperId = 14;
	@MLProp public static int electricCreeperId = 15;
	@MLProp public static int earthCreeperId = 16;
	@MLProp public static int psychicCreeperId = 17;
	@MLProp public static int cookieCreeperId = 8;
	@MLProp public static int magmaCreeperId = 19;
	@MLProp public static int ghostCreeperId = 20;
	@MLProp public static int friendlyCreeperId = 21;
	@MLProp public static int illusionCreeperId = 22;
	@MLProp public static int fakeIllusionCreeperId = 23;
	@MLProp public static int lightCreeperId = 24;
	@MLProp public static int darkCreeperId = 25;
	@MLProp public static int reverseCreeperId = 26;
	
	@Override
	public String getVersion() {
		return "1.3.2";
	}

	@Override
	public void load() {
		// COMMENT THIS LINE BEFORE RELEASE
		// ModLoader.addRecipe(new ItemStack(Block.mobSpawner), new Object[] {"x", Character.valueOf('x'), Block.dirt});
		
		ModLoader.registerEntityID(EC_EntityWaterCreeper.class, "WaterCreeper", waterCreeperId);
		ModLoader.registerEntityID(EC_EntityFireCreeper.class, "FireCreeper", fireCreeperId);
		ModLoader.registerEntityID(EC_EntityIceCreeper.class, "IceCreeper", iceCreeperId);
		ModLoader.registerEntityID(EC_EntityElectricCreeper.class, "ElectricCreeper", electricCreeperId);
		ModLoader.registerEntityID(EC_EntityEarthCreeper.class, "EarthCreeper", earthCreeperId);
		ModLoader.registerEntityID(EC_EntityPsychicCreeper.class, "PsychicCreeper", psychicCreeperId);
		ModLoader.registerEntityID(EC_EntityCookieCreeper.class, "CookieCreeper", cookieCreeperId);
		ModLoader.registerEntityID(EC_EntityMagmaCreeper.class, "MagmaCreeper", magmaCreeperId);
		ModLoader.registerEntityID(EC_EntityGhostCreeper.class, "GhostCreeper", ghostCreeperId);
		ModLoader.registerEntityID(EC_EntityFriendlyCreeper.class, "FriendlyCreeper", friendlyCreeperId);
		ModLoader.registerEntityID(EC_EntityIllusionCreeper.class, "IllusionCreeper", illusionCreeperId);
		ModLoader.registerEntityID(EC_EntityFakeIllusionCreeper.class, "FakeIllusionCreeper", fakeIllusionCreeperId);
		ModLoader.registerEntityID(EC_EntityLightCreeper.class, "LightCreeper", lightCreeperId);
		ModLoader.registerEntityID(EC_EntityDarkCreeper.class, "DarkCreeper", darkCreeperId);
		ModLoader.registerEntityID(EC_EntityReverseCreeper.class, "ReverseCreeper", reverseCreeperId);
		
		if(waterCreeperSpawn > 0) ModLoader.addSpawn("WaterCreeper", waterCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(fireCreeperSpawn > 0) ModLoader.addSpawn("FireCreeper", fireCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(iceCreeperSpawn > 0) ModLoader.addSpawn("IceCreeper", iceCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(electricCreeperSpawn > 0) ModLoader.addSpawn("ElectricCreeper", electricCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(earthCreeperSpawn > 0) ModLoader.addSpawn("EarthCreeper", earthCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(psychicCreeperSpawn > 0) ModLoader.addSpawn("PsychicCreeper", psychicCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(cookieCreeperSpawn > 0) ModLoader.addSpawn("CookieCreeper", cookieCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(magmaCreeperSpawn > 0) ModLoader.addSpawn("MagmaCreeper", magmaCreeperSpawn, 1, 2, EnumCreatureType.monster, new BiomeGenBase[] {BiomeGenBase.hell});
		if(friendlyCreeperSpawn > 0) ModLoader.addSpawn("FriendlyCreeper", friendlyCreeperSpawn, 1, 1, EnumCreatureType.creature);
		if(illusionCreeperSpawn > 0) ModLoader.addSpawn("IllusionCreeper", illusionCreeperSpawn, 1, 1, EnumCreatureType.monster);
		if(lightCreeperSpawn > 0) ModLoader.addSpawn("LightCreeper", lightCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(darkCreeperSpawn > 0) ModLoader.addSpawn("DarkCreeper", darkCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(reverseCreeperSpawn > 0) ModLoader.addSpawn("ReverseCreeper", reverseCreeperSpawn, 1, 3, EnumCreatureType.monster);
	
		EntityList.entityEggs.put(Integer.valueOf(waterCreeperId), new EntityEggInfo(waterCreeperId, 894731, new Color(59, 115, 205).getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(fireCreeperId), new EntityEggInfo(fireCreeperId, 894731, new Color(227, 111, 24).getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(iceCreeperId), new EntityEggInfo(iceCreeperId, 894731, Color.white.getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(electricCreeperId), new EntityEggInfo(electricCreeperId, 894731, new Color(251, 234, 57).getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(earthCreeperId), new EntityEggInfo(earthCreeperId, 894731, new Color(93, 50, 0).getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(psychicCreeperId), new EntityEggInfo(psychicCreeperId, 894731, new Color(121, 51, 142).getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(cookieCreeperId), new EntityEggInfo(cookieCreeperId, 894731, new Color(202, 147, 98).getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(magmaCreeperId), new EntityEggInfo(magmaCreeperId, 894731, new Color(165, 0, 16).getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(friendlyCreeperId), new EntityEggInfo(friendlyCreeperId, 894731, new Color(215, 113, 211).getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(illusionCreeperId), new EntityEggInfo(illusionCreeperId, 894731, new Color(158, 158, 158).getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(lightCreeperId), new EntityEggInfo(lightCreeperId, 894731, new Color(255, 244, 125).getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(darkCreeperId), new EntityEggInfo(darkCreeperId, 894731, new Color(50, 50, 50).getRGB()));
		EntityList.entityEggs.put(Integer.valueOf(reverseCreeperId), new EntityEggInfo(reverseCreeperId, 0, 894731));
		
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, waterCreeperId), new Object[] {Item.bucketWater, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, fireCreeperId), new Object[] {Item.flintAndSteel, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, iceCreeperId), new Object[] {Item.snowball, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, electricCreeperId), new Object[] {Item.redstone, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, earthCreeperId), new Object[] {Block.dirt, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, psychicCreeperId), new Object[] {Item.feather, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, cookieCreeperId), new Object[] {Item.cookie, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, magmaCreeperId), new Object[] {Item.bucketLava, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, friendlyCreeperId), new Object[] {Item.sugar, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, illusionCreeperId), new Object[] {Item.compass, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, lightCreeperId), new Object[] {Item.lightStoneDust, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, darkCreeperId), new Object[] {Item.coal, new ItemStack(Item.monsterPlacer, 1, 50)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, reverseCreeperId), new Object[] {Item.eyeOfEnder, new ItemStack(Item.monsterPlacer, 1, 50)});
	}
}
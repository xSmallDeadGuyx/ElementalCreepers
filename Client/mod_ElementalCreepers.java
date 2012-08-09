package net.minecraft.src;

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
		return "1.3.1";
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
	}

	@Override
	public void addRenderer(Map map) {
		map.put(EC_EntityWaterCreeper.class, new RenderCreeper());
		map.put(EC_EntityFireCreeper.class, new RenderCreeper());
		map.put(EC_EntityIceCreeper.class, new RenderCreeper());
		map.put(EC_EntityElectricCreeper.class, new RenderCreeper());
		map.put(EC_EntityEarthCreeper.class, new RenderCreeper());
		map.put(EC_EntityPsychicCreeper.class, new RenderCreeper());
		map.put(EC_EntityCookieCreeper.class, new RenderCreeper());
		map.put(EC_EntityMagmaCreeper.class, new RenderCreeper());
		map.put(EC_EntityGhostCreeper.class, new EC_RenderGhostCreeper());
		map.put(EC_EntityFriendlyCreeper.class, new EC_RenderFriendlyCreeper());
		map.put(EC_EntityIllusionCreeper.class, new RenderCreeper());
		map.put(EC_EntityFakeIllusionCreeper.class, new RenderCreeper());
		map.put(EC_EntityLightCreeper.class, new RenderCreeper());
		map.put(EC_EntityDarkCreeper.class, new RenderCreeper());
		map.put(EC_EntityReverseCreeper.class, new RenderCreeper());
    }
}
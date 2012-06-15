package net.minecraft.src;

import java.lang.reflect.Method;
import java.util.Map;

import net.minecraft.src.forge.*;

public class mod_ElementalCreepers extends NetworkMod {

	@MLProp public static int waterCreeperSpawn = 5;
	@MLProp public static int fireCreeperSpawn = 5;
	@MLProp public static int iceCreeperSpawn = 5;
	@MLProp public static int electricCreeperSpawn = 5;
	@MLProp public static int earthCreeperSpawn = 5;
	@MLProp public static int psychicCreeperSpawn = 5;
	@MLProp public static int cookieCreeperSpawn = 1;
	@MLProp public static int magmaCreeperSpawn = 5;
	@MLProp public static int friendlyCreeperSpawn = 1;
	@MLProp public static int illusionCreeperSpawn = 2;
	@MLProp public static int lightCreeperSpawn = 5;
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
	@MLProp public static int lightCreeperRadius = 8;
	@MLProp public static int darkCreeperRadius = 12;
	@MLProp public static int reverseCreeperRadius = 8;

	@MLProp public static int waterCreeperID = 12;
	@MLProp public static int fireCreeperID = 13;
	@MLProp public static int iceCreeperID = 14;
	@MLProp public static int electricCreeperID = 15;
	@MLProp public static int earthCreeperID = 16;
	@MLProp public static int psychicCreeperID = 17;
	@MLProp public static int cookieCreeperID = 18;
	@MLProp public static int magmaCreeperID = 19; 
	@MLProp public static int ghostCreeperID = 20;
	@MLProp public static int friendlyCreeperID = 21;
	@MLProp public static int illusionCreeperID = 22;
	@MLProp public static int fakeIllusionCreeperID = 23;
	@MLProp public static int lightCreeperID = 24;
	@MLProp public static int darkCreeperID = 25;
	@MLProp public static int reverseCreeperID = 26;


	
	@Override
	public String getVersion() {
		return "1.2.5";
	}

	@Override
	public void load() {
		ModLoader.registerEntityID(EC_EntityWaterCreeper.class, "WaterCreeper", waterCreeperID);
		ModLoader.registerEntityID(EC_EntityFireCreeper.class, "FireCreeper", fireCreeperID);
		ModLoader.registerEntityID(EC_EntityIceCreeper.class, "IceCreeper", iceCreeperID);
		ModLoader.registerEntityID(EC_EntityElectricCreeper.class, "ElectricCreeper", electricCreeperID);
		ModLoader.registerEntityID(EC_EntityEarthCreeper.class, "EarthCreeper", earthCreeperID);
		ModLoader.registerEntityID(EC_EntityPsychicCreeper.class, "PsychicCreeper", psychicCreeperID);
		ModLoader.registerEntityID(EC_EntityCookieCreeper.class, "CookieCreeper", cookieCreeperID);
		ModLoader.registerEntityID(EC_EntityMagmaCreeper.class, "MagmaCreeper", magmaCreeperID);
		ModLoader.registerEntityID(EC_EntityGhostCreeper.class, "GhostCreeper", ghostCreeperID);
		ModLoader.registerEntityID(EC_EntityFriendlyCreeper.class, "FriendlyCreeper", friendlyCreeperID);
		ModLoader.registerEntityID(EC_EntityIllusionCreeper.class, "IllusionCreeper", illusionCreeperID);
		ModLoader.registerEntityID(EC_EntityFakeIllusionCreeper.class, "FakeIllusionCreeper", fakeIllusionCreeperID);
		ModLoader.registerEntityID(EC_EntityLightCreeper.class, "LightCreeper", lightCreeperID);
		ModLoader.registerEntityID(EC_EntityDarkCreeper.class, "DarkCreeper", darkCreeperID);
		ModLoader.registerEntityID(EC_EntityReverseCreeper.class, "ReverseCreeper", reverseCreeperID);
		
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
    public boolean clientSideRequired()
    {
            return true;
    }

    @Override
    public boolean serverSideRequired()
    {
            return false;
    }

}

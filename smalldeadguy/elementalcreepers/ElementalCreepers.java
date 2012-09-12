package smalldeadguy.elementalcreepers;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.Map;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.EntityEggInfo;
import net.minecraft.src.EntityList;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderCreeper;
import net.minecraftforge.common.Configuration;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "ElementalCreepers", name = "ElementalCreepers", version = "2.5")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ElementalCreepers {

	@Instance
	public static ElementalCreepers instance;

	@SidedProxy(clientSide="smalldeadguy.elementalcreepers.ClientProxy", serverSide="smalldeadguy.elementalcreepers.CommonProxy")
	public static CommonProxy proxy;

	public static int waterCreeperSpawn = 5;
	public static int fireCreeperSpawn = 5;
	public static int iceCreeperSpawn = 5;
	public static int electricCreeperSpawn = 5;
	public static int earthCreeperSpawn = 5;
	public static int psychicCreeperSpawn = 5;
	public static int cookieCreeperSpawn = 1;
	public static int magmaCreeperSpawn = 3;
	public static int friendlyCreeperSpawn = 1;
	public static int illusionCreeperSpawn = 3;
	public static int lightCreeperSpawn = 2;
	public static int darkCreeperSpawn = 5;
	public static int reverseCreeperSpawn = 3;

	public static int waterCreeperRadius = 4;
	public static int fireCreeperRadius = 6;
	public static int iceCreeperRadius = 12;
	public static int electricCreeperRadius = 5;
	public static int earthCreeperRadius = 8;
	public static int psychicCreeperRadius = 5;
	public static int psychicCreeperPower = 8;
	public static int cookieCreeperAmount = 25;
	public static int magmaCreeperRadius = 3;
	public static int ghostCreeperRadius = 5;
	public static int ghostCreeperChance = 35;
	public static int lightCreeperRadius = 4;
	public static int darkCreeperRadius = 12;
	public static int reverseCreeperRadius = 8;

	@PreInit
	public void preload(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		waterCreeperSpawn = config.getOrCreateIntProperty("waterCreeperSpawn", Configuration.CATEGORY_GENERAL, 5).getInt();
		fireCreeperSpawn = config.getOrCreateIntProperty("fireCreeperSpawn", Configuration.CATEGORY_GENERAL, 5).getInt();
		iceCreeperSpawn = config.getOrCreateIntProperty("iceCreeperSpawn", Configuration.CATEGORY_GENERAL, 5).getInt();
		electricCreeperSpawn = config.getOrCreateIntProperty("electricCreeperSpawn", Configuration.CATEGORY_GENERAL, 5).getInt();
		earthCreeperSpawn = config.getOrCreateIntProperty("earthCreeperSpawn", Configuration.CATEGORY_GENERAL, 5).getInt();
		psychicCreeperSpawn = config.getOrCreateIntProperty("psychicCreeperSpawn", Configuration.CATEGORY_GENERAL, 5).getInt();
		cookieCreeperSpawn = config.getOrCreateIntProperty("cookieCreeperSpawn", Configuration.CATEGORY_GENERAL, 5).getInt();
		magmaCreeperSpawn = config.getOrCreateIntProperty("magmaCreeperSpawn", Configuration.CATEGORY_GENERAL, 3).getInt();
		friendlyCreeperSpawn = config.getOrCreateIntProperty("friendlyCreeperSpawn", Configuration.CATEGORY_GENERAL, 1).getInt();
		illusionCreeperSpawn = config.getOrCreateIntProperty("illusionCreeperSpawn", Configuration.CATEGORY_GENERAL, 3).getInt();
		lightCreeperSpawn = config.getOrCreateIntProperty("lightCreeperSpawn", Configuration.CATEGORY_GENERAL, 2).getInt();
		darkCreeperSpawn = config.getOrCreateIntProperty("darkCreeperSpawn", Configuration.CATEGORY_GENERAL, 5).getInt();
		reverseCreeperSpawn = config.getOrCreateIntProperty("reverseCreeperSpawn", Configuration.CATEGORY_GENERAL, 3).getInt();

		waterCreeperRadius = config.getOrCreateIntProperty("waterCreeperRadius", Configuration.CATEGORY_GENERAL, 4).getInt();
		fireCreeperRadius = config.getOrCreateIntProperty("fireCreeperRadius", Configuration.CATEGORY_GENERAL, 6).getInt();
		iceCreeperRadius = config.getOrCreateIntProperty("iceCreeperRadius", Configuration.CATEGORY_GENERAL, 12).getInt();
		electricCreeperRadius = config.getOrCreateIntProperty("electricCreeperRadius", Configuration.CATEGORY_GENERAL, 5).getInt();
		earthCreeperRadius = config.getOrCreateIntProperty("earthCreeperRadius", Configuration.CATEGORY_GENERAL, 8).getInt();
		psychicCreeperRadius = config.getOrCreateIntProperty("psychicCreeperRadius", Configuration.CATEGORY_GENERAL, 5).getInt();
		psychicCreeperPower = config.getOrCreateIntProperty("psychicCreeperPower", Configuration.CATEGORY_GENERAL, 8).getInt();
		cookieCreeperAmount = config.getOrCreateIntProperty("cookieCreeperAmount", Configuration.CATEGORY_GENERAL, 25).getInt();
		magmaCreeperRadius = config.getOrCreateIntProperty("magmaCreeperRadius", Configuration.CATEGORY_GENERAL, 3).getInt();
		ghostCreeperRadius = config.getOrCreateIntProperty("ghostCreeperRadius", Configuration.CATEGORY_GENERAL, 5).getInt();
		ghostCreeperChance = config.getOrCreateIntProperty("ghostCreeperChance", Configuration.CATEGORY_GENERAL, 35).getInt();
		lightCreeperRadius = config.getOrCreateIntProperty("lightCreeperRadius", Configuration.CATEGORY_GENERAL, 4).getInt();
		darkCreeperRadius = config.getOrCreateIntProperty("darkCreeperRadius", Configuration.CATEGORY_GENERAL, 12).getInt();
		reverseCreeperRadius = config.getOrCreateIntProperty("reverseCreeperRadius", Configuration.CATEGORY_GENERAL, 8).getInt();

		config.save();
	}

	@Init
	public void load(FMLInitializationEvent event) {
		int waterCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityWaterCreeper.class, "WaterCreeper", waterCreeperId, 894731, new Color(59, 115, 205).getRGB());
		int fireCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityFireCreeper.class, "FireCreeper", fireCreeperId, 894731, new Color(227, 111, 24).getRGB());
		int iceCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityIceCreeper.class, "IceCreeper", iceCreeperId, 894731, Color.white.getRGB());
		int electricCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityElectricCreeper.class, "ElectricCreeper", electricCreeperId, 894731, new Color(251, 234, 57).getRGB());
		int earthCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityEarthCreeper.class, "EarthCreeper", earthCreeperId, 894731, new Color(93, 50, 0).getRGB());
		int psychicCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityPsychicCreeper.class, "PsychicCreeper", psychicCreeperId, 894731, new Color(121, 51, 142).getRGB());
		int cookieCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityCookieCreeper.class, "CookieCreeper", cookieCreeperId, 894731, new Color(202, 147, 98).getRGB());
		int magmaCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityMagmaCreeper.class, "MagmaCreeper", magmaCreeperId, 894731, new Color(165, 0, 16).getRGB());
		int ghostCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityGhostCreeper.class, "GhostCreeper", ghostCreeperId);
		int friendlyCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityFriendlyCreeper.class, "FriendlyCreeper", friendlyCreeperId, 894731, new Color(215, 113, 211).getRGB());
		int illusionCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityIllusionCreeper.class, "IllusionCreeper", illusionCreeperId, 894731, new Color(158, 158, 158).getRGB());
		int fakeIllusionCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityFakeIllusionCreeper.class, "FakeIllusionCreeper", fakeIllusionCreeperId);
		int lightCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityLightCreeper.class, "LightCreeper", lightCreeperId, 894731, new Color(255, 244, 125).getRGB());
		int darkCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityDarkCreeper.class, "DarkCreeper", darkCreeperId, 894731, new Color(50, 50, 50).getRGB());
		int reverseCreeperId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityReverseCreeper.class, "ReverseCreeper", reverseCreeperId, 0, 894731);

		EntityRegistry.registerModEntity(EntityWaterCreeper.class, "WaterCreeper", waterCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityFireCreeper.class, "FireCreeper", fireCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityIceCreeper.class, "IceCreeper", iceCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityElectricCreeper.class, "ElectricCreeper", electricCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityEarthCreeper.class, "EarthCreeper", earthCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityPsychicCreeper.class, "PsychicCreeper", psychicCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityCookieCreeper.class, "CookieCreeper", cookieCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityMagmaCreeper.class, "MagmaCreeper", magmaCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityGhostCreeper.class, "GhostCreeper", ghostCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityFriendlyCreeper.class, "FriendlyCreeper", friendlyCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityIllusionCreeper.class, "IllusionCreeper", illusionCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityFakeIllusionCreeper.class, "FakeIllusionCreeper", fakeIllusionCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityLightCreeper.class, "LightCreeper", lightCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityDarkCreeper.class, "DarkCreeper", darkCreeperId, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityReverseCreeper.class, "ReverseCreeper", reverseCreeperId, this, 128, 1, true);

		if(waterCreeperSpawn > 0) EntityRegistry.addSpawn("WaterCreeper", waterCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(fireCreeperSpawn > 0) EntityRegistry.addSpawn("FireCreeper", fireCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(iceCreeperSpawn > 0) EntityRegistry.addSpawn("IceCreeper", iceCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(electricCreeperSpawn > 0) EntityRegistry.addSpawn("ElectricCreeper", electricCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(earthCreeperSpawn > 0) EntityRegistry.addSpawn("EarthCreeper", earthCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(psychicCreeperSpawn > 0) EntityRegistry.addSpawn("PsychicCreeper", psychicCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(cookieCreeperSpawn > 0) EntityRegistry.addSpawn("CookieCreeper", cookieCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(magmaCreeperSpawn > 0) EntityRegistry.addSpawn("MagmaCreeper", magmaCreeperSpawn, 1, 2, EnumCreatureType.monster, new BiomeGenBase[] {BiomeGenBase.hell});
		if(friendlyCreeperSpawn > 0) EntityRegistry.addSpawn("FriendlyCreeper", friendlyCreeperSpawn, 1, 1, EnumCreatureType.creature);
		if(illusionCreeperSpawn > 0) EntityRegistry.addSpawn("IllusionCreeper", illusionCreeperSpawn, 1, 1, EnumCreatureType.monster);
		if(lightCreeperSpawn > 0) EntityRegistry.addSpawn("LightCreeper", lightCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(darkCreeperSpawn > 0) EntityRegistry.addSpawn("DarkCreeper", darkCreeperSpawn, 1, 3, EnumCreatureType.monster);
		if(reverseCreeperSpawn > 0) EntityRegistry.addSpawn("ReverseCreeper", reverseCreeperSpawn, 1, 3, EnumCreatureType.monster);

		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, waterCreeperId), new Object[] {Item.bucketWater, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, fireCreeperId), new Object[] {Item.flintAndSteel, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, iceCreeperId), new Object[] {Item.snowball, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, electricCreeperId), new Object[] {Item.redstone, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, earthCreeperId), new Object[] {Block.dirt, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, psychicCreeperId), new Object[] {Item.feather, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, cookieCreeperId), new Object[] {Item.cookie, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, magmaCreeperId), new Object[] {Item.bucketLava, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, friendlyCreeperId), new Object[] {Item.sugar, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, illusionCreeperId), new Object[] {Item.compass, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, lightCreeperId), new Object[] {Item.lightStoneDust, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, darkCreeperId), new Object[] {Item.coal, new ItemStack(Item.monsterPlacer, 1, 50)});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, reverseCreeperId), new Object[] {Item.eyeOfEnder, new ItemStack(Item.monsterPlacer, 1, 50)});

		LanguageRegistry.instance().addStringLocalization("entity.WaterCreeper.name", "Water Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.FireCreeper.name", "Fire Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.IceCreeper.name", "Ice Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.ElectricCreeper.name", "Electric Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.EarthCreeper.name", "Earth Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.PsychicCreeper.name", "Psychic Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.CookieCreeper.name", "Cookie Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.MagmaCreeper.name", "Magma Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.FriendlyCreeper.name", "Friendly Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.IllusionCreeper.name", "Illusion Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.LightCreeper.name", "Light Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.DarkCreeper.name", "Dark Creeper");
		LanguageRegistry.instance().addStringLocalization("entity.ReverseCreeper.name", "Reverse Creeper");
		
		proxy.registerRenderers();
	}
}
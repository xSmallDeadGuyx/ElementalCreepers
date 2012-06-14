package net.minecraft.src;

public class mod_ElementalCreepers extends BaseModMp
{
    @MLProp
    public static int waterCreeperSpawn = 5;
    @MLProp
    public static int fireCreeperSpawn = 5;
    @MLProp
    public static int iceCreeperSpawn = 5;
    @MLProp
    public static int electricCreeperSpawn = 5;
    @MLProp
    public static int earthCreeperSpawn = 5;
    @MLProp
    public static int psychicCreeperSpawn = 5;
    @MLProp
    public static int cookieCreeperSpawn = 1;
    @MLProp
    public static int magmaCreeperSpawn = 5;
    @MLProp
    public static int friendlyCreeperSpawn = 1;
    @MLProp
    public static int illusionCreeperSpawn = 2;
    @MLProp
    public static int lightCreeperSpawn = 5;
    @MLProp
    public static int darkCreeperSpawn = 5;
    @MLProp
    public static int reverseCreeperSpawn = 3;
    @MLProp
    public static int waterCreeperRadius = 4;
    @MLProp
    public static int fireCreeperRadius = 6;
    @MLProp
    public static int iceCreeperRadius = 12;
    @MLProp
    public static int electricCreeperRadius = 5;
    @MLProp
    public static int earthCreeperRadius = 8;
    @MLProp
    public static int psychicCreeperRadius = 5;
    @MLProp
    public static int psychicCreeperPower = 8;
    @MLProp
    public static int cookieCreeperAmount = 25;
    @MLProp
    public static int magmaCreeperRadius = 3;
    @MLProp
    public static int ghostCreeperRadius = 5;
    @MLProp
    public static int ghostCreeperChance = 35;
    @MLProp
    public static int lightCreeperRadius = 8;
    @MLProp
    public static int darkCreeperRadius = 12;
    @MLProp
    public static int reverseCreeperRadius = 8;

    public String getVersion()
    {
        return "1.2.5";
    }

    public void load()
    {
        ModLoader.registerEntityID(EC_EntityWaterCreeper.class, "WaterCreeper", 12);
        ModLoader.registerEntityID(EC_EntityFireCreeper.class, "FireCreeper", 13);
        ModLoader.registerEntityID(EC_EntityIceCreeper.class, "IceCreeper", 14);
        ModLoader.registerEntityID(EC_EntityElectricCreeper.class, "ElectricCreeper", 15);
        ModLoader.registerEntityID(EC_EntityEarthCreeper.class, "EarthCreeper", 16);
        ModLoader.registerEntityID(EC_EntityPsychicCreeper.class, "PsychicCreeper", 17);
        ModLoader.registerEntityID(EC_EntityCookieCreeper.class, "CookieCreeper", 18);
        ModLoader.registerEntityID(EC_EntityMagmaCreeper.class, "MagmaCreeper", 19);
        ModLoader.registerEntityID(EC_EntityGhostCreeper.class, "GhostCreeper", 20);
        ModLoader.registerEntityID(EC_EntityFriendlyCreeper.class, "FriendlyCreeper", 21);
        ModLoader.registerEntityID(EC_EntityIllusionCreeper.class, "IllusionCreeper", 22);
        ModLoader.registerEntityID(EC_EntityFakeIllusionCreeper.class, "FakeIllusionCreeper", 23);
        ModLoader.registerEntityID(EC_EntityLightCreeper.class, "LightCreeper", 24);
        ModLoader.registerEntityID(EC_EntityDarkCreeper.class, "DarkCreeper", 25);
        ModLoader.registerEntityID(EC_EntityReverseCreeper.class, "ReverseCreeper", 26);

        if (waterCreeperSpawn > 0)
        {
            ModLoader.addSpawn("WaterCreeper", waterCreeperSpawn, 1, 3, EnumCreatureType.monster);
        }

        if (fireCreeperSpawn > 0)
        {
            ModLoader.addSpawn("FireCreeper", fireCreeperSpawn, 1, 3, EnumCreatureType.monster);
        }

        if (iceCreeperSpawn > 0)
        {
            ModLoader.addSpawn("IceCreeper", iceCreeperSpawn, 1, 3, EnumCreatureType.monster);
        }

        if (electricCreeperSpawn > 0)
        {
            ModLoader.addSpawn("ElectricCreeper", electricCreeperSpawn, 1, 3, EnumCreatureType.monster);
        }

        if (earthCreeperSpawn > 0)
        {
            ModLoader.addSpawn("EarthCreeper", earthCreeperSpawn, 1, 3, EnumCreatureType.monster);
        }

        if (psychicCreeperSpawn > 0)
        {
            ModLoader.addSpawn("PsychicCreeper", psychicCreeperSpawn, 1, 3, EnumCreatureType.monster);
        }

        if (cookieCreeperSpawn > 0)
        {
            ModLoader.addSpawn("CookieCreeper", cookieCreeperSpawn, 1, 3, EnumCreatureType.monster);
        }

        if (magmaCreeperSpawn > 0)
        {
            ModLoader.addSpawn("MagmaCreeper", magmaCreeperSpawn, 1, 2, EnumCreatureType.monster, new BiomeBase[] {BiomeBase.HELL});
        }

        if (friendlyCreeperSpawn > 0)
        {
            ModLoader.addSpawn("FriendlyCreeper", friendlyCreeperSpawn, 1, 1, EnumCreatureType.creature);
        }

        if (illusionCreeperSpawn > 0)
        {
            ModLoader.addSpawn("IllusionCreeper", illusionCreeperSpawn, 1, 1, EnumCreatureType.monster);
        }

        if (lightCreeperSpawn > 0)
        {
            ModLoader.addSpawn("LightCreeper", lightCreeperSpawn, 1, 3, EnumCreatureType.monster);
        }

        if (darkCreeperSpawn > 0)
        {
            ModLoader.addSpawn("DarkCreeper", darkCreeperSpawn, 1, 3, EnumCreatureType.monster);
        }

        if (reverseCreeperSpawn > 0)
        {
            ModLoader.addSpawn("ReverseCreeper", reverseCreeperSpawn, 1, 3, EnumCreatureType.monster);
        }
    }
}

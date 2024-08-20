package io.github.xsmalldeadguyx.elementalcreepers.common;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ElementalCreepers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	// ==================== Explosion radius/power ====================

	private static final ForgeConfigSpec.IntValue COOKIE_CREEPER_QUANTITY = BUILDER.comment("Cookie Creeper quantity")
			.defineInRange("cookieCreeperQuantity", 25, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue DARK_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Dark Creeper explosion radius")
			.defineInRange("darkCreeperExplosionRadius", 12.d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue EARTH_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Earth Creeper explosion radius")
			.defineInRange("earthCreeperExplosionRadius", 8.d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue ELECTRIC_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Electric Creeper explosion radius")
			.defineInRange("electricCreeperExplosionRadius", 8.d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue FIRE_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Fire Creeper explosion radius")
			.defineInRange("fireCreeperExplosionRadius", 6.d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue ICE_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Ice Creeper explosion radius")
			.defineInRange("iceCreeperExplosionRadius", 10.d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue GHOST_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Ghost Creeper explosion radius")
			.defineInRange("ghostCreeperExplosionRadius", 2.5d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue LIGHT_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Light Creeper explosion radius")
			.defineInRange("lightCreeperExplosionRadius", 4.d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue MAGMA_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Magma Creeper explosion radius")
			.defineInRange("magmaCreeperExplosionRadius", 2d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue PSYCHIC_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Psychic Creeper explosion radius")
			.defineInRange("psychicCreeperExplosionRadius", 3d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue REVERSE_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Reverse Creeper explosion radius")
			.defineInRange("reverseCreeperExplosionRadius", 6d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue SPIDER_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Spider Creeper explosion radius")
			.defineInRange("spiderCreeperExplosionRadius", 7d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue WATER_CREEPER_EXPLOSION_RADIUS = BUILDER
			.comment("Water Creeper explosion radius")
			.defineInRange("waterCreeperExplosionRadius", 5.d, 0.d, Double.MAX_VALUE);

	// ==================== Explosion details ====================

	private static final ForgeConfigSpec.ConfigValue<List<? extends String>> DARK_CREEPER_DESTROY_BLOCKS = BUILDER
			.comment("A list of blocks which dark creepers destroy").defineList("darkCreeperDestroyBlocks",
					Arrays.asList("minecraft:torch", "minecraft:redstone_torch", "minecraft:redstone_lamp",
							"minecraft:redstone_wall_torch", "minecraft:glowstone", "minecraft:lantern", "minecraft:shroomlight", "minecraft:campfire",
							"minecraft:soul_campfire"),
					Config::validateBlockName);

	private static final ForgeConfigSpec.DoubleValue GHOST_CREEPER_DAMAGE_MULTIPLIER = BUILDER
			.comment("Ghost Creeper damage multiplier (compared to regular creeper)")
			.defineInRange("ghostCreeperDamageMultiplier", 0.7d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue PSYCHIC_CREEPER_LAUNCH_MULTIPLIER = BUILDER
			.comment("Psychica Creeper launch multiplier (compared to regular creeper)")
			.defineInRange("psychicCreeperLaunchMultiplier", 7d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue SPIDER_CREEPER_POISON_TIME_MEDIUM = BUILDER
			.comment("Spider Creeper poison time on medium difficulty (easy = 0.66x, hard = 1.5x)")
			.defineInRange("spiderCreeperPoisonTimeMedium", 8d, 0.d, Double.MAX_VALUE);

	private static final ForgeConfigSpec.DoubleValue WATER_CREEPER_PERMANENT_RADIUS = BUILDER
			.comment("Water Creeper permanent puddle radius")
			.defineInRange("waterCreeperPermanentRadius", 1.d, 0.d, Double.MAX_VALUE);
	
	// ==================== Spawns (<= 1.18 only) ====================
	
	private static final ForgeConfigSpec.IntValue CELEBRATION_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Celebration Creeper spawn rate in all overworld biomes")
			.defineInRange("celebrationCreeperOverworldWeight", 3, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue COOKIE_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Cookie Creeper spawn rate in all overworld biomes")
			.defineInRange("cookieCreeperOverworldWeight", 5, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue DARK_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Dark Creeper spawn rate in all overworld biomes")
			.defineInRange("darkCreeperOverworldWeight", 20, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue EARTH_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Earth Creeper spawn rate in all overworld biomes")
			.defineInRange("earthCreeperOverworldWeight", 25, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue ELECTRIC_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Electric Creeper spawn rate in all overworld biomes")
			.defineInRange("electricCreeperOverworldWeight", 20, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue FIRE_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Fire Creeper spawn rate in all overworld biomes")
			.defineInRange("fireCreeperOverworldWeight", 20, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue FRIENDLY_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Friendly Creeper spawn rate in all overworld biomes")
			.defineInRange("friendlyCreeperOverworldWeight", 2, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue ICE_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Ice Creeper spawn rate in all overworld biomes")
			.defineInRange("iceCreeperOverworldWeight", 20, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue ILLUSION_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Illusion Creeper spawn rate in all overworld biomes")
			.defineInRange("illusionCreeperOverworldWeight", 20, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue LIGHT_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Light Creeper spawn rate in all overworld biomes")
			.defineInRange("lightCreeperOverworldWeight", 8, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue PSYCHIC_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Psychic Creeper spawn rate in all overworld biomes")
			.defineInRange("psychicCreeperOverworldWeight", 20, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue REVERSE_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Reverse Creeper spawn rate in all overworld biomes")
			.defineInRange("reverseCreeperOverworldWeight", 12, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue SPIDER_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Spider Creeper spawn rate in all overworld biomes")
			.defineInRange("spiderCreeperOverworldWeight", 20, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue WATER_CREEPER_OVERWORLD_WEIGHT = BUILDER
			.comment("Water Creeper spawn rate in all overworld biomes")
			.defineInRange("waterCreeperOverworldWeight", 20, 0, Integer.MAX_VALUE);
	
	private static final ForgeConfigSpec.IntValue MAGMA_CREEPER_NETHER_WEIGHT = BUILDER
			.comment("Magma Creeper spawn rate in the nether")
			.defineInRange("magmaCreeperNetherWeight", 100, 0, Integer.MAX_VALUE);

	// ==================== Misc ====================

	private static final ForgeConfigSpec.DoubleValue GHOST_CREEPER_SPAWN_CHANCE = BUILDER
			.comment("Ghost Creeper spawn chance when an elemental creeper dies")
			.defineInRange("ghostCreeperSpawnChance", 0.01, 0, 1);
	
	public static final ForgeConfigSpec SPEC = BUILDER.build();

	public static int cookieCreeperQuantity;
	public static double darkCreeperExplosionRadius;
	public static double earthCreeperExplosionRadius;
	public static double electricCreeperExplosionRadius;
	public static double fireCreeperExplosionRadius;
	public static double iceCreeperExplosionRadius;
	public static double ghostCreeperExplosionRadius;
	public static double lightCreeperExplosionRadius;
	public static double magmaCreeperExplosionRadius;
	public static double psychicCreeperExplosionRadius;
	public static double reverseCreeperExplosionRadius;
	public static double spiderCreeperExplosionRadius;
	public static double waterCreeperExplosionRadius;

	public static Set<Block> darkCreeperDestroyBlocks;
	public static double ghostCreeperDamageMultiplier;
	public static double psychicCreeperLaunchMultiplier;
	public static double spiderCreeperPoisonTimeMedium;
	public static double waterCreeperPermanentRadius;
	
	public static int celebrationCreeperOverworldWeight;
	public static int cookieCreeperOverworldWeight;
	public static int darkCreeperOverworldWeight;
	public static int earthCreeperOverworldWeight;
	public static int electricCreeperOverworldWeight;
	public static int fireCreeperOverworldWeight;
	public static int friendlyCreeperOverworldWeight;
	public static int iceCreeperOverworldWeight;
	public static int illusionCreeperOverworldWeight;
	public static int lightCreeperOverworldWeight;
	public static int psychicCreeperOverworldWeight;
	public static int reverseCreeperOverworldWeight;
	public static int spiderCreeperOverworldWeight;
	public static int waterCreeperOverworldWeight;
	
	public static int magmaCreeperNetherWeight;

	public static double ghostCreeperSpawnChance;

	Config(ForgeConfigSpec.Builder builder) {
	}

	private static boolean validateBlockName(final Object obj) {
		if (obj instanceof String) {
			final String blockName = (String) obj;
			ElementalCreepers.LOGGER.info(
					blockName + " is valid = " + ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(blockName)));
			return ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(blockName));
		}

		return false;
	}

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		cookieCreeperQuantity = COOKIE_CREEPER_QUANTITY.get();
		darkCreeperExplosionRadius = DARK_CREEPER_EXPLOSION_RADIUS.get();
		earthCreeperExplosionRadius = EARTH_CREEPER_EXPLOSION_RADIUS.get();
		electricCreeperExplosionRadius = ELECTRIC_CREEPER_EXPLOSION_RADIUS.get();
		fireCreeperExplosionRadius = FIRE_CREEPER_EXPLOSION_RADIUS.get();
		iceCreeperExplosionRadius = ICE_CREEPER_EXPLOSION_RADIUS.get();
		ghostCreeperExplosionRadius = GHOST_CREEPER_EXPLOSION_RADIUS.get();
		lightCreeperExplosionRadius = LIGHT_CREEPER_EXPLOSION_RADIUS.get();
		magmaCreeperExplosionRadius = MAGMA_CREEPER_EXPLOSION_RADIUS.get();
		psychicCreeperExplosionRadius = PSYCHIC_CREEPER_EXPLOSION_RADIUS.get();
		reverseCreeperExplosionRadius = REVERSE_CREEPER_EXPLOSION_RADIUS.get();
		spiderCreeperExplosionRadius = SPIDER_CREEPER_EXPLOSION_RADIUS.get();
		waterCreeperExplosionRadius = WATER_CREEPER_EXPLOSION_RADIUS.get();

		darkCreeperDestroyBlocks = DARK_CREEPER_DESTROY_BLOCKS.get().stream()
				.map(blockName -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName)))
				.collect(Collectors.toSet());
		ghostCreeperDamageMultiplier = GHOST_CREEPER_DAMAGE_MULTIPLIER.get();
		psychicCreeperLaunchMultiplier = PSYCHIC_CREEPER_LAUNCH_MULTIPLIER.get();
		spiderCreeperPoisonTimeMedium = SPIDER_CREEPER_POISON_TIME_MEDIUM.get();
		waterCreeperPermanentRadius = WATER_CREEPER_PERMANENT_RADIUS.get();
		
		celebrationCreeperOverworldWeight = CELEBRATION_CREEPER_OVERWORLD_WEIGHT.get();
		cookieCreeperOverworldWeight= COOKIE_CREEPER_OVERWORLD_WEIGHT.get();
		darkCreeperOverworldWeight = DARK_CREEPER_OVERWORLD_WEIGHT.get();
		earthCreeperOverworldWeight = EARTH_CREEPER_OVERWORLD_WEIGHT.get();
		electricCreeperOverworldWeight = ELECTRIC_CREEPER_OVERWORLD_WEIGHT.get();
		fireCreeperOverworldWeight = FIRE_CREEPER_OVERWORLD_WEIGHT.get();
		friendlyCreeperOverworldWeight = FRIENDLY_CREEPER_OVERWORLD_WEIGHT.get();
		iceCreeperOverworldWeight = ICE_CREEPER_OVERWORLD_WEIGHT.get();
		illusionCreeperOverworldWeight = ILLUSION_CREEPER_OVERWORLD_WEIGHT.get();
		lightCreeperOverworldWeight = LIGHT_CREEPER_OVERWORLD_WEIGHT.get();
		psychicCreeperOverworldWeight = PSYCHIC_CREEPER_OVERWORLD_WEIGHT.get();
		reverseCreeperOverworldWeight = REVERSE_CREEPER_OVERWORLD_WEIGHT.get();
		spiderCreeperOverworldWeight = SPIDER_CREEPER_OVERWORLD_WEIGHT.get();
		waterCreeperOverworldWeight = WATER_CREEPER_OVERWORLD_WEIGHT.get();

		magmaCreeperNetherWeight = MAGMA_CREEPER_NETHER_WEIGHT.get();

		ghostCreeperSpawnChance = GHOST_CREEPER_SPAWN_CHANCE.get();
	}
}

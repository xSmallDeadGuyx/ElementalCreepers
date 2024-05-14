package io.github.xsmalldeadguyx.elementalcreepers.common;

import java.awt.Color;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.xsmalldeadguyx.elementalcreepers.common.entity.CelebrationCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.CookieCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.DarkCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.EarthCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.ElectricCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.ElementalCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.FakeIllusionCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.FireCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.FriendlyCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.GhostCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.IceCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.IllusionCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.LightCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.MagmaCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.PsychicCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.ReverseCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.SpiderCreeper;
import io.github.xsmalldeadguyx.elementalcreepers.common.entity.WaterCreeper;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(ElementalCreepers.MODID)
public class ElementalCreepers {

	public static final String MODID = "elementalcreepers";

	public static final Logger LOGGER = LogManager.getLogger();

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			MODID);

	public static final ItemGroup CREATIVE_MODE_TAB = new ItemGroup(MODID) {
		@Override
		public ItemStack makeIcon() {
			return Items.CREEPER_SPAWN_EGG.getDefaultInstance();
		}
	};

	public static RegistryObject<EntityType<CelebrationCreeper>> CELEBRATION_CREEPER = registerCreeper(
			CelebrationCreeper::new, "celebration_creeper", new Color(98, 182, 24));
	public static RegistryObject<EntityType<CookieCreeper>> COOKIE_CREEPER = registerCreeper(CookieCreeper::new,
			"cookie_creeper", new Color(202, 147, 98));
	public static RegistryObject<EntityType<DarkCreeper>> DARK_CREEPER = registerCreeper(DarkCreeper::new,
			"dark_creeper", new Color(50, 50, 50));
	public static RegistryObject<EntityType<EarthCreeper>> EARTH_CREEPER = registerCreeper(EarthCreeper::new,
			"earth_creeper", new Color(93, 50, 0));
	public static RegistryObject<EntityType<ElectricCreeper>> ELECTRIC_CREEPER = registerCreeper(ElectricCreeper::new,
			"electric_creeper", new Color(251, 234, 57));
	public static RegistryObject<EntityType<FakeIllusionCreeper>> FAKE_ILLUSION_CREEPER = registerCreeper(
			FakeIllusionCreeper::new, "fake_illusion_creeper", null);
	public static RegistryObject<EntityType<FireCreeper>> FIRE_CREEPER = registerCreeper(FireCreeper::new,
			"fire_creeper", new Color(227, 111, 24));
	public static RegistryObject<EntityType<IceCreeper>> ICE_CREEPER = registerCreeper(IceCreeper::new, "ice_creeper",
			Color.WHITE);
	public static RegistryObject<EntityType<IllusionCreeper>> ILLUSION_CREEPER = registerCreeper(IllusionCreeper::new,
			"illusion_creeper", new Color(158, 158, 158));
	public static RegistryObject<EntityType<GhostCreeper>> GHOST_CREEPER = registerCreeper(GhostCreeper::new,
			"ghost_creeper", null, null);
	public static RegistryObject<EntityType<LightCreeper>> LIGHT_CREEPER = registerCreeper(LightCreeper::new,
			"light_creeper", new Color(255, 244, 125));
	public static RegistryObject<EntityType<MagmaCreeper>> MAGMA_CREEPER = registerCreeper(MagmaCreeper::new,
			"magma_creeper", new Color(165, 0, 16));
	public static RegistryObject<EntityType<PsychicCreeper>> PSYCHIC_CREEPER = registerCreeper(PsychicCreeper::new,
			"psychic_creeper", new Color(121, 51, 142));
	public static RegistryObject<EntityType<ReverseCreeper>> REVERSE_CREEPER = registerCreeper(ReverseCreeper::new,
			"reverse_creeper", new Color(894731), Color.black);
	public static RegistryObject<EntityType<SpiderCreeper>> SPIDER_CREEPER = registerCreeper(SpiderCreeper::new,
			"spider_creeper", Color.RED);
	public static RegistryObject<EntityType<WaterCreeper>> WATER_CREEPER = registerCreeper(WaterCreeper::new,
			"water_creeper", new Color(59, 115, 205));

	public static RegistryObject<EntityType<FriendlyCreeper>> FRIENDLY_CREEPER = ENTITIES.register("friendly_creeper",
			() -> EntityType.Builder.of(FriendlyCreeper::new, EntityClassification.CREATURE).sized(0.6F, 1.7F)
					.clientTrackingRange(10).build("friendly_creeper"));

	protected int creeperCount = 0;

	public ElementalCreepers() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::entityAttributeCreationEvent);
		modEventBus.addListener(this::commonSetupEvent);

		ITEMS.register("friendly_creeper_spawn_egg", () -> new ForgeSpawnEggItem(FRIENDLY_CREEPER, 894731,
				new Color(215, 113, 211).getRGB(), new Item.Properties().tab(CREATIVE_MODE_TAB)));

		ENTITIES.register(modEventBus);
		ITEMS.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}

	private static <T extends ElementalCreeper> RegistryObject<EntityType<T>> registerCreeper(
			EntityType.IFactory<T> creeperFactory, String name, @Nullable Color colour) {
		return registerCreeper(creeperFactory, name, colour, null);
	}

	private static <T extends ElementalCreeper> RegistryObject<EntityType<T>> registerCreeper(
			EntityType.IFactory<T> creeperFactory, String name, @Nullable Color colour, @Nullable Color colour2) {
		RegistryObject<EntityType<T>> entityRO = ENTITIES.register(name, () -> EntityType.Builder
				.of(creeperFactory, EntityClassification.MONSTER).sized(0.6F, 1.7F).clientTrackingRange(8).build(name));

		if (colour != null) {
			ITEMS.register(name + "_spawn_egg",
					() -> new ForgeSpawnEggItem(entityRO, colour2 != null ? colour2.getRGB() : 894731, colour.getRGB(),
							new Item.Properties().tab(CREATIVE_MODE_TAB)));
		}
		return entityRO;
	}

	@SubscribeEvent
	public void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
		event.put(CELEBRATION_CREEPER.get(), CelebrationCreeper.createAttributes().build());
		event.put(COOKIE_CREEPER.get(), CookieCreeper.createAttributes().build());
		event.put(DARK_CREEPER.get(), DarkCreeper.createAttributes().build());
		event.put(EARTH_CREEPER.get(), EarthCreeper.createAttributes().build());
		event.put(ELECTRIC_CREEPER.get(), ElectricCreeper.createAttributes().build());
		event.put(FAKE_ILLUSION_CREEPER.get(), FakeIllusionCreeper.createAttributes().build());
		event.put(FIRE_CREEPER.get(), FireCreeper.createAttributes().build());
		event.put(ICE_CREEPER.get(), IceCreeper.createAttributes().build());
		event.put(ILLUSION_CREEPER.get(), IllusionCreeper.createAttributes().build());
		event.put(GHOST_CREEPER.get(), GhostCreeper.createAttributes().build());
		event.put(LIGHT_CREEPER.get(), LightCreeper.createAttributes().build());
		event.put(MAGMA_CREEPER.get(), MagmaCreeper.createAttributes().build());
		event.put(PSYCHIC_CREEPER.get(), PsychicCreeper.createAttributes().build());
		event.put(REVERSE_CREEPER.get(), ReverseCreeper.createAttributes().build());
		event.put(SPIDER_CREEPER.get(), SpiderCreeper.createAttributes().build());
		event.put(WATER_CREEPER.get(), WaterCreeper.createAttributes().build());

		event.put(FRIENDLY_CREEPER.get(), FriendlyCreeper.createAttributes().build());
	}

	@SubscribeEvent
	public void commonSetupEvent(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {

			EntitySpawnPlacementRegistry.register(CELEBRATION_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(COOKIE_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(DARK_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(EARTH_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(ELECTRIC_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(FAKE_ILLUSION_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(FIRE_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(ICE_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(ILLUSION_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(GHOST_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(LIGHT_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(MAGMA_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(PSYCHIC_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(REVERSE_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(SPIDER_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(WATER_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::checkMonsterSpawnRules);

			EntitySpawnPlacementRegistry.register(FRIENDLY_CREEPER.get(),
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					AnimalEntity::checkAnimalSpawnRules);
		});

	}
}

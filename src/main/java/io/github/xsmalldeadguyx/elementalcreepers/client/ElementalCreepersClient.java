package io.github.xsmalldeadguyx.elementalcreepers.client;

import io.github.xsmalldeadguyx.elementalcreepers.client.model.CelebrationCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.client.model.SpiderCreeperModel;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.CelebrationCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.CookieCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.DarkCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.EarthCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.ElectricCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.FireCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.FriendlyCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.GhostCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.IceCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.IllusionCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.LightCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.MagmaCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.PsychicCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.ReverseCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.SpiderCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.client.renderer.WaterCreeperRenderer;
import io.github.xsmalldeadguyx.elementalcreepers.common.ElementalCreepers;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ElementalCreepersClient {

	public static ModelLayerLocation SPIDER_CREEPER_MODEL_LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(ElementalCreepers.MODID, "spider_creeper"), "main");

	public static ModelLayerLocation CELEBRATION_CREEPER_MODEL_LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(ElementalCreepers.MODID, "celebration_creeper"), "main");

	@Mod.EventBusSubscriber(modid = ElementalCreepers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent(priority = EventPriority.LOW)
		public static void onClientSetup(FMLClientSetupEvent event) {
			EntityRenderers.register(ElementalCreepers.CELEBRATION_CREEPER.get(), CelebrationCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.COOKIE_CREEPER.get(), CookieCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.DARK_CREEPER.get(), DarkCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.EARTH_CREEPER.get(), EarthCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.ELECTRIC_CREEPER.get(), ElectricCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.FAKE_ILLUSION_CREEPER.get(), IllusionCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.FIRE_CREEPER.get(), FireCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.ICE_CREEPER.get(), IceCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.ILLUSION_CREEPER.get(), IllusionCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.GHOST_CREEPER.get(), GhostCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.LIGHT_CREEPER.get(), LightCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.MAGMA_CREEPER.get(), MagmaCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.PSYCHIC_CREEPER.get(), PsychicCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.REVERSE_CREEPER.get(), ReverseCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.SPIDER_CREEPER.get(), SpiderCreeperRenderer::new);
			EntityRenderers.register(ElementalCreepers.WATER_CREEPER.get(), WaterCreeperRenderer::new);

			EntityRenderers.register(ElementalCreepers.FRIENDLY_CREEPER.get(), FriendlyCreeperRenderer::new);
		}

		@SubscribeEvent
		public static void setupModelLayers(RegisterLayerDefinitions event) {
			event.registerLayerDefinition(SPIDER_CREEPER_MODEL_LAYER_LOCATION, SpiderCreeperModel::createBodyLayer);
			event.registerLayerDefinition(CELEBRATION_CREEPER_MODEL_LAYER_LOCATION, CelebrationCreeperModel::createBodyLayer);
		}
	}
}

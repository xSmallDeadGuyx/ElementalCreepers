package io.github.xsmalldeadguyx.elementalcreepers.client;

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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ElementalCreepersClient {
	@Mod.EventBusSubscriber(modid = ElementalCreepers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent(priority = EventPriority.LOW)
		public static void onClientSetup(FMLClientSetupEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.CELEBRATION_CREEPER.get(),
					CelebrationCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.COOKIE_CREEPER.get(),
					CookieCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.DARK_CREEPER.get(),
					DarkCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.EARTH_CREEPER.get(),
					EarthCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.ELECTRIC_CREEPER.get(),
					ElectricCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.FAKE_ILLUSION_CREEPER.get(),
					IllusionCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.FIRE_CREEPER.get(),
					FireCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.ICE_CREEPER.get(),
					IceCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.ILLUSION_CREEPER.get(),
					IllusionCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.GHOST_CREEPER.get(),
					GhostCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.LIGHT_CREEPER.get(),
					LightCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.MAGMA_CREEPER.get(),
					MagmaCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.PSYCHIC_CREEPER.get(),
					PsychicCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.REVERSE_CREEPER.get(),
					ReverseCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.SPIDER_CREEPER.get(),
					SpiderCreeperRenderer::new);
			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.WATER_CREEPER.get(),
					WaterCreeperRenderer::new);

			RenderingRegistry.registerEntityRenderingHandler(ElementalCreepers.FRIENDLY_CREEPER.get(),
					FriendlyCreeperRenderer::new);
		}
	}
}

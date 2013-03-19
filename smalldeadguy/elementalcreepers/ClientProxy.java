package smalldeadguy.elementalcreepers;

import net.minecraft.client.renderer.entity.*;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityFireCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityIceCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityElectricCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityEarthCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityPsychicCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityCookieCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityMagmaCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityGhostCreeper.class, new RenderGhostCreeper());
		//RenderingRegistry.registerEntityRenderingHandler(EntityFriendlyCreeper.class, new RenderFriendlyCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityIllusionCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityFakeIllusionCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityLightCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityDarkCreeper.class, new RenderCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityReverseCreeper.class, new RenderCreeper());
	}
}

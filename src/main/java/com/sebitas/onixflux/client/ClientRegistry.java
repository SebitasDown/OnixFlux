package com.sebitas.onixflux.client;

import com.sebitas.onixflux.client.hud.FXHudOverlay;
import com.sebitas.onixflux.client.particle.ModParticles;
import com.sebitas.onixflux.client.renderer.FluxTableBER;
import com.sebitas.onixflux.registry.ModBlockEntities;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "onixflux", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientRegistry {

    public static final KeyMapping TOGGLE_HUD = new KeyMapping(
            "key.onixflux.open_hud",
            GLFW.GLFW_KEY_F12,
            "key.categories.onixflux"
    );

    private ClientRegistry() {}

    public static void registerAll() {
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_HUD);
    }

    @SubscribeEvent
    public static void onRegisterBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.FLUX_TABLE.get(), FluxTableBER::new);
    }

    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("fx_hud", FXHudOverlay.INSTANCE);
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        ModParticles.registerProviders(event);
    }

}

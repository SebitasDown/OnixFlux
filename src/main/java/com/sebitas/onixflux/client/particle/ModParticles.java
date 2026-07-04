package com.sebitas.onixflux.client.particle;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = OnixFlux.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, OnixFlux.MOD_ID);

    public static final RegistryObject<SimpleParticleType> FX_PARTICLE =
            PARTICLE_TYPES.register("fx_particle", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> LEARNING_PARTICLE =
            PARTICLE_TYPES.register("learning_particle", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> CREATION_PARTICLE =
            PARTICLE_TYPES.register("creation_particle", () -> new SimpleParticleType(false));

    private ModParticles() {}

    public static void registerProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(FX_PARTICLE.get(), FXParticle.Provider::new);
        event.registerSpriteSet(LEARNING_PARTICLE.get(), FXLearningParticle.Provider::new);
        event.registerSpriteSet(CREATION_PARTICLE.get(), FXCreationParticle.Provider::new);
    }

}

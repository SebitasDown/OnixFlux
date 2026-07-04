package com.sebitas.onixflux;

import com.sebitas.onixflux.api.FluxAPI;
import com.sebitas.onixflux.client.ClientEventHandler;
import com.sebitas.onixflux.client.particle.ModParticles;
import com.sebitas.onixflux.config.ConfigManager;
import com.sebitas.onixflux.fx.FluxBootstrap;
import com.sebitas.onixflux.command.OnixFluxCommand;
import com.sebitas.onixflux.integration.IntegrationManager;
import com.sebitas.onixflux.network.NetworkManager;
import com.sebitas.onixflux.player.PlayerCapabilityAttacher;
import com.sebitas.onixflux.player.PlayerDataEvents;
import com.sebitas.onixflux.registry.ModBlocks;
import com.sebitas.onixflux.registry.ModBlockEntities;
import com.sebitas.onixflux.registry.ModCreativeTabs;
import com.sebitas.onixflux.registry.ModItems;
import com.sebitas.onixflux.registry.ModMenuTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(OnixFlux.MOD_ID)
public class OnixFlux {

    public static final String MOD_ID = "onixflux";

    @SuppressWarnings("deprecation")
    public OnixFlux() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onCommonSetup);
        modBus.addListener(this::onLoadComplete);

        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modBus);
        ModMenuTypes.MENUS.register(modBus);
        ModCreativeTabs.TABS.register(modBus);
        ModParticles.PARTICLE_TYPES.register(modBus);

        if (FMLEnvironment.dist.isClient()) {
            modBus.register(ClientEventHandler.class);
        }

        MinecraftForge.EVENT_BUS.addListener(this::onServerAboutToStart);
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
        MinecraftForge.EVENT_BUS.addGenericListener(net.minecraft.world.entity.Entity.class, PlayerCapabilityAttacher::onAttach);
        MinecraftForge.EVENT_BUS.addListener(PlayerCapabilityAttacher::onPlayerClone);
        MinecraftForge.EVENT_BUS.addListener(PlayerDataEvents::onPlayerLogin);
        MinecraftForge.EVENT_BUS.addListener(PlayerDataEvents::onPlayerChangedDimension);
        MinecraftForge.EVENT_BUS.addListener(PlayerDataEvents::onPlayerRespawn);

        ConfigManager.initialize();
        FluxBootstrap.bootstrap();
        FluxAPI.initialize();
        NetworkManager.initialize();
        IntegrationManager.initialize();
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        FluxBootstrap.loadConfig();
        ConfigManager.onCommonSetup(event);
        IntegrationManager.onCommonSetup(event);
    }

    @SubscribeEvent
    public void onLoadComplete(FMLLoadCompleteEvent event) {
    }

    private void onServerAboutToStart(ServerAboutToStartEvent event) {
        var server = event.getServer();
        FluxBootstrap.finalize(server.getRecipeManager(), server.registryAccess());
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        OnixFluxCommand.register(event.getDispatcher(), event.getBuildContext());
    }

}

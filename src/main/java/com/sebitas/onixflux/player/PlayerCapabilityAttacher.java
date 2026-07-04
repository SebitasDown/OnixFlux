package com.sebitas.onixflux.player;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class PlayerCapabilityAttacher {

    public static final Capability<PlayerCapability> PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {});

    private static final ResourceLocation CAPABILITY_ID = ResourceLocation.fromNamespaceAndPath("onixflux", "player_data");

    private PlayerCapabilityAttacher() {
    }

    @SubscribeEvent
    public static void onAttach(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) return;

        PlayerDataProvider provider = new PlayerDataProvider();
        event.addCapability(CAPABILITY_ID, provider);
        event.addListener(provider::invalidate);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        Player original = event.getOriginal();
        Player player = event.getEntity();

        original.reviveCaps();

        original.getCapability(PLAYER_DATA).ifPresent(oldData -> {
            player.getCapability(PLAYER_DATA).ifPresent(newData -> {
                if (oldData instanceof PlayerData old && newData instanceof PlayerData nw) {
                    PlayerData copy = old.copy();
                    nw.deserializeNBT(copy.serializeNBT());
                }
            });
        });

        original.invalidateCaps();
    }

}

package dev.emi.trinkets.platform.neoforge;

import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.network.TrinketsNetwork;
import dev.emi.trinkets.network.payload.BreakPayload;
import dev.emi.trinkets.network.payload.SyncInventoryPayload;
import dev.emi.trinkets.network.payload.SyncSlotsPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(TrinketsMain.MOD_ID)
public class TrinketsNeoForge {
    public TrinketsNeoForge(IEventBus modEventBus) {
        TrinketsMain.init();
        
        TrinketsDataAttachments.register(modEventBus);
    }
}

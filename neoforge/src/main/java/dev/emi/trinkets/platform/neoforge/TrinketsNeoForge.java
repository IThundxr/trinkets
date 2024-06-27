package dev.emi.trinkets.platform.neoforge;

import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.network.TrinketsNetwork;
import dev.emi.trinkets.network.payload.BreakPayload;
import dev.emi.trinkets.network.payload.SyncInventoryPayload;
import dev.emi.trinkets.network.payload.SyncSlotsPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(TrinketsMain.MOD_ID)
@EventBusSubscriber
public class TrinketsNeoForge {
    public TrinketsNeoForge() {
        TrinketsMain.init();
    }

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(TrinketsMain.MOD_ID);

        MinecraftClient client = MinecraftClient.getInstance();
        
        registrar.playToClient(TrinketsNetwork.BREAK, BreakPayload.CODEC, (payload, context) -> BreakPayload.handle(payload, client, (ClientPlayerEntity) context.player()));
        registrar.playToClient(TrinketsNetwork.SYNC_INVENTORY, SyncInventoryPayload.CODEC, (payload, context) -> SyncInventoryPayload.handle(payload, client, (ClientPlayerEntity) context.player()));
        registrar.playToClient(TrinketsNetwork.SYNC_SLOTS, SyncSlotsPayload.CODEC, (payload, context) -> SyncSlotsPayload.handle(payload, client, (ClientPlayerEntity) context.player()));
    }
}

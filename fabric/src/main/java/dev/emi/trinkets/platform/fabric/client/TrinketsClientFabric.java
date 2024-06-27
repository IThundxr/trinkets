package dev.emi.trinkets.platform.fabric.client;

import dev.emi.trinkets.TrinketsClient;
import dev.emi.trinkets.network.TrinketsNetwork;
import dev.emi.trinkets.network.payload.BreakPayload;
import dev.emi.trinkets.network.payload.SyncInventoryPayload;
import dev.emi.trinkets.network.payload.SyncSlotsPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class TrinketsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TrinketsClient.init();
        
        ClientPlayNetworking.registerGlobalReceiver(TrinketsNetwork.BREAK, (payload, context) -> BreakPayload.handle(payload, context.client(), context.player()));
        ClientPlayNetworking.registerGlobalReceiver(TrinketsNetwork.SYNC_INVENTORY, (payload, context) -> SyncInventoryPayload.handle(payload, context.client(), context.player()));
        ClientPlayNetworking.registerGlobalReceiver(TrinketsNetwork.SYNC_SLOTS, (payload, context) -> SyncSlotsPayload.handle(payload, context.client(), context.player()));
    }
}

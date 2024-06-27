package dev.emi.trinkets.platform.fabric;

import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.network.TrinketsNetwork;
import dev.emi.trinkets.data.EntitySlotLoader;
import dev.emi.trinkets.data.SlotLoader;
import dev.emi.trinkets.network.payload.BreakPayload;
import dev.emi.trinkets.network.payload.SyncInventoryPayload;
import dev.emi.trinkets.network.payload.SyncSlotsPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class TrinketsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TrinketsMain.init();

        ResourceManagerHelper resourceManagerHelper = ResourceManagerHelper.get(ResourceType.SERVER_DATA);
        resourceManagerHelper.registerReloadListener((IdentifiableResourceReloadListener) SlotLoader.INSTANCE);
        resourceManagerHelper.registerReloadListener((IdentifiableResourceReloadListener) EntitySlotLoader.SERVER);

        PayloadTypeRegistry.playS2C().register(TrinketsNetwork.BREAK, BreakPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(TrinketsNetwork.SYNC_INVENTORY, SyncInventoryPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(TrinketsNetwork.SYNC_SLOTS, SyncSlotsPayload.CODEC);
    }
}

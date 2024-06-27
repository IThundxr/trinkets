package dev.emi.trinkets.platform.neoforge.events;

import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.data.EntitySlotLoader;
import dev.emi.trinkets.data.SlotLoader;
import dev.emi.trinkets.events.CommonEvents;
import dev.emi.trinkets.network.TrinketsNetwork;
import dev.emi.trinkets.network.payload.BreakPayload;
import dev.emi.trinkets.network.payload.SyncInventoryPayload;
import dev.emi.trinkets.network.payload.SyncSlotsPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class CommonEventsNeoForge {
    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(SlotLoader.INSTANCE);
        event.addListener(EntitySlotLoader.SERVER);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath() && event.getOriginal().hasData(MY_DATA)) {
            event.getEntity().getData(MY_DATA).fieldToCopy = event.getOriginal().getData(MY_DATA).fieldToCopy;
        }
    }

    @SubscribeEvent
    public static void onRegisterPayloadHandlers(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(TrinketsMain.MOD_ID);

        MinecraftClient client = MinecraftClient.getInstance();

        registrar.playToClient(TrinketsNetwork.BREAK, BreakPayload.CODEC, (payload, context) -> BreakPayload.handle(payload, client, (ClientPlayerEntity) context.player()));
        registrar.playToClient(TrinketsNetwork.SYNC_INVENTORY, SyncInventoryPayload.CODEC, (payload, context) -> SyncInventoryPayload.handle(payload, client, (ClientPlayerEntity) context.player()));
        registrar.playToClient(TrinketsNetwork.SYNC_SLOTS, SyncSlotsPayload.CODEC, (payload, context) -> SyncSlotsPayload.handle(payload, client, (ClientPlayerEntity) context.player()));
    }

    // 		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success)
    //				-> EntitySlotLoader.SERVER.sync(server.getPlayerManager().getPlayerList()));
    
    @SubscribeEvent
    public static void onUseItem(LivingEntityUseItemEvent event) {
        if (event.getEntity() instanceof PlayerEntity player) {
            CommonEvents.onUseItem(player, player.getWorld(), player.getActiveHand());
        }
    }
}

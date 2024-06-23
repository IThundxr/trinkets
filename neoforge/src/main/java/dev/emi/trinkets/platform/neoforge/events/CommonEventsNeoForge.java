package dev.emi.trinkets.platform.neoforge.events;

import dev.emi.trinkets.data.EntitySlotLoader;
import dev.emi.trinkets.data.SlotLoader;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

@EventBusSubscriber
public class CommonEventsNeoForge {
    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(SlotLoader.INSTANCE);
        event.addListener(EntitySlotLoader.SERVER);
    }
}

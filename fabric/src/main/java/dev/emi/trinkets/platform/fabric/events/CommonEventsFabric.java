package dev.emi.trinkets.platform.fabric.events;

import dev.emi.trinkets.events.CommonEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public class CommonEventsFabric {
    public static void init() {
        UseItemCallback.EVENT.register(CommonEvents::onUseItem);
    }
}

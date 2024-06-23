package dev.emi.trinkets.platform.fabric;

import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.data.EntitySlotLoader;
import dev.emi.trinkets.data.SlotLoader;
import net.fabricmc.api.ModInitializer;
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
    }
}

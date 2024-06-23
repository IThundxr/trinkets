package dev.emi.trinkets.platform;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketEnums.DropRule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class TrinketsAgnos {
    public static TrinketsAgnos delegate;

    static {
        try {
            Class.forName("dev.emi.trinkets.platform.fabric.TrinketsAgnosFabric");
        } catch (Throwable ignored) {}
        try {
            Class.forName("dev.emi.trinkets.platform.neoforge.TrinketsAgnosNeoForge");
        } catch (Throwable ignored) {}
    }

    public static boolean isNeoForge() {
        return delegate.isNeoForgeAgnos();
    }

    protected abstract boolean isNeoForgeAgnos();

    public static boolean isDevelopmentEnvironment() {
        return delegate.isDevelopmentEnvironmentAgnos();
    }

    protected abstract boolean isDevelopmentEnvironmentAgnos();

    public static DropRule fireTrinketDropEvent(DropRule rule, ItemStack stack, SlotReference ref, LivingEntity entity) {
        return delegate.fireTrinketDropEventAgnos(rule, stack, ref, entity);
    }

    protected abstract DropRule fireTrinketDropEventAgnos(DropRule rule, ItemStack stack, SlotReference ref, LivingEntity entity);

    public static void sendToClient(ServerPlayerEntity player, CustomPayload payload) {
        delegate.sendToClientAgnos(player, payload);
    }

    protected abstract void sendToClientAgnos(ServerPlayerEntity player, CustomPayload payload);
    
    public static void sendToClientsTrackingEntity(Entity entity, CustomPayload payload) {
        delegate.sendToClientsTrackingEntityAgnos(entity, payload);
    }
    
    protected abstract void sendToClientsTrackingEntityAgnos(Entity entity, CustomPayload payload);
}

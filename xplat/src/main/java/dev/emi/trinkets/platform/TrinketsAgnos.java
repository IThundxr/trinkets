package dev.emi.trinkets.platform;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketEnums.DropRule;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

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
}

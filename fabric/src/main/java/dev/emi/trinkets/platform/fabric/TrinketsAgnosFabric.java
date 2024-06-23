package dev.emi.trinkets.platform.fabric;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketEnums.DropRule;
import dev.emi.trinkets.api.fabric.event.TrinketDropEvent;
import dev.emi.trinkets.platform.TrinketsAgnos;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class TrinketsAgnosFabric extends TrinketsAgnos {
    static {
        TrinketsAgnos.delegate = new TrinketsAgnosFabric();
    }
    
    @Override
    protected boolean isNeoForgeAgnos() {
        return false;
    }

    @Override
    protected boolean isDevelopmentEnvironmentAgnos() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    protected DropRule fireTrinketDropEventAgnos(DropRule rule, ItemStack stack, SlotReference ref, LivingEntity entity) {
        return TrinketDropEvent.EVENT.invoker().drop(rule, stack, ref, entity);
    }
}

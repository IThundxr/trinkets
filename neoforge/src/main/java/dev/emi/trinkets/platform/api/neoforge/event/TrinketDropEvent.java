package dev.emi.trinkets.platform.api.neoforge.event;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketEnums.DropRule;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.neoforged.bus.api.Event;

public class TrinketDropEvent extends Event {
    private DropRule rule;
    private final ItemStack stack;
    private final SlotReference ref;
    private final LivingEntity entity;

    public TrinketDropEvent(DropRule rule, ItemStack stack, SlotReference ref, LivingEntity entity) {
        this.rule = rule;
        this.stack = stack;
        this.ref = ref;
        this.entity = entity;
    }

    public DropRule getRule() {
        return rule;
    }

    public void setRule(DropRule rule) {
        this.rule = rule;
    }

    public ItemStack getStack() {
        return stack;
    }

    public SlotReference getRef() {
        return ref;
    }

    public LivingEntity getEntity() {
        return entity;
    }
}

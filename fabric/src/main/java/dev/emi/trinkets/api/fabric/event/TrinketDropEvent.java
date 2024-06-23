package dev.emi.trinkets.api.fabric.event;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketEnums.DropRule;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface TrinketDropEvent {
	Event<TrinketDropEvent> EVENT = EventFactory.createArrayBacked(TrinketDropEvent.class,
	listeners -> (rule, stack, ref, entity) -> {
		for (TrinketDropEvent listener : listeners) {
			rule = listener.drop(rule, stack, ref, entity);
		}
		return rule;
	});

	DropRule drop(DropRule rule, ItemStack stack, SlotReference ref, LivingEntity entity);
}

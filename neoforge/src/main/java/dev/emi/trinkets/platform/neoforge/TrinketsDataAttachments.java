package dev.emi.trinkets.platform.neoforge;

import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.api.LivingEntityTrinketComponent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class TrinketsDataAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, TrinketsMain.MOD_ID);

    public static final Supplier<AttachmentType<LivingEntityTrinketComponent>> LIVING_ENTITY_TRINKET_COMPONENT = ATTACHMENT_TYPES.register(
            "living_entity_trinket_component", () -> AttachmentType.serializable(new LivingEntityTrinketComponent(null)).copyOnDeath().build()
    );

    public static void register(IEventBus modEventBus) { ATTACHMENT_TYPES.register(modEventBus); }
}

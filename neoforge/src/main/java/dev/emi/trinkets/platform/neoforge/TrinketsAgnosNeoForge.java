package dev.emi.trinkets.platform.neoforge;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketEnums.DropRule;
import dev.emi.trinkets.platform.TrinketsAgnos;
import dev.emi.trinkets.platform.api.neoforge.event.TrinketDropEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

public class TrinketsAgnosNeoForge extends TrinketsAgnos {
    static {
        TrinketsAgnos.delegate = new TrinketsAgnosNeoForge();
    }
    
    @Override
    protected boolean isNeoForgeAgnos() {
        return true;
    }

    @Override
    protected boolean isDevelopmentEnvironmentAgnos() {
        return !FMLLoader.isProduction();
    }

    @Override
    protected DropRule fireTrinketDropEventAgnos(DropRule rule, ItemStack stack, SlotReference ref, LivingEntity entity) {
        TrinketDropEvent event = new TrinketDropEvent(rule, stack, ref, entity);
        NeoForge.EVENT_BUS.post(event);
        return event.getRule();
    }

    @Override
    protected void sendToClientAgnos(ServerPlayerEntity player, CustomPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    @Override
    protected void sendToClientsTrackingEntityAgnos(Entity entity, CustomPayload payload) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, payload);
    }
}

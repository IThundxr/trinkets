package dev.emi.trinkets.network.payload;

import dev.emi.trinkets.TrinketPlayerScreenHandler;
import dev.emi.trinkets.TrinketScreenManager;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.network.TrinketsNetwork;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

import java.util.Map;


public record BreakPayload(int entityId, String group, String slot, int index) implements CustomPayload {
	public static final PacketCodec<RegistryByteBuf, BreakPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT,
			BreakPayload::entityId,
			PacketCodecs.STRING,
			BreakPayload::group,
			PacketCodecs.STRING,
			BreakPayload::slot,
			PacketCodecs.VAR_INT,
			BreakPayload::index,
			BreakPayload::new
	);
	
	@Environment(EnvType.CLIENT)
	public static void handle(BreakPayload payload, MinecraftClient client, ClientPlayerEntity clientPlayer) {
		Entity e = client.world.getEntityById(payload.entityId());
		if (e instanceof LivingEntity entity) {
			TrinketsApi.getTrinketComponent(entity).ifPresent(comp -> {
				var groupMap = comp.getInventory().get(payload.group());
				if (groupMap != null) {
					TrinketInventory inv = groupMap.get(payload.slot());
					if (payload.index() < inv.size()) {
						ItemStack stack = inv.getStack(payload.index());
						SlotReference ref = new SlotReference(inv, payload.index());
						Trinket trinket = TrinketsApi.getTrinket(stack.getItem());
						trinket.onBreak(stack, ref, entity);
					}
				}
			});
		}
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return TrinketsNetwork.BREAK;
	}
}

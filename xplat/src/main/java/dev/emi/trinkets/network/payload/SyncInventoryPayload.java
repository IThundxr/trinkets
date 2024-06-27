package dev.emi.trinkets.network.payload;

import dev.emi.trinkets.TrinketPlayerScreenHandler;
import dev.emi.trinkets.TrinketScreenManager;
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

import java.util.HashMap;
import java.util.Map;

public record SyncInventoryPayload(int entityId, Map<String, ItemStack> contentUpdates, Map<String, NbtCompound> inventoryUpdates) implements CustomPayload {
	public static final PacketCodec<RegistryByteBuf, SyncInventoryPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT,
			SyncInventoryPayload::entityId,
			PacketCodecs.map(HashMap::new, PacketCodecs.STRING, ItemStack.OPTIONAL_PACKET_CODEC),
			SyncInventoryPayload::contentUpdates,
			PacketCodecs.map(HashMap::new, PacketCodecs.STRING, PacketCodecs.NBT_COMPOUND),
			SyncInventoryPayload::inventoryUpdates,
			SyncInventoryPayload::new);

	@Environment(EnvType.CLIENT)
	public static void handle(SyncInventoryPayload payload, MinecraftClient client, ClientPlayerEntity clientPlayer) {
		Entity entity = client.world.getEntityById(payload.entityId());
		if (entity instanceof LivingEntity) {
			TrinketsApi.getTrinketComponent((LivingEntity) entity).ifPresent(trinkets -> {
				for (Map.Entry<String, NbtCompound> entry : payload.inventoryUpdates().entrySet()) {
					String[] split = entry.getKey().split("/");
					String group = split[0];
					String slot = split[1];
					Map<String, TrinketInventory> slots = trinkets.getInventory().get(group);
					if (slots != null) {
						TrinketInventory inv = slots.get(slot);
						if (inv != null) {
							inv.applySyncTag(entry.getValue());
						}
					}
				}

				if (entity instanceof PlayerEntity && ((PlayerEntity) entity).playerScreenHandler instanceof TrinketPlayerScreenHandler screenHandler) {
					screenHandler.trinkets$updateTrinketSlots(false);
					if (TrinketScreenManager.currentScreen != null) {
						TrinketScreenManager.currentScreen.trinkets$updateTrinketSlots();
					}
				}

				for (Map.Entry<String, ItemStack> entry : payload.contentUpdates().entrySet()) {
					String[] split = entry.getKey().split("/");
					String group = split[0];
					String slot = split[1];
					int index = Integer.parseInt(split[2]);
					Map<String, TrinketInventory> slots = trinkets.getInventory().get(group);
					if (slots != null) {
						TrinketInventory inv = slots.get(slot);
						if (inv != null && index < inv.size()) {
							inv.setStack(index, entry.getValue());
						}
					}
				}
			});
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return TrinketsNetwork.SYNC_INVENTORY;
	}
}

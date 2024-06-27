package dev.emi.trinkets.network.payload;

import dev.emi.trinkets.TrinketPlayerScreenHandler;
import dev.emi.trinkets.TrinketScreen;
import dev.emi.trinkets.data.EntitySlotLoader;
import dev.emi.trinkets.network.TrinketsNetwork;
import dev.emi.trinkets.api.SlotGroup;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryKeys;

import java.util.HashMap;
import java.util.Map;

public record SyncSlotsPayload(Map<EntityType<?>, Map<String, SlotGroup>> map) implements CustomPayload {
	public static final PacketCodec<RegistryByteBuf, SyncSlotsPayload> CODEC = PacketCodecs.map(
			(x) -> (Map<EntityType<?>, Map<String, SlotGroup>>) new HashMap<EntityType<?>, Map<String, SlotGroup>>(x),
			PacketCodecs.registryValue(RegistryKeys.ENTITY_TYPE),
			PacketCodecs.map(HashMap::new, PacketCodecs.STRING, PacketCodecs.NBT_COMPOUND.xmap(SlotGroup::read, (x) -> {
				var nbt = new NbtCompound();
				x.write(nbt);
				return nbt;
			}))
			).xmap(SyncSlotsPayload::new, SyncSlotsPayload::map);

	@Environment(EnvType.CLIENT)
	public static void handle(SyncSlotsPayload payload, MinecraftClient client, ClientPlayerEntity clientPlayer) {
		EntitySlotLoader.CLIENT.setSlots(payload.map());

		if (clientPlayer != null) {
			((TrinketPlayerScreenHandler) clientPlayer.playerScreenHandler).trinkets$updateTrinketSlots(true);
			
			if (client.currentScreen instanceof TrinketScreen trinketScreen) {
				trinketScreen.trinkets$updateTrinketSlots();
			}

			for (AbstractClientPlayerEntity clientWorldPlayer : clientPlayer.clientWorld.getPlayers()) {
				((TrinketPlayerScreenHandler) clientWorldPlayer.playerScreenHandler).trinkets$updateTrinketSlots(true);
			}
		}
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return TrinketsNetwork.SYNC_SLOTS;
	}
}

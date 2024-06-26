package dev.emi.trinkets.network;

import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.network.payload.BreakPayload;
import dev.emi.trinkets.network.payload.SyncInventoryPayload;
import dev.emi.trinkets.network.payload.SyncSlotsPayload;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class TrinketsNetwork {

  public static final CustomPayload.Id<SyncSlotsPayload> SYNC_SLOTS = new CustomPayload.Id<>(Identifier.of(TrinketsMain.MOD_ID, "sync_slots"));
  public static final CustomPayload.Id<SyncInventoryPayload> SYNC_INVENTORY = new CustomPayload.Id<>(Identifier.of(TrinketsMain.MOD_ID, "sync_inventory"));
  public static final CustomPayload.Id<BreakPayload> BREAK = new CustomPayload.Id<>(Identifier.of(TrinketsMain.MOD_ID, "break"));

  private TrinketsNetwork() {
  }
}

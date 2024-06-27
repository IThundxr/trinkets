package dev.emi.trinkets;

import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.LivingEntityTrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.TrinketsAttributeModifiersComponent;
import dev.emi.trinkets.api.SlotGroup;
import dev.emi.trinkets.api.SlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;

import dev.emi.trinkets.data.EntitySlotLoader;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class TrinketsMain {

	public static final String MOD_ID = "trinkets";
	public static final Logger LOGGER = LogManager.getLogger();
	
	public static void init() {
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success)
				-> EntitySlotLoader.SERVER.sync(server.getPlayerManager().getPlayerList()));
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MOD_ID, "attribute_modifiers"), TrinketsAttributeModifiersComponent.TYPE);
		CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) ->
			dispatcher.register(literal("trinkets")
				.requires(source -> source.hasPermissionLevel(2))
				.then(
					literal("set")
					.then(
						argument("group", string())
						.then(
							argument("slot", string())
							.then(
								argument("offset", integer(0))
								.then(
									argument("stack", ItemStackArgumentType.itemStack(registry))
									.executes(context -> {
										try {
										return trinketsCommand(context, 1);

										} catch (Exception e) {
											e.printStackTrace();
											return -1;
										}
									})
									.then(
										argument("count", integer(1))
										.executes(context -> {
											int amount = context.getArgument("amount", Integer.class);
											return trinketsCommand(context, amount);
										})
									)
								)
							)
						)
					)
				)
			));
	}

	private static int trinketsCommand(CommandContext<ServerCommandSource> context, int amount) {
		try {
			String group = context.getArgument("group", String.class);
			String slot = context.getArgument("slot", String.class);
			int offset = context.getArgument("offset", Integer.class);
			ItemStackArgument stack = context.getArgument("stack", ItemStackArgument.class);
			ServerPlayerEntity player = context.getSource().getPlayer();
			if (player != null) {
				TrinketComponent comp = TrinketsApi.getTrinketComponent(player).get();
				SlotGroup slotGroup = comp.getGroups().getOrDefault(group, null);
				if (slotGroup != null) {
					SlotType slotType = slotGroup.getSlots().getOrDefault(slot, null);
					if (slotType != null) {
						if (offset >= 0 && offset < slotType.getAmount()) {
							comp.getInventory().get(group).get(slot).setStack(offset, stack.createStack(amount, true));
							return Command.SINGLE_SUCCESS;
						} else {
							context.getSource().sendError(Text.literal(offset + " offset does not exist for slot"));
						}
					} else {
						context.getSource().sendError(Text.literal(slot + " does not exist"));
					}
				} else {
					context.getSource().sendError(Text.literal(group + " does not exist"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(LivingEntity.class, TrinketsApi.TRINKET_COMPONENT, LivingEntityTrinketComponent::new);
		registry.registerForPlayers(TrinketsApi.TRINKET_COMPONENT, LivingEntityTrinketComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
	}
}
package dev.emi.trinkets.events;

import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CommonEvents {
    // 		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success)
    //				-> EntitySlotLoader.SERVER.sync(server.getPlayerManager().getPlayerList()));
    
    public static TypedActionResult<ItemStack> onUseItem(PlayerEntity player, World world, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        Trinket trinket = TrinketsApi.getTrinket(stack.getItem());
        if (trinket.canEquipFromUse(stack, player)) {
            if (TrinketItem.equipItem(player, stack)) {
                return TypedActionResult.success(stack);
            }
        }
        return TypedActionResult.pass(stack);
    }
}

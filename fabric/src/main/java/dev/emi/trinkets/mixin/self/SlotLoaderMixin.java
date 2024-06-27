package dev.emi.trinkets.mixin.self;

import dev.emi.trinkets.data.SlotLoader;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Self mixin, I know funny right, by the power of casting I cast you to work.
 */
@Mixin(SlotLoader.class)
public abstract class SlotLoaderMixin implements IdentifiableResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return SlotLoader.ID;
    }
}

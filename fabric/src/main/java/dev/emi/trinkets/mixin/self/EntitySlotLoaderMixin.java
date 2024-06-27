package dev.emi.trinkets.mixin.self;

import com.google.common.collect.Lists;
import dev.emi.trinkets.data.EntitySlotLoader;
import dev.emi.trinkets.data.SlotLoader;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collection;

/**
 * Self mixin, I know funny right, by the power of casting I cast you to work.
 */
@Mixin(EntitySlotLoader.class)
public abstract class EntitySlotLoaderMixin implements IdentifiableResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return EntitySlotLoader.ID;
    }

    @Override
    public Collection<Identifier> getFabricDependencies() {
        return Lists.newArrayList(SlotLoader.ID, ResourceReloadListenerKeys.TAGS);
    }
}

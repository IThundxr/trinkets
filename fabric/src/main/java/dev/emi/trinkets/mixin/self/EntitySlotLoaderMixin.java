/*
 * Steam 'n' Rails
 * Copyright (c) 2022-2024 The Railways Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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

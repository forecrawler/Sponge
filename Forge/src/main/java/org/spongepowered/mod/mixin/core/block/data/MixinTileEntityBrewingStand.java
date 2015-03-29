/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.mod.mixin.core.block.data;

import static org.spongepowered.api.service.persistence.data.DataQuery.of;

import org.spongepowered.api.block.tile.carrier.BrewingStand;
import org.spongepowered.api.service.persistence.data.DataContainer;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@NonnullByDefault
@Implements(@Interface(iface = BrewingStand.class, prefix = "brewingstand$"))
@Mixin(net.minecraft.tileentity.TileEntityBrewingStand.class)
public abstract class MixinTileEntityBrewingStand extends MixinTileEntityLockable {

    @Shadow
    private String customName;

    @Shadow
    public abstract int getField(int id);

    @Shadow
    public abstract void setField(int id, int value);

    public int brewingstand$getRemainingBrewTime() {
        return getField(0);
    }

    public void brewingstand$setRemainingBrewTime(int time) {
        setField(0, time);
    }

    @Override
    public DataContainer toContainer() {
        DataContainer container = super.toContainer();
        container.set(of("BrewTime"), this.brewingstand$getRemainingBrewTime());
        if (this.customName != null) {
            container.set(of("CustomName"), this.customName);
        }
        return container;
    }
}
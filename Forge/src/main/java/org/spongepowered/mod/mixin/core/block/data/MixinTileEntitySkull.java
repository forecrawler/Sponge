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

import com.google.common.base.Optional;
import org.spongepowered.api.GameProfile;
import org.spongepowered.api.block.tile.Skull;
import org.spongepowered.api.block.tile.data.SkullType;
import org.spongepowered.api.service.persistence.data.DataContainer;
import org.spongepowered.api.service.persistence.data.DataView;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.mod.SpongeMod;

import java.util.List;

@NonnullByDefault
@Implements(@Interface(iface = Skull.class, prefix = "skull$"))
@Mixin(net.minecraft.tileentity.TileEntitySkull.class)
public abstract class MixinTileEntitySkull extends MixinTileEntity {

    @Shadow
    private int skullRotation;

    @Shadow
    public abstract void setPlayerProfile(com.mojang.authlib.GameProfile playerProfile);

    @Shadow
    public abstract com.mojang.authlib.GameProfile getPlayerProfile();

    @Shadow
    public abstract void setType(int type);

    @Shadow
    public abstract int getSkullType();

    public Direction skull$getRotation() {
        return null; //TODO
    }

    public void skull$setRotation(Direction rotation) {
        //TODO
    }

    public Optional<GameProfile> skull$getPlayer() {
        return Optional.fromNullable((GameProfile) getPlayerProfile());
    }

    public void skull$setPlayer(GameProfile player) {
        setPlayerProfile((com.mojang.authlib.GameProfile) player);
    }

    public SkullType skull$getType() {
        // TODO: Fix GameRegistry for API changes
        return ((List<SkullType>) SpongeMod.instance.getGame().getRegistry().getSkullTypes()).get(getSkullType());
    }

    public void skull$setType(SkullType type) {
        setType(type.getId());
    }

    @Override
    public DataContainer toContainer() {
        DataContainer container = super.toContainer();
        container.set(of("Type"), this.getSkullType());
        container.set(of("Rotation"), this.skull$getRotation().ordinal());
        if (this.skull$getPlayer().isPresent()) {
            DataView ownerView = container.createView(of("Owner"));
            ownerView.set(of("UniqueId"), this.skull$getPlayer().get().getUniqueId().toString());
            ownerView.set(of("UserName"), this.skull$getPlayer().get().getName());
        }
        return container;
    }
}

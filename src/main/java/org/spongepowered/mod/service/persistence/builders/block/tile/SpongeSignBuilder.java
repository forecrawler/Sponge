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

package org.spongepowered.mod.service.persistence.builders.block.tile;

import com.google.common.base.Optional;
import net.minecraft.tileentity.TileEntitySign;
import org.spongepowered.api.Game;
import org.spongepowered.api.block.tile.Sign;
import org.spongepowered.api.service.persistence.InvalidDataException;
import org.spongepowered.api.service.persistence.data.DataQuery;
import org.spongepowered.api.service.persistence.data.DataView;
import org.spongepowered.api.text.Texts;

public class SpongeSignBuilder extends AbstractTileBuilder<Sign> {

    public SpongeSignBuilder(Game game) {
        super(game);
    }

    @Override
    @SuppressWarnings({"unchecked", "deprecation"})
    public Optional<Sign> build(DataView container) throws InvalidDataException {
        Optional<Sign> signOptional = super.build(container);
        if (!signOptional.isPresent()) {
            throw new InvalidDataException("The container had insufficient data to create a Sign tile entity!");
        }
        if (!container.contains(new DataQuery("Lines"))) {
            throw new InvalidDataException("The container had insufficient data to create a Sign tile entity!");
        }
        Sign sign = signOptional.get();
        // TODO Write SignData
//        int i = 0;
//        for (String message : container.getStringList(new DataQuery("Lines")).get()) {
//            sign.setLine(i++, Texts.fromLegacy(message));
//        }
        ((TileEntitySign) sign).validate();
        return Optional.of(sign);
    }
}

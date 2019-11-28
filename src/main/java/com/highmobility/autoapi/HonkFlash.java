/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.PropertyInteger;
import javax.annotation.Nullable;

/**
 * Honk flash
 */
public class HonkFlash extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.HONK_HORN_FLASH_LIGHTS;

    public static final byte IDENTIFIER_HONK_SECONDS = 0x02;
    public static final byte IDENTIFIER_FLASH_TIMES = 0x03;

    @Nullable PropertyInteger honkSeconds = new PropertyInteger(IDENTIFIER_HONK_SECONDS, false);
    @Nullable PropertyInteger flashTimes = new PropertyInteger(IDENTIFIER_FLASH_TIMES, false);

    /**
     * @return The honk seconds
     */
    public @Nullable PropertyInteger getHonkSeconds() {
        return honkSeconds;
    }
    
    /**
     * @return The flash times
     */
    public @Nullable PropertyInteger getFlashTimes() {
        return flashTimes;
    }
    
    /**
     * Honk flash
     *
     * @param honkSeconds Number of seconds to honk the horn
     * @param flashTimes Number of times to flash the lights
     */
    public HonkFlash(@Nullable Integer honkSeconds, @Nullable Integer flashTimes) {
        super(IDENTIFIER);
    
        addProperty(this.honkSeconds.update(false, 1, honkSeconds));
        addProperty(this.flashTimes.update(false, 1, flashTimes));
        if (this.honkSeconds.getValue() == null && this.flashTimes.getValue() == null) throw new IllegalArgumentException();
        createBytes();
    }

    HonkFlash(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_HONK_SECONDS: return honkSeconds.update(p);
                    case IDENTIFIER_FLASH_TIMES: return flashTimes.update(p);
                }
                return null;
            });
        }
        if (this.honkSeconds.getValue() == null && this.flashTimes.getValue() == null) throw new NoPropertiesException();
    }
}
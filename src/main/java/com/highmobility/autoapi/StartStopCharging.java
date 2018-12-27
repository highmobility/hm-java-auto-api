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

import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.Property;

/**
 * Start or stop charging, which can only be controlled when the car is plugged in. The result is
 * sent through the evented Charge State command.
 */
public class StartStopCharging extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x12);
    private static final byte ACTIVE_STATE_IDENTIFIER = 0x01;
    boolean start;

    /**
     * @return Whether charging should start.
     */
    public boolean getStart() {
        return start;
    }

    /**
     * @param start The charging state.
     */
    public StartStopCharging(boolean start) {
        super(TYPE.addProperty(new BooleanProperty(ACTIVE_STATE_IDENTIFIER, start)));
        this.start = start;
    }

    StartStopCharging(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case ACTIVE_STATE_IDENTIFIER:
                    start = Property.getBool(property.getValueByte());
                    break;
            }
        }

        this.start = Property.getBool(bytes[3]);
    }
}

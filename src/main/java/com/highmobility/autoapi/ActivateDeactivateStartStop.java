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
 * Activate or deactivate the Start-Stop system of the engine. When activated, this will
 * automatically shut down and restart the internal combustion engine when the car is stopped. The
 * result is sent through the Start-Stop State message.
 */
public class ActivateDeactivateStartStop extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.START_STOP, 0x02);
    boolean activate;

    /**
     * @return Whether Start-Stop should be activated.
     */
    public boolean activate() {
        return activate;
    }

    public ActivateDeactivateStartStop(boolean activate) {
        super(TYPE.addProperty(new BooleanProperty((byte) 0x01, activate)));
        this.activate = activate;
    }

    ActivateDeactivateStartStop(byte[] bytes) {
        super(bytes);
        for (Property property : properties) {
            if (property.getPropertyIdentifier() == 0x01)
                activate = Property.getBool(property.getValueByte());
        }
    }
}

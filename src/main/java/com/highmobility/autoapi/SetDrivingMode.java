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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.DrivingMode;

/**
 * Set driving mode
 */
public class SetDrivingMode extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.CHASSIS_SETTINGS;

    public static final byte IDENTIFIER_DRIVING_MODE = 0x01;

    Property<DrivingMode> drivingMode = new Property(DrivingMode.class, IDENTIFIER_DRIVING_MODE);

    /**
     * @return The driving mode
     */
    public Property<DrivingMode> getDrivingMode() {
        return drivingMode;
    }
    
    /**
     * Set driving mode
     *
     * @param drivingMode The driving mode
     */
    public SetDrivingMode(DrivingMode drivingMode) {
        super(IDENTIFIER);
    
        addProperty(this.drivingMode.update(drivingMode));
        createBytes();
    }

    SetDrivingMode(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_DRIVING_MODE: return drivingMode.update(p);
                }
                return null;
            });
        }
        if (this.drivingMode.getValue() == null) 
            throw new NoPropertiesException();
    }
}
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

package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;

public enum FailureReason implements HMProperty {
    UNSUPPORTED_CAPABILITY((byte) 0x00),
    UNAUTHORIZED((byte) 0x01),
    INCORRECT_STATE((byte) 0x02),
    EXECUTION_TIMEOUT((byte) 0x03),
    VEHICLE_ASLEEP((byte) 0x04),
    INVALID_COMMAND((byte) 0x05);

    public byte getByte() {
        return value;
    }

    public static FailureReason fromByte(byte value) throws CommandParseException {
        FailureReason[] allValues = FailureReason.values();

        for (int i = 0; i < allValues.length; i++) {
            FailureReason value1 = allValues[i];

            if (value1.getByte() == value) {
                return value1;
            }
        }

        throw new CommandParseException();
    }

    FailureReason(byte reason) {
        this.value = reason;
    }

    private byte value;

    @Override public byte getPropertyIdentifier() {
        return 0x02;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(), value);
    }
}
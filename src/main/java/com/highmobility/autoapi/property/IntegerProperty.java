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

import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

public class IntegerProperty extends Property {
    int value;

    /**
     * @return The value.
     */
    public int getValue() {
        return value;
    }

    public IntegerProperty(byte identifier, int value, int length) {
        super(getBytes(identifier, value, length));
        this.value = value;
    }

    public IntegerProperty(Bytes bytes, boolean signed) {
        super(bytes);
        if (signed) value = Property.getSignedInt(getValueBytes());
        else value = Property.getUnsignedInt(getValueBytes());
    }

    public IntegerProperty(int value) {
        this((byte) 0x00, value, 4);
    }

    public IntegerProperty(int value, PropertyTimestamp timestamp, PropertyFailure failure) {
        this(value);
        this.timestamp = timestamp;
        this.failure = failure;
    }

    /**
     * Reset the value length. This will create a new base byte array if necessary.
     *
     * @param identifier  The property identifier.
     * @param newIntegerLength The new length.
     */
    public void setIdentifier(byte identifier, int newIntegerLength) {
        // reset the bytes to new length.
        // used in builders: builder knows the length, but we don't want the user to think about it
        this.bytes = getBytes(identifier, value, newIntegerLength);
    }

    static byte[] getBytes(byte identifier, int value, int length) {
        byte[] bytes = baseBytes(identifier, length);

        if (length == 1) {
            bytes[3] = (byte) value;
        } else {
            ByteUtils.setBytes(bytes, intToBytes(value, length), 3);
        }

        return bytes;
    }
}

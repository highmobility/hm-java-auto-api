// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.ByteEnum;

public enum StartStop implements ByteEnum {
    START((byte) 0x00),
    STOP((byte) 0x01);

    public static StartStop fromByte(byte byteValue) throws CommandParseException {
        StartStop[] values = StartStop.values();

        for (int i = 0; i < values.length; i++) {
            StartStop state = values[i];
            if (state.getByte() == byteValue) {
                return state;
            }
        }

        throw new CommandParseException();
    }

    private byte value;

    StartStop(byte value) {
        this.value = value;
    }

    @Override public byte getByte() {
        return value;
    }
}
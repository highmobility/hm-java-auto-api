package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Manually start or stop defogging. The result is sent through the evented Climate State message.
 */
public class StartStopDefogging extends Command {
    public static final Type TYPE = new Type(Identifier.CLIMATE, 0x04);

    public StartStopDefogging(boolean start) {
        super(TYPE.addByte(Property.boolToByte(start)), true);
    }

    StartStopDefogging(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}

// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.value.Bytes;

public class DoorPosition extends PropertyValueObject {
    public static final int SIZE = 2;

    Location location;
    Position position;

    /**
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return The position.
     */
    public Position getPosition() {
        return position;
    }

    public DoorPosition(Location location, Position position) {
        super(2);
        update(location, position);
    }

    public DoorPosition(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public DoorPosition() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        location = Location.fromByte(get(bytePosition));
        bytePosition += 1;

        position = Position.fromByte(get(bytePosition));
    }

    public void update(Location location, Position position) {
        this.location = location;
        this.position = position;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, location.getByte());
        bytePosition += 1;

        set(bytePosition, position.getByte());
    }

    public void update(DoorPosition value) {
        update(value.location, value.position);
    }

    @Override public int getLength() {
        return 1 + 1;
    }

    public enum Location implements ByteEnum {
        FRONT_LEFT((byte) 0x00),
        FRONT_RIGHT((byte) 0x01),
        REAR_RIGHT((byte) 0x02),
        REAR_LEFT((byte) 0x03),
        ALL((byte) 0x05);
    
        public static Location fromByte(byte byteValue) throws CommandParseException {
            Location[] values = Location.values();
    
            for (int i = 0; i < values.length; i++) {
                Location state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Location(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}
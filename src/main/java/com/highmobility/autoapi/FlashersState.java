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

/**
 * This message is sent when a Get Flashers State message is received by the car.
 */
public class FlashersState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.HONK_FLASH, 0x01);

    State state;

    /**
     * @return The flashers state.
     */
    public State getState() {
        return state;
    }

    FlashersState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            com.highmobility.autoapi.property.Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case 0x01:
                    state = State.fromByte(property.getValueByte());
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    public enum State {
        INACTIVE((byte) 0x00),
        EMERGENCY_ACTIVE((byte) 0x01),
        LEFT_ACTIVE((byte) 0x02),
        RIGHT_ACTIVE((byte) 0x03);

        public static State fromByte(byte value) throws CommandParseException {
            State[] allValues = State.values();

            for (int i = 0; i < allValues.length; i++) {
                State value1 = allValues[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        State(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}

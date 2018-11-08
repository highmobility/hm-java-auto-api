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

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.WiperIntensity;
import com.highmobility.autoapi.property.WiperState;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Control the wipers. The result is sent through the Windscreen State message.
 */
public class ControlWipers extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x14);

    private static final byte IDENTIFIER_WIPER_STATE = 0x01;
    private static final byte IDENTIFIER_WIPER_INTENSITY = 0x02;

    private WiperState state;
    private WiperIntensity intensity;

    /**
     * @return The wipers state.
     */
    public WiperState getState() {
        return state;
    }

    /**
     * @return The wipers intensity.
     */
    @Nullable public WiperIntensity getIntensity() {
        return intensity;
    }

    public ControlWipers(WiperState state, @Nullable WiperIntensity intensity) {
        super(TYPE, getProperties(state, intensity));
        this.state = state;
        this.intensity = intensity;
    }

    static HMProperty[] getProperties(WiperState state, WiperIntensity intensity) {
        ArrayList<HMProperty> builder = new ArrayList<>();

        if (state != null) builder.add(new Property(IDENTIFIER_WIPER_STATE, state.getByte()));

        if (intensity != null)
            builder.add(new Property(IDENTIFIER_WIPER_INTENSITY, intensity.getByte()));

        return builder.toArray(new HMProperty[0]);
    }

    ControlWipers(byte[] bytes) throws CommandParseException {
        super(bytes);
        Property state = getProperty(IDENTIFIER_WIPER_STATE);
        if (state == null) throw new CommandParseException();
        this.state = WiperState.fromByte(state.getValueByte());

        Property intensity = getProperty(IDENTIFIER_WIPER_INTENSITY);
        if (intensity != null) this.intensity = WiperIntensity.fromByte(intensity.getValueByte());
    }
}

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

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.exception.ParseException;

import java.io.UnsupportedEncodingException;

public class ObjectPropertyString extends ObjectProperty<String> {
    public static final String CHARSET = "UTF-8";

    public ObjectPropertyString(String value) {
        this((byte) 0x00, value);
    }

    public ObjectPropertyString(Property p) throws CommandParseException {
        super(String.class, p.getPropertyIdentifier());
        update(p);
    }

    public ObjectPropertyString(byte identifier, String value) {
        this(identifier);
        update(value);
    }

    public ObjectPropertyString(byte identifier) {
        super(String.class, identifier);
    }

    @Override public ObjectProperty update(String value) {
        if (value != null) {
            byte[] stringBytes;
            try {
                stringBytes = value.getBytes(CHARSET);
            } catch (UnsupportedEncodingException e) {
                Command.logger.info(CHARSET + " charset not supported.");
                throw new ParseException();
            }

            bytes = getPropertyBytes(bytes[0], stringBytes.length, stringBytes);
        }

        this.value = value;
        return this;
    }

    @Override public ObjectProperty update(Property p) throws CommandParseException {
        super.update(p);
        if (p.getValueLength() != 0) {
            try {
                this.value = new String(getValueBytesArray(), CHARSET);
            } catch (UnsupportedEncodingException e) {
                this.value = "Unsupported encoding";
            }
        }

        return this;
    }
}
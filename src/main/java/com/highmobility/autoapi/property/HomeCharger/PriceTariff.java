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

package com.highmobility.autoapi.property.homecharger;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.utils.ByteUtils;

public class PriceTariff extends Property {
    PricingType pricingType;
    String currency;
    float price;

    /**
     * @return The pricing type.
     */
    public PricingType getPricingType() {
        return pricingType;
    }

    /**
     * @return The currency name.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return The price.
     */
    public float getPrice() {
        return price;
    }

    public PriceTariff(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < 10) throw new CommandParseException();
        pricingType = PricingType.fromByte(bytes[6]);
        price = Property.getFloat(bytes, 7);
        int currencyLength = Property.getUnsignedInt(bytes, 11, 1);
        currency = Property.getString(bytes, 12, currencyLength);
    }

    public PriceTariff(PricingType pricingType, String currency, float price) {
        super((byte) 0x00, 5 + 1 + currency.length());
        if (currency.length() < 3) throw new IllegalArgumentException("Currency length needs to be > 3");

        bytes[6] = pricingType.getByte();
        ByteUtils.setBytes(bytes, Property.floatToBytes(price), 7);
        ByteUtils.setBytes(bytes, Property.intToBytes(currency.length(), 1), 11);
        ByteUtils.setBytes(bytes, Property.stringToBytes(currency), 12);

        this.pricingType = pricingType;
        this.currency = currency;
        this.price = price;
    }

    public enum PricingType {
        STARTING_FEE((byte) 0x00),
        PER_MINUTE((byte) 0x01),
        PER_KWH((byte) 0x02);

        public static PricingType fromByte(byte byteValue) {
            PricingType[] values = PricingType.values();

            for (int i = 0; i < values.length; i++) {
                PricingType state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }

            return null;
        }

        private byte value;

        PricingType(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}

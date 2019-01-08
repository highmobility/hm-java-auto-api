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

import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PercentageProperty;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Offroad State is received by the car.
 */
public class OffroadState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.OFF_ROAD, 0x01);

    private static final byte ROUTE_ID = 0x01;
    private static final byte WHEEL_ID = 0x02;

    IntegerProperty routeIncline;
    PercentageProperty wheelSuspension;

    /**
     * @return The route elevation incline in degrees, which is a negative number for decline.
     */
    @Nullable public IntegerProperty getRouteIncline() {
        return routeIncline;
    }

    /**
     * @return The wheel suspension level percentage, whereas 0 is no suspension and 1 maximum.
     * suspension
     */
    @Nullable public PercentageProperty getWheelSuspension() {
        return wheelSuspension;
    }

    public OffroadState(byte[] bytes) {
        super(TYPE, bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case ROUTE_ID:
                        routeIncline = new IntegerProperty(p, false);
                        return routeIncline;
                    case WHEEL_ID:
                        wheelSuspension = new PercentageProperty(p);
                        return wheelSuspension;
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private OffroadState(Builder builder) {
        super(builder);
        routeIncline = builder.routeIncline;
        wheelSuspension = builder.wheelSuspension;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private IntegerProperty routeIncline;
        private PercentageProperty wheelSuspension;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param routeIncline The route elevation incline in degrees, which is a negative number
         *                     for decline.
         * @return The builder.
         */
        public Builder setRouteIncline(IntegerProperty routeIncline) {
            this.routeIncline = routeIncline;
            routeIncline.setIdentifier(ROUTE_ID, 2);
            addProperty(routeIncline);
            return this;
        }

        /**
         * @param wheelSuspension The wheel suspension level percentage, whereas 0 is no suspension
         *                        and 1 maximum suspension.
         * @return The builder.
         */
        public Builder setWheelSuspension(PercentageProperty wheelSuspension) {
            this.wheelSuspension = wheelSuspension;
            wheelSuspension.setIdentifier(WHEEL_ID);
            addProperty(wheelSuspension);
            return this;
        }

        public OffroadState build() {
            return new OffroadState(this);
        }
    }
}
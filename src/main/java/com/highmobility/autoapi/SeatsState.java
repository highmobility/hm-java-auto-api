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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.PersonDetected;
import com.highmobility.autoapi.value.SeatbeltState;
import javax.annotation.Nullable;
import com.highmobility.autoapi.value.SeatLocation;
import java.util.ArrayList;
import java.util.List;

/**
 * The seats state
 */
public class SeatsState extends SetCommand {
    public static final int IDENTIFIER = Identifier.SEATS;

    public static final byte IDENTIFIER_PERSONS_DETECTED = 0x02;
    public static final byte IDENTIFIER_SEATBELTS_STATE = 0x03;

    Property<PersonDetected>[] personsDetected;
    Property<SeatbeltState>[] seatbeltsState;

    /**
     * @return The persons detected
     */
    public Property<PersonDetected>[] getPersonsDetected() {
        return personsDetected;
    }

    /**
     * @return The seatbelts state
     */
    public Property<SeatbeltState>[] getSeatbeltsState() {
        return seatbeltsState;
    }

    /**
     * @param location The seat location.
     * @return A person detection on a seat.
     */
    @Nullable public Property<PersonDetected> getPersonDetection(SeatLocation location) {
        for (int i = 0; i < personsDetected.length; i++) {
            Property<PersonDetected> property = personsDetected[i];
            if (property.getValue() != null && property.getValue().getSeatLocation() == location)
                return property;
        }

        return null;
    }

    /**
     * @param location The seat location.
     * @return The seat belt state.
     */
    @Nullable public Property<SeatbeltState> getSeatBeltFastened(SeatLocation location) {
        for (int i = 0; i < seatbeltsState.length; i++) {
            Property<SeatbeltState> property = seatbeltsState[i];
            if (property.getValue() != null && property.getValue().getSeatLocation() == location)
                return property;
        }

        return null;
    }

    SeatsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> personsDetectedBuilder = new ArrayList<>();
        ArrayList<Property> seatbeltsStateBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_PERSONS_DETECTED:
                        Property<PersonDetected> personDetected = new Property(PersonDetected.class, p);
                        personsDetectedBuilder.add(personDetected);
                        return personDetected;
                    case IDENTIFIER_SEATBELTS_STATE:
                        Property<SeatbeltState> seatbeltState = new Property(SeatbeltState.class, p);
                        seatbeltsStateBuilder.add(seatbeltState);
                        return seatbeltState;
                }

                return null;
            });
        }

        personsDetected = personsDetectedBuilder.toArray(new Property[0]);
        seatbeltsState = seatbeltsStateBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private SeatsState(Builder builder) {
        super(builder);

        personsDetected = builder.personsDetected.toArray(new Property[0]);
        seatbeltsState = builder.seatbeltsState.toArray(new Property[0]);
    }

    public static final class Builder extends SetCommand.Builder {
        private List<Property> personsDetected = new ArrayList<>();
        private List<Property> seatbeltsState = new ArrayList<>();

        public Builder() {
            super(IDENTIFIER);
        }

        public SeatsState build() {
            return new SeatsState(this);
        }

        /**
         * Add an array of persons detected.
         * 
         * @param personsDetected The persons detected
         * @return The builder
         */
        public Builder setPersonsDetected(Property<PersonDetected>[] personsDetected) {
            this.personsDetected.clear();
            for (int i = 0; i < personsDetected.length; i++) {
                addPersonDetected(personsDetected[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single person detected.
         * 
         * @param personDetected The person detected
         * @return The builder
         */
        public Builder addPersonDetected(Property<PersonDetected> personDetected) {
            personDetected.setIdentifier(IDENTIFIER_PERSONS_DETECTED);
            addProperty(personDetected);
            personsDetected.add(personDetected);
            return this;
        }
        
        /**
         * Add an array of seatbelts state.
         * 
         * @param seatbeltsState The seatbelts state
         * @return The builder
         */
        public Builder setSeatbeltsState(Property<SeatbeltState>[] seatbeltsState) {
            this.seatbeltsState.clear();
            for (int i = 0; i < seatbeltsState.length; i++) {
                addSeatbeltState(seatbeltsState[i]);
            }
        
            return this;
        }
        /**
         * Add a single seatbelt state.
         * 
         * @param seatbeltState The seatbelt state
         * @return The builder
         */
        public Builder addSeatbeltState(Property<SeatbeltState> seatbeltState) {
            seatbeltState.setIdentifier(IDENTIFIER_SEATBELTS_STATE);
            addProperty(seatbeltState);
            seatbeltsState.add(seatbeltState);
            return this;
        }
    }
}
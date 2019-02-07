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

import com.highmobility.autoapi.property.ObjectPropertyInteger;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.maintenance.ConditionBasedService;
import com.highmobility.autoapi.property.maintenance.TeleserviceAvailability;

import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Maintenance State message is received by the car. The new state is
 * included in the message payload and may be the result of user, device or car triggered action.
 */
public class MaintenanceState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.MAINTENANCE, 0x01);

    private static final byte IDENTIFIER_DAYS_TO_NEXT_SERVICE = 0x01;
    private static final byte IDENTIFIER_KILOMETERS_TO_NEXT_SERVICE = 0x02;
    private static final byte IDENTIFIER_CBS_REPORTS_COUNT = 0x03;
    private static final byte IDENTIFIER_MONTHS_TO_EXHAUST_INSPECTION = 0x04;
    private static final byte IDENTIFIER_SERVICE_DISTANCE_THRESHOLD = 0x06;
    private static final byte IDENTIFIER_SERVICE_TIME_THRESHOLD = 0x07;
    private static final byte IDENTIFIER_AUTOMATIC_TELESERVICE_CALL_DATE = 0x08;
    private static final byte IDENTIFIER_TELESERVICE_BATTERY_CALL_DATE = 0x09;
    private static final byte IDENTIFIER_NEXT_INSPECTION_DATE = 0x0A;
    private static final byte IDENTIFIER_CONDITION_BASED_SERVICES = 0x0B;
    private static final byte IDENTIFIER_BRAKE_FLUID_CHANGE_DATE = 0x0C;

    private ObjectPropertyInteger kilometersToNextService =
            new ObjectPropertyInteger(IDENTIFIER_KILOMETERS_TO_NEXT_SERVICE, false);
    private ObjectPropertyInteger daysToNextService =
            new ObjectPropertyInteger(IDENTIFIER_DAYS_TO_NEXT_SERVICE, false);

    // level8
    private ObjectPropertyInteger cbsReportsCount =
            new ObjectPropertyInteger(IDENTIFIER_CBS_REPORTS_COUNT, false);
    private ObjectPropertyInteger monthsToExhaustInspection =
            new ObjectPropertyInteger(IDENTIFIER_MONTHS_TO_EXHAUST_INSPECTION, false);
    private TeleserviceAvailability teleserviceAvailability;
    private ObjectPropertyInteger serviceDistanceThreshold =
            new ObjectPropertyInteger(IDENTIFIER_SERVICE_DISTANCE_THRESHOLD, false);
    private ObjectPropertyInteger serviceTimeThreshold =
            new ObjectPropertyInteger(IDENTIFIER_SERVICE_TIME_THRESHOLD, false);

    private Calendar automaticTeleserviceCallDate;
    private Calendar teleserviceBatteryCallDate;
    private Calendar nextInspectionDate;
    private ConditionBasedService[] conditionBasedServices;
    private Calendar brakeFluidChangeDate;

    /**
     * @return The amount of kilometers until next servicing of the car
     */
    @Nullable public ObjectPropertyInteger getKilometersToNextService() {
        return kilometersToNextService;
    }

    /**
     * @return The number of days until next servicing of the car, whereas negative is overdue
     */
    @Nullable public ObjectPropertyInteger getDaysToNextService() {
        return daysToNextService;
    }

    /**
     * @return The number of CBS reports.
     */
    @Nullable public ObjectPropertyInteger getCbsReportsCount() {
        return cbsReportsCount;
    }

    /**
     * @return The number of Months until exhaust inspection.
     */
    @Nullable public ObjectPropertyInteger getMonthsToExhaustInspection() {
        return monthsToExhaustInspection;
    }

    /**
     * @return The Teleservice availability.
     */
    @Nullable public TeleserviceAvailability getTeleserviceAvailability() {
        return teleserviceAvailability;
    }

    /**
     * @return The service distance threshold in km.
     */
    @Nullable public ObjectPropertyInteger getServiceDistanceThreshold() {
        return serviceDistanceThreshold;
    }

    /**
     * @return The service time threshold in weeks.
     */
    @Nullable public ObjectPropertyInteger getServiceTimeThreshold() {
        return serviceTimeThreshold;
    }

    /**
     * @return The automatic Teleservice call date.
     */
    @Nullable public Calendar getAutomaticTeleserviceCallDate() {
        return automaticTeleserviceCallDate;
    }

    /**
     * @return The Teleservice battery call date.
     */
    @Nullable public Calendar getTeleserviceBatteryCallDate() {
        return teleserviceBatteryCallDate;
    }

    /**
     * @return The next inspection date.
     */
    @Nullable public Calendar getNextInspectionDate() {
        return nextInspectionDate;
    }

    /**
     * @return The condition based services.
     */
    @Nullable public ConditionBasedService[] getConditionBasedServices() {
        return conditionBasedServices;
    }

    /**
     * @return The brake fluid change date.
     */
    @Nullable public Calendar getBrakeFluidChangeDate() {
        return brakeFluidChangeDate;
    }

    MaintenanceState(byte[] bytes) {
        super(bytes);
        ArrayList<ConditionBasedService> conditionBasedServices = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_DAYS_TO_NEXT_SERVICE:
                        return daysToNextService.update(p);
                    case IDENTIFIER_KILOMETERS_TO_NEXT_SERVICE:
                        return kilometersToNextService.update(p);
                    case IDENTIFIER_CBS_REPORTS_COUNT:
                        return cbsReportsCount.update(p);
                    case IDENTIFIER_MONTHS_TO_EXHAUST_INSPECTION:
                        return monthsToExhaustInspection.update(p);
                    case TeleserviceAvailability.IDENTIFIER:
                        teleserviceAvailability =
                                TeleserviceAvailability.fromByte(p.getValueByte());
                        return teleserviceAvailability;
                    case IDENTIFIER_SERVICE_DISTANCE_THRESHOLD:
                        return serviceDistanceThreshold.update(p);
                    case IDENTIFIER_SERVICE_TIME_THRESHOLD:
                        return serviceTimeThreshold.update(p);
                    case IDENTIFIER_AUTOMATIC_TELESERVICE_CALL_DATE:
                        automaticTeleserviceCallDate = Property.getCalendar(p.getValueBytesArray());
                        return automaticTeleserviceCallDate;
                    case IDENTIFIER_TELESERVICE_BATTERY_CALL_DATE:
                        teleserviceBatteryCallDate = Property.getCalendar(p.getValueBytesArray());
                        return teleserviceBatteryCallDate;
                    case IDENTIFIER_NEXT_INSPECTION_DATE:
                        nextInspectionDate = Property.getCalendar(p.getValueBytesArray());
                        return nextInspectionDate;
                    case IDENTIFIER_CONDITION_BASED_SERVICES:
                        ConditionBasedService s = new ConditionBasedService(p.getByteArray());
                        conditionBasedServices.add(s);
                        return s;
                    case IDENTIFIER_BRAKE_FLUID_CHANGE_DATE:
                        brakeFluidChangeDate = Property.getCalendar(p.getValueBytesArray());
                        return brakeFluidChangeDate;
                }

                return null;
            });
        }

        this.conditionBasedServices = conditionBasedServices.toArray(new ConditionBasedService[0]);
    }

    private MaintenanceState(Builder builder) {
        super(builder);
        kilometersToNextService = builder.kilometersToNextService;
        daysToNextService = builder.daysToNextService;
        this.cbsReportsCount = builder.cbsReportsCount;
        this.monthsToExhaustInspection = builder.monthsToExhaustInspection;
        this.teleserviceAvailability = builder.teleserviceAvailability;
        this.serviceDistanceThreshold = builder.serviceDistanceThreshold;
        this.serviceTimeThreshold = builder.serviceTimeThreshold;
        this.automaticTeleserviceCallDate = builder.automaticTeleserviceCallDate;
        this.teleserviceBatteryCallDate = builder.teleserviceBatteryCallDate;
        this.nextInspectionDate = builder.nextInspectionDate;
        this.conditionBasedServices = builder.conditionBasedServices.toArray(new
                ConditionBasedService[0]);
        this.brakeFluidChangeDate = builder.brakeFluidChangeDate;
    }

    @Override public boolean isState() {
        return true;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private ObjectPropertyInteger kilometersToNextService;
        private ObjectPropertyInteger daysToNextService;
        private ObjectPropertyInteger cbsReportsCount;
        private ObjectPropertyInteger monthsToExhaustInspection;
        private TeleserviceAvailability teleserviceAvailability;
        private ObjectPropertyInteger serviceDistanceThreshold;
        private ObjectPropertyInteger serviceTimeThreshold;
        private Calendar automaticTeleserviceCallDate;
        private Calendar teleserviceBatteryCallDate;
        private Calendar nextInspectionDate;
        private ArrayList<ConditionBasedService> conditionBasedServices = new ArrayList<>();
        private Calendar brakeFluidChangeDate;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param kilometersToNextService The amount of kilometers until next servicing of the car.
         * @return The builder.
         */
        public Builder setKilometersToNextService(ObjectPropertyInteger kilometersToNextService) {
            this.kilometersToNextService = kilometersToNextService;
            kilometersToNextService.update(IDENTIFIER_KILOMETERS_TO_NEXT_SERVICE, false, 3);
            addProperty(kilometersToNextService);
            return this;
        }

        /**
         * @param daysToNextService The number of days until next servicing of the car, whereas
         *                          negative is overdue.
         * @return The builder.
         */
        public Builder setDaysToNextService(ObjectPropertyInteger daysToNextService) {
            this.daysToNextService = daysToNextService;
            daysToNextService.update(IDENTIFIER_DAYS_TO_NEXT_SERVICE, false, 2);
            addProperty(daysToNextService);
            return this;
        }
/*
        public Builder setCbsReportsCount(ObjectPropertyInteger cbsReportsCount) {
            this.cbsReportsCount = cbsReportsCount;
            addProperty(new ObjectPropertyInteger(IDENTIFIER_CBS_REPORTS_COUNT, cbsReportsCount,
            1));
            return this;
        }

        public Builder setMonthsToExhaustInspection(ObjectPropertyInteger
        monthsToExhaustInspection) {
            this.monthsToExhaustInspection = monthsToExhaustInspection;
            addProperty(new ObjectPropertyInteger(IDENTIFIER_MONTHS_TO_EXHAUST_INSPECTION,
                    monthsToExhaustInspection, 1));
            return this;
        }

        public Builder setTeleserviceAvailability(TeleserviceAvailability teleserviceAvailability) {
            this.teleserviceAvailability = teleserviceAvailability;
            addProperty(teleserviceAvailability);
            return this;
        }

        public Builder setServiceDistanceThreshold(ObjectPropertyInteger serviceDistanceThreshold) {
            this.serviceDistanceThreshold = serviceDistanceThreshold;
            addProperty(new ObjectPropertyInteger(IDENTIFIER_SERVICE_DISTANCE_THRESHOLD,
                    serviceDistanceThreshold, 2));
            return this;
        }

        public Builder setServiceTimeThreshold(ObjectPropertyInteger serviceTimeThreshold) {
            this.serviceTimeThreshold = serviceTimeThreshold;
            addProperty(new ObjectPropertyInteger(IDENTIFIER_SERVICE_TIME_THRESHOLD,
                    serviceDistanceThreshold, 1));
            return this;
        }

        public Builder setAutomaticTeleserviceCallDate(Calendar automaticTeleserviceCallDate) {
            this.automaticTeleserviceCallDate = automaticTeleserviceCallDate;
            addProperty(new CalendarProperty(IDENTIFIER_AUTOMATIC_TELESERVICE_CALL_DATE,
                    automaticTeleserviceCallDate));
            return this;
        }

        public Builder setTeleserviceBatteryCallDate(Calendar teleserviceBatteryCallDate) {
            this.teleserviceBatteryCallDate = teleserviceBatteryCallDate;
            addProperty(new CalendarProperty(IDENTIFIER_TELESERVICE_BATTERY_CALL_DATE,
                    teleserviceBatteryCallDate));
            return this;
        }

        public Builder setNextInspectionDate(Calendar nextInspectionDate) {
            this.nextInspectionDate = nextInspectionDate;
            addProperty(new CalendarProperty(IDENTIFIER_NEXT_INSPECTION_DATE, nextInspectionDate));
            return this;
        }

        public Builder setConditionBasedServices(ConditionBasedService[] conditionBasedServices) {
            for (ConditionBasedService conditionBasedService : conditionBasedServices) {
                addConditionBasedService(conditionBasedService);
            }

            return this;
        }

        public Builder addConditionBasedService(ConditionBasedService conditionBasedService) {
            addProperty(conditionBasedService);
            conditionBasedServices.add(conditionBasedService);
            return this;
        }

        public Builder setBrakeFluidChangeDate(Calendar brakeFluidChangeDate) {
            this.brakeFluidChangeDate = brakeFluidChangeDate;
            addProperty(new CalendarProperty(IDENTIFIER_BRAKE_FLUID_CHANGE_DATE,
                    brakeFluidChangeDate));
            return this;
        }
        */

        public MaintenanceState build() {
            return new MaintenanceState(this);
        }
    }
}
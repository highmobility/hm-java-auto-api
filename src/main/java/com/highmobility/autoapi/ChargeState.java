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

import com.highmobility.autoapi.property.BooleanProperty;
import com.highmobility.autoapi.property.ChargeMode;
import com.highmobility.autoapi.property.ChargingState;
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.ChargePortState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.TimeProperty;
import com.highmobility.autoapi.property.charging.ChargingTimer;
import com.highmobility.autoapi.property.charging.DepartureTime;
import com.highmobility.autoapi.property.charging.PlugType;
import com.highmobility.autoapi.property.value.Time;
import com.highmobility.utils.ByteUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Charge State command is received by the car or when the car is plugged
 * in, disconnected, starts or stops charging, or when the charge limit is changed.
 */
public class ChargeState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CHARGING, 0x01);

    private static final byte ESTIMATED_RANGE_IDENTIFIER = 0x02;
    private static final byte BATTERY_LEVEL_IDENTIFIER = 0x03;
    private static final byte BATTERY_CURRENT_AC_IDENTIFIER = 0x04;
    private static final byte BATTERY_CURRENT_DC_IDENTIFIER = 0x05;
    private static final byte CHARGER_VOLTAGE_AC_IDENTIFIER = 0x06;
    private static final byte CHARGER_VOLTAGE_DC_IDENTIFIER = 0x07;
    private static final byte CHARGE_LIMIT_IDENTIFIER = 0x08;
    private static final byte TIME_TO_COMPLETE_CHARGE_IDENTIFIER = 0x09;
    private static final byte CHARGE_RATE_IDENTIFIER = 0x0A;
    private static final byte CHARGE_PORT_STATE_IDENTIFIER = 0x0B;
    private static final byte CHARGE_MODE_IDENTIFIER = 0x0C;

    private static final byte MAX_CHARGING_CURRENT_IDENTIFIER = 0x0E;
    private static final byte PLUG_TYPE_IDENTIFIER = 0x0F;
    private static final byte CHARGING_WINDOW_CHOSEN_IDENTIFIER = 0x10;
    private static final byte DEPARTURE_TIMES_IDENTIFIER = 0x11;
    private static final byte REDUCTION_OF_CHARGING_CURRENT_TIMES_IDENTIFIER = 0x13;
    private static final byte BATTERY_TEMPERATURE_IDENTIFIER = 0x14;
    private static final byte TIMER_IDENTIFIER = 0x15;
    private static final byte PLUGGED_IN_IDENTIFIER = 0x16;

    Integer estimatedRange;
    Float batteryLevel;
    Float batteryCurrentAC;
    Float batteryCurrentDC;

    Float chargerVoltageAC;
    Float chargerVoltageDC;

    Float chargeLimit;
    Integer timeToCompleteCharge;

    Float chargingRate;
    ChargePortState chargeChargePortState;

    ChargeMode chargeMode;

    Float maxChargingCurrent;
    PlugType plugType;
    Boolean chargingWindowChosen;
    DepartureTime[] departureTimes;

    Time[] reductionOfChargingCurrentTimes;
    Float batteryTemperature;
    ChargingTimer[] timers;
    Boolean pluggedIn;
    ChargingState activeState;

    /**
     * @return The estimated range in km.
     */
    @Nullable public Integer getEstimatedRange() {
        return estimatedRange;
    }

    /**
     * @return The battery level percentage.
     */
    @Nullable public Float getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * @return The battery current in AC.
     */
    @Nullable public Float getBatteryCurrentAC() {
        return batteryCurrentAC;
    }

    /**
     * @return The battery current in DC.
     */
    @Nullable public Float getBatteryCurrentDC() {
        return batteryCurrentDC;
    }

    /**
     * @return The Charger voltage in AC.
     */
    @Nullable public Float getChargerVoltageAC() {
        return chargerVoltageAC;
    }

    /**
     * @return The Charger voltage in DC.
     */
    @Nullable public Float getChargerVoltageDC() {
        return chargerVoltageDC;
    }

    /**
     * @return The Charge limit percentage.
     */
    @Nullable public Float getChargeLimit() {
        return chargeLimit;
    }

    /**
     * @return The time to complete the charge in minutes.
     */
    @Nullable public Integer getTimeToCompleteCharge() {
        return timeToCompleteCharge;
    }

    /**
     * @return The charging rate in kW, when charging.
     */
    @Nullable public Float getChargingRate() {
        return chargingRate;
    }

    /**
     * @return The charge port state.
     */
    @Nullable public ChargePortState getChargeChargePortState() {
        return chargeChargePortState;
    }

    /**
     * @return The charge mode.
     */
    @Nullable public ChargeMode getChargeMode() {
        return chargeMode;
    }

    /**
     * @return The maximum charging current.
     */
    @Nullable public Float getMaxChargingCurrent() {
        return maxChargingCurrent;
    }

    /**
     * @return The plug type.
     */
    @Nullable public PlugType getPlugType() {
        return plugType;
    }

    /**
     * @return Indication on whether charging window is chosen.
     */
    @Nullable public Boolean getChargingWindowChosen() {
        return chargingWindowChosen;
    }

    /**
     * @return The departure times.
     */
    @Nullable public DepartureTime[] getDepartureTimes() {
        return departureTimes;
    }

    /**
     * @return The reduction of charging current times.
     */
    @Nullable public Time[] getReductionOfChargingCurrentTimes() {
        return reductionOfChargingCurrentTimes;
    }

    /**
     * @return The battery temperature in Celsius.
     */
    @Nullable public Float getBatteryTemperature() {
        return batteryTemperature;
    }

    /**
     * @return The charging timers.
     */
    @Nullable public ChargingTimer[] getTimers() {
        return timers;
    }

    /**
     * Get the charge timer for the given type.
     *
     * @param type The charge timer type.
     * @return The charge timer.
     */
    @Nullable public ChargingTimer getTimer(ChargingTimer.Type type) {
        if (timers != null) {
            for (ChargingTimer timer : timers) {
                if (timer.getType() == type) return timer;
            }
        }
        return null;
    }

    /**
     * @return The plugged in state.
     */
    @Nullable public Boolean getPluggedIn() {
        return pluggedIn;
    }

    /**
     * @return The charging active state.
     */
    @Nullable
    public ChargingState getActiveState() {
        return activeState;
    }

    public ChargeState(byte[] bytes) {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];

            try {
                switch (property.getPropertyIdentifier()) {
                    case ChargingState.IDENTIFIER:
                        activeState = ChargingState.fromByte(property.getValueByte());
                        break;
                    case ESTIMATED_RANGE_IDENTIFIER:
                        estimatedRange = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case BATTERY_LEVEL_IDENTIFIER:
                        batteryLevel = property.getValueByte() / 100f;
                        break;
                    case BATTERY_CURRENT_AC_IDENTIFIER:
                        batteryCurrentAC = Property.getFloat(property.getValueBytes());
                        break;
                    case BATTERY_CURRENT_DC_IDENTIFIER:
                        batteryCurrentDC = Property.getFloat(property.getValueBytes());
                        break;
                    case CHARGER_VOLTAGE_AC_IDENTIFIER:
                        chargerVoltageAC = Property.getFloat(property.getValueBytes());
                        break;
                    case CHARGER_VOLTAGE_DC_IDENTIFIER:
                        chargerVoltageDC = Property.getFloat(property.getValueBytes());
                        break;
                    case CHARGE_LIMIT_IDENTIFIER:
                        chargeLimit = property.getValueByte() / 100f;
                        break;
                    case TIME_TO_COMPLETE_CHARGE_IDENTIFIER:
                        timeToCompleteCharge = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case CHARGE_RATE_IDENTIFIER:
                        chargingRate = Property.getFloat(property.getValueBytes());
                        break;
                    case CHARGE_PORT_STATE_IDENTIFIER:
                        chargeChargePortState = ChargePortState.fromByte(property.getValueByte());
                        break;
                    case CHARGE_MODE_IDENTIFIER:
                        chargeMode = ChargeMode.fromByte(property.getValueByte());
                        break;
                    case MAX_CHARGING_CURRENT_IDENTIFIER:
                        maxChargingCurrent = Property.getFloat(property.getValueBytes());
                        break;
                    case PLUG_TYPE_IDENTIFIER:
                        plugType = PlugType.fromByte(property.getValueByte());
                        break;
                    case CHARGING_WINDOW_CHOSEN_IDENTIFIER:
                        chargingWindowChosen = Property.getBool(property.getValueByte());
                        break;
                    case DEPARTURE_TIMES_IDENTIFIER:
                        departureTimes = createOrAddToArray(departureTimes, new
                                DepartureTime(property.getPropertyBytes()));
                        break;
                    case REDUCTION_OF_CHARGING_CURRENT_TIMES_IDENTIFIER:
                        reductionOfChargingCurrentTimes = createOrAddToArray
                                (reductionOfChargingCurrentTimes, new Time(property
                                        .getValueBytes()));
                        break;
                    case BATTERY_TEMPERATURE_IDENTIFIER:
                        batteryTemperature = Property.getFloat(property.getValueBytes());
                        break;
                    case PLUGGED_IN_IDENTIFIER:
                        pluggedIn = Property.getBool(property.getValueByte());
                        break;
                    case TIMER_IDENTIFIER:
                        timers = createOrAddToArray(timers, new ChargingTimer(property
                                .getPropertyBytes()));
                        break;
                }
            } catch (Exception e) {
                logger.info("Failed to parse command:" + ByteUtils.hexFromBytes(bytes) + " " + e
                        .getMessage());
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private ChargeState(Builder builder) {
        super(builder);
        estimatedRange = builder.estimatedRange;
        batteryLevel = builder.batteryLevel;
        batteryCurrentAC = builder.batteryCurrentAC;
        batteryCurrentDC = builder.batteryCurrentDC;
        chargerVoltageAC = builder.chargerVoltageAC;
        chargerVoltageDC = builder.chargerVoltageDC;
        chargeLimit = builder.chargeLimit;
        timeToCompleteCharge = builder.timeToCompleteCharge;
        chargingRate = builder.chargingRate;
        chargeChargePortState = builder.chargePortState;
        chargeMode = builder.chargeMode;
        maxChargingCurrent = builder.maxChargingCurrent;
        plugType = builder.plugType;
        chargingWindowChosen = builder.chargingWindowChosen;
        departureTimes = builder.departureTimes.toArray(new
                DepartureTime[builder.departureTimes.size()]);
        reductionOfChargingCurrentTimes = builder.reductionOfChargingCurrentTimes.toArray(new
                Time[builder.reductionOfChargingCurrentTimes.size()]);
        batteryTemperature = builder.batteryTemperature;
        timers = builder.timers.toArray(new ChargingTimer[builder.timers.size()]);
        pluggedIn = builder.pluggedIn;
        activeState = builder.activeState;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Integer estimatedRange;
        private Float batteryLevel;
        private Float batteryCurrentAC;
        private Float batteryCurrentDC;

        private Float chargerVoltageAC;
        private Float chargerVoltageDC;

        private Float chargeLimit;
        private Integer timeToCompleteCharge;

        private Float chargingRate;
        private ChargePortState chargePortState;

        private ChargeMode chargeMode;
        private Float maxChargingCurrent;
        private PlugType plugType;
        private Boolean chargingWindowChosen;
        private List<DepartureTime> departureTimes = new ArrayList<>();

        private List<Time> reductionOfChargingCurrentTimes = new ArrayList<>();
        private Float batteryTemperature;
        private List<ChargingTimer> timers = new ArrayList<>();
        private Boolean pluggedIn;
        private ChargingState activeState;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param activeState The charging state.
         * @return The builder.
         */
        public Builder setActiveState(ChargingState activeState) {
            this.activeState = activeState;
            addProperty(activeState);
            return this;
        }

        /**
         * @param estimatedRange The estimated range in km.
         * @return The builder.
         */
        public Builder setEstimatedRange(Integer estimatedRange) {
            this.estimatedRange = estimatedRange;
            addProperty(new IntegerProperty(ESTIMATED_RANGE_IDENTIFIER, estimatedRange, 2));
            return this;
        }

        /**
         * @param batteryLevel The battery level percentage.
         * @return The builder.
         */
        public Builder setBatteryLevel(Float batteryLevel) {
            this.batteryLevel = batteryLevel;
            addProperty(new PercentageProperty(BATTERY_LEVEL_IDENTIFIER, batteryLevel));
            return this;
        }

        /**
         * @param batteryCurrentAC The battery current in AC.
         * @return The builder.
         */
        public Builder setBatteryCurrentAC(Float batteryCurrentAC) {
            this.batteryCurrentAC = batteryCurrentAC;
            addProperty(new FloatProperty(BATTERY_CURRENT_AC_IDENTIFIER, batteryCurrentAC));
            return this;
        }

        /**
         * @param batteryCurrentDC The battery current in DC.
         * @return The builder.
         */
        public Builder setBatteryCurrentDC(Float batteryCurrentDC) {
            this.batteryCurrentDC = batteryCurrentDC;
            addProperty(new FloatProperty(BATTERY_CURRENT_DC_IDENTIFIER, batteryCurrentDC));
            return this;
        }

        /**
         * @param chargerVoltageAC The charger voltage in AC.
         * @return The builder.
         */
        public Builder setChargerVoltageAC(Float chargerVoltageAC) {
            this.chargerVoltageAC = chargerVoltageAC;
            addProperty(new FloatProperty(CHARGER_VOLTAGE_AC_IDENTIFIER, chargerVoltageAC));
            return this;
        }

        /**
         * @param chargerVoltageDC The charger voltage in DC.
         * @return The builder.
         */
        public Builder setChargerVoltageDC(Float chargerVoltageDC) {
            this.chargerVoltageDC = chargerVoltageDC;
            addProperty(new FloatProperty(CHARGER_VOLTAGE_DC_IDENTIFIER, chargerVoltageDC));
            return this;
        }

        /**
         * @param chargeLimit The charge limit percentage.
         * @return The builder.
         */
        public Builder setChargeLimit(Float chargeLimit) {
            this.chargeLimit = chargeLimit;
            addProperty(new PercentageProperty(CHARGE_LIMIT_IDENTIFIER, chargeLimit));
            return this;
        }

        /**
         * @param timeToCompleteCharge The time to complete the charge in minutes.
         * @return The builder.
         */
        public Builder setTimeToCompleteCharge(Integer timeToCompleteCharge) {
            this.timeToCompleteCharge = timeToCompleteCharge;
            addProperty(new IntegerProperty(TIME_TO_COMPLETE_CHARGE_IDENTIFIER,
                    timeToCompleteCharge, 1));
            return this;
        }

        /**
         * @param chargingRate The charge rate in kW, when charging.
         * @return The builder.
         */
        public Builder setChargingRate(Float chargingRate) {
            this.chargingRate = chargingRate;
            addProperty(new FloatProperty(CHARGE_RATE_IDENTIFIER, chargingRate));
            return this;
        }

        /**
         * @param chargePortState The charge port state.
         * @return The builder.
         */
        public Builder setChargePortState(ChargePortState chargePortState) {
            this.chargePortState = chargePortState;
            chargePortState.setIdentifier(CHARGE_PORT_STATE_IDENTIFIER);
            addProperty(chargePortState);
            return this;
        }

        /**
         * @param chargeMode The charge mode.
         * @return The builder.
         */
        public Builder setChargeMode(ChargeMode chargeMode) {
            this.chargeMode = chargeMode;
            chargeMode.setIdentifier(CHARGE_MODE_IDENTIFIER);
            addProperty(chargeMode);
            return this;
        }

        public Builder setMaxChargingCurrent(Float maxChargingCurrent) {
            addProperty(new FloatProperty(MAX_CHARGING_CURRENT_IDENTIFIER, maxChargingCurrent));
            this.maxChargingCurrent = maxChargingCurrent;
            return this;
        }

        public Builder setPlugType(PlugType plugType) {
            this.plugType = plugType;
            addProperty(plugType);
            return this;
        }

        public Builder setChargingWindowChosen(Boolean chargingWindowChosen) {
            this.chargingWindowChosen = chargingWindowChosen;
            addProperty(new BooleanProperty(CHARGING_WINDOW_CHOSEN_IDENTIFIER,
                    chargingWindowChosen));
            return this;
        }

        public Builder setDepartureTimes(DepartureTime[] departureTimes) {
            this.departureTimes = Arrays.asList(departureTimes);

            for (int i = 0; i < departureTimes.length; i++) {
                addProperty(departureTimes[i]);
            }

            return this;
        }

        public Builder addDepartureTime(DepartureTime departureTime) {
            this.departureTimes.add(departureTime);
            addProperty(departureTime);
            return this;
        }

        public Builder setReductionOfChargingCurrentTimes(Time[] reductionOfChargingCurrentTimes) {
            this.reductionOfChargingCurrentTimes = Arrays.asList(reductionOfChargingCurrentTimes);

            for (int i = 0; i < reductionOfChargingCurrentTimes.length; i++) {
                addProperty(new TimeProperty(REDUCTION_OF_CHARGING_CURRENT_TIMES_IDENTIFIER,
                        reductionOfChargingCurrentTimes[i]));
            }

            return this;
        }

        public Builder addReductionOfChargingCurrentTime(Time reductionOfChargingCurrentTime) {
            this.reductionOfChargingCurrentTimes.add(reductionOfChargingCurrentTime);
            addProperty(new TimeProperty(REDUCTION_OF_CHARGING_CURRENT_TIMES_IDENTIFIER,
                    reductionOfChargingCurrentTime));
            return this;

        }

        public Builder setBatteryTemperature(Float batteryTemperature) {
            this.batteryTemperature = batteryTemperature;
            addProperty(new FloatProperty(BATTERY_TEMPERATURE_IDENTIFIER, batteryTemperature));
            return this;
        }

        /**
         * @param chargingTimers All of the charge timers.
         * @return The builder.
         */
        public Builder setTimers(ChargingTimer[] chargingTimers) {
            this.timers = Arrays.asList(chargingTimers);

            for (int i = 0; i < chargingTimers.length; i++) {
                ChargingTimer timer = chargingTimers[i];
                timer.setIdentifier(TIMER_IDENTIFIER);
                addProperty(chargingTimers[i]);
            }

            return this;
        }

        /**
         * Add a single charge timer.
         *
         * @param timer The charge timer.
         * @return The builder.
         */
        public Builder addTimer(ChargingTimer timer) {
            this.timers.add(timer);
            timer.setIdentifier(TIMER_IDENTIFIER);
            addProperty(timer);
            return this;
        }

        public Builder setPluggedIn(Boolean pluggedIn) {
            this.pluggedIn = pluggedIn;
            addProperty(new BooleanProperty(PLUGGED_IN_IDENTIFIER, pluggedIn));
            return this;
        }

        public ChargeState build() {
            return new ChargeState(this);
        }
    }
}
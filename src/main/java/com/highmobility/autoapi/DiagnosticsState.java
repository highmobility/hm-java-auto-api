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
import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.diagnostics.BrakeFluidLevel;
import com.highmobility.autoapi.property.diagnostics.CheckControlMessage;
import com.highmobility.autoapi.property.diagnostics.DiagnosticsTroubleCode;
import com.highmobility.autoapi.property.diagnostics.TirePressure;
import com.highmobility.autoapi.property.diagnostics.TireTemperature;
import com.highmobility.autoapi.property.diagnostics.WasherFluidLevel;
import com.highmobility.autoapi.property.diagnostics.WheelRpm;
import com.highmobility.autoapi.property.value.TireLocation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Command sent when a Get Diagnostics State command is received by the car. The new status is
 * included in the command payload and may be the result of user, device or car triggered action.
 */
public class DiagnosticsState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.DIAGNOSTICS, 0x01);

    private static final byte MILEAGE_IDENTIFIER = 0x01;
    private static final byte OIL_TEMPERATURE_IDENTIFIER = 0x02;
    private static final byte SPEED_IDENTIFIER = 0x03;
    private static final byte RPM_IDENTIFIER = 0x04;
    private static final byte FUEL_LEVEL_IDENTIFIER = 0x05;
    private static final byte RANGE_IDENTIFIER = 0x06;
    private static final byte WASHER_FLUID_LEVEL_IDENTIFIER = 0x09;
    private static final byte BATTERY_VOLTAGE_IDENTIFIER = 0x0B;
    private static final byte AD_BLUE_LEVEL_IDENTIFIER = 0x0C;
    private static final byte DISTANCE_DRIVEN_SINCE_RESET_IDENTIFIER = 0x0D;
    private static final byte DISTANCE_DRIVEN_SINCE_ENGINE_START_IDENTIFIER = 0x0E;
    private static final byte FUEL_VOLUME_IDENTIFIER = 0x0F;

    private static final byte ANTI_LOCK_BRAKING_ACTIVE_IDENTIFIER = 0x10;
    private static final byte ENGINE_COOLANT_TEMPERATURE_IDENTIFIER = 0x11;
    private static final byte ENGINE_TOTAL_OPERATING_HOURS_IDENTIFIER = 0x12;
    private static final byte ENGINE_TOTAL_FUEL_CONSUMPTION_IDENTIFIER = 0x13;

    private static final byte ENGINE_TORQUE_IDENTIFIER = 0x15;
    private static final byte ENGINE_LOAD_IDENTIFIER = 0x16;
    private static final byte WHEEL_BASE_SPEED_IDENTIFIER = 0x17;

    private static final byte IDENTIFIER_BATTERY_LEVEL = 0x18;
    private static final byte IDENTIFIER_CHECK_CONTROL_MESSAGES = 0x19;
    private static final byte IDENTIFIER_TIRE_PRESSURES = 0x1A;
    private static final byte IDENTIFIER_TIRE_TEMPERATURES = 0x1B;
    private static final byte IDENTIFIER_WHEEL_RPM = 0x1C;
    private static final byte IDENTIFIER_DIAGNOSTICS_TROUBLE_CODE = 0x1D;

    private static final byte IDENTIFIER_BRAKE_FLUID = 0x14;
    private static final byte MILEAGE_METERS_IDENTIFIER = 0x1E;

    IntegerProperty mileage;
    IntegerProperty oilTemperature;
    IntegerProperty speed;
    IntegerProperty rpm;
    PercentageProperty fuelLevel;
    IntegerProperty range;
    WasherFluidLevel washerFluidLevel;
    FloatProperty batteryVoltage;
    FloatProperty adBlueLevel;
    IntegerProperty distanceDrivenSinceReset;
    IntegerProperty distanceDrivenSinceEngineStart;
    FloatProperty fuelVolume;

    // level7
    BooleanProperty antiLockBrakingActive;
    IntegerProperty engineCoolantTemperature;
    FloatProperty engineTotalOperatingHours;
    FloatProperty engineTotalFuelConsumption;
    BrakeFluidLevel brakeFluidLevel;
    PercentageProperty engineTorque;
    PercentageProperty engineLoad;
    IntegerProperty wheelBasedSpeed;

    // level8
    PercentageProperty batteryLevel;
    CheckControlMessage[] checkControlMessages;
    TirePressure[] tirePressures;
    TireTemperature[] tireTemperatures;
    WheelRpm[] wheelRpms;
    DiagnosticsTroubleCode[] troubleCodes;

    IntegerProperty mileageMeters;

    /**
     * @return The car mileage (odometer) in km.
     */
    @Nullable public IntegerProperty getMileage() {
        return mileage;
    }

    /**
     * @return The engine oil temperature in Celsius, whereas can be negative.
     */
    @Nullable public IntegerProperty getOilTemperature() {
        return oilTemperature;
    }

    /**
     * @return The car speed in km/h, whereas can be negative.
     */
    @Nullable public IntegerProperty getSpeed() {
        return speed;
    }

    /**
     * @return The RPM of the engine.
     */
    @Nullable public IntegerProperty getRpm() {
        return rpm;
    }

    /**
     * @return The Fuel level percentage.
     */
    @Nullable public PercentageProperty getFuelLevel() {
        return fuelLevel;
    }

    /**
     * @return The estimated range.
     */
    @Nullable public IntegerProperty getRange() {
        return range;
    }

    /**
     * @return Washer fluid level.
     */
    @Nullable public WasherFluidLevel getWasherFluidLevel() {
        return washerFluidLevel;
    }

    /**
     * @return The battery voltage.
     */
    @Nullable public FloatProperty getBatteryVoltage() {
        return batteryVoltage;
    }

    /**
     * @return AdBlue level in liters.
     */
    @Nullable public FloatProperty getAdBlueLevel() {
        return adBlueLevel;
    }

    /**
     * @return The distance driven in km since reset.
     */
    @Nullable public IntegerProperty getDistanceDrivenSinceReset() {
        return distanceDrivenSinceReset;
    }

    /**
     * @return The distance driven in km since engine start.
     */
    @Nullable public IntegerProperty getDistanceDrivenSinceEngineStart() {
        return distanceDrivenSinceEngineStart;
    }

    /**
     * @return The fuel volume measured in liters.
     */
    @Nullable public FloatProperty getFuelVolume() {
        return fuelVolume;
    }

    /**
     * @return The anti-lock braking system (ABS) state.
     */
    @Nullable public BooleanProperty isAntiLockBrakingActive() {
        return antiLockBrakingActive;
    }

    /**
     * @return The engine coolant temperature in Celsius, whereas can be negative.
     */
    @Nullable public IntegerProperty getEngineCoolantTemperature() {
        return engineCoolantTemperature;
    }

    /**
     * @return The the accumulated time of engine operation.
     */
    @Nullable public FloatProperty getEngineTotalOperatingHours() {
        return engineTotalOperatingHours;
    }

    /**
     * @return The the accumulated lifespan fuel consumption in liters.
     */
    @Nullable public FloatProperty getEngineTotalFuelConsumption() {
        return engineTotalFuelConsumption;
    }

    /**
     * @return The brake fluid level.
     */
    @Nullable public BrakeFluidLevel getBrakeFluidLevel() {
        return brakeFluidLevel;
    }

    /**
     * @return The current engine torque percentage.
     */
    @Nullable public PercentageProperty getEngineTorque() {
        return engineTorque;
    }

    /**
     * @return The current engine load percentage.
     */
    @Nullable public PercentageProperty getEngineLoad() {
        return engineLoad;
    }

    /**
     * @return The vehicle speed in km/h measured at the wheel base, whereas can be negative.
     */
    @Nullable public IntegerProperty getWheelBasedSpeed() {
        return wheelBasedSpeed;
    }

    /**
     * @return The battery level percentage.
     */
    @Nullable public PercentageProperty getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * @return The Check Control Message.
     */
    @Nullable public CheckControlMessage[] getCheckControlMessages() {
        return checkControlMessages;
    }

    /**
     * @return The tire pressures.
     */
    public TirePressure[] getTirePressures() {
        return tirePressures;
    }

    /**
     * Get the tire pressure for a tire.
     *
     * @param tireLocation The tire location
     * @return The tire pressure.
     */
    @Nullable public TirePressure getTirePressure(TireLocation tireLocation) {
        for (int i = 0; i < tirePressures.length; i++) {
            TirePressure pressure = tirePressures[i];
            if (pressure.getTireLocation() == tireLocation) return pressure;
        }

        return null;
    }

    /**
     * @return The tire temperatures.
     */
    public TireTemperature[] getTireTemperatures() {
        return tireTemperatures;
    }

    /**
     * The tire temperature for a tire.
     *
     * @param tireLocation The tire location.
     * @return The tire temperature.
     */
    @Nullable public TireTemperature getTireTemperature(TireLocation tireLocation) {
        for (int i = 0; i < tireTemperatures.length; i++) {
            TireTemperature temperature = tireTemperatures[i];
            if (temperature.getTireLocation() == tireLocation) return temperature;
        }

        return null;
    }

    /**
     * @return The wheel rpms.
     */
    public WheelRpm[] getWheelRpms() {
        return wheelRpms;
    }

    /**
     * The tire temperature for a tire.
     *
     * @param tireLocation The tire location.
     * @return The tire temperature.
     */
    @Nullable public WheelRpm getWheelRpm(TireLocation tireLocation) {
        for (int i = 0; i < wheelRpms.length; i++) {
            WheelRpm wheelRpm = wheelRpms[i];
            if (wheelRpm.getTireLocation() == tireLocation) return wheelRpm;
        }

        return null;
    }

    /**
     * @return The trouble codes.
     */
    public DiagnosticsTroubleCode[] getTroubleCodes() {
        return troubleCodes;
    }

    /**
     * @return The mileage meters.
     */
    public IntegerProperty getMileageMeters() {
        return mileageMeters;
    }

    DiagnosticsState(byte[] bytes) {
        super(bytes);

        ArrayList<CheckControlMessage> checkControlMessages = new ArrayList<>();
        ArrayList<TirePressure> tirePressures = new ArrayList<>();
        ArrayList<TireTemperature> tireTemperatures = new ArrayList<>();
        ArrayList<WheelRpm> wheelRpms = new ArrayList<>();
        ArrayList<DiagnosticsTroubleCode> troubleCodes = new ArrayList<>();

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case MILEAGE_IDENTIFIER:
                        mileage = new IntegerProperty(p, false);
                        return mileage;
                    case OIL_TEMPERATURE_IDENTIFIER:
                        oilTemperature = new IntegerProperty(p, false);
                        return oilTemperature;
                    case SPEED_IDENTIFIER:
                        speed = new IntegerProperty(p, false);
                        return speed;
                    case RPM_IDENTIFIER:
                        rpm = new IntegerProperty(p, false);
                        return rpm;
                    case FUEL_LEVEL_IDENTIFIER:
                        fuelLevel = new PercentageProperty(p);
                        return fuelLevel;
                    case RANGE_IDENTIFIER:
                        range = new IntegerProperty(p, false);
                        return range;
                    case WASHER_FLUID_LEVEL_IDENTIFIER:
                        washerFluidLevel = WasherFluidLevel.fromByte(p.getValueByte());
                        return washerFluidLevel;
                    case BATTERY_VOLTAGE_IDENTIFIER:
                        batteryVoltage = new FloatProperty(p);
                        return batteryVoltage;
                    case AD_BLUE_LEVEL_IDENTIFIER:
                        adBlueLevel = new FloatProperty(p);
                        return adBlueLevel;
                    case DISTANCE_DRIVEN_SINCE_RESET_IDENTIFIER:
                        distanceDrivenSinceReset = new IntegerProperty(p, false);
                        return distanceDrivenSinceReset;
                    case DISTANCE_DRIVEN_SINCE_ENGINE_START_IDENTIFIER:
                        distanceDrivenSinceEngineStart = new IntegerProperty(p, false);
                        return distanceDrivenSinceEngineStart;
                    case FUEL_VOLUME_IDENTIFIER:
                        fuelVolume = new FloatProperty(p);
                        return fuelVolume;
                    case ANTI_LOCK_BRAKING_ACTIVE_IDENTIFIER:
                        antiLockBrakingActive = new BooleanProperty(p);
                        return antiLockBrakingActive;
                    case ENGINE_COOLANT_TEMPERATURE_IDENTIFIER:
                        engineCoolantTemperature = new IntegerProperty(p, false);
                        return engineCoolantTemperature;
                    case ENGINE_TOTAL_OPERATING_HOURS_IDENTIFIER:
                        engineTotalOperatingHours = new FloatProperty(p);
                        return engineTotalOperatingHours;
                    case ENGINE_TOTAL_FUEL_CONSUMPTION_IDENTIFIER:
                        engineTotalFuelConsumption = new FloatProperty(p);
                        return engineTotalFuelConsumption;
                    case IDENTIFIER_BRAKE_FLUID:
                        brakeFluidLevel = BrakeFluidLevel.fromByte(p.getValueByte());
                        return brakeFluidLevel;
                    case ENGINE_TORQUE_IDENTIFIER:
                        engineTorque = new PercentageProperty(p);
                        return engineTorque;
                    case ENGINE_LOAD_IDENTIFIER:
                        engineLoad = new PercentageProperty(p);
                        return engineLoad;
                    case WHEEL_BASE_SPEED_IDENTIFIER:
                        wheelBasedSpeed = new IntegerProperty(p, true);
                        return wheelBasedSpeed;
                    case IDENTIFIER_BATTERY_LEVEL:
                        batteryLevel = new PercentageProperty(p);
                        return batteryLevel;
                    case IDENTIFIER_CHECK_CONTROL_MESSAGES:
                        CheckControlMessage message = new CheckControlMessage(p.getByteArray());
                        checkControlMessages.add(message);
                        return message;
                    case IDENTIFIER_TIRE_PRESSURES:
                        TirePressure pressure = new TirePressure(p.getByteArray());
                        tirePressures.add(pressure);
                        return pressure;
                    case IDENTIFIER_TIRE_TEMPERATURES:
                        TireTemperature temp = new TireTemperature(p.getByteArray());
                        tireTemperatures.add(temp);
                        return temp;
                    case IDENTIFIER_WHEEL_RPM:
                        WheelRpm rpm = new WheelRpm(p.getByteArray());
                        wheelRpms.add(rpm);
                        return rpm;
                    case IDENTIFIER_DIAGNOSTICS_TROUBLE_CODE:
                        DiagnosticsTroubleCode code = new DiagnosticsTroubleCode(p.getByteArray());
                        troubleCodes.add(code);
                        return code;
                    case MILEAGE_METERS_IDENTIFIER:
                        mileageMeters = new IntegerProperty(p, false);
                        return mileageMeters;
                }

                return null;
            });

            this.checkControlMessages = checkControlMessages.toArray(new CheckControlMessage[0]);
            this.tirePressures = tirePressures.toArray(new TirePressure[0]);
            this.tireTemperatures = tireTemperatures.toArray(new TireTemperature[0]);
            this.wheelRpms = wheelRpms.toArray(new WheelRpm[0]);
            this.troubleCodes = troubleCodes.toArray(new DiagnosticsTroubleCode[0]);
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private DiagnosticsState(Builder builder) {
        super(builder);
        mileage = builder.mileage;
        oilTemperature = builder.oilTemperature;
        speed = builder.speed;
        rpm = builder.rpm;
        range = builder.range;
        fuelLevel = builder.fuelLevel;
        washerFluidLevel = builder.washerFluidLevel;
        batteryVoltage = builder.batteryVoltage;
        adBlueLevel = builder.adBlueLevel;
        distanceDrivenSinceReset = builder.distanceDrivenSinceReset;
        distanceDrivenSinceEngineStart = builder.distanceDrivenSinceEngineStart;
        fuelVolume = builder.fuelVolume;

        // level7
        antiLockBrakingActive = builder.antiLockBrakingActive;
        engineCoolantTemperature = builder.engineCoolantTemperature;
        engineTotalOperatingHours = builder.engineTotalOperatingHours;
        engineTotalFuelConsumption = builder.engineTotalFuelConsumption;
        brakeFluidLevel = builder.brakeFluidLevel;
        engineTorque = builder.engineTorque;
        engineLoad = builder.engineLoad;
        wheelBasedSpeed = builder.wheelBasedSpeed;

        // level 8
        batteryLevel = builder.batteryLevel;

        checkControlMessages = builder.checkControlMessages.toArray(new CheckControlMessage[0]);
        tirePressures = builder.tirePressures.toArray(new TirePressure[0]);
        tireTemperatures = builder.tireTemperatures.toArray(new TireTemperature[0]);
        wheelRpms = builder.wheelRpms.toArray(new WheelRpm[0]);
        troubleCodes = builder.troubleCodes.toArray(new DiagnosticsTroubleCode[0]);
        mileageMeters = builder.mileageMeters;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private IntegerProperty mileage;
        private IntegerProperty oilTemperature;
        private IntegerProperty speed;
        private IntegerProperty rpm;
        private PercentageProperty fuelLevel;
        private IntegerProperty range;
        private WasherFluidLevel washerFluidLevel;
        private FloatProperty batteryVoltage;
        private FloatProperty adBlueLevel;
        private IntegerProperty distanceDrivenSinceReset;
        private IntegerProperty distanceDrivenSinceEngineStart;
        private FloatProperty fuelVolume;

        BooleanProperty antiLockBrakingActive;
        IntegerProperty engineCoolantTemperature;
        FloatProperty engineTotalOperatingHours;
        FloatProperty engineTotalFuelConsumption;
        BrakeFluidLevel brakeFluidLevel;
        PercentageProperty engineTorque;
        PercentageProperty engineLoad;
        IntegerProperty wheelBasedSpeed;

        PercentageProperty batteryLevel;
        private List<TirePressure> tirePressures = new ArrayList<>();
        private List<TireTemperature> tireTemperatures = new ArrayList<>();
        private List<WheelRpm> wheelRpms = new ArrayList<>();
        private List<CheckControlMessage> checkControlMessages = new ArrayList<>();
        private List<DiagnosticsTroubleCode> troubleCodes = new ArrayList<>();
        IntegerProperty mileageMeters;

        public Builder() {
            super(TYPE);
        }

        public DiagnosticsState build() {
            return new DiagnosticsState(this);
        }

        /**
         * @param mileage The mileage.
         * @return The builder.
         */
        public Builder setMileage(IntegerProperty mileage) {
            this.mileage = mileage;
            mileage.setIdentifier(MILEAGE_IDENTIFIER, 3);
            addProperty(mileage);
            return this;
        }

        /**
         * @param oilTemperature The oil temperature in Celsius.
         * @return The builder.
         */
        public Builder setOilTemperature(IntegerProperty oilTemperature) {
            this.oilTemperature = oilTemperature;
            oilTemperature.setIdentifier(OIL_TEMPERATURE_IDENTIFIER, 2);
            addProperty(oilTemperature);
            return this;
        }

        /**
         * @param speed The speed in km/h.
         * @return The builder.
         */
        public Builder setSpeed(IntegerProperty speed) {
            this.speed = speed;
            speed.setIdentifier(SPEED_IDENTIFIER, 2);
            addProperty(speed);
            return this;
        }

        /**
         * @param rpm The RPM of the engine.
         * @return The builder.
         */
        public Builder setRpm(IntegerProperty rpm) {
            this.rpm = rpm;
            rpm.setIdentifier(RPM_IDENTIFIER, 2);
            addProperty(rpm);
            return this;
        }

        /**
         * @param fuelLevel The fuel level percentage between 0 and 1.
         * @return The builder.
         */
        public Builder setFuelLevel(PercentageProperty fuelLevel) {
            fuelLevel.setIdentifier(FUEL_LEVEL_IDENTIFIER);
            this.fuelLevel = fuelLevel;
            addProperty(fuelLevel);
            return this;
        }

        /**
         * @param range The estimated range.
         * @return The builder.
         */
        public Builder setRange(IntegerProperty range) {
            this.range = range;
            range.setIdentifier(RANGE_IDENTIFIER, 2);
            addProperty(range);
            return this;
        }

        /**
         * @param washerFluidLevel The washer fluid level.
         * @return The builder.
         */
        public Builder setWasherFluidLevel(WasherFluidLevel washerFluidLevel) {
            this.washerFluidLevel = washerFluidLevel;
            addProperty(new Property(WASHER_FLUID_LEVEL_IDENTIFIER,
                    washerFluidLevel.getByte()));
            return this;
        }

        /**
         * @param batteryVoltage The battery voltage.
         * @return The builder.
         */
        public Builder setBatteryVoltage(FloatProperty batteryVoltage) {
            this.batteryVoltage = batteryVoltage;
            batteryVoltage.setIdentifier(BATTERY_VOLTAGE_IDENTIFIER);
            addProperty(batteryVoltage);
            return this;
        }

        /**
         * @param adBlueLevel The adBlue level in liters.
         * @return The builder.
         */
        public Builder setAdBlueLevel(FloatProperty adBlueLevel) {
            this.adBlueLevel = adBlueLevel;
            adBlueLevel.setIdentifier(AD_BLUE_LEVEL_IDENTIFIER);
            addProperty(adBlueLevel);
            return this;
        }

        /**
         * @param distanceDrivenSinceReset The distance driven in km since reset.
         * @return The builder.
         */
        public Builder setDistanceDrivenSinceReset(IntegerProperty distanceDrivenSinceReset) {
            this.distanceDrivenSinceReset = distanceDrivenSinceReset;
            distanceDrivenSinceReset.setIdentifier(DISTANCE_DRIVEN_SINCE_RESET_IDENTIFIER, 2);
            addProperty(distanceDrivenSinceReset);
            return this;
        }

        /**
         * @param distanceDrivenSinceEngineStart The distance driven in km since engine start
         * @return The builder.
         */
        public Builder setDistanceDrivenSinceEngineStart(IntegerProperty distanceDrivenSinceEngineStart) {
            this.distanceDrivenSinceEngineStart = distanceDrivenSinceEngineStart;
            distanceDrivenSinceEngineStart.setIdentifier(DISTANCE_DRIVEN_SINCE_ENGINE_START_IDENTIFIER, 2);
            addProperty(distanceDrivenSinceEngineStart);
            return this;
        }

        /**
         * @param fuelVolume The fuel volume measured in liters.
         * @return The builder.
         */
        public Builder setFuelVolume(FloatProperty fuelVolume) {
            this.fuelVolume = fuelVolume;
            fuelVolume.setIdentifier(FUEL_VOLUME_IDENTIFIER);
            addProperty(fuelVolume);
            return this;
        }

        /**
         * @param antiLockBrakingActive The anti-lock braking system (ABS) state.
         * @return The builder.
         */
        public Builder setAntiLockBrakingActive(BooleanProperty antiLockBrakingActive) {
            this.antiLockBrakingActive = antiLockBrakingActive;
            antiLockBrakingActive.setIdentifier(ANTI_LOCK_BRAKING_ACTIVE_IDENTIFIER);
            addProperty(antiLockBrakingActive);
            return this;
        }

        /**
         * @param engineCoolantTemperature The engine coolant temperature in Celsius, whereas can be
         *                                 negative.
         * @return The builder.
         */
        public Builder setEngineCoolantTemperature(IntegerProperty engineCoolantTemperature) {
            this.engineCoolantTemperature = engineCoolantTemperature;
            engineCoolantTemperature.setIdentifier(ENGINE_COOLANT_TEMPERATURE_IDENTIFIER, 2);
            addProperty(engineCoolantTemperature);
            return this;
        }

        /**
         * @param engineTotalOperatingHours The the accumulated time of engine operation.
         * @return The builder.
         */
        public Builder setEngineTotalOperatingHours(FloatProperty engineTotalOperatingHours) {
            this.engineTotalOperatingHours = engineTotalOperatingHours;
            engineTotalOperatingHours.setIdentifier(ENGINE_TOTAL_OPERATING_HOURS_IDENTIFIER);
            addProperty(engineTotalOperatingHours);
            return this;
        }

        /**
         * @param engineTotalFuelConsumption The the accumulated lifespan fuel consumption in
         *                                   liters.
         * @return The builder.
         */
        public Builder setEngineTotalFuelConsumption(FloatProperty engineTotalFuelConsumption) {
            this.engineTotalFuelConsumption = engineTotalFuelConsumption;
            engineTotalFuelConsumption.setIdentifier(ENGINE_TOTAL_FUEL_CONSUMPTION_IDENTIFIER);
            addProperty(engineTotalFuelConsumption);
            return this;
        }

        /**
         * @param brakeFluidLevel The brake fluid level.
         * @return The builder.
         */
        public Builder setBrakeFluidLevel(BrakeFluidLevel brakeFluidLevel) {
            this.brakeFluidLevel = brakeFluidLevel;
            addProperty(new Property(IDENTIFIER_BRAKE_FLUID, brakeFluidLevel.getByte()));
            return this;
        }

        /**
         * @param engineTorque The current engine torque percentage between 0-1.
         * @return The builder.
         */
        public Builder setEngineTorque(PercentageProperty engineTorque) {
            this.engineTorque = engineTorque;
            engineTorque.setIdentifier(ENGINE_TORQUE_IDENTIFIER);
            addProperty(engineTorque);
            return this;
        }

        /**
         * @param engineLoad The current engine load percentage between 0-1.
         * @return The builder.
         */
        public Builder setEngineLoad(PercentageProperty engineLoad) {
            this.engineLoad = engineLoad;
            engineLoad.setIdentifier(ENGINE_LOAD_IDENTIFIER);
            addProperty(engineLoad);
            return this;
        }

        /**
         * @param wheelBasedSpeed The vehicle speed in km/h measured at the wheel base, whereas can
         *                        be negative.
         * @return The builder.
         */
        public Builder setWheelBasedSpeed(IntegerProperty wheelBasedSpeed) {
            this.wheelBasedSpeed = wheelBasedSpeed;
            wheelBasedSpeed.setIdentifier(WHEEL_BASE_SPEED_IDENTIFIER, 2);
            addProperty(wheelBasedSpeed);
            return this;
        }

        /**
         * Set the battery level.
         *
         * @param batteryLevel The battery level.
         * @return The builder.
         */
        public Builder setBatteryLevel(PercentageProperty batteryLevel) {
            this.batteryLevel = batteryLevel;
            batteryLevel.setIdentifier(IDENTIFIER_BATTERY_LEVEL);
            addProperty(batteryLevel);
            return this;
        }

        /**
         * Add an array of tire pressures.
         *
         * @param pressures The tire pressures.
         * @return The builder.
         */
        public Builder setTirePressures(TirePressure[] pressures) {
            tirePressures.clear();
            for (int i = 0; i < pressures.length; i++) {
                addTirePressure(pressures[i]);
            }

            return this;
        }

        /**
         * Add a single tire pressure.
         *
         * @param pressure The tire pressure.
         * @return The builder.
         */
        public Builder addTirePressure(TirePressure pressure) {
            pressure.setIdentifier(IDENTIFIER_TIRE_PRESSURES);
            addProperty(pressure);
            tirePressures.add(pressure);
            return this;
        }

        /**
         * Add an array of tire temperatures.
         *
         * @param temperatures The tire temperatures.
         * @return The builder.
         */
        public Builder setTireTemperatures(TireTemperature[] temperatures) {
            this.tireTemperatures.clear();

            for (int i = 0; i < temperatures.length; i++) {
                addTireTemperature(temperatures[i]);
            }

            return this;
        }

        /**
         * Add a single tire temperature.
         *
         * @param temperature The tire temperature.
         * @return The builder.
         */
        public Builder addTireTemperature(TireTemperature temperature) {
            temperature.setIdentifier(IDENTIFIER_TIRE_TEMPERATURES);
            addProperty(temperature);
            tireTemperatures.add(temperature);
            return this;
        }

        /**
         * Add an array of wheel RPM's.
         *
         * @param wheelRpms The wheel's RPM.
         * @return The builder.
         */
        public Builder setWheelRpms(WheelRpm[] wheelRpms) {
            for (int i = 0; i < wheelRpms.length; i++) {
                addWheelRpm(wheelRpms[i]);
            }

            return this;
        }

        /**
         * Add a single wheel Rpm.
         *
         * @param wheelRpm The wheel RPM.
         * @return The builder.
         */
        public Builder addWheelRpm(WheelRpm wheelRpm) {
            wheelRpm.setIdentifier(IDENTIFIER_WHEEL_RPM);
            addProperty(wheelRpm);
            wheelRpms.add(wheelRpm);
            return this;
        }

        public Builder setCheckControlMessages(CheckControlMessage[] checkControlMessages) {
            this.checkControlMessages.clear();

            for (CheckControlMessage checkControlMessage : checkControlMessages) {
                addCheckControlMessage(checkControlMessage);
            }

            return this;
        }

        public void addCheckControlMessage(CheckControlMessage checkControlMessage) {
            checkControlMessage.setIdentifier(IDENTIFIER_CHECK_CONTROL_MESSAGES);
            addProperty(checkControlMessage);
            checkControlMessages.add(checkControlMessage);
        }

        public Builder setTroubleCodes(DiagnosticsTroubleCode[] troubleCodes) {
            this.troubleCodes.clear();

            for (DiagnosticsTroubleCode troubleCode : troubleCodes) {
                addTroubleCode(troubleCode);
            }

            return this;
        }

        public Builder addTroubleCode(DiagnosticsTroubleCode code) {
            code.setIdentifier(IDENTIFIER_DIAGNOSTICS_TROUBLE_CODE);
            addProperty(code);
            troubleCodes.add(code);
            return this;
        }

        /**
         * @param mileageMeters The mileage meters.
         * @return The builder.
         */
        public Builder setMileageMeters(IntegerProperty mileageMeters) {
            this.mileageMeters = mileageMeters;
            mileageMeters.setIdentifier(MILEAGE_METERS_IDENTIFIER, 4);
            addProperty(mileageMeters);
            return this;
        }
    }
}
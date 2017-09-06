package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class FeatureState {
    Command.Identifier feature;

    FeatureState(Command.Identifier feature) {
        this.feature = feature;
    }

    public static FeatureState fromBytes(byte[] bytes) throws CommandParseException {
        if (bytes.length < 3) throw new CommandParseException();
        Command.Identifier feature = Command.Identifier.fromIdentifier(bytes[0], bytes[1]);

        if (feature == Command.Identifier.DOOR_LOCKS) return new DoorLocks(bytes);
        else if (feature == Command.Identifier.TRUNK_ACCESS) return new TrunkAccess(bytes);
        else if (feature == Command.Identifier.REMOTE_CONTROL) return new RemoteControl(bytes);
        else if (feature == Command.Identifier.CHARGING) return new Charging(bytes);
        else if (feature == Command.Identifier.CLIMATE) return new Climate(bytes);
        else if (feature == Command.Identifier.VEHICLE_LOCATION) return new VehicleLocation(bytes);
        else if (feature == Command.Identifier.VALET_MODE) return new ValetMode(bytes);
        else if (feature == Command.Identifier.ROOFTOP) return new RooftopState(bytes);
        else if (feature == Command.Identifier.DIAGNOSTICS) return new Diagnostics(bytes);
        else if (feature == Command.Identifier.MAINTENANCE) return new Maintenance(bytes);
        else if (feature == Command.Identifier.ENGINE) return new Engine(bytes);
        else if (feature == Command.Identifier.LIGHTS) return new Lights(bytes);
        else if (feature == Command.Identifier.THEFT_ALARM) return new TheftAlarm(bytes);
        else if (feature == Command.Identifier.PARKING_TICKET) return new ParkingTicket(bytes);
        else if (feature == Command.Identifier.VEHICLE_TIME) return new VehicleTime(bytes);

        return null;
    }

    public Command.Identifier getFeature() {
        return feature;
    }
}

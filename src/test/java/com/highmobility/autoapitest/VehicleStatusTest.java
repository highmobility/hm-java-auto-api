package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlMode;
import com.highmobility.autoapi.GetVehicleStatus;
import com.highmobility.autoapi.TheftAlarmState;
import com.highmobility.autoapi.TrunkState;
import com.highmobility.autoapi.VehicleStatus;
import com.highmobility.autoapi.WindowsState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.DisplayUnit;
import com.highmobility.autoapi.value.DriverSeatLocation;
import com.highmobility.autoapi.value.Gearbox;
import com.highmobility.autoapi.value.Lock;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.value.PowerTrain;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by ttiganik on 15/09/16.
 */
public class VehicleStatusTest {
    Bytes bytes = new Bytes(
            "001101" +
                    "0100140100114a46325348424443374348343531383639" +
                    "02000401000101" +
                    "030009010006547970652058" +
                    "0400090100064d7920436172" +
                    "050009010006414243313233" +
                    "06000B0100085061636B6167652B" +
                    "07000501000207E1" +
                    "08000F01000C4573746f72696c20426c6175" +
                    "09000501000200DC" +
                    "0A000401000105" +
                    "0B000401000105" +
                    "0C000701000440200000" +
                    "0D000501000200F5" +
                    "0E000401000101" +
                    "9900140100110021010100040100010002000401000101" + // Trunk open
                    "99000D01000A00270101000401000102" + // Remote Control Started

                    // l8
                    "0F000401000100" + // display unit km
                    "10000401000100" + // driver seat left
                    "11001201000F5061726B696E672073656E736F7273" + // Parking sensors
                    "1100130100104175746F6D6174696320776970657273" + // Automatic wipers
                    // l9
                    "12000B0100084D65726365646573"
    );

    @Test public void state() {
        testState((VehicleStatus) CommandResolver.resolve(bytes));
    }

    private void testState(VehicleStatus state) {
        assertTrue(state.getStates().length == 2);
        assertTrue(state.getVin().getValue().equals("JF2SHBDC7CH451869"));
        assertTrue(state.getPowerTrain().getValue() == PowerTrain.ALLELECTRIC);
        assertTrue(state.getModelName().getValue().equals("Type X"));
        assertTrue(state.getName().getValue().equals("My Car"));
        assertTrue(state.getLicensePlate().getValue().equals("ABC123"));

        assertTrue(state.getSalesDesignation().getValue().equals("Package+"));
        assertTrue(state.getModelYear().getValue() == 2017);
        assertTrue(state.getColorName().getValue().equals("Estoril Blau"));
        assertTrue(state.getPower().getValue() == 220);
        assertTrue(state.getNumberOfDoors().getValue() == 5);
        assertTrue(state.getNumberOfSeats().getValue() == 5);

        assertTrue(state.getState(TrunkState.TYPE).getValue() != null);

        assertTrue(state.getEngineVolume().getValue() == 2.5f);
        assertTrue(state.getMaxTorque().getValue() == 245);
        assertTrue(state.getGearBox().getValue() == Gearbox.AUTOMATIC);

        assertTrue(state.getDisplayUnit().getValue() == DisplayUnit.KM);
        assertTrue(state.getDriverSeatLocation().getValue() == DriverSeatLocation.LEFT);
        assertTrue(state.getEquipments().length == 2);

        int count = 0;
        for (Property<String> s : state.getEquipments()) {
            if (s.getValue().equals("Parking sensors") || s.getValue().equals("Automatic wipers"))
                count++;
        }
        assertTrue(count == 2);
        assertTrue(state.getBrand().getValue().equals("Mercedes"));

        Command command = getState(ControlMode.class, state);
        if (command == null) fail();
        if (command.is(ControlMode.TYPE) == false) fail();
        ControlMode controlMode = (ControlMode) command;
        assertTrue(controlMode.getMode().getValue() == ControlMode.Value.STARTED);

        command = getState(TrunkState.class, state);
        if (command == null) fail();
        if (command.is(TrunkState.TYPE) == false) fail();
        TrunkState trunkState = (TrunkState) command;
        assertTrue(trunkState.getLockState().getValue() == Lock.UNLOCKED);
        assertTrue(trunkState.getPosition().getValue() == Position.OPEN);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void build() {
        VehicleStatus status = getVehicleStatusBuilderWithoutSignature().build();
        testState(status);
    }

    @Test public void get() {
        Bytes bytes = new Bytes("001100");
        Bytes commandBytes = new GetVehicleStatus();
        assertTrue(bytes.equals(commandBytes));

        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof GetVehicleStatus);
    }

    Command getState(Class forClass, VehicleStatus command) {
        for (int i = 0; i < command.getStates().length; i++) {
            Property<Command> state = command.getStates()[i];
            if (state != null && state.getValue().getClass().equals(forClass))
                return state.getValue();
        }

        return null;
    }

    VehicleStatus.Builder getVehicleStatusBuilderWithoutSignature() {
        VehicleStatus.Builder builder = new VehicleStatus.Builder();
        builder.setVin(new Property("JF2SHBDC7CH451869"));
        builder.setPowerTrain(new Property(PowerTrain.ALLELECTRIC));
        builder.setModelName(new Property("Type X"));
        builder.setName(new Property("My Car"));
        builder.setLicensePlate(new Property("ABC123"));
        builder.setSalesDesignation(new Property("Package+"));
        builder.setModelYear(new Property(2017));

        builder.setColorName(new Property("Estoril Blau"));
        builder.setPower(new Property(220));
        builder.setNumberOfDoors(new Property(5)).setNumberOfSeats(new Property(5));

        // l7
        builder.setEngineVolume(new Property(2.5f));
        builder.setMaxTorque(new Property(245));
        builder.setGearBox(new Property(Gearbox.AUTOMATIC));

        TrunkState.Builder trunkState = new TrunkState.Builder();
        trunkState.setLockState(new Property(Lock.UNLOCKED));
        trunkState.setPosition(new Property(Position.OPEN));
        builder.addState(new Property(trunkState.build()));

        ControlMode controlMode = new ControlMode(new Bytes("00270101000401000102").getByteArray());
        builder.addState(new Property(controlMode));

        // l8
        builder.setDisplayUnit(new Property(DisplayUnit.KM));
        builder.setDriverSeatLocation(new Property(DriverSeatLocation.LEFT));
        builder.addEquipment(new Property("Parking sensors"));
        builder.addEquipment(new Property("Automatic wipers"));

        // l9
        builder.setBrand(new Property("Mercedes"));

        return builder;
    }

    @Test public void createWithSignature() {
        VehicleStatus.Builder builder = getVehicleStatusBuilderWithoutSignature();
        Bytes nonce = new Bytes("324244433743483436");
        builder.setNonce(nonce);
        Bytes signature = new Bytes
                ("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6");
        builder.setSignature(signature);

        VehicleStatus status = builder.build();
        byte[] command = status.getByteArray();
        assertTrue(Arrays.equals(command, command));
        assertTrue(status.getNonce().equals(nonce));
        assertTrue(status.getSignature().equals(signature));
    }

    @Test public void testInvalidProperty() {
        Bytes bytes = new Bytes
                ("001101" +
                        "0100140100094a46325348424443374348343531383639" +
                        "99002E01002B" + // 2b // 43
                        "004501" + // windows
                        "0200050100020238" + // invalid
                        "0200050100020312" + // invalid
                        "0300050100020201" +
                        "0300050100020300" +
                        "03000501000201FF" + // invalid Window Position FF
                        "99000D01000A00270101000401000102"); // control mode command
        TestUtils.errorLogExpected(3, () -> {
            Command command = CommandResolver.resolve(bytes);
            VehicleStatus vs = (VehicleStatus) command;
            // one window property will fail to parse
            WindowsState ws = (WindowsState) vs.getState(WindowsState.TYPE).getValue();
            assertTrue(ws.getProperties().length == 5);
            assertTrue(ws.getWindowPositions().length == 3);
        });
    }

    @Test public void zeroProperties() {
        VehicleStatus.Builder builder = new VehicleStatus.Builder();
        VehicleStatus vs = builder.build();

        assertTrue(vs.getStates().length == 0);
        assertTrue(vs.getNumberOfDoors() == null);
        assertTrue(vs.getState(TheftAlarmState.TYPE) == null);
        assertTrue(vs.getByteArray().length == 3);

        Bytes bytes = new Bytes("00110100");
        vs = (VehicleStatus) CommandResolver.resolve(bytes);
        assertTrue(vs.getStates().length == 0);
        assertTrue(vs.getNumberOfDoors().getValue() == null);
        assertTrue(vs.getState(TheftAlarmState.TYPE) == null);
    }

    @Test public void testOneInvalidVsStateDoesNotMatter() {
        Bytes bytes = new Bytes
                ("001101" +
                        "9900140100110021010100040100010002000401000101" +
                        "99000D01000A00270101000401000115"); //invalid control mode
        TestUtils.errorLogExpected(() -> {
            VehicleStatus command = (VehicleStatus) CommandResolver.resolve(bytes);
            assertTrue(command.getStates().length == 2); // invalid command is added as a base
            // command class
        });
    }
}

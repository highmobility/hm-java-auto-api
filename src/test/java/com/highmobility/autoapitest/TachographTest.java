package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GetTachographState;
import com.highmobility.autoapi.TachographState;
import com.highmobility.autoapi.property.DriverCard;
import com.highmobility.autoapi.property.DriverTimeState;
import com.highmobility.autoapi.property.DriverWorkingState;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class TachographTest {
    byte[] bytes = Bytes.bytesFromHex
            ("0064010100020102010002020002000201020200020206030002010103000202010400010105000100060001000700020050");

    @Test
    public void state() {
        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(TachographState.TYPE));
        TachographState state = (TachographState) command;

        assertTrue(state.getDriverWorkingState(1).getWorkingState() == DriverWorkingState
                .WorkingState.WORKING);
        assertTrue(state.getDriverWorkingState(2).getWorkingState() == DriverWorkingState
                .WorkingState.RESTING);

        assertTrue(state.getDriverTimeState(1).getTimeState() == DriverTimeState.TimeState
                .FOUR_AND_HALF_HOURS_REACHED
        );
        assertTrue(state.getDriverTimeState(2).getTimeState() == DriverTimeState.TimeState
                .SIXTEEN_HOURS_REACHED
        );

        assertTrue(state.getDriverCard(1).isPresent());
        assertTrue(state.getDriverCard(2).isPresent());

        assertTrue(state.isVehicleMotionDetected() == true);
        assertTrue(state.isVehicleOverspeeding() == false);
        assertTrue(state.getVehicleDirection() == TachographState.VehicleDirection.FORWARD);
        assertTrue(state.getVehicleSpeed() == 80);
    }

    @Test public void build() {
        TachographState.Builder builder = new TachographState.Builder();

        builder.addDriverWorkingState(new DriverWorkingState(1, DriverWorkingState.WorkingState
                .WORKING));
        builder.addDriverWorkingState(new DriverWorkingState(2, DriverWorkingState.WorkingState
                .RESTING));

        builder.addDriverTimeState(new DriverTimeState(1, DriverTimeState.TimeState
                .FOUR_AND_HALF_HOURS_REACHED));
        builder.addDriverTimeState(new DriverTimeState(2, DriverTimeState.TimeState
                .SIXTEEN_HOURS_REACHED));

        builder.addDriverCard(new DriverCard(1, true));
        builder.addDriverCard(new DriverCard(2, true));

        builder.setVehicleMotionDetected(true);
        builder.setVehicleOverspeed(false);
        builder.setVehicleDirection(TachographState.VehicleDirection.FORWARD);
        builder.setVehicleSpeed(80);

        TachographState state = builder.build();
        assertTrue(Arrays.equals(state.getBytes(), bytes));
    }

    @Test public void get() {
        byte[] waitingForBytes = Bytes.bytesFromHex("006400");
        byte[] commandBytes = new GetTachographState().getBytes();

        assertTrue(Arrays.equals(waitingForBytes, commandBytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetTachographState);
    }
}
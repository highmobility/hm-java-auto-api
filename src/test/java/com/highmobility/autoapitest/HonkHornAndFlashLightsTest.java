package com.highmobility.autoapitest;

import com.highmobility.autoapi.ActivateDeactivateEmergencyFlasher;
import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.FlashersState;
import com.highmobility.autoapi.GetFlashersState;
import com.highmobility.autoapi.HonkAndFlash;
import com.highmobility.autoapi.property.FlashersStateProperty;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class HonkHornAndFlashLightsTest {
    Bytes bytes = new Bytes("00260101000102");

    @Test
    public void state() {
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command.is(FlashersState.TYPE));
        FlashersState state = (FlashersState) command;
        assertTrue(state.getState().getValue() == FlashersStateProperty.Value.LEFT_ACTIVE);
    }

    @Test public void get() {
        String waitingForBytes = "002600";
        String commandBytes = ByteUtils.hexFromBytes(new GetFlashersState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void honkAndFlash() {
        String waitingForBytes = "0026120100010002000103";
        String commandBytes = ByteUtils.hexFromBytes(new HonkAndFlash(0, 3).getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        HonkAndFlash command = (HonkAndFlash) CommandResolver.resolveHex(waitingForBytes);
        assertTrue(command.getSeconds() == 0);
        assertTrue(command.getLightFlashCount() == 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void honkAndFlashNoArguments() throws IllegalArgumentException {
        new HonkAndFlash(null, null);
    }

    @Test public void activateDeactivate() {
        String waitingForBytes = "00261301000101";

        String commandBytes = ByteUtils.hexFromBytes(new ActivateDeactivateEmergencyFlasher(true)
                .getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        ActivateDeactivateEmergencyFlasher command = (ActivateDeactivateEmergencyFlasher)
                CommandResolver.resolveHex(waitingForBytes);
        assertTrue(command.activate() == true);
    }

    @Test public void state0Properties() {
        Bytes waitingForBytes = new Bytes("002601");
        Command state = CommandResolver.resolve(waitingForBytes);
        assertTrue(((FlashersState) state).getState() == null);
    }

    @Test public void builder() {
        FlashersState.Builder builder = new FlashersState.Builder();
        builder.setState(new FlashersStateProperty(FlashersStateProperty.Value.LEFT_ACTIVE));
        assertTrue(builder.build().equals(bytes));
    }

      @Test public void stateWithTimestamp() {
        Bytes timestampBytes = bytes.concat(new Bytes("A4000911010A112200000001"));
        FlashersState command = (FlashersState) CommandResolver.resolve(timestampBytes);
        assertTrue(command.getState().getTimestamp() != null);
    }
}

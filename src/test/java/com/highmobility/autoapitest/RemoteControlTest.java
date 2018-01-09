package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlCommand;
import com.highmobility.autoapi.ControlMode;
import com.highmobility.autoapi.GetControlMode;
import com.highmobility.autoapi.StartControlMode;
import com.highmobility.autoapi.StopControlMode;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class RemoteControlTest {
    @Test
    public void controlMode() {
        byte[] bytes = Bytes.bytesFromHex(
                "002701" +
                    "01000102" +
                    "0200020032");

        Command command = null;

        try {
            command = CommandResolver.resolve(bytes);
        } catch (CommandParseException e) {
            fail("init failed");
        }

        assertTrue(command.is(ControlMode.TYPE));
        ControlMode state = (ControlMode) command;
        assertTrue(state.getAngle() == 50);
        assertTrue(state.getMode() == ControlMode.Mode.STARTED);
    }

    @Test public void get() {
        String waitingForBytes = "002700";
        String commandBytes = Bytes.hexFromBytes(new GetControlMode().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void startControlMode() {
        String waitingForBytes = "002702";
        String commandBytes = Bytes.hexFromBytes(new StartControlMode().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void stopControlMode() {
        String waitingForBytes = "002703";
        String commandBytes = Bytes.hexFromBytes(new StopControlMode().getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void controlCommand() {
        String waitingForBytes = "002704010001030200020032";
        String commandBytes = Bytes.hexFromBytes(new ControlCommand(3, 50).getBytes());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}

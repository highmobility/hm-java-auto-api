package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.GasFlapState;
import com.highmobility.autoapi.GetGasFlapState;
import com.highmobility.autoapi.OpenGasFlap;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FuelingTest {
    @Test
    public void state() {
        byte[] bytes = ByteUtils.bytesFromHex(
                "00400101000101");

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(GasFlapState.TYPE));
        GasFlapState state = (GasFlapState) command;
        assertTrue(state.getState() == com.highmobility.autoapi.property.GasFlapState.OPEN);
    }

    @Test public void build() {
        GasFlapState.Builder builder = new GasFlapState.Builder();
        builder.setState(com.highmobility.autoapi.property.GasFlapState.OPEN);
        GasFlapState state = builder.build();
        assertTrue(state.equals("00400101000101"));
    }

    @Test public void get() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004000");
        byte[] bytes = new GetGasFlapState().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));
        assertTrue(CommandResolver.resolve(waitingForBytes) instanceof GetGasFlapState);
    }

    @Test public void open() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex("004002");
        byte[] bytes = new OpenGasFlap().getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));

        OpenGasFlap openGasFlap = (OpenGasFlap) CommandResolver.resolve(waitingForBytes);
        assertTrue(Arrays.equals(openGasFlap.getByteArray(), waitingForBytes));
    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("004001");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((GasFlapState) state).getState() == null);
    }
}

package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.ControlLights;
import com.highmobility.autoapi.GetLightsState;
import com.highmobility.autoapi.LightsState;
import com.highmobility.autoapi.property.FrontExteriorLightState;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LightsTest {
    byte[] bytes = ByteUtils.bytesFromHex(
            "003601" +
                    "01000102" +
                    "02000101" +
                    "03000100" +
                    "040003ff0000" +
                    "0500010106000101" /*l7*/);

    @Test
    public void state() {

        Command command = null;
        try {
            command = CommandResolver.resolve(bytes);
        } catch (Exception e) {
            fail();
        }

        assertTrue(command.is(LightsState.TYPE));

        LightsState state = (LightsState) command;
        assertTrue(command.is(LightsState.TYPE));
        assertTrue(state.getFrontExteriorLightState() == FrontExteriorLightState
                .ACTIVE_WITH_FULL_BEAM);
        assertTrue(state.isRearExteriorLightActive() == true);
        assertTrue(state.isInteriorLightActive() == false);

        assertTrue(state.getAmbientColor()[0] == 0xFF);
        assertTrue(state.getAmbientColor()[1] == 0);
        assertTrue(state.getAmbientColor()[2] == 0);

        assertTrue(state.isReverseLightActive() == true);
        assertTrue(state.isEmergencyBrakeLightActive() == true);
    }

    @Test public void build() {
        LightsState.Builder builder = new LightsState.Builder();

        builder.setFrontExteriorLightState(FrontExteriorLightState.ACTIVE_WITH_FULL_BEAM);
        builder.setRearExteriorLightActive(true);
        builder.setInteriorLightActive(false);

        int[] ambientColor = new int[]{0xFF, 0, 0};
        builder.setAmbientColor(ambientColor);
        builder.setReverseLightActive(true);
        builder.setEmergencyBrakeLightActive(true);

        byte[] actualBytes = builder.build().getByteArray();
        assertTrue(Arrays.equals(actualBytes, bytes));
    }

    @Test public void get() {
        String waitingForBytes = "003600";
        String commandBytes = ByteUtils.hexFromBytes(new GetLightsState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void control() {
        byte[] expecting = ByteUtils.bytesFromHex("003602" +
                "01000102" +
                "02000100" +
                "03000100" +
                "040003ff0000");

        byte[] bytes = new ControlLights(
                FrontExteriorLightState.ACTIVE_WITH_FULL_BEAM
                , false, false, new int[]{255, 0, 0, 255}
        ).getByteArray();

        assertTrue(Arrays.equals(expecting, bytes));

        ControlLights command = (ControlLights) CommandResolver.resolve(expecting);
        assertTrue(command.getFrontExteriorLightState() == FrontExteriorLightState
                .ACTIVE_WITH_FULL_BEAM);
        assertTrue(command.getRearExteriorLightActive() == false);
        assertTrue(command.getInteriorLightActive() == false);
        assertTrue(Arrays.equals(command.getAmbientColor(), new int[]{255, 0, 0, 255}));

    }

    @Test public void state0Properties() {
        byte[] bytes = ByteUtils.bytesFromHex("003601");
        Command state = CommandResolver.resolve(bytes);
        assertTrue(((LightsState) state).getAmbientColor() == null);
    }
}

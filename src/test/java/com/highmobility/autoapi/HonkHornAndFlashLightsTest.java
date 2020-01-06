/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HonkHornAndFlashLightsTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "002601" +
            "01000401000102");

    @Test
    public void state() {
        HonkHornFlashLights.State state =
                (HonkHornFlashLights.State) CommandResolver.resolve(bytes);
        testState(state);
    }

    private void testState(HonkHornFlashLights.State state) {
        assertTrue(state.getFlashers().getValue() == HonkHornFlashLights.Flashers.LEFT_FLASHER_ACTIVE);
        assertTrue(TestUtils.bytesTheSame(state, bytes));
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "002600";
        String commandBytes =
                ByteUtils.hexFromBytes(new HonkHornFlashLights.GetFlashersState().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }

    @Test public void honkAndFlash() {
        String waitingForBytes = COMMAND_HEADER + "002601" +
                "02000401000100" +
                "03000401000103";
        HonkHornFlashLights.HonkFlash command = new HonkHornFlashLights.HonkFlash(0, 3);
        assertTrue(command.equals(waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        command = (HonkHornFlashLights.HonkFlash) CommandResolver.resolveHex(waitingForBytes);
        assertTrue(command.getHonkSeconds().getValue() == 0);
        assertTrue(command.getFlashTimes().getValue() == 3);
    }

    @Test
    public void honkAndFlashNoArguments() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> new HonkHornFlashLights.HonkFlash(null, null));
    }

    @Test public void activateDeactivate() {
        String waitingForBytes = COMMAND_HEADER + "002601" +
                "04000401000101";

        String commandBytes =
                ByteUtils.hexFromBytes(new HonkHornFlashLights.ActivateDeactivateEmergencyFlasher(ActiveState.ACTIVE)
                        .getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        HonkHornFlashLights.ActivateDeactivateEmergencyFlasher command =
                (HonkHornFlashLights.ActivateDeactivateEmergencyFlasher)
                CommandResolver.resolveHex(waitingForBytes);
        assertTrue(command.getEmergencyFlashersState().getValue() == ActiveState.ACTIVE);
    }

    @Test public void builder() {
        HonkHornFlashLights.State.Builder builder = new HonkHornFlashLights.State.Builder();
        builder.setFlashers(new Property(HonkHornFlashLights.Flashers.LEFT_FLASHER_ACTIVE));
        HonkHornFlashLights.State state = builder.build();
        testState(state);
    }
}

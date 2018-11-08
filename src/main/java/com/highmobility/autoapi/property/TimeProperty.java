package com.highmobility.autoapi.property;

import com.highmobility.autoapi.property.value.Time;

import java.util.Arrays;

/**
 * Local time with hours and minutes.
 */
public class TimeProperty extends Property {
    Time time;

    public TimeProperty(byte identifier, int hour, int minute) {
        super(identifier, 2);
        this.time = new Time(hour, minute);
        this.bytes[3] = (byte) hour;
        this.bytes[4] = (byte) minute;
    }

    public TimeProperty(byte identifier, Time time) {
        super(identifier, 2);
        this.time = time;
        this.bytes[3] = (byte) time.getHour();
        this.bytes[4] = (byte) time.getMinute();
    }

    public TimeProperty(byte[] bytes) {
        super(bytes);
        if (bytes.length < 5) throw new IllegalArgumentException();
        time = new Time(Arrays.copyOfRange(bytes, 3, 5));
    }

    /**
     * @return The time.
     */
    public Time getTime() {
        return time;
    }

    /**
     * @return Hour from 0 - 23.
     */
    public int getHour() {
        return time.getHour();
    }

    /**
     * @return Minute from 0 - 59.
     */
    public int getMinute() {
        return time.getMinute();
    }
}

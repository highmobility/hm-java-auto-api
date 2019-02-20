package com.highmobility.autoapitest;

import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static junit.framework.TestCase.assertTrue;

public class TestUtils {

    public static boolean dateIsSame(Calendar c, String dateString) throws ParseException {
        DateFormat format = getFormat(dateString);
        Date expectedDate;
        try {
            expectedDate = format.parse(dateString);
        } catch (ParseException e) {
            expectedDate = format.parse(dateString + "+0000"); // add timezone
        }

        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(expectedDate);
        expectedCalendar.setTimeZone(format.getTimeZone());
        Date commandDate = c.getTime();

        float rawOffset = c.getTimeZone().getRawOffset();
        float expectedRawOffset = expectedCalendar.getTimeZone().getRawOffset();
        assertTrue(rawOffset == expectedRawOffset);

        String commandDateString = getUTCFormat().format(commandDate);
        String expectedDateString = getUTCFormat().format(expectedDate);

        return (commandDateString.equals(expectedDateString));
    }

    public static DateFormat getFormat(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String offset = date.substring(19);
        TimeZone tz = TimeZone.getTimeZone("GMT" + offset);
        format.setTimeZone(tz);
        return format;
    }

    public static boolean dateIsSameUTC(Calendar c, String date) throws ParseException {
        float rawOffset = c.getTimeZone().getRawOffset();

        Date expectedDate = getUTCFormat().parse(date);
        Date commandDate = c.getTime();

        float expectedRawOffset = 0;
        assertTrue(rawOffset == expectedRawOffset);

        String commandDateString = getUTCFormat().format(commandDate);
        String expectedDateString = getUTCFormat().format(expectedDate);

        return (commandDateString.equals(expectedDateString));
    }

    /**
     * This does not consider time zone.
     */
    public static boolean dateIsSameIgnoreTimezone(Calendar c1, Calendar c2) {
        if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) return false;
        if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) return false;
        if (c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH)) return false;
        if (c1.get(Calendar.HOUR_OF_DAY) != c2.get(Calendar.HOUR_OF_DAY)) return false;
        if (c1.get(Calendar.MINUTE) != c2.get(Calendar.MINUTE)) return false;
        if (c1.get(Calendar.SECOND) != c2.get(Calendar.SECOND)) return false;
        return true;
    }

    public static boolean dateIsSameIgnoreTimezone(Calendar c1, String c2) throws ParseException {
        Date expectedDate = getUTCFormat().parse(c2);
        Date commandDate = c1.getTime();
        String commandDateString = getUTCFormat().format(commandDate);
        String expectedDateString = getUTCFormat().format(expectedDate);
        return (commandDateString.equals(expectedDateString));
    }

//    private static DateFormat format;

    public static DateFormat getUTCFormat() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format;
    }

    public static Calendar getUTCCalendar(String dateString, int timeZoneMinuteOffset) throws
            ParseException {
        Date date = getUTCFormat().parse(dateString);
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.setTimeZone(TimeZone.getTimeZone(TimeZone.getAvailableIDs(timeZoneMinuteOffset * 60 *
                1000)[0]));

        return c;
    }

    public static Calendar getUTCCalendar(String dateString) throws ParseException {
        return getUTCCalendar(dateString, 0);
    }

    public static Calendar getCalendar(String dateString) throws ParseException {
        DateFormat format = getFormat(dateString);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            date = format.parse(dateString + "+0000"); // add timezone
        }
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.setTimeZone(format.getTimeZone());
        return c;
    }

    public static boolean bytesTheSame(Bytes state, Bytes bytes) {
        for (int i = 0; i < state.getLength(); i++) {
            byte stateByte = state.getByteArray()[i];

            if (bytes.getByteArray().length < i + 1) {
                System.out.println("state bytes has more bytes");
                return false;
            }

            byte otherByte = bytes.getByteArray()[i];
            if (stateByte != otherByte) {
                System.out.println("bytes not equal at index " + i + ". expected: " + ByteUtils
                        .hexFromBytes(new byte[]{otherByte}) + ", actual: " + ByteUtils
                        .hexFromBytes(new byte[]{stateByte}) +
                        "\nbytes1: " + ByteUtils.hexFromBytes(Arrays.copyOf
                        (bytes.getByteArray(), i + 1)) +
                        "\nbytes2: " + ByteUtils.hexFromBytes(Arrays.copyOf(state
                        .getByteArray(), i + 1)));

                System.out.println("bytes1: " + bytes);
                System.out.println("bytes2: " + state);
                return false;
            }
        }

        if (bytes.getLength() > state.getLength()) {
            System.out.println("expected bytes has more bytes");
            return false;
        }

        return true;
    }
}

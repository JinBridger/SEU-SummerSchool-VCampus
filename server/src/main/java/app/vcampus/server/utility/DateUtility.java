package app.vcampus.server.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date utility class.
 */
public class DateUtility {
    /**
     * Format a date to string.
     *
     * @param date The date to format.
     * @return The formatted string.
     */
    public static String fromDate(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * Format a date to string with a format.
     *
     * @param date The date to format.
     * @param format The format to use.
     * @return The formatted string.
     */
    public static String fromDate(Date date, String format) {
        if (date == null) return "";
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Parse a string to date.
     *
     * @param date The string to parse.
     * @return The parsed date.
     */
    public static Date toDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}

package app.vcampus.server.utility;

public class DateUtility {
    public static String fromDate(java.util.Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static java.util.Date toDate(String date) {
        try {
            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}

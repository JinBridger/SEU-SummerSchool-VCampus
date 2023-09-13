package app.vcampus.server.utility;

/**
 * Text utility class.
 */
public class TextUtility {
    public static String intToChineseWeek(Integer num) {
        return switch (num) {
            case 1 -> "一";
            case 2 -> "二";
            case 3 -> "三";
            case 4 -> "四";
            case 5 -> "五";
            case 6 -> "六";
            case 7 -> "日";
            default -> "";
        };
    }
}

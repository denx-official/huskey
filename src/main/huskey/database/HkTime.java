package database;

import java.time.LocalDateTime;

public class HkTime {
    private final int year;
    private final int month;
    private final int date;
    private final int hours;
    private final int minutes;
    private final int seconds;

    public HkTime(int year, int month, int date, int hours, int minutes, int seconds) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int get(String target) {
        switch (target) {
            case "year":
                return this.year;
            case "month":
                return this.month;
            case "date":
                return this.date;
            case "hours":
                return this.hours;
            case "minutes":
                return this.minutes;
            case "seconds":
                return this.seconds;
            default:
                throw new IllegalArgumentException("target " + target + " はメンバ変数に含まれていません。");
        }
    }

    public static String[] iterator() {
        return new String[] {"year", "month", "date", "hours", "minutes", "seconds"};
    }

    public static HkTime now() {
        LocalDateTime now = LocalDateTime.now();
        return new HkTime(
            now.getYear(),
            now.getMonthValue(),
            now.getDayOfMonth(),
            now.getHour(),
            now.getMinute(),
            now.getSecond()
        );
    }
}

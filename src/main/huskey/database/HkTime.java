package database;

public class HkTime {
    public final int year;
    public final int month;
    public final int date;
    public final int hours;
    public final int minutes;
    public final int seconds;

    public HkTime(int year, int month, int date, int hours, int minutes, int seconds) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
}

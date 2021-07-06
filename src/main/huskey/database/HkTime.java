package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDateTime;

/**
 * Dataインスタンス内で保持する時間情報
 *
 * @author いっぺー
 * @see Data
 */
public class HkTime {
    public final int year;
    public final int month;
    public final int date;
    public final int hours;
    public final int minutes;
    public final int seconds;

    private HkTime(int year, int month, int date, int hours, int minutes, int seconds) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * 現在時刻をHkTime型で取得
     *
     * @return String[]
     * @author いっぺー
     */
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

    /**
     * HkTime型をElement型に変換
     *
     * <p>引数 tag で指定したタグ名でHkTime型をElement型に変換する。
     *
     * @param doc データベースにアクセスするためのDocumentインスタンス
     * @param tag タグ名 (created, updated)
     * @return Element
     * @author いっぺー
     */
    public Element toElement(Document doc, String tag) {
        if (!(tag.equals("created") || tag.equals("updated"))) {
            throw new IllegalArgumentException("引数 tag には、created/updated のどちらかを指定してください。");
        }

        Element elem = doc.createElement(tag);
        for (String name : HkTime.iterator()) {
            elem.setAttribute(name, String.valueOf(this.get(name)));
        }

        return elem;
    }

    /**
     * 時間の取得
     *
     * <p>引数 target で指定した単位の情報をint型で取得する。
     *
     * @param target (year, month, date, hours, minutes, seconds)
     * @return int
     * @author いっぺー
     */
    private int get(String target) {
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

    /**
     * 時間情報を格納するElementを生成する際に使用するイテレーター
     *
     * @return String[]
     * @author いっぺー
     */
    private static String[] iterator() {
        return new String[]{"year", "month", "date", "hours", "minutes", "seconds"};
    }
}

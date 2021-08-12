package xml.database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * パスワード等の情報を入れるクラス
 *
 * <p>Dataは "title, userName, password, message, created, updated" の6つの情報から成る。
 * created/updatedはHkTimeインスタンスによって保持する。
 *
 * @author いっぺー
 * @see HkTime
 */
public class Data {
    public final String _title;
    public final String _userName;
    public final Password _password;
    public final String _message;
    public final HkTime _created;
    public final HkTime _updated;

    public Data(String title, String userName, Password password, String message, HkTime created, HkTime updated) {
        this._title = title;
        this._userName = userName;
        this._password = password;
        this._message = message;
        this._created = created;
        this._updated = updated;
    }

    /**
     * Data型をElement型に変換
     *
     * @param doc データベースにアクセスするためのDocumentインスタンス
     * @return Element
     * @author いっぺー
     */
    public Element toElement(Document doc) {
        Element dataElem = doc.createElement("data");
        dataElem.setAttribute("title", this.getText("title"));

        for (String target : Data.textIterator()) {
            Element elem = doc.createElement(target);
            elem.appendChild(doc.createTextNode(this.getText(target)));
            dataElem.appendChild(elem);
        }

        for (String target : Data.timeIterator()) {
            Element elem = this.getTime(target).toElement(doc, target);
            dataElem.appendChild(elem);
        }

        Element password = this._password.toElement(doc);
        dataElem.appendChild(password);

        return dataElem;
    }

    /**
     * 文字列情報の取得
     *
     * <p>引数 target で指定した情報を文字列で取得する。
     *
     * @param target 取得する情報 (title, userName, password, message)
     * @return String
     * @author いっぺー
     */
    private String getText(String target) {
        switch (target) {
            case "title":
                return this._title;
            case "userName":
                return this._userName;
            case "message":
                return this._message;
            default:
                throw new IllegalArgumentException("target " + target + " getTextでは取得できません。");
        }
    }

    /**
     * 時間情報の取得
     *
     * <p>引数 target で指定した情報をHkTimeインスタンスで取得する。
     *
     * @param target 取得する情報 (created, updated)
     * @return HkTime
     * @author いっぺー
     */
    private HkTime getTime(String target) {
        switch (target) {
            case "created":
                return this._created;
            case "updated":
                return this._updated;
            default:
                throw new IllegalArgumentException("target " + target + " getTimeでは取得できません。");
        }
    }

    /**
     * 文字列情報を格納するElementを生成する際に使用するイテレーター
     *
     * @return String[]
     * @author いっぺー
     */
    private static String[] textIterator() {
        return new String[]{"userName", "message"};
    }

    /**
     * 時間情報を格納するElementを生成する際に使用するイテレーター
     *
     * @return String[]
     * @author いっぺー
     */
    private static String[] timeIterator() {
        return new String[]{"updated", "created"};
    }
}

package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * パスワード等の情報を入れるクラス
 *
 * <p>Dataは "title, userName, password, message, created, updated" の6つの情報から成る。
 * created/updatedはHkTimeインスタンスによって保持する。
 *
 * @see HkTime
 * @author いっぺー
 */
public class Data {
    private String _title;
    private String _userName;
    private String _password;
    private String _message;
    private final HkTime _created;
    private HkTime _updated;

    public Data(String title, String userName, String password, String message, HkTime created, HkTime updated) {
        this._title = title;
        this._userName = userName;
        this._password = password;
        this._message = message;
        this._created = created;
        this._updated = updated;
    }

    /**
     * Element型のデータからDataインスタンスを生成
     *
     * @param element Element型のデータ
     * @return Data
     * @author いっぺー
     */
    public static Data newInstanceByElement(Element element) {
        Element createdElem = (Element) element.getElementsByTagName("created").item(0);
        Element updatedElem = (Element) element.getElementsByTagName("updated").item(0);
        return new Data(
            element.getAttribute("title"),
            element.getElementsByTagName("userName").item(0).getTextContent(),
            element.getElementsByTagName("password").item(0).getTextContent(),
            element.getElementsByTagName("message").item(0).getTextContent(),
            HkTime.newInstanceByElement(createdElem),
            HkTime.newInstanceByElement(updatedElem)
        );
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
    public String getText(String target) {
        switch (target) {
            case "title":
                return this._title;
            case "userName":
                return this._userName;
            case "password":
                return this._password;
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
    public HkTime getTime(String target) {
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
     * 情報の更新
     *
     * <p>引数 target で指定した情報をnewValueで更新する。更新後に自動的にupdatedも更新される。
     *
     * @param target 更新する対象 (title, userName, password, message)
     * @param newValue 新しい値
     * @author いっぺー
     */
    public void update(String target, String newValue) {
        switch (target) {
            case "title":
                this._title = newValue;
                break;
            case "userName":
                this._userName = newValue;
                break;
            case "password":
                this._password = newValue;
                break;
            case "message":
                this._message = newValue;
                break;
            default:
                throw new IllegalArgumentException("引数 target には、title/userName/password/message のいずれかを指定してください。");
        }
        this._updated = HkTime.now();
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

        for (String target: Data.textIterator()) {
            Element elem = doc.createElement(target);
            elem.appendChild(doc.createTextNode(this.getText(target)));
            dataElem.appendChild(elem);
        }

        for (String target: Data.timeIterator()) {
            Element elem = this.getTime(target).toElement(doc, target);
            dataElem.appendChild(elem);
        }

        return dataElem;
    }

    /**
     * 文字列情報を格納するElementを生成する際に使用するイテレーター
     *
     * @return String[]
     * @author いっぺー
     */
    public static String[] textIterator() {
        return new String[] {"userName", "password", "message"};
    }

    /**
     * 時間情報を格納するElementを生成する際に使用するイテレーター
     *
     * @return String[]
     * @author いっぺー
     */
    public static String[] timeIterator() {
        return new String[] {"updated", "created"};
    }
}

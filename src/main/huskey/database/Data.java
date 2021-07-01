package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

    public static String[] textIterator() {
        return new String[] {"userName", "password", "message"};
    }

    public static String[] timeIterator() {
        return new String[] {"updated", "created"};
    }
}

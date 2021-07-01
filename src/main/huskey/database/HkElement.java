package database;

import org.w3c.dom.Element;

/**
 * Element型とData型のデータを繋ぐAPI
 *
 * @author いっぺー
 */
public class HkElement {
    private final Element element;

    public HkElement(Element element) {
        this.element = element;
    }

    /**
     * Element型でデータを取得
     *
     * @return Element
     * @author いっぺー
     */
    public Element toElement() {
        return this.element;
    }

    /**
     * データの名前が引数に取った値と等しいか否か
     *
     * @param target データの名前
     * @return boolean
     * @author いっぺー
     */
    public boolean isTitleAttrEqualTo(String target) {
        String title = this.element.getAttribute("title");
        return title.equals(target);
    }

    /**
     * Data型でデータを取得
     *
     * @return Data
     * @author いっぺー
     */
    public Data toData() {
        Element createdElem = (Element) this.element.getElementsByTagName("created").item(0);
        Element updatedElem = (Element) this.element.getElementsByTagName("updated").item(0);
        return new Data(
            this.element.getAttribute("title"),
            this.element.getElementsByTagName("userName").item(0).getTextContent(),
            this.element.getElementsByTagName("password").item(0).getTextContent(),
            this.element.getElementsByTagName("message").item(0).getTextContent(),
            this.elemToHkTime(createdElem),
            this.elemToHkTime(updatedElem)
        );
    }

    /**
     * created/updatedのElementをHkTimeに変換
     *
     * @param elem created/updatedのElement
     * @return HkTime
     * @author いっぺー
     */
    private HkTime elemToHkTime(Element elem) {
        return new HkTime(
            Integer.parseInt(elem.getAttribute("year")),
            Integer.parseInt(elem.getAttribute("month")),
            Integer.parseInt(elem.getAttribute("date")),
            Integer.parseInt(elem.getAttribute("hours")),
            Integer.parseInt(elem.getAttribute("minutes")),
            Integer.parseInt(elem.getAttribute("seconds"))
        );
    }
}

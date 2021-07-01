package database;

import org.w3c.dom.Element;

public class HkElement {
    private final Element element;

    public HkElement(Element element) {
        this.element = element;
    }

    public Element toElement() {
        return this.element;
    }

    public boolean isTitleAttrEqualTo(String target) {
        String title = this.element.getAttribute("title");
        return title.equals(target);
    }

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

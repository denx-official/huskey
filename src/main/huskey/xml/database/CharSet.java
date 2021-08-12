package xml.database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CharSet {
    private final String lowerCase;
    private final String upperCase;
    private final String number;
    private final String symbols;
    private final String space;
    private final String customChar;

    public CharSet(String lowerCase, String upperCase, String number, String symbols, String space, String customChar) {
        this.lowerCase = lowerCase;
        this.upperCase = upperCase;
        this.number = number;
        this.symbols = symbols;
        this.space = space;
        this.customChar = customChar;
    }

    public Element toElement(Document doc) {
        Element charSet = doc.createElement("charSet");
        for (String name : CharSet.attrIterator()) {
            charSet.setAttribute(name, this.get(name));
        }

        charSet.appendChild(doc.createTextNode(this.get("customChar")));

        return charSet;
    }

    private String get(String target) {
        switch (target) {
            case "lowerCase":
                return String.valueOf(this.lowerCase);
            case "upperCase":
                return String.valueOf(this.upperCase);
            case "number":
                return String.valueOf(this.number);
            case "symbols":
                return String.valueOf(this.symbols);
            case "space":
                return String.valueOf(this.space);
            case "customChar":
                return this.customChar;
            default:
                throw new IllegalArgumentException("target " + target + " getTimeでは取得できません。");
        }
    }

    private static String[] attrIterator() {
        return new String[]{"lowerCase", "upperCase", "number", "symbols", "space"};
    }
}

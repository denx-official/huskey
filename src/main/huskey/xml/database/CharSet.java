package xml.database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utility.UTF8;

public class CharSet {
    private final String lowerCase;
    private final String upperCase;
    private final String number;
    private final String symbols;
    private final String space;
    private final String custom;

    public CharSet(String lowerCase, String upperCase, String number, String symbols, String space, String custom) {
        this.lowerCase = lowerCase;
        this.upperCase = upperCase;
        this.number = number;
        this.symbols = symbols;
        this.space = space;
        this.custom = custom;
    }

    public Element toElement(Document doc) {
        Element charSet = doc.createElement("charSet");
        for (String name : CharSet.attrIterator()) {
            charSet.setAttribute(name, this.get(name));
        }

        return charSet;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.lowerCase.equals("true")) {
            sb.append(UTF8.getStringsInRange(97, 122)); // a-z
        }

        if (this.upperCase.equals("true")) {
            sb.append(UTF8.getStringsInRange(65, 90)); //A-Z
        }

        if (this.number.equals("true")) {
            sb.append(UTF8.getStringsInRange(48, 57)); // 0-9
        }

        if (this.symbols.equals("true")) {
            sb.append(UTF8.getStringsInRange(33, 47));   // !"#$%&'()*+,-./
            sb.append(UTF8.getStringsInRange(58, 64));   // :;<=>?@
            sb.append(UTF8.getStringsInRange(91, 96));   // [\]^_`
            sb.append(UTF8.getStringsInRange(123, 126)); // {|}~
        }

        if (this.space.equals("true")) {
            sb.append(UTF8.getStringsInRange(32, 32));
        }

        if (this.custom.length() != 0) {
            sb.append(this.custom);
        }

        // 文字の重複を削除する処理
        StringBuilder result = new StringBuilder();
        sb.toString().chars().distinct().forEach(c -> result.append((char) c));

        return result.toString();
    }

    public String get(String target) {
        switch (target) {
            case "lowerCase":
                return this.lowerCase;
            case "upperCase":
                return this.upperCase;
            case "number":
                return this.number;
            case "symbols":
                return this.symbols;
            case "space":
                return this.space;
            case "custom":
                return this.custom;
            default:
                throw new IllegalArgumentException("target " + target + " はメンバ変数に含まれていません。");
        }
    }

    private static String[] attrIterator() {
        return new String[]{"lowerCase", "upperCase", "number", "symbols", "space", "custom"};
    }
}

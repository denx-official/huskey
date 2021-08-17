package xml.database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utility.UTF8;

import java.util.ArrayList;
import java.util.Arrays;

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

    public String[] toStrings() {
        ArrayList<String> arrStr = new ArrayList<>();

        if (this.lowerCase.equals("true")) {
            arrStr.addAll(UTF8.getStringsInRange(97, 122));
        }

        if (this.upperCase.equals("true")) {
            arrStr.addAll(UTF8.getStringsInRange(65, 90));
        }

        if (this.number.equals("true")) {
            arrStr.addAll(UTF8.getStringsInRange(48, 57));
        }

        if (this.symbols.equals("true")) {
            arrStr.addAll(UTF8.getStringsInRange(33, 47));
            arrStr.addAll(UTF8.getStringsInRange(58, 64));
            arrStr.addAll(UTF8.getStringsInRange(91, 96));
            arrStr.addAll(UTF8.getStringsInRange(123, 126));
        }

        if (this.space.equals("true")) {
            arrStr.addAll(UTF8.getStringsInRange(32, 32));
        }

        if (this.custom.length() != 0) {
            String[] chars = this.custom.split("");
            ArrayList<String> a = new ArrayList<>(Arrays.asList(chars));
            arrStr.addAll(a);
        }

        // 文字の重複を削除する処理
        ArrayList<String> result = new ArrayList<>();
        for (String str : arrStr.toArray(new String[0])) {
            if (!result.contains(str)) {
                result.add(str);
            }
        }

        return result.toArray(new String[0]);
    }

    private String get(String target) {
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
                throw new IllegalArgumentException("target " + target + " getTimeでは取得できません。");
        }
    }

    private static String[] attrIterator() {
        return new String[]{"lowerCase", "upperCase", "number", "symbols", "space", "custom"};
    }
}

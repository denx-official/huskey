package xml.database;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class PasswordBuilder {
    private final Database db;
    private final String settingsTarget;

    public PasswordBuilder(Database db, String settingsTarget) {
        this.db = db;
        this.settingsTarget = settingsTarget;
    }

    public Password build() {
        String lenStr = this.getInitialPasswdLen();
        CharSet charSet = this.getInitialCharset();

        String newLenStr = "null";
        String[] charSetList = new String[6];

        try (Scanner scan = new Scanner(System.in)) {
            while (!this.isParseToInt(newLenStr)) {
                System.out.print("\npasswordLength (" + lenStr + "): ");
                newLenStr = scan.nextLine();
            }

            String[] attrName = CharSet.attrIterator();
            for (int i = 0; i < 5; i++) {
                String input = "";
                while (!this.isParseToBool(input)) {
                    System.out.print("\n" + attrName[i] + " (" + charSet.get(attrName[i]) + "): ");
                    input = scan.nextLine();
                }
                charSetList[i] = input;
            }

            System.out.print("\ncustom (" + charSet.get("custom") + "): ");
            charSetList[5] = scan.nextLine();
        } catch (NoSuchElementException _e) {
            System.exit(0);
        }

        CharSet newCharSet = new CharSet(
                charSetList[0],
                charSetList[1],
                charSetList[2],
                charSetList[3],
                charSetList[4],
                charSetList[5]
        );
        return new Password(
                newLenStr,
                newCharSet,
                ""
        );
    }

    private boolean isParseToInt(String lenStr) {
        try {
            Integer.parseInt(lenStr);
        } catch (Exception _e) {
            return false;
        }
        return true;
    }

    private boolean isParseToBool(String attrValue) {
        return attrValue.equals("true") || attrValue.equals("false");
    }

    String getInitialPasswdLen() {
        if (this.settingsTarget.equals("settings")) {
            Node lenNode = this.db.searchNode("//settings/passLength");
            return lenNode.getTextContent();
        }

        Node lenNode = this.db.searchNode("//data[@title='" + this.settingsTarget + "']/password/value");
        int len = lenNode.getTextContent().length();
        return String.valueOf(len);
    }

    CharSet getInitialCharset() {
        String expr;
        if (this.settingsTarget.equals("settings")) {
            expr = "//settings/charSet";
        } else {
            expr = "//data[@title='" + this.settingsTarget + "']/password/charSet";
        }

        Element charSetElem = (Element) this.db.searchNode(expr);
        return new CharSet(
                charSetElem.getAttribute("lowerCase"),
                charSetElem.getAttribute("upperCase"),
                charSetElem.getAttribute("number"),
                charSetElem.getAttribute("symbols"),
                charSetElem.getAttribute("space"),
                charSetElem.getAttribute("custom")
        );
    }
}

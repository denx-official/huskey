package xml.database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Dataインスタンス内で保持するパスワード周りの情報
 *
 * @author いっぺー
 * @see Data
 */
public class Password {
    private final String passLength;
    private final CharSet charSet;
    private String value;

    public Password(String passLength, CharSet charSet, String value) {
        this.passLength = passLength;
        this.charSet = charSet;
        this.value = value;
    }

    /**
     * パスワードの更新
     *
     * <p>メンバ変数valueの値をランダムに生成したパスワードで更新する。
     *
     * @author いっぺー
     */
    public void update() {}

    /**
     * パスワードの更新
     *
     * <p>メンバ変数valueの値を新しいパスワードで更新する。
     *
     * @param newPassword 新しいパスワード
     * @author いっぺー
     */
    public void update(String newPassword) {
        this.value = newPassword;
    }

    /**
     * Password型をElement型に変換
     *
     * <p>引数 tag で指定したタグ名でPassword型をElement型に変換する。
     *
     * @param doc データベースにアクセスするためのDocumentインスタンス
     * @return Element
     * @author いっぺー
     */
    public Element toElement(Document doc) {
        Element password = doc.createElement("password");
        for (String name : Password.iterator()) {
            Element elem = doc.createElement(name);
            elem.appendChild(doc.createTextNode(this.get(name)));
            password.appendChild(elem);
        }

        Element charSet = this.charSet.toElement(doc);
        password.appendChild(charSet);

        return password;
    }

    /**
     * メンバ変数の取得
     *
     * <p>引数 target で指定した情報をString型で取得する。
     *
     * @param target (value, passLength, charSet)
     * @return String
     * @author いっぺー
     */
    public String get(String target) {
        switch (target) {
            case "value":
                return this.value;
            case "passLength":
                return this.passLength;
            default:
                throw new IllegalArgumentException("target " + target + " はメンバ変数に含まれていません。");
        }
    }

    /**
     * Passwordのメンバ変数名イテレーター
     *
     * @return String[]
     * @author いっぺー
     */
    private static String[] iterator() {
        return new String[]{"value", "passLength"};
    }
}

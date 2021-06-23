package utility;

import java.io.Console;

/**
 * 標準入力を非表示で取得
 *
 * @author いっぺー
 */
public class HiddenInput {
    /**
     * <p>標準入力の値をコンソール上に表示せずに取得する。
     *
     * <pre>
     *     String databaseName = "sample";
     *     String masterKey = HiddenInput.read(databaseName);
     *     // コンソールの出力: データベース sample のパスワード:
     * </pre>
     *
     * @param databaseName データベースの名前
     * @author いっぺー
     */
    public static String read(String databaseName) {
        String msg = "データベース " + databaseName + " のパスワード: ";
        Console console = System.console();
        char[] input = console.readPassword(msg);

        if (input == null) {
            System.exit(0);
        }

        return new String(input);
    }
}

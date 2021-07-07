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
     *     String msg = "データベース SampleDB のパスワード: ";
     *     String masterKey = HiddenInput.read(msg);
     *     // コンソールの出力: データベース sample のパスワード:
     * </pre>
     *
     * @param msg 文字を入力する際に表示されるメッセージ
     * @author いっぺー
     */
    public static String read(String msg) {
        Console console = System.console();
        char[] input = console.readPassword(msg);

        if (input == null) {
            // Ctrl + C 等で入力がキャンセルされた場合
            System.exit(0);
        }

        return new String(input);
    }
}

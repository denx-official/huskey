package utility;

/**
 * huskey実行時に発生しうる例外クラス
 *
 * <p>この例外は cmd.CommandRouting クラスの run メソッドで捕捉され、与えられたメッセージが標準エラーとして表示される。
 *
 * @see cmd.CommandRouting
 * @author いっぺー
 */
public class HuskeyException extends RuntimeException {
    public HuskeyException(String message) {
        super(message);
    }
}

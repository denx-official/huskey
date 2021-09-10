package utility;

/**
 * huskey実行時にユーザー側の不正な操作が発生した時の例外クラス
 *
 * <p>この例外は cmd.CommandRouting クラスの run メソッドで捕捉され、与えられたメッセージが標準エラーとして表示される。
 *
 * @author いっぺー
 * @see cmd.CommandRouting
 */
public class HuskeyRuntimeException extends RuntimeException {
    public HuskeyRuntimeException(String message) {
        super(message);
    }
}

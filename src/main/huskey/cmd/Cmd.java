package cmd;

/**
 * 各コマンドのインターフェース
 *
 * <p>各コマンドに最低限実装すべきものとして、runメソッドを持つことを定義したインターフェース。
 *
 * @author いっぺー
 */
public interface Cmd {
    void run();
}

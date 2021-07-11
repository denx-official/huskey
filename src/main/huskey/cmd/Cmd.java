package cmd;

/**
 * 各コマンドの抽象クラス
 *
 * <p>各コマンドに最低限実装すべきものとして、<br>
 * 1. command, values, optionsをメンバ変数に持つこと<br>
 * 2. runメソッドを持つこと<br>
 * を定義した抽象クラス。
 *
 * @author いっぺー
 */
public interface Cmd {
    void run();
}

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
public abstract class Cmd {
    protected final String command;
    protected final String[] values;
    protected final String[] options;

    public Cmd(String command, String[] values, String[] options) {
        this.command = command;
        this.values = values;
        this.options = options;
    }

    abstract public void run();
}

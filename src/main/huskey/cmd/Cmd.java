package cmd;

import types.HuskeyArgs;

/**
 * 各コマンドの抽象クラス
 *
 * <p>各コマンドに最低限実装すべきものとして、<br>
 * 1. HuskeyArgsをメンバ変数に持つこと<br>
 * 2. runメソッドを持つこと<br>
 * を定義した抽象クラス。
 *
 * @author いっぺー
 */
abstract class Cmd {
    protected HuskeyArgs hkArgs;

    public Cmd(HuskeyArgs hkArgs) {
        this.hkArgs = hkArgs;
    }

    abstract void run();
}

package cmd;

import args.HkArgs;
import cmd.changeCmd.ChangeCmd;
import cmd.configCmd.ConfigCmd;
import cmd.databaseCmd.DatabaseCmd;
import cmd.getCmd.GetCmd;
import cmd.helpCmd.HelpCmd;
import cmd.initCmd.InitCmd;
import cmd.listCmd.ListCmd;
import cmd.mergeCmd.MergeCmd;
import cmd.removeCmd.RemoveCmd;
import cmd.setCmd.SetCmd;
import utility.HuskeyRuntimeException;

/**
 * 指定されたコマンドを実行するクラス
 *
 * <p>与えられた引数から条件分岐によってコマンドを決定し実行する。
 *
 * @author いっぺー
 */
public class CommandRunner {
    private final HkArgs hkArgs;

    public CommandRunner(HkArgs hkArgs) {
        this.hkArgs = hkArgs;
    }

    /**
     * コマンドの実行
     *
     * <p>huskeyで補足できる実行時エラーは、HuskeyRuntimeExceptionで補足される。
     *
     * @author いっぺー
     */
    public void run() {
        try {
            this._run();
        } catch (HuskeyRuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * コマンドの決定->実行
     *
     * <p>commandの値によってコマンドを決定・実行する。
     *
     * @author いっぺー
     */
    void _run() {
        Cmd cmd;

        switch (this.hkArgs.command) {
            case "help":
                cmd = new HelpCmd();
                break;

            case "config":
                cmd = new ConfigCmd();
                break;

            case "init":
                cmd = new InitCmd();
                break;

            case "change":
                cmd = new ChangeCmd();
                break;

            case "database":
                cmd = new DatabaseCmd();
                break;

            case "merge":
                cmd = new MergeCmd();
                break;

            case "list":
                cmd = new ListCmd();
                break;

            case "get":
                cmd = new GetCmd();
                break;

            case "set":
                cmd = new SetCmd();
                break;

            case "remove":
                cmd = new RemoveCmd();
                break;

            default:
                throw new HuskeyRuntimeException("huskey: コマンド '" + this.hkArgs.command + "' は存在しません。コマンドの一覧は、'huskey help' によって確認できます。");
        }

        cmd.run();
    }
}

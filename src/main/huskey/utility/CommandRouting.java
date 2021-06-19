package utility;

import cmd.Cmd;
import cmd.changeCmd.ChangeCmd;
import cmd.databaseCmd.DatabaseCmd;
import cmd.exportCmd.ExportCmd;
import cmd.getCmd.GetCmd;
import cmd.helpCmd.HelpCmd;
import cmd.importCmd.ImportCmd;
import cmd.initCmd.InitCmd;
import cmd.listCmd.ListCmd;
import cmd.mergeCmd.MergeCmd;
import cmd.removeCmd.RemoveCmd;
import cmd.searchCmd.SearchCmd;
import cmd.setCmd.SetCmd;
import cmd.switchCmd.SwitchCmd;
import types.HuskeyArgs;

/**
 * コマンドルーティング
 *
 * <p>与えられたcommandから条件分岐によって実行するコマンドを決定するルーティングシステム。
 *
 * @author いっぺー
 */
public class CommandRouting {
    private final HuskeyArgs hkArgs;

    public CommandRouting(HuskeyArgs hkArgs) {
        this.hkArgs= hkArgs;
    }

    /**
     * 実行するコマンドの決定
     *
     * <p>HuskeyArgs型からcommandを取得し、
     * その値によって実行するコマンドをswitch-case文によって決定する。
     *
     * <p>該当するコマンドが存在しない場合、IllegalArgumentException（引数エラー）を発生させる。
     *
     * @author いっぺー
     */
    public void run() throws IllegalArgumentException {
        String commandName = this.hkArgs.getCommand();
        Cmd cmd;

        switch (commandName) {
            case "help":
                cmd = new HelpCmd(this.hkArgs);
                break;

            case "init":
                cmd = new InitCmd(this.hkArgs);
                break;

            case "change":
                cmd = new ChangeCmd(this.hkArgs);
                break;

            case "database":
                cmd = new DatabaseCmd(this.hkArgs);
                break;

            case "switch":
                cmd = new SwitchCmd(this.hkArgs);
                break;

            case "merge":
                cmd = new MergeCmd(this.hkArgs);
                break;

            case "export":
                cmd = new ExportCmd(this.hkArgs);
                break;

            case "import":
                cmd = new ImportCmd(this.hkArgs);
                break;

            case "list":
                cmd = new ListCmd(this.hkArgs);
                break;

            case "search":
                cmd = new SearchCmd(this.hkArgs);
                break;

            case "get":
                cmd = new GetCmd(this.hkArgs);
                break;

            case "set":
                cmd = new SetCmd(this.hkArgs);
                break;

            case "remove":
                cmd = new RemoveCmd(this.hkArgs);
                break;

            default:
                throw new IllegalArgumentException("huskey: コマンド '" + commandName + "' は存在しません。コマンドの一覧は、'huskey help' によって確認できます。");
        }

        cmd.run();
    }
}

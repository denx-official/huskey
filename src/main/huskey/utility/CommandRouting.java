package utility;

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
    private HuskeyArgs hkArgs;

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
        String command = this.hkArgs.getCommand();

        switch (command) {
            case "help":
                HelpCmd helpCmd = new HelpCmd(this.hkArgs);
                helpCmd.run();
                break;

            case "init":
                InitCmd initCmd = new InitCmd(this.hkArgs);
                initCmd.run();
                break;

            case "change":
                ChangeCmd changeCmd = new ChangeCmd(this.hkArgs);
                changeCmd.run();
                break;

            case "database":
                DatabaseCmd databaseCmd = new DatabaseCmd(this.hkArgs);
                databaseCmd.run();
                break;

            case "switch":
                SwitchCmd switchCmd = new SwitchCmd(this.hkArgs);
                switchCmd.run();
                break;

            case "merge":
                MergeCmd mergeCmd = new MergeCmd(this.hkArgs);
                mergeCmd.run();
                break;

            case "export":
                ExportCmd exportCmd = new ExportCmd(this.hkArgs);
                exportCmd.run();
                break;

            case "import":
                ImportCmd importCmd = new ImportCmd(this.hkArgs);
                importCmd.run();
                break;

            case "list":
                ListCmd listCmd = new ListCmd(this.hkArgs);
                listCmd.run();
                break;

            case "search":
                SearchCmd searchCmd = new SearchCmd(this.hkArgs);
                searchCmd.run();
                break;

            case "get":
                GetCmd getCmd = new GetCmd(this.hkArgs);
                getCmd.run();
                break;

            case "set":
                SetCmd setCmd = new SetCmd(this.hkArgs);
                setCmd.run();
                break;

            case "remove":
                RemoveCmd removeCmd = new RemoveCmd(this.hkArgs);
                removeCmd.run();
                break;

            default:
                throw new IllegalArgumentException("huskey: コマンド '" + command + "' は存在しません。コマンドの一覧は、'huskey help' によって確認できます。");
        }
    }
}

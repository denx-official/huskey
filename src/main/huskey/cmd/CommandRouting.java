package cmd;

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

/**
 * コマンドルーティング
 *
 * <p>与えられた引数から条件分岐によってコマンドを決定し実行するルーティングシステム。
 *
 * @author いっぺー
 */
public class CommandRouting extends Cmd {
    public CommandRouting(String command, String[] values, String[] options) {
        super(command, values, options);
    }

    /**
     * 実行するコマンドの決定
     *
     * <p>commandの値によってコマンドを決定・実行する。
     *
     * @exception IllegalArgumentException 該当するコマンドが存在しない場合
     * @author いっぺー
     */
    public void run() throws IllegalArgumentException {
        Cmd cmd;

        switch (this.command) {
            case "help":
                cmd = new HelpCmd(this.command, this.values, this.options);
                break;

            case "init":
                cmd = new InitCmd(this.command, this.values, this.options);
                break;

            case "change":
                cmd = new ChangeCmd(this.command, this.values, this.options);
                break;

            case "database":
                cmd = new DatabaseCmd(this.command, this.values, this.options);
                break;

            case "switch":
                cmd = new SwitchCmd(this.command, this.values, this.options);
                break;

            case "merge":
                cmd = new MergeCmd(this.command, this.values, this.options);
                break;

            case "export":
                cmd = new ExportCmd(this.command, this.values, this.options);
                break;

            case "import":
                cmd = new ImportCmd(this.command, this.values, this.options);
                break;

            case "list":
                cmd = new ListCmd(this.command, this.values, this.options);
                break;

            case "search":
                cmd = new SearchCmd(this.command, this.values, this.options);
                break;

            case "get":
                cmd = new GetCmd(this.command, this.values, this.options);
                break;

            case "set":
                cmd = new SetCmd(this.command, this.values, this.options);
                break;

            case "remove":
                cmd = new RemoveCmd(this.command, this.values, this.options);
                break;

            default:
                throw new IllegalArgumentException("huskey: コマンド '" + this.command + "' は存在しません。コマンドの一覧は、'huskey help' によって確認できます。");
        }

        cmd.run();
    }
}

package cmd;

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
import utility.GlobalConst;
import utility.HuskeyRuntimeException;
import xml.database.Database;
import xml.database.DatabaseBuilder;
import xml.database.DatabaseFactory;

/**
 * コマンドルーティング
 *
 * <p>与えられた引数から条件分岐によってコマンドを決定し実行するルーティングシステム。
 *
 * @author いっぺー
 */
public class CommandRouting {
    private final String command;
    private final String[] values;
    private final String[] options;

    public CommandRouting(String command, String[] values, String[] options) {
        this.command = command;
        this.values = values;
        this.options = options;
    }

    /**
     * 実行するコマンドの決定
     *
     * <p>commandの値によってコマンドを決定・実行する。
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

    void _run() {
        Cmd cmd;

        switch (this.command) {
            case "help":
                cmd = new HelpCmd(GlobalConst.HUSKEY_DIR);
                break;

            case "config":
                cmd = new ConfigCmd(GlobalConst.HUSKEY_DIR);
                break;

            case "init":
                cmd = new InitCmd(GlobalConst.HUSKEY_DIR);
                break;

            case "change":
                cmd = new ChangeCmd(GlobalConst.HUSKEY_DIR);
                break;

            case "database":
                cmd = new DatabaseCmd(GlobalConst.HUSKEY_DIR);
                break;

            case "merge":
                cmd = new MergeCmd(GlobalConst.HUSKEY_DIR);
                break;

            case "list":
                cmd = new ListCmd(GlobalConst.HUSKEY_DIR);
                break;

            case "get":
                cmd = new GetCmd(GlobalConst.HUSKEY_DIR);
                break;

            case "set":
                cmd = new SetCmd(GlobalConst.HUSKEY_DIR);
                break;

            case "remove":
                cmd = new RemoveCmd(GlobalConst.HUSKEY_DIR);
                break;

            default:
                throw new HuskeyRuntimeException("huskey: コマンド '" + this.command + "' は存在しません。コマンドの一覧は、'huskey help' によって確認できます。");
        }

        cmd.run();
    }

    Database useDB() {
        DatabaseFactory factory = new DatabaseFactory(
                this.command,
                this.values,
                this.options,
                GlobalConst.HUSKEY_DIR
        );
        DatabaseBuilder builder = factory.build();

        if (!builder.exists()) {
            System.err.println("huskey: データベース '" + builder.dbName + "' は存在しません。");
            System.exit(1);
        }

        if (!builder.isKeyMatched()) {
            System.err.println("huskey: データベース '" + builder.dbName + "' の masterKey が不正です。");
            System.exit(1);
        }

        return builder.build();
    }
}

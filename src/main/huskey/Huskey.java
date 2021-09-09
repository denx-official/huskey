import cmd.CommandRouting;
import utility.GlobalConst;
import utility.SeparateArgs;
import xml.database.Database;
import xml.database.DatabaseBuilder;
import xml.database.DatabaseFactory;

/**
 * コマンドライン引数を分解 -> コマンドルーティングへ渡し、実行
 *
 * @author いっぺー
 */
public class Huskey {
    public static void main(String[] args) {
        SeparateArgs sepArgs = new SeparateArgs(args);

        String cmd = sepArgs.getCommand();
        String[] values = sepArgs.getValues();
        String[] options = sepArgs.getOptions();

        DatabaseFactory factory = new DatabaseFactory(cmd, values, options, GlobalConst.HUSKEY_DIR);
        DatabaseBuilder builder = factory.build();

        if (!builder.exists()) {
            System.err.println("huskey: データベース '" + builder.dbName + "' は存在しません。");
            System.exit(1);
        }

        if (!builder.isKeyMatched()) {
            System.err.println("huskey: データベース '" + builder.dbName + "' の masterKey が不正です。");
            System.exit(1);
        }

        Database db = builder.build();
        CommandRouting cr = new CommandRouting(cmd, values, options, db);
        cr.run();
    }
}

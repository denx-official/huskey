package cmd;

import utility.HuskeyRuntimeException;
import xml.database.Database;
import xml.database.DatabaseBuilder;
import xml.database.DatabaseFactory;

public class UseDatabase {
    public static Database useDB(String command, String[] values, String[] options, String huskeyDir) {
        DatabaseFactory factory = new DatabaseFactory(command, values, options, huskeyDir);
        DatabaseBuilder builder = factory.build();

        if (!builder.exists()) {
            throw new HuskeyRuntimeException("huskey: データベース '" + builder.dbName + "' は存在しません。");
        }

        if (!builder.isKeyMatched()) {
            throw new HuskeyRuntimeException("huskey: データベース '" + builder.dbName + "' の masterKey が不正です。");
        }

        return builder.build();
    }
}

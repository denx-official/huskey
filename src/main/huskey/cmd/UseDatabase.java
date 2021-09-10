package cmd;

import args.HkArgs;
import utility.HiddenInput;
import utility.HuskeyRuntimeException;
import xml.database.Database;
import xml.database.DatabaseBuilder;

public class UseDatabase {
    public static Database useDB(HkArgs hkArgs, String huskeyDir) {
        String dbName = hkArgs.dbName(huskeyDir);
        String masterKey = HiddenInput.read("データベース " + dbName + " のパスワード: ");

        DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);

        if (!builder.exists()) {
            throw new HuskeyRuntimeException("huskey: データベース '" + builder.dbName + "' は存在しません。");
        }

        if (!builder.isKeyMatched()) {
            throw new HuskeyRuntimeException("huskey: データベース '" + builder.dbName + "' の masterKey が不正です。");
        }

        return builder.build();
    }
}

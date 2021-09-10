package cmd;

import utility.HuskeyRuntimeException;
import xml.database.Database;
import xml.database.DatabaseBuilder;

/**
 * データベースの取得を行うクラス
 *
 * @author いっぺー
 */
public class UseDatabase {
    /**
     * データベースの取得
     *
     * <p>masterKeyを照合する処理や、データベースの存在確認といった、
     * 各コマンドで共通するフローをまとめた static method。
     *
     * @param dbName データベース名
     * @param huskeyDir ドットフォルダーのディレクトリ
     * @return Database
     */
    public static Database useDB(String dbName, String masterKey, String huskeyDir) {
        DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);

        if (!builder.exists()) {
            throw new HuskeyRuntimeException("huskey: データベース '" + dbName + "' は存在しません。");
        }

        if (!builder.isKeyMatched()) {
            throw new HuskeyRuntimeException("huskey: データベース '" + dbName + "' の masterKey が不正です。");
        }

        return builder.build();
    }
}

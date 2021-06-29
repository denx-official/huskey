package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database db;

    @BeforeEach
    void setup() {
        String dbName = "SampleDB";
        String masterKey = "sample";
        String dbDir = "./target/test-classes/resources/database/";
        DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, dbDir);
        db = builder.buildDatabase();
    }

    @Test
    void データセットの取得() {}

    @Test
    void コンフィグの取得() {}

    @Test
    void データセットの更新() {}

    @Test
    void コンフィグの更新() {}

    @Test
    void データベース名の変更() {}

    @Test
    void masterKeyの更新() {}

    @Test
    void データベースのマージ() {} // 優先度_低

    @Test
    void データベースの書き出し() {
        db.write();
    }
}
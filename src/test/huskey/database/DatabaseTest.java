package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database db;
    private final String dbName = "SampleDB";
    private final String masterKey = "sample";
    private final String dbDir = "./target/test-classes/resources/database/";

    @BeforeEach
    void setup() {
        DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, dbDir);
        db = builder.buildDatabase();
    }

    @Test
    void データベース名のリストを取得() {
        String[] dbList = Database.getDBList(dbDir);
        String[] expect = {dbName};
        assertArrayEquals(expect, dbList);
    }

    @Test
    void データセットの取得() {
        try {
            db.useDataset();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

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
        // db.write();
    }
}
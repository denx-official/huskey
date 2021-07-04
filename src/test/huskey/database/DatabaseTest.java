package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

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
            Dataset _dataset = db.useDataset();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void コンフィグの取得() {
        try {
            Config _config = db.useConfig();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void データセットの更新() {
        Dataset dataset = db.useDataset();
        try {
            db.setDataset(dataset);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void コンフィグの更新() {
        Config config = db.useConfig();
        try {
            db.setConfig(config);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void データベース名の取得() {
        assertEquals(dbName, db.getDBName());
    }

    @Test
    void データベース名の変更() {
        String expect = "SampleDB2";
        db.setDBName(expect);
        assertEquals(expect, db.getDBName());
    }

    @Test
    void masterKeyの更新() {
        String expect = "sample2";
        db.setMasterKey(expect);
        assertEquals(expect, db._getMasterKey());
    }

    @Test
    void データベースの書き出し() {
        // db.write();
    }

    @Test
    void データベースのマージ() {} // 優先度_低
}
package database;

import database.config.Config;
import database.dataset.Data;
import database.dataset.Dataset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.w3c.dom.NodeList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database db;
    private final String dbName = "SampleDB";
    private final String masterKey = "sample";
    private final String huskeyDir = "./target/test-classes/resources/";

    @BeforeEach
    void setup() {
        DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);
        db = builder.buildDatabase();
    }

    @Test
    void データベース名のリストを取得() {
        String[] dbList = Database.getDBList(huskeyDir);
        String[] expect = {dbName};
        assertArrayEquals(expect, dbList);
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
    void データセットの取得() {
        try {
            Dataset _dataset = db.useDBChild("dataset");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void コンフィグの取得() {
        try {
            Config _config = db.useDBChild("config");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void データセットの更新() {
        {
            Dataset dataset = db.useDBChild("dataset");
            Data data = dataset.useData("Google");
            data.set("title", "Google2");
            dataset.setData("Google", data);
            db.setDBChild("dataset", dataset);
        }

        Dataset dataset = db.useDBChild("dataset");
        dataset.useData("Google2");
    }

    @Test
    void コンフィグの更新() {
        Config config = db.useDBChild("config");
        try {
            db.setDBChild("config", config);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void データベースの書き出し() {
        // db.write();
    }

    @Test
    void データベースのマージ() {} // 優先度_低

    @Test
    void ノードの検索() {
        NodeList nodeList = db.searchNodeList("/database");
        if (nodeList.getLength() == 1) return;
        fail();
    }

    @Nested
    class 異常系 {
        @Test
        void 検索したノードが見つからなかった場合() {
            try {
                db.searchNodeList("/hoge");
            } catch (IllegalArgumentException _e) {
                return;
            }

            fail();
        }
    }
}
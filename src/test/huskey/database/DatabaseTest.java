package database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

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
    void masterKeyの更新() {
        String expect = "sample2";
        db.setMasterKey(expect);
        assertEquals(expect, db._getMasterKey());
    }

    @Test
    void ノードの検索() {
        Node _node = db.searchNodeList("/database").item(0);
    }

    @Nested
    class nodeExist {
        @Test
        void ノードが存在するか否か_true() {
            assertTrue(db.nodeExist("/database"));
        }

        @Test
        void ノードが存在するか否か_false() {
            assertFalse(db.nodeExist("/hoge"));
        }
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

    @Test
    void データベースの書き出し() {
        Path path = Paths.get(huskeyDir + "database/SampleDB/SampleDB.hkdb");

        try {
            FileTime before = Files.getLastModifiedTime(path);
            db.write();
            FileTime after = Files.getLastModifiedTime(path);

            assertNotEquals(before.toString(), after.toString());
        } catch (Exception e) {
            e.getStackTrace();
            fail();
        }
    }

    @Test
    void データベースのマージ() {} // 優先度_低
}
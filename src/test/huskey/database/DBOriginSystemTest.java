package database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import static org.junit.jupiter.api.Assertions.*;

class DBOriginSystemTest {
    private DBOriginSystem dbOriginSystem;
    private final String dbName = "SampleDB";
    private final String masterKey = "sample";
    private final String huskeyDir = "./target/test-classes/resources/";

    @BeforeEach
    void setup() {
        DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);
        dbOriginSystem = builder.buildDatabase();
    }

    @Test
    void ノードの検索() {
        Node _node = dbOriginSystem.searchNode("/database");
    }

    @Nested
    class 異常系 {
        @Test
        void 検索したノードが見つからなかった場合() {
            try {
                dbOriginSystem.searchNode("/hoge");
            } catch (IllegalArgumentException _e) {
                return;
            }

            fail();
        }
    }
}
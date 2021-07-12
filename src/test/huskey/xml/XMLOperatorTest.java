package xml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xml.database.Database;
import xml.database.DatabaseBuilder;

import static org.junit.jupiter.api.Assertions.*;

class XMLOperatorTest {
    private XMLOperator operator;

    String dbName = "SampleDB";
    String masterKey = "sample";
    String huskeyDir = "./target/test-classes/resources/";
    DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);
    Database db = builder.build();

    @BeforeEach
    void setup() {
        operator = new XMLOperator(db.getDoc());
    }

    @Test
    void ノードの取得() {
    }

    @Test
    void ノードの更新() {
    }

    @Test
    void updatedの更新() {
    }

    @Nested
    class nodeExist {
        @Test
        void ノードが存在するか否か_true() {
            assertTrue(operator.exists("/database"));
        }

        @Test
        void ノードが存在するか否か_false() {
            assertFalse(operator.exists("/hoge"));
        }
    }
}
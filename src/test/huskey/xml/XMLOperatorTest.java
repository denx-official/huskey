package xml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import xml.database.Database;
import xml.database.DatabaseBuilder;
import xml.database.HkTime;

import java.util.Arrays;

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
        Node node = db.searchNode("/database");
        if (node != null) return;
        fail("ノードを取得できませんでした");
    }

    @Test
    void updatedの更新() {
        String expr = "//data[@title = 'Google']/updated";

        Element beforeElem = (Element) db.searchNode(expr);
        db.setUpdatedTime("Google");
        Element afterElem = (Element) db.searchNode(expr);

        String[] iter = HkTime.iterator();
        String[] before = new String[iter.length];
        String[] after = new String[iter.length];

        for (int i = 0; i < iter.length; i++) {
            before[i] = beforeElem.getAttribute(iter[i]);
            after[i] = afterElem.getAttribute(iter[i]);
        }

        if (!Arrays.equals(before, after)) return;
        fail("updated の値が更新されていません。");
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
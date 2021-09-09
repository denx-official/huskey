package xml.database;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testUtil.GlobalConst;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseFactoryTest {
    DatabaseFactory factory;

    @Nested
    class argsからデータベース名を取得する {
        @Test
        void データベース名がないの場合() {
            String cmd = "get";
            String[] values = {"foo"};
            String[] options = {""};

            factory = new DatabaseFactory(cmd, values, options, GlobalConst.huskeyDir);
            assertEquals("SampleDB", factory.getDBName());
        }

        @Nested
        class データベース名がargsにある場合 {
            @Test
            void optionsから取得する場合() {
                String cmd = "get";
                String[] values = {"foo"};
                String[] options = {"--db=SampleDB2"};

                factory = new DatabaseFactory(cmd, values, options, GlobalConst.huskeyDir);
                assertEquals("SampleDB2", factory.getDBName());
            }

            @Test
            void valuesから取得する場合() {
                String cmd = "merge";
                String[] values = {"SampleDB2", "SampleDB3"};
                String[] options = {""};

                factory = new DatabaseFactory(cmd, values, options, GlobalConst.huskeyDir);
                assertEquals("SampleDB2", factory.getDBName());
            }
        }
    }
}
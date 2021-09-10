package args;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testUtil.GlobalConst;

import static org.junit.jupiter.api.Assertions.*;

class HkArgsTest {
    HkArgs hkArgs;

    @Nested
    class argsからデータベース名を取得する {
        @Test
        void データベース名がないの場合() {
            String cmd = "get";
            String[] values = {"foo"};
            String[] options = {""};

            hkArgs = new HkArgs(cmd, values, options);
            assertEquals("SampleDB", hkArgs.dbName(GlobalConst.huskeyDir));
        }

        @Nested
        class データベース名がargsにある場合 {
            @Test
            void optionsから取得する場合() {
                String cmd = "get";
                String[] values = {"foo"};
                String[] options = {"--db=SampleDB2"};

                hkArgs = new HkArgs(cmd, values, options);
                assertEquals("SampleDB2", hkArgs.dbName(GlobalConst.huskeyDir));
            }

            @Test
            void valuesから取得する場合() {
                String cmd = "merge";
                String[] values = {"SampleDB2", "SampleDB3"};
                String[] options = {""};

                hkArgs = new HkArgs(cmd, values, options);
                assertEquals("SampleDB2", hkArgs.dbName(GlobalConst.huskeyDir));
            }
        }
    }
}
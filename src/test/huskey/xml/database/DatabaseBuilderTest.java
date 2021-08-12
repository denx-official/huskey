package xml.database;

import org.junit.jupiter.api.*;
import testUtil.GlobalConst;

import java.io.UncheckedIOException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseBuilderTest {
    private DatabaseBuilder builder;

    @Nested
    class isKeyMatched {
        @Test
        void masterKeyの照合_true() {
            builder = new DatabaseBuilder(GlobalConst.dbName, GlobalConst.masterKey, GlobalConst.huskeyDir);
            assertTrue(builder.isKeyMatched());
        }

        @Test
        void masterKeyの照合_false() {
            builder = new DatabaseBuilder(GlobalConst.dbName, "sanple", GlobalConst.huskeyDir);
            assertFalse(builder.isKeyMatched());
        }
    }

    @Nested
    class exists {
        @Test
        void データベースの存在確認_true() {
            builder = new DatabaseBuilder(GlobalConst.dbName, GlobalConst.masterKey, GlobalConst.huskeyDir);
            assertTrue(builder.exists());
        }

        @Test
        void データベースの存在確認_false() {
            builder = new DatabaseBuilder(GlobalConst.dbName, GlobalConst.masterKey, "./hoge/");
            assertFalse(builder.exists());
        }
    }

    @Test
    void データベースの構築() {
        builder = new DatabaseBuilder(GlobalConst.dbName, GlobalConst.masterKey, GlobalConst.huskeyDir);
        Database _db = builder.build();
    }

    @Nested
    class 異常系 {
        @BeforeEach
        void setup() {
            builder = new DatabaseBuilder(GlobalConst.dbName, GlobalConst.masterKey, "./hoge/");
        }

        @Test
        void ハッシュ化したファイルが存在しなかった場合() {
            assertThrows(UncheckedIOException.class, builder::isKeyMatched);
        }
    }
}
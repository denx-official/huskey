package xml.database;

import org.junit.jupiter.api.*;
import xml.SampleDB;

import java.io.UncheckedIOException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseBuilderTest {
    private DatabaseBuilder builder;

    @Nested
    class isKeyMatched {
        @Test
        void masterKeyの照合_true() {
            builder = new DatabaseBuilder(SampleDB.dbName, SampleDB.masterKey, SampleDB.huskeyDir);
            assertTrue(builder.isKeyMatched());
        }

        @Test
        void masterKeyの照合_false() {
            builder = new DatabaseBuilder(SampleDB.dbName, "sanple", SampleDB.huskeyDir);
            assertFalse(builder.isKeyMatched());
        }
    }

    @Nested
    class exists {
        @Test
        void データベースの存在確認_true() {
            builder = new DatabaseBuilder(SampleDB.dbName, SampleDB.masterKey, SampleDB.huskeyDir);
            assertTrue(builder.exists());
        }

        @Test
        void データベースの存在確認_false() {
            builder = new DatabaseBuilder(SampleDB.dbName, SampleDB.masterKey, "./hoge/");
            assertFalse(builder.exists());
        }
    }

    @Test
    void データベースの構築() {
        builder = new DatabaseBuilder(SampleDB.dbName, SampleDB.masterKey, SampleDB.huskeyDir);
        Database db = builder.build();
        if (db != null) return;
        fail("データベースの構築に失敗しました。");
    }

    @Nested
    class 異常系 {
        @BeforeEach
        void setup() {
            builder = new DatabaseBuilder(SampleDB.dbName, SampleDB.masterKey, "./hoge/");
        }

        @Test
        void ハッシュ化したファイルが存在しなかった場合() {
            assertThrows(UncheckedIOException.class, builder::isKeyMatched);
        }
    }
}
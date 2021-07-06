package database;

import org.junit.jupiter.api.*;

import java.io.UncheckedIOException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseBuilderTest {
    private DatabaseBuilder builder;
    private final String dbName = "SampleDB";
    private final String masterKey = "sample";
    private final String huskeyDir = "./target/test-classes/resources/";

    @Nested
    class isKeyMatched {
        @Test
        void masterKeyの照合_true() {
            builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);
            assertTrue(builder.isKeyMatched());
        }

        @Test
        void masterKeyの照合_false() {
            builder = new DatabaseBuilder(dbName, "sanple", huskeyDir);
            assertFalse(builder.isKeyMatched());
        }
    }

    @Nested
    class exists {
        @Test
        void データベースの存在確認_true() {
            builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);
            assertTrue(builder.exists());
        }

        @Test
        void データベースの存在確認_false() {
            builder = new DatabaseBuilder(dbName, masterKey, "./hoge/");
            assertFalse(builder.exists());
        }
    }

    @Nested
    class buildDatabase {
        @Test
        void データベースの構築() {
            builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);
            try {
                Database _db = builder.buildDatabase();
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }
    }

    @Nested
    class 異常系 {
        @BeforeEach
        void setup() {
            builder = new DatabaseBuilder(dbName, masterKey, "./hoge/");
        }

        @Test
        void ハッシュ化したファイルが存在しなかった場合() {
            assertThrows(UncheckedIOException.class, builder::isKeyMatched);
        }
    }
}
package database;

import org.junit.jupiter.api.*;
import utility.HuskeyException;

import java.io.UncheckedIOException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseBuilderTest {
    private DatabaseBuilder builder;
    private final String dbName = "SampleDB";
    private final String masterKey = "sample";
    private final String dbDir = "./target/test-classes/resources/database/";

    @Nested
    class isKeyMatched {
        @Test
        void masterKeyの照合_true() {
            builder = new DatabaseBuilder(dbName, masterKey, dbDir);
            assertTrue(builder.isKeyMatched());
        }

        @Test
        void masterKeyの照合_false() {
            builder = new DatabaseBuilder(dbName, "sanple", dbDir);
            assertFalse(builder.isKeyMatched());
        }
    }

    @Nested
    class buildDatabase {
        @Test
        void データベースの構築() {
            builder = new DatabaseBuilder(dbName, masterKey, dbDir);
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
        void データベースが存在しなかった場合() {
            assertThrows(HuskeyException.class, builder::buildDatabase);
        }

        @Test
        void ハッシュ化したファイルが存在しなかった場合() {
            assertThrows(UncheckedIOException.class, builder::isKeyMatched);
        }
    }
}
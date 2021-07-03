package database;

import org.junit.jupiter.api.*;
import utility.HuskeyException;

import java.io.UncheckedIOException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseBuilderTest {
    private DatabaseBuilder db;
    private final String dbName = "SampleDB";
    private final String masterKey = "sample";
    private final String dbDir = "./target/test-classes/resources/database/";

    @Nested
    class isKeyMatched {
        @Test
        void masterKeyの照合_true() {
            db = new DatabaseBuilder(dbName, masterKey, dbDir);
            assertTrue(db.isKeyMatched());
        }

        @Test
        void masterKeyの照合_false() {
            db = new DatabaseBuilder(dbName, "sanple", dbDir);
            assertFalse(db.isKeyMatched());
        }
    }

    @Nested
    class buildDatabase {
        @Test
        void データベースの構築() {
            db = new DatabaseBuilder(dbName, masterKey, dbDir);
            try {
                db.buildDatabase();
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }
    }

    @Nested
    class 異常系 {
        @BeforeEach
        void setup() {
            db = new DatabaseBuilder(dbName, masterKey, "./hoge/");
        }

        @Test
        void データベースが存在しなかった場合() {
            assertThrows(HuskeyException.class, db::buildDatabase);
        }

        @Test
        void ハッシュ化したファイルが存在しなかった場合() {
            assertThrows(UncheckedIOException.class, db::isKeyMatched);
        }
    }
}
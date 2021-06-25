package utility;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database db;

    @Test
    void データベース名のリストを取得() {
        String[] dbList = Database.showDBList();
        String[] expect = {"sample"};
        assertArrayEquals(expect, dbList);
    }

    @Nested
    class isKeyMatched {
        @Test
        void masterKeyの照合_true() throws FileNotFoundException {
            db = new Database("sample", "sample");
            assertTrue(db.isKeyMatched());
        }

        @Test
        void masterKeyの照合_false() throws FileNotFoundException {
            db = new Database("sample", "sanple");
            assertFalse(db.isKeyMatched());
        }
    }

    @Nested
    class 異常系 {
        @BeforeAll
        void setup() {
            db = new Database("sample", "sample");
            // データベースとハッシュ化したmasterKeyの削除
        }

        @AfterAll
        void cleanUp() {
            // 消したファイルの復元
        }

        @Test
        void ハッシュ化したファイルが存在しなかった場合() {
            assertThrows(FileNotFoundException.class, db::isKeyMatched);
        }
    }
}
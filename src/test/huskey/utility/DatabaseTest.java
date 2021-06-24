package utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database db;

    @AfterEach
    void cleanUpEach() {
        db = null;
    }

    @Test
    void データベース名のリストを取得() {
        String[] dbList = Database.showDBList();
        String[] expect = {"sample"};
        assertArrayEquals(expect, dbList);
    }

    @Nested
    class isKeyMatched {
        @Nested
        class 正常系 {
            @Test
            void masterKeyの照合_true() {
                db = new Database("sample", "sample");
                assertTrue(db.isKeyMatched);
            }

            @Test
            void masterKeyの照合_false() {
                db = new Database("sample", "sanple");
                assertFalse(db.isKeyMatched);
            }
        }

        @Nested
        class 異常系 {
            @BeforeEach
            void setupEach() {
                db = new Database("sample", "sample");
                // ハッシュ化したファイルの削除
            }

            @AfterEach
            void cleanUpEach() {
                // ハッシュ化したファイルの復元
            }

            @Test
            void ハッシュ化したファイルが存在しなかった場合() {
                assertThrows(FileNotFoundException.class, db::isKeyMatched);
            }
        }
    }
}
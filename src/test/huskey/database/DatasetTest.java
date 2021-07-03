package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DatasetTest {
    private Dataset dataset;

    @BeforeEach
    void setup() {
        String dbName = "SampleDB";
        String masterKey = "sample";
        String dbDir = "./target/test-classes/resources/database/";
        DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, dbDir);
        Database db = builder.buildDatabase();
        dataset = db.useDataset();
    }

    @Test
    void Dataの一覧を取得() {
        String[] expect = {"Google", "Discord", "Twitter", "GitHub", "GitLab", "GitBucket"};
        String[] result = dataset.getDataList();
        assertArrayEquals(expect, result);
    }

    @Test
    void Dataの取得() {
        String title = "Google";
        try {
            dataset.useData(title);
        } catch (IllegalArgumentException _e) {
            fail("データ " + title + " は存在しません。");
        }
    }

    @Test
    void データの削除() {
        dataset.removeData("Google");

        try {
            dataset.useData("Google");
        } catch (IllegalArgumentException _e) {
            return;
        }

        fail("データが削除されていません。");
    }

    @Test
    void データの追加() {
        Data data = new Data(
            "huskey",
            "sample@sample.com",
            "S3nXpZMLBxJE2gWTqdN8",
            "something",
            HkTime.now(),
            HkTime.now()
        );
        dataset.setData("huskey", data);
        dataset.useData("huskey");
    }

    @Test
    void データの更新() {
        Data data = dataset.useData("Google");
        data.update("title", "Google2");
        dataset.setData("Google", data);

        try {
            dataset.useData("Google");
        } catch (IllegalArgumentException _e) {
            return;
        }

        fail("データが更新されていません。");
    }

    @Test
    void データの存在確認_true() {
        assertTrue(dataset.isDataExist("Google"));
    }

    @Test
    void データの存在確認_false() {
        assertFalse(dataset.isDataExist("hoge"));
    }
}
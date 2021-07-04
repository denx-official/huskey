package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import utility.HuskeyException;

import static org.junit.jupiter.api.Assertions.*;

class DatasetTest {
    private Dataset dataset;

    @BeforeEach
    void setup() {
        String dbName = "SampleDB";
        String masterKey = "sample";
        String huskeyDir = "./target/test-classes/resources/";
        DatabaseBuilder builder = new DatabaseBuilder(dbName, masterKey, huskeyDir);
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
            Data _data = dataset.useData(title);
        } catch (HuskeyException _e) {
            fail("データ " + title + " は存在しません。");
        }
    }

    @Test
    void データの削除() {
        dataset.removeData("Google");

        try {
            dataset.useData("Google");
        } catch (HuskeyException _e) {
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
        data.set("title", "Google2");
        dataset.setData("Google", data);

        try {
            dataset.useData("Google");
        } catch (HuskeyException _e) {
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
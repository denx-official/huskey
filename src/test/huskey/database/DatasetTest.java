package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

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
    void 新しいデータの追加() {
        Data data = new Data(
            "huskey",
            "sample@sample.com",
            "S3nXpZMLBxJE2gWTqdN8",
            "something",
            HkTime.now(),
            HkTime.now()
        );
        dataset.setData("huskey", data);

        assertEquals("huskey", dataset.useData("huskey").title());
    }

    @Test
    void データの更新() {
        String newTitle = "Google2";
        Data data = dataset.useData("Google");
        data.update("title", newTitle);
        dataset.setData("Google", data);
        assertEquals(newTitle, dataset.useData(newTitle).title());
    }
}
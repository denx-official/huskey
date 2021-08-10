package xml.database;

import org.junit.jupiter.api.*;
import utility.StrFileIO;
import xml.SampleDB;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database db;

    private static final String hashPath = "./target/test-classes/resources/database/SampleDB/hash";
    private static final String beforeHash = new StrFileIO(hashPath).readStrFile();

    @AfterAll
    static void cleanUpAll() {
        StrFileIO io = new StrFileIO(hashPath);
        io.writeStrFile(beforeHash);
    }

    @BeforeEach
    void setup() {
        DatabaseBuilder builder = new DatabaseBuilder(SampleDB.dbName, SampleDB.masterKey, SampleDB.huskeyDir);
        db = builder.build();
    }

    @Test
    void データベース名のリストを取得() {
        String[] dbList = Database.getDBList(SampleDB.huskeyDir);
        String[] expect = {SampleDB.dbName};
        assertArrayEquals(expect, dbList);
    }

    @Test
    void masterKeyの更新() {
        String expect = "sample2";
        db.setMasterKey(expect);
        assertEquals(expect, db._getMasterKey());
    }

    @Test
    void データベースの書き出し() {
        Path path = Paths.get(SampleDB.huskeyDir + "database/SampleDB/.hkdb");

        try {
            FileTime before = Files.getLastModifiedTime(path);
            db.write();
            FileTime after = Files.getLastModifiedTime(path);

            assertNotEquals(before.toString(), after.toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
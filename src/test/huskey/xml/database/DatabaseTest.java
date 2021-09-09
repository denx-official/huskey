package xml.database;

import org.junit.jupiter.api.*;
import utility.StrFileIO;
import testUtil.GlobalConst;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database db;

    @BeforeEach
    void setup() {
        DatabaseBuilder builder = new DatabaseBuilder(GlobalConst.dbName, GlobalConst.masterKey, GlobalConst.huskeyDir);
        db = builder.build();
    }

    @Test
    void データベース名のリストを取得() {
        String[] dbList = Database.getDBList(GlobalConst.huskeyDir);
        String[] expect = {GlobalConst.dbName};
        assertArrayEquals(expect, dbList);
    }

    @Nested
    class setMasterKey {
        private final String hashPath = GlobalConst.huskeyDir + "database/SampleDB/hash";
        private final String beforeHash = new StrFileIO(hashPath).readStrFile();

        @AfterEach
        void cleanUp() {
            StrFileIO io = new StrFileIO(hashPath);
            io.writeStrFile(beforeHash);
        }

        @Test
        void masterKey更新時にそのハッシュ値が変更されるか() {
            db.setMasterKey("sample2");

            String afterHash = new StrFileIO(hashPath).readStrFile();
            assertNotEquals(beforeHash, afterHash);
        }
    }

    @Test
    void データベースの書き出し() {
        Path path = Paths.get(GlobalConst.huskeyDir + "database/SampleDB/.hkdb");

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
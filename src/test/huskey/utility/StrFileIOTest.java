package utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testUtil.GlobalConst;

import static org.junit.jupiter.api.Assertions.*;

class StrFileIOTest {
    private StrFileIO fileIO;

    @BeforeEach
    void setup() {
        fileIO = new StrFileIO(GlobalConst.huskeyDir + "testFile");
    }

    @AfterEach
    void cleanUp() {
        String content = "testFile";
        fileIO.writeStrFile(content);
    }

    @Test
    void ファイルの読み込み() {
        String result = fileIO.readStrFile();
        assertEquals("testFile", result);
    }

    @Test
    void ファイルの書き出し() {
        String content = "testFile2";
        fileIO.writeStrFile(content);

        String result = fileIO.readStrFile();
        assertEquals(content, result);
    }
}
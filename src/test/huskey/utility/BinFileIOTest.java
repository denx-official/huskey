package utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xml.SampleDB;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class BinFileIOTest {
    private BinFileIO fileIO;

    @BeforeEach
    void setup() {
        fileIO = new BinFileIO(SampleDB.huskeyDir + "testFile");
    }

    @AfterEach
    void cleanUp() {
        byte[] content = "testFile".getBytes(StandardCharsets.UTF_8);
        fileIO.writeBinFile(content);
    }

    @Test
    void ファイルの読み込み() {
        String result = new String(fileIO.readBinFile());
        assertEquals("testFile", result);
    }

    @Test
    void ファイルの書き出し() {
        String content = "testFile2";
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        fileIO.writeBinFile(bytes);

        String result = new String(fileIO.readBinFile());
        assertEquals(content, result);
    }
}
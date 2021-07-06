package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class BinFileIOTest {
    private final String path = "./target/test-classes/resources/testFile";

    @AfterEach
    void cleanUp() {
        byte[] content = "testFile".getBytes(StandardCharsets.UTF_8);
        BinFileIO.writeBinFile(path, content);
    }

    @Test
    void ファイルの読み込み() {
        String result = new String(BinFileIO.readBinFile(path));
        assertEquals("testFile", result);
    }

    @Test
    void ファイルの書き出し() {
        String content = "testFile2";
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        BinFileIO.writeBinFile(path, bytes);

        String result = new String(BinFileIO.readBinFile(path));
        assertEquals(content, result);
    }
}
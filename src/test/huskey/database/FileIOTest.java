package database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {
    private final String path = "./target/test-classes/resources/testFile";

    @AfterEach
    void cleanUp() {
        byte[] content = "testFile".getBytes(StandardCharsets.UTF_8);
        FileIO.writeFileFromBytes(path, content);
    }

    @Test
    void ファイルの読み込み() {
        String result = new String(FileIO.readFileAsBytes(path));
        assertEquals("testFile", result);
    }

    @Test
    void ファイルの書き出し() {
        String content = "testFile2";
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        FileIO.writeFileFromBytes(path, bytes);

        String result = new String(FileIO.readFileAsBytes(path));
        assertEquals(content, result);
    }
}
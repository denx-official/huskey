package xml.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {
    private Config conf;
    private final String huskeyDir = "./target/classes/";

    @BeforeEach
    void setup() {
        ConfigBuilder builder = new ConfigBuilder(huskeyDir);
        conf = builder.build();
    }

    @Test
    void configファイルの書き出し() {
        Path path = Paths.get(huskeyDir + "config.xml");

        try {
            FileTime before = Files.getLastModifiedTime(path);
            conf.write();
            FileTime after = Files.getLastModifiedTime(path);

            assertNotEquals(before.toString(), after.toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
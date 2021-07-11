package xml.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import xml.StaticXMLMethods;

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
    void configファイルの読み込み() {
        Node node = StaticXMLMethods.searchNodeList(conf.getDoc(), "//defaultDB").item(0);
        String result = node.getTextContent();
        assertEquals("SampleDB", result);
    }
}
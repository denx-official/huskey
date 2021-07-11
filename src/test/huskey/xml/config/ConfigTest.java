package xml.config;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import xml.StaticXMLMethods;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {
    private Config conf;
    private ConfigBuilder builder;
    private final String huskeyDir = "./target/classes/";

    @Test
    void configファイルの読み込み() {
        builder = new ConfigBuilder(huskeyDir);
        conf = builder.build();

        Node node = StaticXMLMethods.searchNodeList(conf.doc, "//defaultDB").item(0);
        String result = node.getTextContent();
        assertEquals("SampleDB", result);
    }
}
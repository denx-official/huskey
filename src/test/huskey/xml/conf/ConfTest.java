package xml.conf;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import static org.junit.jupiter.api.Assertions.*;

class ConfTest {
    private Conf conf;
    private ConfBuilder builder;
    private final String huskeyDir = "./target/classes/";

    @Test
    void globalConfの読み込み() {
        builder = new ConfBuilder(ConfType.globalConf, huskeyDir);
        conf = builder.build();

        Node node = conf.searchNodeList("//defaultDB").item(0);
        String result = node.getTextContent();
        assertEquals("SampleDB", result);
    }

    @Test
    void initialConfの読み込み() {
        builder = new ConfBuilder(ConfType.initialConf, huskeyDir);
        conf = builder.build();

        Node node = conf.searchNodeList("//passLength").item(0);
        String result = node.getTextContent();
        assertEquals("16", result);
    }
}
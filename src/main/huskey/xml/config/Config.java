package xml.config;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xml.XMLOperator;
import xml.XMLWriter;
import org.w3c.dom.Document;

/**
 * config.xmlにアクセスするためのクラス
 *
 * @author いっぺー
 * @see XMLWriter
 */
public class Config extends XMLWriter {
    private final Document doc;
    private final String fileDir;
    private final XMLOperator operator;

    public Config(Document doc, String fileDir) {
        this.doc = doc;
        this.fileDir = fileDir;
        this.operator = new XMLOperator(doc);
    }

    public boolean exists(String expression) {
        return this.operator.exists(expression);
    }

    public Node searchNode(String expression) {
        return this.operator.searchNode(expression);
    }

    public NodeList searchNodeList(String expression) {
        return this.operator.searchNodeList(expression);
    }

    @Override
    public Document getDoc() {
        return this.doc;
    }

    @Override
    protected byte[] encrypt(byte[] bytes) {
        return bytes;
    }

    @Override
    protected String getFilePath() {
        return this.fileDir + "config.xml";
    }
}

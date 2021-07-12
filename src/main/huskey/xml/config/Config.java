package xml.config;

import org.w3c.dom.Node;
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

    /**
     * 対象のノードが存在するか否か
     *
     * <p>XPathを用いてDocumentを検索し、その条件に一致したノードが存在するかを判定する。
     *
     * @param expression 対象のノード（XPath構文）
     * @return boolean
     * @author いっぺー
     */
    public boolean exists(String expression) {
        return this.operator.exists(expression);
    }

    /**
     * ノードの検索
     *
     * <p>XPathを用いてDocumentを検索し、条件に一致したノードを取得する。
     *
     * @param expression 検索条件
     * @return NodeList
     * @author いっぺー
     */
    public Node searchNode(String expression) {
        return this.operator.searchNode(expression);
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

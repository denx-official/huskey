package xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;

public class XMLOperator {
    private final Document doc;

    public XMLOperator(Document doc) {
        this.doc = doc;
    }

    /**
     * ノードの検索
     *
     * <p>XPathを用いてDocumentを検索し、条件に一致したノードをNodeListで取得する。
     *
     * @param expression 検索条件
     * @return NodeList
     * @author いっぺー
     */
    public NodeList searchNodeList(String expression) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expr = xpath.compile(expression);
            NodeList nodeList = (NodeList) expr.evaluate(this.doc, XPathConstants.NODESET);

            if (nodeList.getLength() == 0) {
                throw new IllegalArgumentException("該当するノードが存在しません。");
            }

            return nodeList;
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 対象のノードが存在するか否か
     *
     * @return boolean
     * @author いっぺー
     */
    public boolean nodeExist(String expression) {
        try {
            this.searchNodeList(expression);
        } catch (IllegalArgumentException _e) {
            return false;
        }

        return true;
    }
}

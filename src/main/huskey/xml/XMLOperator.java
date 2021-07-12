package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xml.database.HkTime;

import javax.xml.xpath.*;

/**
 * Document の検索や更新を行うクラス
 *
 * <p>Database や Config クラスに委譲して使用する。
 *
 * @author いっぺー
 * @see xml.database.Database
 * @see xml.config.Config
 */
public class XMLOperator {
    private final Document doc;

    public XMLOperator(Document doc) {
        this.doc = doc;
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
        try {
            this.searchNodeList(expression);
        } catch (IllegalArgumentException _e) {
            return false;
        }
        return true;
    }

    /**
     * 対象のDataのupdatedを更新
     *
     * @param target 対象のDataのタイトル
     * @author いっぺー
     */
    public void updateTime(String target) {
        String dataExpr = "//data[@title = '" + target + "']";
        String updatedExpr = dataExpr + "/updated";

        Node data = this.searchNode(dataExpr);
        Node updated = this.searchNode(updatedExpr);
        Element newUpdated = HkTime.now().toElement(this.doc, "updated");

        data.removeChild(updated);
        data.appendChild(newUpdated);
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
        NodeList nodeList = this.searchNodeList(expression);

        if (1 < nodeList.getLength()) {
            throw new IllegalArgumentException("検索条件 " + expression + " に一致したノードが複数存在します。");
        }

        return nodeList.item(0);
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
}

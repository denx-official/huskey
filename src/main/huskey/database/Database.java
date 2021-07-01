package database;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;

/**
 * データベース
 *
 * <p>DatabaseBuilderから取得することで、データベースの中身を操作することができる。
 * データベースはXML形式であるが、Database/Dataset/Configクラスではその具体的なDOM操作を隠蔽する。
 *
 * @see DatabaseBuilder
 * @author いっぺー
 */
public class Database {
    private Document doc;
    private String dbName;
    private String masterKey;

    public Database(Document doc, String dbName, String masterKey) {
        this.doc = doc;
        this.dbName = dbName;
        this.masterKey = masterKey;
    }

    /**
     * データセットの取得
     *
     * <p>データベースからデータセットを取得する。
     *
     * @return Dataset
     * @author いっぺー
     */
    public Dataset useDataset() {
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expr = xpath.compile("/database/dataset");
            NodeList dataset = (NodeList) expr.evaluate(this.doc, XPathConstants.NODESET);
            Node root = dataset.item(0);
            return new Dataset(this.doc, root);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }
}

package database;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.io.File;

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
    private final Document doc;
    private String masterKey;
    private String huskeyDir;

    public Database(Document doc, String masterKey, String huskeyDir) {
        this.doc = doc;
        this.masterKey = masterKey;
        this.huskeyDir = huskeyDir;
    }

    /**
     * 全データベースの名前を一覧で取得
     *
     * @return String[]
     * @author いっぺー
     */
    public static String[] getDBList(String huskeyDir) {
        File[] dbFiles = new File(huskeyDir + "database/").listFiles();
        if (dbFiles == null) {
            return new String[] {""};
        }

        String[] result = new String[dbFiles.length];
        for (int i = 0; i < dbFiles.length; i++) {
            result[i] = dbFiles[i].getName();
        }

        return result;
    }

    public String getDBName() {
        Node name = this.searchNodeList("/database/name").item(0);
        return name.getTextContent();
    }

    public void setDBName(String newDBName) {
        Node name = this.searchNodeList("/database/name").item(0);
        name.setTextContent(newDBName);
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    String _getMasterKey() {
        return this.masterKey;
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
        NodeList dataset = this.searchNodeList("/database/dataset");
        Node root = dataset.item(0);
        return new Dataset(this.doc, root);
    }

    public Config useConfig() {
        NodeList dataset = this.searchNodeList("/database/config");
        Node root = dataset.item(0);
        return new Config(this.doc, root);
    }

    /**
     * ノードの検索
     *
     * <p>XPathを用いてDocumentを検索する。
     *
     * @param expression 検索条件
     * @return NodeList
     * @author いっぺー
     */
    NodeList searchNodeList(String expression) {
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

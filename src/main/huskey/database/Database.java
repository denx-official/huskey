package database;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utility.GlobalConst;

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
    private final String huskeyDir;

    public Database(Document doc, String masterKey) {
        this.doc = doc;
        this.masterKey = masterKey;
        this.huskeyDir = GlobalConst.HUSKEY_DIR;
    }

    Database(Document doc, String masterKey, String huskeyDir) {
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
    public static String[] getDBList() {
        return Database.getDBList(GlobalConst.HUSKEY_DIR);
    }

    /**
     * 全データベースの名前を一覧で取得
     *
     * @param huskeyDir ドットフォルダーのディレクトリ
     * @return String[]
     * @author いっぺー
     */
    static String[] getDBList(String huskeyDir) {
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

    /**
     * データベース名の取得
     *
     * @return String
     * @author いっぺー
     */
    public String getDBName() {
        Node name = this.searchNodeList("/database/name").item(0);
        return name.getTextContent();
    }

    /**
     * データベース名の更新
     *
     * @param newDBName 新しいデータベース名
     * @author いっぺー
     */
    public void setDBName(String newDBName) {
        Node name = this.searchNodeList("/database/name").item(0);
        name.setTextContent(newDBName);
    }

    /**
     * データベースの暗号化に使用するmasterKeyの更新
     *
     * @param newKey 新しいmasterKey
     * @author いっぺー
     */
    public void setMasterKey(String newKey) {
        this.masterKey = newKey;
    }

    /**
     * masterKeyの取得（テスト用）
     *
     * @return String
     * @author いっぺー
     */
    String _getMasterKey() {
        return this.masterKey;
    }

    /**
     * データセットの取得
     *
     * @return Dataset
     * @see Dataset
     * @author いっぺー
     */
    public Dataset useDataset() {
        NodeList dataset = this.searchNodeList("/database/dataset");
        Node root = dataset.item(0);
        return new Dataset(this.doc, root);
    }

    /**
     * コンフィグの取得
     *
     * @return Config
     * @see Config
     * @author いっぺー
     */
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

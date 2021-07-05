package database;

import database.config.Config;
import database.dataset.Dataset;
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
     * データセット/コンフィグの取得
     *
     * @param target "dataset" or "config"
     * @return T extends DBChild (Dataset, Config)
     * @see DBChild
     * @see Dataset
     * @see Config
     * @author いっぺー
     */
    @SuppressWarnings("unchecked")
    public <T extends DBChild> T useDBChild(String target) {
        Node child = this.searchNodeList("/database/" + target).item(0);

        switch (target) {
            case "dataset":
                return (T) new Dataset(this.doc, child);
            case "config":
                return (T) new Config(this.doc, child);
            default:
                throw new IllegalArgumentException("引数 target の値は dataset/config のどちらかを指定してください。");
        }
    }

    public <T extends DBChild> void setDBChild(String target, T child) {
        if (!(target.equals("dataset") || target.equals("config"))) {
            throw new IllegalArgumentException("引数 target の値は dataset/config のどちらかを指定してください。");
        }

        Node db = this.searchNodeList("/database").item(0);
        Node oldChild = this.searchNodeList("/database/" + target).item(0);
        db.removeChild(oldChild);
        db.appendChild(child.root);
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

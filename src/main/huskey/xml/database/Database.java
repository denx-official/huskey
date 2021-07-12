package xml.database;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utility.GlobalConst;
import xml.XMLOperator;
import xml.XMLWriter;

import java.io.File;

/**
 * データベース
 *
 * <p>DatabaseBuilderから取得することで、データベースの中身を操作することができる。
 * データベースはXML形式であり、その操作にはDOMとXPathを用いる。
 *
 * @author いっぺー
 * @see DatabaseBuilder
 */
public class Database extends XMLWriter {
    private final Document doc;
    private String masterKey;
    private final String fileDir;
    private final XMLOperator operator;

    public Database(Document doc, String masterKey, String fileDir) {
        this.doc = doc;
        this.masterKey = masterKey;
        this.fileDir = fileDir;
        this.operator = new XMLOperator(doc);
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
            return new String[]{""};
        }

        String[] result = new String[dbFiles.length];
        for (int i = 0; i < dbFiles.length; i++) {
            result[i] = dbFiles[i].getName();
        }

        return result;
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
     * 対象のDataのupdatedを更新
     *
     * @param target 対象のDataのタイトル
     * @author いっぺー
     */
    public void updateTime(String target) {
        this.operator.updateTime(target);
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
        return this.operator.searchNodeList(expression);
    }

    /**
     * XML文章の取得
     *
     * @return Document
     * @author いっぺー
     */
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
        return this.fileDir + ".hkdb";
    }
}

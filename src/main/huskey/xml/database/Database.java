package xml.database;

import crypt.AES;
import crypt.SHA256;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utility.BinFileIO;
import xml.XMLOperator;
import xml.XMLWriter;

import javax.crypto.Cipher;
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
    private final String dbDir;
    private final XMLOperator operator;

    public Database(Document doc, String masterKey, String dbDir) {
        this.doc = doc;
        this.masterKey = masterKey;
        this.dbDir = dbDir;
        this.operator = new XMLOperator(doc);
    }

    /**
     * 全データベースの名前を一覧で取得
     *
     * @param huskeyDir ドットフォルダーのディレクトリ
     * @return String[]
     * @author いっぺー
     */
    public static String[] getDBList(String huskeyDir) {
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

    private byte[] iv() {
        String ivPath = this.dbDir + "iv";
        BinFileIO fileIO = new BinFileIO(ivPath);
        return fileIO.readBinFile();
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
        byte[] bytesKey = SHA256.hashText(this.masterKey);
        return AES.EnDeCrypt(Cipher.ENCRYPT_MODE, bytes, bytesKey, this.iv());
    }

    @Override
    protected String getFilePath() {
        return this.dbDir + ".hkdb";
    }
}

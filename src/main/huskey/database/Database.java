package database;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import utility.GlobalConst;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * データベース
 *
 * <p>DatabaseBuilderから取得することで、データベースの中身を操作することができる。
 * データベースはXML形式であり、その操作にはDOMとXPathを用いる。
 *
 * @author いっぺー
 * @see DatabaseBuilder
 */
public class Database {
    public final Document doc;
    private final String dbName;
    private String masterKey;
    private String dbDir;

    public Database(Document doc, String dbName, String masterKey, String dbDir) {
        this.doc = doc;
        this.dbName = dbName;
        this.masterKey = masterKey;
        this.dbDir = dbDir;
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
     * データベースを暗号化して書き出し
     *
     * @author いっぺー
     */
    public void write() {
        String path = this.dbDir + ".hkdb";
        byte[] byteDB = this.xmlToBytes();

        // （データベースの暗号化処理）
        // （masterKeyのハッシュ値を書き出す処理）

        BinFileIO.writeBinFile(path, byteDB);
    }

    /**
     * XML文章をbyte[]に変換
     *
     * @return byte[]
     * @author いっぺー
     */
    private byte[] xmlToBytes() {
        StringWriter writer = new StringWriter();
        TransformerFactory factory = TransformerFactory.newInstance();

        try {
            Transformer transformer = factory.newTransformer();
            transformer.transform(new DOMSource(this.doc), new StreamResult(writer));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        return writer.toString().getBytes(StandardCharsets.UTF_8);
    }
}

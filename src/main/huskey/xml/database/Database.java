package xml.database;

import org.w3c.dom.Document;
import utility.GlobalConst;
import xml.AbsXML;
import xml.StaticXMLMethods;

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
public class Database extends AbsXML {
    private final Document doc;
    private String masterKey;
    private final String fileDir;

    public Database(Document doc, String masterKey, String fileDir) {
        this.doc = doc;
        this.masterKey = masterKey;
        this.fileDir = fileDir;
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
            StaticXMLMethods.searchNodeList(this.doc, expression);
        } catch (IllegalArgumentException _e) {
            return false;
        }

        return true;
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
        return this.fileDir + ".hkdb";
    }
}

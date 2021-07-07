package xml.database;

import org.w3c.dom.Document;
import utility.GlobalConst;
import xml.AbsXML;
import utility.BinFileIO;

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
    private String masterKey;

    public Database(Document doc, String masterKey, String fileDir) {
        super(doc, fileDir);
        this.masterKey = masterKey;
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
     * データベースを暗号化して書き出し
     *
     * @author いっぺー
     */
    public void write() {
        String path = this.fileDir + ".hkdb";
        byte[] byteDB = this.xmlToBytes();

        // （データベースの暗号化処理）
        // （masterKeyのハッシュ値を書き出す処理）

        BinFileIO fileIO = new BinFileIO(path);
        fileIO.writeBinFile(byteDB);
    }
}

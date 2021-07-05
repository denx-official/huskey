package database;

import database.config.Config;
import database.dataset.Dataset;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import utility.GlobalConst;

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
public class Database extends DBOriginSystem {
    private String masterKey;
    private final String huskeyDir;

    public Database(Document doc, String masterKey) {
        super(doc);
        this.masterKey = masterKey;
        this.huskeyDir = GlobalConst.HUSKEY_DIR;
    }

    Database(Document doc, String masterKey, String huskeyDir) {
        super(doc);
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
        Node name = this.searchNode("/database/name");
        return name.getTextContent();
    }

    /**
     * データベース名の更新
     *
     * @param newDBName 新しいデータベース名
     * @author いっぺー
     */
    public void setDBName(String newDBName) {
        Node name = this.searchNode("/database/name");
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
        Node child = this.searchNode("/database/" + target);

        switch (target) {
            case "dataset":
                return (T) new Dataset(this.doc, child);
            case "config":
                return (T) new Config(this.doc, child);
            default:
                throw new IllegalArgumentException("引数 target の値は dataset/config のどちらかを指定してください。");
        }
    }

    /**
     * データセット/コンフィグの更新
     *
     * @param target "dataset" or "config"
     * @param child DBChild及びそのサブクラス
     * @see DBChild
     * @see Dataset
     * @see Config
     * @author いっぺー
     */
    public <T extends DBChild> void setDBChild(String target, T child) {
        if (!(target.equals("dataset") || target.equals("config"))) {
            throw new IllegalArgumentException("引数 target の値は dataset/config のどちらかを指定してください。");
        }

        if ((target.equals("dataset") && child.getClass() != Dataset.class) ||
            (target.equals("config") && child.getClass() != Config.class)
        ) {
            throw new IllegalArgumentException("引数 target の内容と引数 child の型が矛盾しています。");
        }

        Node db = this.searchNode("/database");
        Node oldChild = this.searchNode("/database/" + target);
        db.removeChild(oldChild);
        db.appendChild(child.root);
    }
}

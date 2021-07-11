package xml.database;

import org.w3c.dom.Document;
import utility.GlobalConst;
import utility.BinFileIO;
import xml.StaticXMLMethods;
import xml.XMLBuilder;

import java.io.*;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Databaseインスタンスを取得するAPI
 *
 * @author いっぺー
 */
public class DatabaseBuilder extends XMLBuilder<Database> {
    private String masterKey;
    private final String dbDir;

    public DatabaseBuilder(String dbName, String masterKey) {
        this.masterKey = masterKey;
        this.dbDir = GlobalConst.HUSKEY_DIR + "/database/" + dbName + "/";
    }

    DatabaseBuilder(String dbName, String masterKey, String huskeyDir) {
        this.masterKey = masterKey;
        this.dbDir = huskeyDir + "/database/" + dbName + "/";
    }

    /**
     * masterKeyの照合
     *
     * @return boolean
     * @author いっぺー
     */
    public boolean isKeyMatched() {
        String path = this.dbDir + "hash";
        File file = Paths.get(path).toFile();

        StringBuilder hash = new StringBuilder();
        try {
            int ch;
            FileReader reader = new FileReader(file);
            while ((ch = reader.read()) != -1) {
                hash.append((char) ch);
            }
            reader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        // （masterKeyをハッシュ化する処理）

        return Objects.equals(this.masterKey, hash.toString());
    }

    /**
     * データベースの存在確認
     *
     * @return boolean
     * @author いっぺー
     */
    public boolean exists() {
        File file = Paths.get(this.hkdbPath()).toFile();
        return file.exists();
    }

    /**
     * データベースの復号に使用するmasterKeyの更新
     *
     * @param newKey 新しいmasterKey
     * @author いっぺー
     */
    public void setMasterKey(String newKey) {
        this.masterKey = newKey;
    }

    /**
     * Databaseインスタンスの構築
     *
     * @return Database
     * @author いっぺー
     */
    public Database build() {
        BinFileIO fileIO = new BinFileIO(this.hkdbPath());
        byte[] bytes = fileIO.readBinFile();

        // （復号処理）

        Document doc = StaticXMLMethods.bytesToDoc(bytes);
        return new Database(doc, this.masterKey, this.dbDir);
    }

    /**
     * データベースファイルのパスを取得
     *
     * @return String
     * @author いっぺー
     */
    private String hkdbPath() {
        return this.dbDir + ".hkdb";
    }
}

package database;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import utility.GlobalConst;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Databaseインスタンスを取得するAPI
 *
 * @author いっぺー
 */
public class DatabaseBuilder {
    private final String dbName;
    private String masterKey;
    private final String dbDir;

    public DatabaseBuilder(String dbName, String masterKey) {
        this.dbName = dbName;
        this.masterKey = masterKey;
        this.dbDir = GlobalConst.HUSKEY_DIR + "/database/";
    }

    DatabaseBuilder(String dbName, String masterKey, String huskeyDir) {
        this.dbName = dbName;
        this.masterKey = masterKey;
        this.dbDir = huskeyDir + "/database/";
    }

    /**
     * masterKeyの照合
     *
     * @return boolean
     * @author いっぺー
     */
    public boolean isKeyMatched() {
        String path = this.dbDir + this.dbName + "/" + "hash";
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
    public Database buildDatabase() {
        byte[] byteDB = FileIO.readDB(this.hkdbPath());
        InputSource src = new InputSource(new ByteArrayInputStream(byteDB));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(src);
            return new Database(doc, this.dbName, this.masterKey, this.dbDir);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * データベースファイルのパスを取得
     *
     * @return String
     * @author いっぺー
     */
    private String hkdbPath() {
        return this.dbDir + this.dbName + "/" + this.dbName + ".hkdb";
    }
}

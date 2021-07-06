package database;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import utility.GlobalConst;
import utility.HuskeyException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
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
        this.dbDir = GlobalConst.HUSKEY_DIR + "/database/" + dbName + "/";
    }

    DatabaseBuilder(String dbName, String masterKey, String huskeyDir) {
        this.dbName = dbName;
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

        return Objects.equals(this.masterKey, hash.toString());
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
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String path = this.dbDir + this.dbName + ".hkdb";
        File file = Paths.get(path).toFile();

        if (!file.exists()) {
            throw new HuskeyException("データベース " + this.dbName + " は存在しません。");
        }

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            return new Database(doc, this.masterKey, this.huskeyDir);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}

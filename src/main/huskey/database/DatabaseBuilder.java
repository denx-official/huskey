package database;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
    private final String masterKey;
    private final String dbDir;

    public DatabaseBuilder(String dbName, String masterKey, String dbDir) {
        this.dbName = dbName;
        this.masterKey = masterKey;
        this.dbDir = dbDir;
    }

    /**
     * masterKeyの照合
     *
     * @return boolean
     * @author いっぺー
     */
    public boolean isKeyMatched() {
        File file = Paths.get(this.dbDir + this.dbName + "/hash").toFile();

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
     * Databaseインスタンスの構築
     *
     * @return Database
     * @author いっぺー
     */
    public Database buildDatabase() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        File file = Paths.get(this.dbDir + this.dbName + "/" + this.dbName + ".hkdb").toFile();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            return new Database(doc, this.dbName, this.masterKey);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}

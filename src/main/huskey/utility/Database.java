package utility;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Objects;

public class Database {
    private final String dbName;
    private final String masterKey;
    private final String dbDir;

    public Database(String dbName, String masterKey, String dbDir) {
        this.dbName = dbName;
        this.masterKey = masterKey;
        this.dbDir = dbDir;
    }

    public static String[] showDBList() {
        return new String[] {"sample"};
    }

    public boolean isKeyMatched() throws FileNotFoundException {
        return Objects.equals(this.masterKey, "sample");
    }

    public Document getDataset() throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(Paths.get(this.dbDir + this.dbName).toFile());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}

package database;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public Document getDBDoc() throws FileNotFoundException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        File file = Paths.get(this.dbDir + this.dbName).toFile();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(file);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}

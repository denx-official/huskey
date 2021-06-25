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

    public Database(String dbName, String masterKey) {
        this.dbName = dbName;
        this.masterKey = masterKey;
    }

    public static String[] showDBList() {
        return new String[] {"sample"};
    }

    public boolean isKeyMatched() throws FileNotFoundException {
        return Objects.equals(this.masterKey, "sample");
    }

    public Document getDataset() throws Exception {
        try {
            String path = Database.class.getClassLoader().getResources(this.dbName).nextElement().toString();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(Paths.get(path.replace("file:/", "")).toFile());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}

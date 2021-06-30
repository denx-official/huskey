package database;

import org.w3c.dom.Document;

public class Database {
    private Document doc;
    private String dbName;
    private String masterKey;

    public Database(Document doc, String dbName, String masterKey) {
        this.doc = doc;
        this.dbName = dbName;
        this.masterKey = masterKey;
    }
}

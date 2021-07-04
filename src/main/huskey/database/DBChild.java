package database;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public abstract class DBChild {
    protected final Document doc;
    protected final Node root;

    public DBChild(Document doc, Node root) {
        this.doc = doc;
        this.root = root;
    }
}

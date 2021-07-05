package database;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class DBChild extends DBOriginSystem {
    protected final Node root;

    protected DBChild(Document doc, Node root) {
        super(doc);
        this.root = root;
    }
}

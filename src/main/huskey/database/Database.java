package database;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;

public class Database {
    private Document doc;
    private String dbName;
    private String masterKey;

    public Database(Document doc, String dbName, String masterKey) {
        this.doc = doc;
        this.dbName = dbName;
        this.masterKey = masterKey;
    }

    public Dataset useDataset() {
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expr = xpath.compile("/database/dataset");
            NodeList dataset = (NodeList) expr.evaluate(this.doc, XPathConstants.NODESET);
            Node root = dataset.item(0);
            return new Dataset(this.doc, root);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }
}

package database;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.io.File;

/**
 * データベース
 *
 * <p>DatabaseBuilderから取得することで、データベースの中身を操作することができる。
 * データベースはXML形式であるが、Database/Dataset/Configクラスではその具体的なDOM操作を隠蔽する。
 *
 * @see DatabaseBuilder
 * @author いっぺー
 */
public class Database {
    private Document doc;
    private String dbName;
    private String masterKey;

    public Database(Document doc, String dbName, String masterKey) {
        this.doc = doc;
        this.dbName = dbName;
        this.masterKey = masterKey;
    }

    /**
     * 全データベースの名前を一覧で取得
     *
     * @return String[]
     * @author いっぺー
     */
    public static String[] getDBList(String dbDir) {
        File[] dbFiles = new File(dbDir).listFiles();
        if (dbFiles == null) {
            return new String[] {""};
        }

        String[] result = new String[dbFiles.length];
        for (int i = 0; i < dbFiles.length; i++) {
            result[i] = dbFiles[i].getName();
        }

        return result;
    }

    /**
     * データセットの取得
     *
     * <p>データベースからデータセットを取得する。
     *
     * @return Dataset
     * @author いっぺー
     */
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

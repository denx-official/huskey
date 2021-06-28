package utility;

import org.junit.jupiter.api.*;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database db;
    private final String dbName = "sample";
    private final String masterKey = "sample";
    private final String dbDir = "./target/test-classes/resources/database/";

    @Test
    void データベース名のリストを取得() {
        String[] dbList = Database.showDBList();
        String[] expect = {dbName};
        assertArrayEquals(expect, dbList);
    }

    @Nested
    class isKeyMatched {
        @Test
        void masterKeyの照合_true() throws FileNotFoundException {
            db = new Database(dbName, masterKey, dbDir);
            assertTrue(db.isKeyMatched());
        }

        @Test
        void masterKeyの照合_false() throws FileNotFoundException {
            db = new Database(dbName, "sanple", dbDir);
            assertFalse(db.isKeyMatched());
        }
    }

    @Nested
    class getDataset {
        @Test
        void データセットの取得() throws Exception {
            db = new Database(dbName, masterKey, dbDir);
            String result = xmlToString(db.getDataset());
            String expect = xmlToString(createExpectDoc());
            assertEquals(expect, result);
        }

        Document createExpectDoc() throws Exception {
            try {
                String path = Database.class.getClassLoader()
                    .getResources("expect.xml")
                    .nextElement()
                    .toString();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                return builder.parse(Paths.get(path.replace("file:/", "")).toFile());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }

        String xmlToString(Document doc) throws TransformerException {
            StringWriter writer = new StringWriter();
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.toString();
        }
    }

    @Nested
    class 異常系 {
        @BeforeEach
        void setup() {
            db = new Database(dbName, masterKey, "./hoge/");
        }

        @Test
        void データベースが存在しなかった場合() {
            assertThrows(FileNotFoundException.class, db::getDataset);
        }

        @Test
        void ハッシュ化したファイルが存在しなかった場合() {
            assertThrows(FileNotFoundException.class, db::isKeyMatched);
        }
    }
}
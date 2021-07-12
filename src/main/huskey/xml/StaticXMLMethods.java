package xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class StaticXMLMethods {
    /**
     * XML文章をbyte[]に変換
     *
     * @return byte[]
     * @author いっぺー
     */
    public static byte[] xmlToBytes(Document doc) {
        StringWriter writer = new StringWriter();
        TransformerFactory factory = TransformerFactory.newInstance();

        try {
            Transformer transformer = factory.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        return writer.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * バイト列のXML文章をDocumentに変換する
     *
     * @param bytes バイト列のXML文章
     * @return Document
     * @author いっぺー
     */
    public static Document bytesToDoc(byte[] bytes) {
        InputSource src = new InputSource(new ByteArrayInputStream(bytes));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(src);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}

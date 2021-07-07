package xml;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * AbsXMLのサブクラスのインスタンスを取得するAPIの抽象クラス
 *
 * @author いっぺー
 * @see XMLBuilder
 */
public abstract class XMLBuilder<T extends AbsXML> {

    /**
     * バイト列のXML文章をDocumentに変換する
     *
     * @param bytes バイト列のXML文章
     * @return Document
     * @author いっぺー
     */
    protected Document bytesToDoc(byte[] bytes) {
        InputSource src = new InputSource(new ByteArrayInputStream(bytes));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(src);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * T (extends AbsXML) インスタンスの構築
     *
     * @return T (extends AbsXML)
     * @author いっぺー
     */
    abstract public T build();
}

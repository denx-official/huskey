package xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public abstract class AbsXML {
    public final Document doc;
    protected final String fileDir;

    public AbsXML(Document doc, String fileDir) {
        this.doc = doc;
        this.fileDir = fileDir;
    }

    /**
     * ノードの検索
     *
     * <p>XPathを用いてDocumentを検索し、条件に一致したノードをNodeListで取得する。
     *
     * @param expression 検索条件
     * @return NodeList
     * @author いっぺー
     */
    public NodeList searchNodeList(String expression) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expr = xpath.compile(expression);
            NodeList nodeList = (NodeList) expr.evaluate(this.doc, XPathConstants.NODESET);

            if (nodeList.getLength() == 0) {
                throw new IllegalArgumentException("該当するノードが存在しません。");
            }

            return nodeList;
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * XML文章をbyte[]に変換
     *
     * @return byte[]
     * @author いっぺー
     */
    protected byte[] xmlToBytes() {
        StringWriter writer = new StringWriter();
        TransformerFactory factory = TransformerFactory.newInstance();

        try {
            Transformer transformer = factory.newTransformer();
            transformer.transform(new DOMSource(this.doc), new StreamResult(writer));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        return writer.toString().getBytes(StandardCharsets.UTF_8);
    }

    abstract public void write();
}

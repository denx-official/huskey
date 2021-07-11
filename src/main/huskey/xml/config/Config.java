package xml.config;

import xml.AbsXML;
import org.w3c.dom.Document;

/**
 * config.xmlにアクセスするためのクラス
 *
 * @author いっぺー
 * @see AbsXML
 */
public class Config extends AbsXML {
    private final Document doc;
    private final String fileDir;

    public Config(Document doc, String fileDir) {
        this.doc = doc;
        this.fileDir = fileDir;
    }

    @Override
    public Document getDoc() {
        return this.doc;
    }

    @Override
    protected byte[] encrypt(byte[] bytes) {
        return bytes;
    }

    @Override
    protected String getFilePath() {
        return this.fileDir + "config.xml";
    }
}

package xml.config;

import xml.AbsXML;
import utility.BinFileIO;
import org.w3c.dom.Document;
import xml.StaticXMLMethods;

/**
 * config.xmlにアクセスするためのクラス
 *
 * @author いっぺー
 * @see AbsXML
 */
public class Config extends AbsXML {
    public Config(Document doc, String fileDir) {
        super(doc, fileDir);
    }

    /**
     * config.xmlの書き出し
     *
     * @author いっぺー
     */
    public void write() {
        String path = this.fileDir + "config.xml";
        byte[] byteDB = StaticXMLMethods.xmlToBytes(this.doc);

        BinFileIO fileIO = new BinFileIO(path);
        fileIO.writeBinFile(byteDB);
    }
}

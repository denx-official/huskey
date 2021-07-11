package xml;

import org.w3c.dom.Document;
import utility.BinFileIO;

/**
 * XMLファイルの情報を出力する機能を持った抽象クラス
 *
 * @author いっぺー
 * @see xml.config.Config
 * @see xml.database.Database
 */
public abstract class XMLWriter {
    abstract protected Document getDoc();

    abstract protected byte[] encrypt(byte[] bytes);

    abstract protected String getFilePath();

    /**
     * Documentの書き出し
     *
     * @author いっぺー
     */
    public void write() {
        byte[] plainXML = StaticXMLMethods.xmlToBytes(this.getDoc());
        byte[] cipherXML = this.encrypt(plainXML);

        String path = this.getFilePath();
        BinFileIO fileIO = new BinFileIO(path);
        fileIO.writeBinFile(cipherXML);
    }
}

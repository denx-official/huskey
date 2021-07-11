package xml;

import org.w3c.dom.Document;
import utility.BinFileIO;

/**
 * Documentの保持やファイルの書き出し機能を定義した抽象クラス
 *
 * @author いっぺー
 * @see xml.config.Config
 * @see xml.database.Database
 */
public abstract class AbsXML {
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

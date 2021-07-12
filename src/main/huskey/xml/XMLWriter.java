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
    /**
     * XML文章の取得
     *
     * @return Document
     * @author いっぺー
     */
    abstract protected Document getDoc();

    /**
     * XML文章の暗号化
     *
     * <p>暗号化の必要が無ければ、受け取った引数をそのまま返すのみの処理でOK。
     *
     * @param bytes 平文のXML文章
     * @return byte[]
     * @author いっぺー
     */
    abstract protected byte[] encrypt(byte[] bytes);

    /**
     * 書き出すXMLファイルのパスを返すメソッド
     *
     * @return String
     * @author いっぺー
     */
    abstract protected String getFilePath();

    /**
     * Documentの書き出し
     *
     * @author いっぺー
     */
    public void write() {
        byte[] plainXML = XMLParser.xmlToBytes(this.getDoc());
        byte[] cipherXML = this.encrypt(plainXML);

        String path = this.getFilePath();
        BinFileIO fileIO = new BinFileIO(path);
        fileIO.writeBinFile(cipherXML);
    }
}

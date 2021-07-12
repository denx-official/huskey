package xml;

import org.w3c.dom.Document;
import utility.BinFileIO;

/**
 * XMLWriterのサブクラスのインスタンスを取得するAPIの抽象クラス
 *
 * @author いっぺー
 * @see XMLBuilder
 */
public abstract class XMLBuilder<T extends XMLWriter> {
    /**
     * XML文章の復号
     *
     * <p>復号の必要が無ければ、受け取った引数をそのまま返すのみの処理でOK。
     *
     * @param bytes 暗号化されたXML文章
     * @return byte[]
     * @author いっぺー
     */
    abstract protected byte[] decrypt(byte[] bytes);

    /**
     * 読み込むXMLファイルのパスを返すメソッド
     *
     * @return String
     * @author いっぺー
     */
    abstract protected String getFilePath();

    /**
     * XMLWriterのサブクラスのインスタンスを返すメソッド
     *
     * @param doc XML文章
     * @return T (extends XMLWriter)
     * @author いっぺー
     */
    abstract protected T returnNewInstance(Document doc);

    /**
     * T (extends XMLWriter) インスタンスの構築
     *
     * @return T (extends XMLWriter)
     * @author いっぺー
     */
    public T build() {
        String path = this.getFilePath();
        BinFileIO fileIO = new BinFileIO(path);

        byte[] cipherXML = fileIO.readBinFile();
        byte[] plainXML = this.decrypt(cipherXML);

        Document doc = XMLParser.bytesToDoc(plainXML);
        return this.returnNewInstance(doc);
    }
}

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
    abstract protected byte[] decrypt(byte[] bytes);

    abstract protected String getFilePath();

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

        Document doc = StaticXMLMethods.bytesToDoc(plainXML);
        return this.returnNewInstance(doc);
    }
}

package xml;

import org.w3c.dom.Document;
import utility.BinFileIO;

/**
 * AbsXMLのサブクラスのインスタンスを取得するAPIの抽象クラス
 *
 * @author いっぺー
 * @see XMLBuilder
 */
public abstract class XMLBuilder<T extends AbsXML> {
    abstract protected byte[] decrypt(byte[] bytes);

    abstract protected String getFilePath();

    abstract protected T returnNewInstance(Document doc);

    /**
     * T (extends AbsXML) インスタンスの構築
     *
     * @return T (extends AbsXML)
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

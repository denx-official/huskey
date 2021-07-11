package xml;

import org.w3c.dom.Document;

/**
 * Documentの保持やファイルの書き出し機能を定義した抽象クラス
 *
 * @author いっぺー
 * @see xml.config.Config
 * @see xml.database.Database
 */
public abstract class AbsXML {
    public final Document doc;
    protected final String fileDir;

    public AbsXML(Document doc, String fileDir) {
        this.doc = doc;
        this.fileDir = fileDir;
    }

    /**
     * Documentの書き出し
     *
     * @author いっぺー
     */
    abstract public void write();
}

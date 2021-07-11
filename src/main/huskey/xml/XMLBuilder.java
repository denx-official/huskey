package xml;

/**
 * AbsXMLのサブクラスのインスタンスを取得するAPIの抽象クラス
 *
 * @author いっぺー
 * @see XMLBuilder
 */
public abstract class XMLBuilder<T extends AbsXML> {
    /**
     * T (extends AbsXML) インスタンスの構築
     *
     * @return T (extends AbsXML)
     * @author いっぺー
     */
    abstract public T build();
}

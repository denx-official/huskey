package database.dataset;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 各データに対して指定した処理を実行するためのクラス
 *
 * <p>データセット内にある各データに対して、processメソッドで処理を行う。
 * 全データに対して処理が終了した際にafterAllメソッドを実行し、指定した型の値を返す。
 *
 * @see Dataset
 * @author いっぺー
 */
abstract class ProcessEachData<T> {
    private boolean isStop = false;
    protected final Node root;

    ProcessEachData(Node root) {
        this.root = root;
    }

    /**
     * 各データに対して処理を実行
     *
     * @return T
     * @author いっぺー
     */
    public T run() {
        NodeList nodeList = this.root.getChildNodes();

        for (int i = nodeList.getLength() - 1; 0 <= i; i--) {
            Element dataElem = (Element) nodeList.item(i);
            Data data = Data.newInstanceByElement(dataElem);
            this.process(data, dataElem, i);

            if (this.isStop) { break; }
        }

        return this.afterAll();
    }

    /**
     * 各データに対する処理
     *
     * @param data Data型のデータ
     * @param dataElem Element型のデータ
     * @param i データのインデックス
     * @author いっぺー
     */
    abstract public void process(Data data, Element dataElem, int i);

    /**
     * 全処理が終了したときの処理
     *
     * <p>戻り値はクラスを宣言した際に指定した型となる。
     *
     * @author いっぺー
     */
    abstract public T afterAll();

    /**
     * processのループ処理を停止する
     *
     * <p>各データに対するループ処理を中断し、afterAllメソッドの実行へ移行する。
     *
     * @author いっぺー
     */
    public void stopProcessing() { this.isStop = true; }
}

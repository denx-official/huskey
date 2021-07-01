package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * データセット
 *
 * <p>Databaseから取得することで、データセットの中身を操作することができる。
 *
 * @see Database
 * @author いっぺー
 */
public class Dataset {
    private final Document doc;
    private final Node root;

    public Dataset(Document doc, Node root) {
        this.doc = doc;
        this.root = root;
    }

    /**
     * データが存在するか否か
     *
     * <p>データセット内に入力した名前のデータが存在するかをチェックする。
     *
     * @return boolean
     * @author いっぺー
     */
    public boolean isDataExist(String target) {
        return this.processEachDataElem(new Callback<Boolean>() {
            boolean result = false;

            @Override
            public void method(HkElement dataHkElem, int i) {
                if (dataHkElem.isTitleAttrEqualTo(target)) {
                    result = true;
                }
            }

            @Override
            public Boolean afterAll() {
                return result;
            }
        });
    }

    /**
     * データセット内にある全データの名前を取得
     *
     * @return String[]
     * @author いっぺー
     */
    public String[] getDataList() {
        int len = this.root.getChildNodes().getLength();

        return this.processEachDataElem(new Callback<String[]>() {
            final String[] result = new String[len];

            @Override
            public void method(HkElement dataHkElem, int i) {
                result[i] = dataHkElem.toElement().getAttribute("title");
            }

            @Override
            public String[] afterAll() { return result; }
        });
    }

    /**
     * データセットから対象のデータを取得
     *
     * @return Data
     * @exception IllegalArgumentException データがデータセット内に存在しなかった場合。
     * @author いっぺー
     */
    public Data useData(String target) throws IllegalArgumentException {
        return this.processEachDataElem(new Callback<Data>() {
            Data result;

            @Override
            public void method(HkElement dataHkElem, int _i) {
                if (dataHkElem.isTitleAttrEqualTo(target)) {
                    result = dataHkElem.toData();
                }
            }

            @Override
            public Data afterAll() {
                if (result == null) {
                    throw new IllegalArgumentException("データ " + target + " は存在しません。");
                }
                return result;
            }
        });
    }

    /**
     * データセットから対象のデータを削除
     *
     * @author いっぺー
     */
    public void removeData(String target) {
        this.processEachDataElem(new Callback<Integer>() {
            int status = 1;

            @Override
            public void method(HkElement dataHkElem, int _i) {
                if (dataHkElem.isTitleAttrEqualTo(target)) {
                    root.removeChild(dataHkElem.toElement());
                    status = 0;
                }
            }

            @Override
            public Integer afterAll() {
                if (status == 1) {
                    throw new IllegalArgumentException("データ " + target + " は存在しません。");
                }
                return status;
            }
        });
    }

    /**
     * データの追加/更新
     *
     * <p>targetで指定したデータを、新しく引数に取ったデータで上書きする。
     * targetと同一名のデータがデータセット内に存在しなかった場合、データを新規作成する。
     *
     * @param target 対象のデータ名
     * @param data 新規データ
     * @author いっぺー
     */
    public void setData(String target, Data data) {
        try {
            this.removeData(target);
        } catch (IllegalArgumentException _e) {
            // do nothing
        }

        this.root.appendChild(data.toElement(this.doc));
    }

    /**
     * processEachDataElemに渡すコールバック関数のインターフェース
     *
     * @author いっぺー
     */
    private interface Callback<T> {
        /**
         * 各データに対する処理
         *
         * @param dataHkElem データ
         * @param i データのインデックス
         * @author いっぺー
         */
        void method(HkElement dataHkElem, int i);

        /**
         * method終了時の処理
         *
         * <p>戻り値はCallbackを宣言した際に指定した型となる。
         *
         * @author いっぺー
         */
        T afterAll();
    }

    /**
     * 各データに対して指定した処理を実行
     *
     * <p>データセット内にある各データに対して、引数に渡したCallback.methodで処理を行う。
     * 各メソッドが終了した際にCallback.afterAllを実行し、指定された型の値を返す。
     *
     * @param callback 各データに対する処理と、処理終了後に実行する関数
     * @author いっぺー
     */
    private <T> T processEachDataElem(Callback<T> callback) {
        NodeList nodeList = this.root.getChildNodes();
        for (int i = nodeList.getLength() - 1; i >= 0; i--) {
            Element elem = (Element) nodeList.item(i);
            callback.method(new HkElement(elem), i);
        }
        return callback.afterAll();
    }
}

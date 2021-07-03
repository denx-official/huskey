package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import utility.HuskeyException;

/**
 * データセット
 *
 * <p>Databaseから取得することで、データセットの中身を操作することができる。
 *
 * @see Database
 * @see ProcessEachData
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
        ProcessEachData<Boolean> process = new ProcessEachData<Boolean>(this.root) {
            boolean status = false;

            @Override
            public void process(Data data, Element _dataElem, int _i) {
                if (data.getText("title").equals(target)) {
                    status = true;
                    this.stopProcessing();
                }
            }

            @Override
            public Boolean afterAll() { return status; }
        };

        return process.run();
    }

    /**
     * データセット内にある全データの名前を取得
     *
     * @return String[]
     * @author いっぺー
     */
    public String[] getDataList() {
        ProcessEachData<String[]> process = new ProcessEachData<String[]>(this.root) {
            final int len = this.root.getChildNodes().getLength();
            final String[] result = new String[len];

            @Override
            public void process(Data data, Element _dataElem, int i) {
                result[i] = data.toElement(doc).getAttribute("title");
            }

            @Override
            public String[] afterAll() { return result; }
        };

        return process.run();
    }

    /**
     * データセットから対象のデータを取得
     *
     * @return Data
     * @exception IllegalArgumentException データがデータセット内に存在しなかった場合。
     * @author いっぺー
     */
    public Data useData(String target) {
        ProcessEachData<Data> process = new ProcessEachData<Data>(this.root) {
            Data result;

            @Override
            public void process(Data data, Element _dataElem, int _i) {
                if (data.getText("title").equals(target)) {
                    result = data;
                    this.stopProcessing();
                }
            }

            @Override
            public Data afterAll() {
                if (result == null) {
                    throw new HuskeyException("データ " + target + " は存在しません。");
                }
                return result;
            }
        };

        return process.run();
    }

    /**
     * データセットから対象のデータを削除
     *
     * @exception IllegalArgumentException データがデータセット内に存在しなかった場合。
     * @author いっぺー
     */
    public void removeData(String target) {
        ProcessEachData<Integer> process = new ProcessEachData<Integer>(this.root) {
            int status = 1;

            @Override
            public void process(Data data, Element dataElem, int _i) {
                if (data.getText("title").equals(target)) {
                    root.removeChild(dataElem);
                    status = 0;
                    this.stopProcessing();
                }
            }

            @Override
            public Integer afterAll() {
                if (status == 1) {
                    throw new HuskeyException("データ " + target + " は存在しません。");
                }
                return status;
            }
        };

        process.run();
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
        } catch (HuskeyException _e) {
            // do nothing
        }

        this.root.appendChild(data.toElement(this.doc));
    }
}

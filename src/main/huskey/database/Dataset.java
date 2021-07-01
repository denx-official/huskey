package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Dataset {
    private final Document doc;
    private final Node root;

    public Dataset(Document doc, Node root) {
        this.doc = doc;
        this.root = root;
    }

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

    public void setData(String target, Data data) {
        try {
            this.removeData(target);
        } catch (IllegalArgumentException _e) {
            // do nothing
        }

        this.root.appendChild(data.toElement(this.doc));
    }

    private interface Callback<T> {
        void method(HkElement dataHkElem, int i);
        T afterAll();
    }

    private <T> T processEachDataElem(Callback<T> callback) {
        NodeList nodeList = this.root.getChildNodes();
        for (int i = nodeList.getLength() - 1; i >= 0; i--) {
            Element elem = (Element) nodeList.item(i);
            callback.method(new HkElement(elem), i);
        }
        return callback.afterAll();
    }
}

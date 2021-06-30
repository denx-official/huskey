package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Objects;

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
            public void method(Element dataElem, int i) {
                String title = dataElem.getAttribute("title");
                if (target.equals(title)) {
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
            public void method(Element dataElem, int i) {
                result[i] = dataElem.getAttribute("title");
            }

            @Override
            public String[] afterAll() { return result; }
        });
    }

    public Data useData(String target) throws IllegalArgumentException {
        return this.processEachDataElem(new Callback<Data>() {
            Data result;

            @Override
            public void method(Element dataElem, int _i) {
                String title = dataElem.getAttribute("title");
                if (Objects.equals(title, target)) {
                    result = createData(dataElem);
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
            public void method(Element dataElem, int _i) {
                String title = dataElem.getAttribute("title");
                if (Objects.equals(title, target)) {
                    root.removeChild(dataElem);
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

    private <T> T processEachDataElem(Callback<T> callback) {
        NodeList nodeList = this.root.getChildNodes();
        for (int i = nodeList.getLength() - 1; i >= 0; i--) {
            Element elem = (Element) nodeList.item(i);
            callback.method(elem, i);
        }
        return callback.afterAll();
    }

    private Data createData(Element data) {
        Element createdElem = (Element) data.getElementsByTagName("created").item(0);
        Element updatedElem = (Element) data.getElementsByTagName("updated").item(0);
        return new Data(
            data.getAttribute("title"),
            data.getElementsByTagName("userName").item(0).getTextContent(),
            data.getElementsByTagName("password").item(0).getTextContent(),
            data.getElementsByTagName("message").item(0).getTextContent(),
            this.elementToHkTime(createdElem),
            this.elementToHkTime(updatedElem)
        );
    }

    private HkTime elementToHkTime(Element elem) {
        return new HkTime(
            Integer.parseInt(elem.getAttribute("year")),
            Integer.parseInt(elem.getAttribute("month")),
            Integer.parseInt(elem.getAttribute("date")),
            Integer.parseInt(elem.getAttribute("hours")),
            Integer.parseInt(elem.getAttribute("minutes")),
            Integer.parseInt(elem.getAttribute("seconds"))
        );
    }

    private interface Callback<T> {
        void method(Element dataElem, int i);
        T afterAll();
    }
}

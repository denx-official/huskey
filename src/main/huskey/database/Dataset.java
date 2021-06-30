package database;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Objects;

public class Dataset {
    private Node root;

    public Dataset(Node root) {
        this.root = root;
    }

    public String[] getDataList() {
        NodeList nodeList = this.root.getChildNodes();
        String[] dataList = new String[nodeList.getLength()];
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element data = (Element) nodeList.item(i);
            dataList[i] = data.getAttribute("title");
        }
        return dataList;
    }

    public Data useData(String target) throws IllegalArgumentException {
        NodeList nodeList = this.root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element data = (Element) nodeList.item(i);
            String title = data.getAttribute("title");
            if (Objects.equals(title, target)) {
                return createData(data);
            }
        }

        throw new IllegalArgumentException(target + " の名前を持つデータが存在しません。");
    }

    private Data createData(Element data) {
        Element createdElem = (Element) data.getElementsByTagName("created").item(0);
        Element updatedElem = (Element) data.getElementsByTagName("updated").item(0);
        return new Data(
            data.getAttribute("title"),
            data.getElementsByTagName("userName").item(0).getTextContent(),
            data.getElementsByTagName("password").item(0).getTextContent(),
            data.getElementsByTagName("message").item(0).getTextContent(),
            elementToHkTime(createdElem),
            elementToHkTime(updatedElem)
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
}

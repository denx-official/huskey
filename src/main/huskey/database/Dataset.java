package database;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Dataset {
    private NodeList nodeList;

    public Dataset(NodeList nodeList) {
        this.nodeList = nodeList;
    }

    public String[] getDataList() {
        String[] dataList = new String[nodeList.getLength()];
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element data = (Element) nodeList.item(i);
            dataList[i] = data.getAttribute("title");
        }
        return dataList;
    }
}

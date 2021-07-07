package xml.conf;

public enum ConfType {
    globalConf("globalConf.xml"),
    initialConf("initialConf.xml");

    public String fileName;

    ConfType(String fileName) {
        this.fileName = fileName;
    }
}

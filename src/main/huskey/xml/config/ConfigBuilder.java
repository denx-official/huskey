package xml.config;

import utility.BinFileIO;
import xml.XMLBuilder;
import org.w3c.dom.Document;
import utility.GlobalConst;

public class ConfigBuilder extends XMLBuilder<Config> {
    private final String huskeyDir;

    public ConfigBuilder() {
        this.huskeyDir = GlobalConst.HUSKEY_DIR;
    }

    ConfigBuilder(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    public Config build() {
        String path = this.huskeyDir + "config.xml";
        BinFileIO fileIO = new BinFileIO(path);
        byte[] bytes = fileIO.readBinFile();

        Document doc = this.bytesToDoc(bytes);
        return new Config(doc, this.huskeyDir);
    }
}

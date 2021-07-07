package xml.config;

import xml.AbsXML;
import utility.BinFileIO;
import org.w3c.dom.Document;

public class Config extends AbsXML {
    public Config(Document doc, String fileDir) {
        super(doc, fileDir);
    }

    public void write() {
        String path = this.fileDir + "config.xml";
        byte[] byteDB = this.xmlToBytes();

        BinFileIO fileIO = new BinFileIO(path);
        fileIO.writeBinFile(byteDB);
    }
}

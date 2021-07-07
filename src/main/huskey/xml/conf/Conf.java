package xml.conf;

import xml.AbsXML;
import utility.BinFileIO;
import org.w3c.dom.Document;

public class Conf extends AbsXML {
    private final ConfType type;
    private final String huskeyDir;

    public Conf(Document doc, ConfType type, String huskeyDir) {
        super(doc);
        this.type = type;
        this.huskeyDir = huskeyDir;
    }

    public void write() {
        String path = this.huskeyDir + this.type.fileName;
        byte[] byteDB = this.xmlToBytes();

        BinFileIO fileIO = new BinFileIO(path);
        fileIO.writeBinFile(byteDB);
    }
}

package xml.conf;

import xml.AbsXML;
import utility.BinFileIO;
import org.w3c.dom.Document;

public class Conf extends AbsXML {
    private final ConfType type;

    public Conf(Document doc, ConfType type, String fileDir) {
        super(doc, fileDir);
        this.type = type;
    }

    public void write() {
        String path = this.fileDir + this.type.fileName;
        byte[] byteDB = this.xmlToBytes();

        BinFileIO fileIO = new BinFileIO(path);
        fileIO.writeBinFile(byteDB);
    }
}

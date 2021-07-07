package database.conf;

import database.BinFileIO;
import database.XMLBuilder;
import org.w3c.dom.Document;
import utility.GlobalConst;

public class ConfBuilder extends XMLBuilder<Conf> {
    private final ConfType type;
    private final String huskeyDir;

    public ConfBuilder(ConfType target) {
        this.type = target;
        this.huskeyDir = GlobalConst.HUSKEY_DIR;
    }

    ConfBuilder(ConfType target, String huskeyDir) {
        this.type = target;
        this.huskeyDir = huskeyDir;
    }

    public Conf build() {
        String path = this.huskeyDir + this.type.fileName;
        BinFileIO fileIO = new BinFileIO(path);
        byte[] bytes = fileIO.readBinFile();

        Document doc = this.bytesToDoc(bytes);
        return new Conf(doc, this.type, this.huskeyDir);
    }
}

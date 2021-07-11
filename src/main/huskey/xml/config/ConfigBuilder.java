package xml.config;

import xml.XMLBuilder;
import org.w3c.dom.Document;
import utility.GlobalConst;

/**
 * Configインスタンスを取得するためのAPI
 *
 * @author いっぺー
 * @see XMLBuilder
 */
public class ConfigBuilder extends XMLBuilder<Config> {
    private final String huskeyDir;

    public ConfigBuilder() {
        this.huskeyDir = GlobalConst.HUSKEY_DIR;
    }

    ConfigBuilder(String huskeyDir) {
        this.huskeyDir = huskeyDir;
    }

    @Override
    protected byte[] decrypt(byte[] bytes) {
        return bytes;
    }

    @Override
    protected String getFilePath() {
        return this.huskeyDir + "config.xml";
    }

    @Override
    protected Config returnNewInstance(Document doc) {
        return new Config(doc, this.huskeyDir);
    }
}

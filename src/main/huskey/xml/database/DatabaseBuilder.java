package xml.database;

import org.w3c.dom.Document;
import xml.XMLBuilder;

import java.io.*;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Databaseインスタンスを取得するAPI
 *
 * @author いっぺー
 */
public class DatabaseBuilder extends XMLBuilder<Database> {
    private String masterKey;
    private final String dbDir;

    public DatabaseBuilder(String dbName, String masterKey, String huskeyDir) {
        this.masterKey = masterKey;
        this.dbDir = huskeyDir + "/database/" + dbName + "/";
    }

    /**
     * masterKeyの照合
     *
     * @return boolean
     * @author いっぺー
     */
    public boolean isKeyMatched() {
        String path = this.dbDir + "hash";
        File file = Paths.get(path).toFile();

        StringBuilder hash = new StringBuilder();
        try {
            int ch;
            FileReader reader = new FileReader(file);
            while ((ch = reader.read()) != -1) {
                hash.append((char) ch);
            }
            reader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        // （masterKeyをハッシュ化する処理）

        return Objects.equals(this.masterKey, hash.toString());
    }

    /**
     * データベースの存在確認
     *
     * @return boolean
     * @author いっぺー
     */
    public boolean exists() {
        File file = Paths.get(this.getFilePath()).toFile();
        return file.exists();
    }

    /**
     * データベースの復号に使用するmasterKeyの更新
     *
     * @param newKey 新しいmasterKey
     * @author いっぺー
     */
    public void setMasterKey(String newKey) {
        this.masterKey = newKey;
    }

    @Override
    protected byte[] decrypt(byte[] bytes) {
        return bytes;
    }

    @Override
    protected String getFilePath() {
        return this.dbDir + ".hkdb";
    }

    @Override
    protected Database returnNewInstance(Document doc) {
        return new Database(doc, this.masterKey, this.dbDir);
    }
}

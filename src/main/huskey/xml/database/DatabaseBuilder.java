package xml.database;

import crypt.AES;
import crypt.SHA256;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.w3c.dom.Document;
import utility.BinFileIO;
import utility.StrFileIO;
import xml.XMLBuilder;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.file.Paths;

/**
 * Databaseインスタンスを取得するAPI
 *
 * @author いっぺー
 */
public class DatabaseBuilder extends XMLBuilder<Database> {
    public final String dbName;
    private final String masterKey;
    private final String dbDir;

    public DatabaseBuilder(String dbName, String masterKey, String huskeyDir) {
        this.dbName = dbName;
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

        StrFileIO io = new StrFileIO(path);
        String hash = io.readStrFile();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(this.masterKey, hash);
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
     * データベースから初期化ベクトルの読み込み
     *
     * @return byte[] 初期化ベクトル
     * @author いっぺー
     */
    private byte[] iv() {
        String ivPath = this.dbDir + "iv";
        BinFileIO fileIO = new BinFileIO(ivPath);
        return fileIO.readBinFile();
    }

    @Override
    protected byte[] decrypt(byte[] bytes) {
        byte[] bytesKey = SHA256.hashText(this.masterKey);
        return AES.EnDeCrypt(Cipher.DECRYPT_MODE, bytes, bytesKey, this.iv());
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

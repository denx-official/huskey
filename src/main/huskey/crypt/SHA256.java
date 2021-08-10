package crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256によるハッシュ化処理を行うクラス
 *
 * @author いっぺー
 */
public class SHA256 {

    /**
     * ハッシュ値計算
     *
     * @return byte[] バイト列のハッシュ値
     * @author いっぺー
     */
    public static byte[] hashText(String text) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

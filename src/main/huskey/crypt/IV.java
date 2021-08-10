package crypt;

import java.security.SecureRandom;

/**
 * AES-GCMを用いる初期化ベクトルを生成するクラス
 *
 * @author いっぺー
 */
public class IV {
    private static final int ivLen = 12;

    /**
     * 初期化ベクトルの生成
     *
     * <p>初期化ベクトルはオリジナルのものを用いる必要があるため、SecureRandomを用いてバイト列を生成している。
     *
     * @return byte[] 初期化ベクトル
     * @author いっぺー
     */
    public static byte[] generateIV() {
        byte[] iv = new byte[ivLen];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }
}

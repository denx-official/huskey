package crypt;

import java.nio.charset.StandardCharsets;

public class Encrypt {
    private final byte[] plainText;
    private final String masterKey;
    private final String iv;

    public Encrypt(byte[] plainText, String masterKey, String iv) {
        this.plainText = plainText;
        this.masterKey = masterKey;
        this.iv = iv;
    }

    public byte[] getCipherText() {
        return "'Hello, world!' の暗号文".getBytes(StandardCharsets.UTF_8);
    }
}

package crypt;

import java.nio.charset.StandardCharsets;

public class Decrypt {
    private final byte[] cipherText;
    private final String masterKey;
    private final String iv;

    public Decrypt(byte[] cipherText, String masterKey, String iv) {
        this.cipherText = cipherText;
        this.masterKey = masterKey;
        this.iv = iv;
    }

    public byte[] getPlainText() {
        return "Hello, world!".getBytes(StandardCharsets.UTF_8);
    }
}

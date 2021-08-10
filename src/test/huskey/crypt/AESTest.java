package crypt;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class AESTest {
    byte[] plainText = "Hello, world!".getBytes(StandardCharsets.UTF_8);
    byte[] masterKey = SHA256.hashText("sample");

    @Test
    void テキストの暗号化_復号() {
        byte[] iv = IV.generateIV();

        byte[] cipherText = AES.EnDeCrypt(Cipher.ENCRYPT_MODE, plainText, masterKey, iv);
        byte[] result = AES.EnDeCrypt(Cipher.DECRYPT_MODE, cipherText, masterKey, iv);

        assertArrayEquals(plainText, result);
    }
}
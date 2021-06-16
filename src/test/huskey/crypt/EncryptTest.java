package crypt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class EncryptTest {
    private Encrypt encrypt;
    byte[] plainText = "Hello, world!".getBytes(StandardCharsets.UTF_8);

    @AfterEach
    void init() {
        encrypt = null;
    }

    @Test
    void 暗号化() {
        // 雰囲気としてこんな感じに使いたい。
        String iv = "something";
        String masterKey = "0123456789";

        encrypt = new Encrypt(plainText, masterKey, iv);
        String cipherText = new String(encrypt.getCipherText());

        assertEquals("'Hello, world!' の暗号文", cipherText);
    }
}
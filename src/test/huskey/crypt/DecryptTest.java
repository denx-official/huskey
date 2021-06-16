package crypt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class DecryptTest {
    private Decrypt decrypt;
    byte[] cipherText = "'Hello, world!' の暗号文".getBytes(StandardCharsets.UTF_8);

    @AfterEach
    void init() {
        decrypt = null;
    }

    @Test
    void 復号() {
        // 雰囲気としてこんな感じに使いたい。
        String iv = "something";
        String masterKey = "0123456789";

        decrypt = new Decrypt(cipherText, masterKey, iv);
        String plainText = new String(decrypt.getPlainText());

        assertEquals("Hello, world!", plainText);
    }
}
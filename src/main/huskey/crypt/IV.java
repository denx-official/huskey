package crypt;

import java.security.SecureRandom;

public class IV {
    private static final int ivLen = 12;

    public static byte[] generateIV() {
        byte[] iv = new byte[ivLen];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }
}

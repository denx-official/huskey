package crypt;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AES {
    private static final int tagLen = 16;

    public static byte[] EnDeCrypt(int opmode, byte[] bytesText, byte[] bytesKey, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(bytesKey, "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(tagLen * 8, iv);
            cipher.init(opmode, keySpec, gcmParameterSpec);
            return cipher.doFinal(bytesText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}

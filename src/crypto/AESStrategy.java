package crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESStrategy implements EncryptionStrategy {

    @Override
    public String encrypt(String data, String key) {
        try {
            SecretKeySpec secretKey = buildKey(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("AES Encryption failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String data, String key) {
        try {
            SecretKeySpec secretKey = buildKey(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("AES Decryption failed: " + e.getMessage(), e);
        }
    }

    private SecretKeySpec buildKey(String key) {
        byte[] keyBytes = new byte[16];
        byte[] raw = key.getBytes();
        System.arraycopy(raw, 0, keyBytes, 0, Math.min(raw.length, 16));
        return new SecretKeySpec(keyBytes, "AES");
    }
}

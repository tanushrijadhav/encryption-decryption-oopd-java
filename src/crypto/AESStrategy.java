package crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AESStrategy implements EncryptionStrategy {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 16;

    @Override
    public String encrypt(String data, String key) {
        try {
            SecretKeySpec secretKey = buildKey(key);

            byte[] iv = new byte[IV_LENGTH];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));

            byte[] result = new byte[IV_LENGTH + encrypted.length];
            System.arraycopy(iv, 0, result, 0, IV_LENGTH);
            System.arraycopy(encrypted, 0, result, IV_LENGTH, encrypted.length);
            return Base64.getEncoder().encodeToString(result);

        } catch (Exception e) {
            throw new RuntimeException("AES Encryption failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String data, String key) {
        try {
            byte[] decoded = Base64.getDecoder().decode(data);
            byte[] iv = Arrays.copyOfRange(decoded, 0, IV_LENGTH);
            byte[] ciphertext = Arrays.copyOfRange(decoded, IV_LENGTH, decoded.length);

            SecretKeySpec secretKey = buildKey(key);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] decrypted = cipher.doFinal(ciphertext);
            return new String(decrypted, "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException("AES Decryption failed: " + e.getMessage(), e);
        }
    }

    private SecretKeySpec buildKey(String key) throws Exception {
        byte[] keyBytes = MessageDigest.getInstance("SHA-256")
                            .digest(key.getBytes("UTF-8"));
        keyBytes = Arrays.copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes, "AES");
    }
}git add src/crypto/AESStrategy.java
git commit -m "Fix AES: ECB→CBC, add IV handling, fix buildKey with SHA-256"
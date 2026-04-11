package crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Blowfish encryption in CBC mode with PBKDF2 key derivation.
 *
 * Blowfish is a symmetric block cipher designed by Bruce Schneier (1993).
 * While older than AES, it remains unbroken and is widely deployed.
 *
 * Security features:
 *   - Blowfish in CBC mode with PKCS5 padding
 *   - PBKDF2WithHmacSHA256 key derivation (password → 128-bit key)
 *   - Random 16-byte salt per encryption
 *   - Random 8-byte IV per encryption (Blowfish block size = 64-bit)
 *
 * Output format (Base64-encoded):
 *   [16 bytes salt][8 bytes IV][N bytes ciphertext]
 */
public class BlowfishStrategy implements EncryptionStrategy {

    private static final int KEY_LENGTH_BITS   = 128;
    private static final int SALT_LENGTH_BYTES = 16;
    private static final int IV_LENGTH_BYTES   = 8;
    private static final int PBKDF2_ITERATIONS = 100_000;
    private static final String KEY_ALGORITHM  = "PBKDF2WithHmacSHA256";
    private static final String CIPHER_ALGO    = "Blowfish/CBC/PKCS5Padding";

    @Override
    public String encrypt(String data, String key) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH_BYTES];
            random.nextBytes(salt);
            byte[] iv = new byte[IV_LENGTH_BYTES];
            random.nextBytes(iv);

            SecretKeySpec secretKey = deriveKey(key, salt);

            Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] ciphertext = cipher.doFinal(data.getBytes("UTF-8"));

            byte[] output = new byte[salt.length + iv.length + ciphertext.length];
            System.arraycopy(salt, 0, output, 0, salt.length);
            System.arraycopy(iv, 0, output, salt.length, iv.length);
            System.arraycopy(ciphertext, 0, output, salt.length + iv.length, ciphertext.length);

            return Base64.getEncoder().encodeToString(output);

        } catch (Exception e) {
            throw new RuntimeException("Blowfish Encryption failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String data, String key) {
        try {
            byte[] decoded = Base64.getDecoder().decode(data);

            byte[] salt = new byte[SALT_LENGTH_BYTES];
            byte[] iv = new byte[IV_LENGTH_BYTES];
            byte[] ciphertext = new byte[decoded.length - SALT_LENGTH_BYTES - IV_LENGTH_BYTES];

            System.arraycopy(decoded, 0, salt, 0, SALT_LENGTH_BYTES);
            System.arraycopy(decoded, SALT_LENGTH_BYTES, iv, 0, IV_LENGTH_BYTES);
            System.arraycopy(decoded, SALT_LENGTH_BYTES + IV_LENGTH_BYTES, ciphertext, 0, ciphertext.length);

            SecretKeySpec secretKey = deriveKey(key, salt);

            Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] plaintext = cipher.doFinal(ciphertext);

            return new String(plaintext, "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException("Blowfish Decryption failed: " + e.getMessage(), e);
        }
    }

    /**
     * Derives a 128-bit Blowfish key from a password using PBKDF2.
     */
    private SecretKeySpec deriveKey(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, KEY_LENGTH_BITS);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "Blowfish");
    }
}

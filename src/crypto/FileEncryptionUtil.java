package crypto;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileEncryptionUtil {

    private AESStrategy aes = new AESStrategy();

    // Encrypts a file and saves it as filename.encrypted
    public void encryptFile(String filePath, String key) throws Exception {
        // Step 1: Read file as raw bytes
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

        // Step 2: Convert bytes to Base64 String so AESStrategy can handle it
        String base64Data = java.util.Base64.getEncoder().encodeToString(fileBytes);

        // Step 3: Encrypt using your existing AESStrategy
        String encrypted = aes.encrypt(base64Data, key);

        // Step 4: Save encrypted result to new file
        Files.write(Paths.get(filePath + ".encrypted"), encrypted.getBytes());
        System.out.println("File encrypted: " + filePath + ".encrypted");
    }

    // Decrypts a .encrypted file and restores original
    public void decryptFile(String filePath, String key) throws Exception {
        // Step 1: Read encrypted file
        String encrypted = new String(Files.readAllBytes(Paths.get(filePath)));

        // Step 2: Decrypt using your existing AESStrategy
        String base64Data = aes.decrypt(encrypted, key);

        // Step 3: Convert Base64 back to original bytes
        byte[] fileBytes = java.util.Base64.getDecoder().decode(base64Data);

        // Step 4: Save restored file (remove .encrypted from name)
        String outputPath = filePath.replace(".encrypted", ".decrypted");
        Files.write(Paths.get(outputPath), fileBytes);
        System.out.println("File decrypted: " + outputPath);
    }
}
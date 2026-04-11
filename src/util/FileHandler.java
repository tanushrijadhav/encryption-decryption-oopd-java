package util;

import context.EncryptionContext;
import exceptions.EncryptionException;

import java.io.*;
import java.nio.file.*;

public class FileHandler {
    public static String readFile(String filePath) {
    try {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    } catch (IOException e) {
        throw new EncryptionException("Could not read file: " + filePath, e);
    }
}

public static void writeFile(String filePath, String content) {
    try {
        Files.write(Paths.get(filePath), content.getBytes());
    } catch (IOException e) {
        throw new EncryptionException("Could not write file: " + filePath, e);
    }
}

    public void encryptFile(String inputPath, String outputPath,
                            EncryptionContext context, String key) {
        String content = readFile(inputPath);
        String encrypted = context.executeEncrypt(content, key);
        writeFile(outputPath, encrypted);
        System.out.println("File encrypted successfully → " + outputPath);
    }

    public void decryptFile(String inputPath, String outputPath,
                            EncryptionContext context, String key) {
        String content = readFile(inputPath);
        String decrypted = context.executeDecrypt(content, key);
        writeFile(outputPath, decrypted);
        System.out.println("File decrypted successfully → " + outputPath);
    }
}

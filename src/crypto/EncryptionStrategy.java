package crypto;

public interface EncryptionStrategy {
  String encrypt(String data, String key);
  String decrypt(String data, String key);
}

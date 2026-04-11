package context;

import crypto.EncryptionStrategy;
import exceptions.EncryptionException;

public class EncryptionContext {
    private EncryptionStrategy strategy;

    public void setStrategy(EncryptionStrategy strategy) {
        if (strategy == null) {
            throw new EncryptionException("Strategy cannot be null");
        }
        this.strategy = strategy;
    }

    public String executeEncrypt(String data, String key) {
        validateInputs(data, key);
        return strategy.encrypt(data, key);
    }

    public String executeDecrypt(String data, String key) {
        validateInputs(data, key);
        return strategy.decrypt(data, key);
    }

    private void validateInputs(String data, String key) {
        if (strategy == null) {
            throw new EncryptionException("No strategy set. Call setStrategy() first.");
        }
        if (data == null || data.isEmpty()) {
            throw new EncryptionException("Input data cannot be empty.");
        }
        if (key == null || key.isEmpty()) {
            throw new EncryptionException("Key cannot be empty.");
        }
    }
}
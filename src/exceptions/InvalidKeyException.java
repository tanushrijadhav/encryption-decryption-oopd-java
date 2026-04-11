package exceptions;

public class InvalidKeyException extends EncryptionException {

    public InvalidKeyException(String message) {
        super("Invalid Key: " + message);
    }
}

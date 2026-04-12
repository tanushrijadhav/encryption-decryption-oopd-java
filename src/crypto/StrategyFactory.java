package crypto;

/**
 * Factory Pattern — creates EncryptionStrategy instances by name.
 *
 * This decouples the controllers from concrete strategy classes.
 * Adding a new algorithm only requires:
 *   1. A new class implementing EncryptionStrategy
 *   2. One new case in this factory
 *
 * OOP Principles demonstrated:
 *   - Factory Method Pattern (GoF)
 *   - Open/Closed Principle (open for extension, closed for modification)
 *   - Dependency Inversion (controllers depend on interface, not concrete classes)
 */
public class StrategyFactory {

    /**
     * Creates an EncryptionStrategy instance based on the algorithm name.
     *
     * @param algorithmName display name from the UI ComboBox
     * @return the corresponding EncryptionStrategy implementation
     * @throws IllegalArgumentException if the algorithm is not recognized
     */
    public static EncryptionStrategy create(String algorithmName) {
        if (algorithmName == null) {
            throw new IllegalArgumentException("Algorithm name cannot be null");
        }

        if (algorithmName.startsWith("AES")) {
            return new AESStrategy();
        } else if (algorithmName.startsWith("Blowfish")) {
            return new BlowfishStrategy();
        } else if (algorithmName.startsWith("Caesar")) {
            return new CaesarStrategy();
        } else {
            throw new IllegalArgumentException("Unknown algorithm: " + algorithmName);
        }
    }
}

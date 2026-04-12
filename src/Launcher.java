/**
 * Launcher wrapper — bypasses the "JavaFX runtime components are missing" error
 * that occurs when launching an Application subclass directly without module-info.
 *
 * Always run THIS class, not MainApp, from the terminal or IntelliJ.
 */
public class Launcher {
    public static void main(String[] args) {
        MainApp.main(args);
    }
}

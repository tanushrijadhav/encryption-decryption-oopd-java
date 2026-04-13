package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;

public class SettingsController {

    // Theme toggle button
    @FXML private ToggleButton settingsThemeToggle;

    // Dropdown for selecting default algorithm
    @FXML private ComboBox<String> defaultAlgoCombo;

    @FXML
    public void initialize() {

        // Populate algorithm dropdown
        defaultAlgoCombo.getItems().addAll(
                "AES-128-CBC (Recommended)",
                "Blowfish-CBC",
                "Caesar Cipher (Educational)"
        );

        // Select default algorithm
        defaultAlgoCombo.getSelectionModel().selectFirst();

        // Set default theme to light mode
        settingsThemeToggle.setSelected(false);
        settingsThemeToggle.setText("LIGHT");
    }

    @FXML
    private void onToggleTheme() {

        // Get current toggle state
        boolean isDark = settingsThemeToggle.isSelected();

        // Get current scene and root node
        javafx.scene.Scene scene = settingsThemeToggle.getScene();
        var root = scene.getRoot();

        if (isDark) {

            // Apply dark mode CSS class if not already present
            if (!root.getStyleClass().contains("dark-mode")) {
                root.getStyleClass().add("dark-mode");
            }

            // Update toggle label
            settingsThemeToggle.setText("DARK");

        } else {

            // Remove dark mode CSS class
            root.getStyleClass().remove("dark-mode");

            // Update toggle label
            settingsThemeToggle.setText("LIGHT");
        }
    }
}
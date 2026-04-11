package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;

public class SettingsController {

    @FXML private ToggleButton settingsThemeToggle;
    @FXML private ComboBox<String> defaultAlgoCombo;

    @FXML
    public void initialize() {
        defaultAlgoCombo.getItems().addAll("AES (Recommended)", "Caesar Cipher");
        defaultAlgoCombo.getSelectionModel().selectFirst();
        settingsThemeToggle.setSelected(false);
        settingsThemeToggle.setText("LIGHT");
    }

    @FXML
    private void onToggleTheme() {
        boolean isDark = settingsThemeToggle.isSelected();
        javafx.scene.Scene scene = settingsThemeToggle.getScene();
        var root = scene.getRoot();

        if (isDark) {
            if (!root.getStyleClass().contains("dark-mode")) {
                root.getStyleClass().add("dark-mode");
            }
            settingsThemeToggle.setText("DARK");
        } else {
            root.getStyleClass().remove("dark-mode");
            settingsThemeToggle.setText("LIGHT");
        }
    }
}

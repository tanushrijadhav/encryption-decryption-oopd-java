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
        settingsThemeToggle.setSelected(true);
    }

    @FXML
    private void onToggleTheme() {
        boolean isDark = settingsThemeToggle.isSelected();
        javafx.scene.Scene scene = settingsThemeToggle.getScene();
        scene.getStylesheets().removeIf(s -> s.contains("dark-theme") || s.contains("light-theme"));
        String themePath = isDark ? "/ui/css/dark-theme.css" : "/ui/css/light-theme.css";
        scene.getStylesheets().add(getClass().getResource(themePath).toExternalForm());
    }
}

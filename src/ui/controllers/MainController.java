package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML private StackPane contentArea;
    @FXML private Label pageTitle;
    @FXML private Label pageSubtitle;
    @FXML private TextField searchField;
    @FXML private ToggleButton themeToggle;
    @FXML private Label themeLabel;

    @FXML private HBox navDashboard;
    @FXML private HBox navEncrypt;
    @FXML private HBox navFiles;
    @FXML private HBox navSettings;

    private HBox activeNavItem;
    private boolean isDarkMode = true;

    private Node dashboardView;
    private Node encryptionView;
    private Node fileView;
    private Node settingsView;

    @FXML
    public void initialize() {
        activeNavItem = navDashboard;
        themeToggle.setSelected(true);
        loadView("Dashboard");
    }

    @FXML
    private void onNavDashboard() {
        setActiveNav(navDashboard);
        setPageHeader("Dashboard", "Overview of your encryption activity");
        loadView("Dashboard");
    }

    @FXML
    private void onNavEncrypt() {
        setActiveNav(navEncrypt);
        setPageHeader("Encrypt / Decrypt", "Transform your data using industry-standard algorithms");
        loadView("Encryption");
    }

    @FXML
    private void onNavFiles() {
        setActiveNav(navFiles);
        setPageHeader("File Operations", "Encrypt and decrypt file contents");
        loadView("File");
    }

    @FXML
    private void onNavSettings() {
        setActiveNav(navSettings);
        setPageHeader("Settings", "Configure your preferences");
        loadView("Settings");
    }

    @FXML
    private void onThemeToggle() {
        isDarkMode = themeToggle.isSelected();
        applyTheme();
        themeLabel.setText(isDarkMode ? "Dark Mode" : "Light Mode");
    }

    private void setActiveNav(HBox navItem) {
        if (activeNavItem != null) {
            activeNavItem.getStyleClass().remove("nav-item-active");
        }
        activeNavItem = navItem;
        if (!activeNavItem.getStyleClass().contains("nav-item-active")) {
            activeNavItem.getStyleClass().add("nav-item-active");
        }
    }

    private void setPageHeader(String title, String subtitle) {
        pageTitle.setText(title);
        pageSubtitle.setText(subtitle);
    }

    private void loadView(String viewName) {
        try {
            Node view = null;
            switch (viewName) {
                case "Dashboard":
                    if (dashboardView == null)
                        dashboardView = FXMLLoader.load(getClass().getResource("/ui/fxml/DashboardView.fxml"));
                    view = dashboardView;
                    break;
                case "Encryption":
                    if (encryptionView == null)
                        encryptionView = FXMLLoader.load(getClass().getResource("/ui/fxml/EncryptionView.fxml"));
                    view = encryptionView;
                    break;
                case "File":
                    if (fileView == null)
                        fileView = FXMLLoader.load(getClass().getResource("/ui/fxml/FileView.fxml"));
                    view = fileView;
                    break;
                case "Settings":
                    if (settingsView == null)
                        settingsView = FXMLLoader.load(getClass().getResource("/ui/fxml/SettingsView.fxml"));
                    view = settingsView;
                    break;
            }
            if (view != null) {
                contentArea.getChildren().setAll(view);
            }
        } catch (IOException e) {
            e.printStackTrace();
            contentArea.getChildren().setAll(new Label("Error loading view: " + e.getMessage()));
        }
    }

    private void applyTheme() {
        var scene = contentArea.getScene();
        scene.getStylesheets().removeIf(s -> s.contains("dark-theme") || s.contains("light-theme"));
        String themePath = isDarkMode ? "/ui/css/dark-theme.css" : "/ui/css/light-theme.css";
        scene.getStylesheets().add(getClass().getResource(themePath).toExternalForm());
    }
}

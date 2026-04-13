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

    // Main content container where views are loaded dynamically
    @FXML private StackPane contentArea;

    // Page title and subtitle labels
    @FXML private Label pageTitle;
    @FXML private Label pageSubtitle;

    // Search field (UI placeholder)
    @FXML private TextField searchField;

    // Theme toggle button and label
    @FXML private ToggleButton themeToggle;
    @FXML private Label themeLabel;

    // Navigation items
    @FXML private HBox navDashboard;
    @FXML private HBox navEncrypt;
    @FXML private HBox navFiles;
    @FXML private HBox navSettings;

    // Currently active navigation item
    private HBox activeNavItem;

    // Theme state (dark/light)
    private boolean isDarkMode = true;

    // Cached views to avoid reloading FXML repeatedly
    private Node dashboardView;
    private Node encryptionView;
    private Node fileView;
    private Node settingsView;

    @FXML
    public void initialize() {

        // Set default active navigation
        activeNavItem = navDashboard;

        // Enable dark mode by default
        themeToggle.setSelected(true);

        // Load initial view
        loadView("Dashboard");
    }

    @FXML
    private void onNavDashboard() {

        // Update active navigation
        setActiveNav(navDashboard);

        // Update page header
        setPageHeader("Dashboard", "Overview of your encryption activity");

        // Load dashboard view
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

        // Update theme state
        isDarkMode = themeToggle.isSelected();

        // Apply selected theme
        applyTheme();

        // Update theme label text
        themeLabel.setText(isDarkMode ? "Dark Mode" : "Light Mode");
    }

    private void setActiveNav(HBox navItem) {

        // Remove active style from previous nav item
        if (activeNavItem != null) {
            activeNavItem.getStyleClass().remove("nav-item-active");
        }

        // Set new active nav item
        activeNavItem = navItem;

        // Add active style
        if (!activeNavItem.getStyleClass().contains("nav-item-active")) {
            activeNavItem.getStyleClass().add("nav-item-active");
        }
    }

    private void setPageHeader(String title, String subtitle) {

        // Update title and subtitle
        pageTitle.setText(title);
        pageSubtitle.setText(subtitle);
    }

    private void loadView(String viewName) {
        try {

            Node view = null;

            // Load view based on name (lazy loading)
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

            // Replace content area with selected view
            if (view != null) {
                contentArea.getChildren().setAll(view);
            }

        } catch (IOException e) {

            // Handle loading errors
            e.printStackTrace();
            contentArea.getChildren().setAll(new Label("Error loading view: " + e.getMessage()));
        }
    }

    private void applyTheme() {

        // Get current scene
        var scene = contentArea.getScene();

        // Remove existing theme stylesheets
        scene.getStylesheets().removeIf(
                s -> s.contains("dark-theme") || s.contains("light-theme")
        );

        // Select theme path
        String themePath = isDarkMode
                ? "/ui/css/dark-theme.css"
                : "/ui/css/light-theme.css";

        // Apply new theme
        scene.getStylesheets().add(
                getClass().getResource(themePath).toExternalForm()
        );
    }
}
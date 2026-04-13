package ui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class StudioController {

    // Main content area for switching views
    @FXML private StackPane contentArea;

    // Container for overlays (menu/contact)
    @FXML private StackPane overlayContainer;

    // Overlay panels
    @FXML private VBox menuOverlay;
    @FXML private VBox contactOverlay;

    // App title label
    @FXML private Label appTitle;

    // Cached views for performance (lazy loading)
    private Node homeView;
    private Node encryptionView;
    private Node fileView;
    private Node settingsView;

    // Track current active view
    private String currentView = "";

    // Theme state
    private boolean isDarkMode = false;

    @FXML
    public void initialize() {

        // Hide overlays initially
        overlayContainer.setVisible(false);
        overlayContainer.setMouseTransparent(true);
        menuOverlay.setVisible(false);
        contactOverlay.setVisible(false);

        // Load default home view
        navigateTo("Home");
    }

    // Handle navigation between views
    public void navigateTo(String viewName) {

        // Prevent reloading same view
        if (viewName.equals(currentView)) return;
        currentView = viewName;

        try {
            Node view = null;

            // Load view dynamically (lazy loading + caching)
            switch (viewName) {

                case "Home":
                    if (homeView == null) {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/ui/fxml/HomeView.fxml")
                        );
                        homeView = loader.load();

                        // Inject controller reference for navigation
                        HomeController hc = loader.getController();
                        hc.setStudioController(this);
                    }
                    view = homeView;
                    break;

                case "Encryption":
                    if (encryptionView == null)
                        encryptionView = FXMLLoader.load(
                                getClass().getResource("/ui/fxml/EncryptionView.fxml")
                        );
                    view = encryptionView;
                    break;

                case "File":
                    if (fileView == null)
                        fileView = FXMLLoader.load(
                                getClass().getResource("/ui/fxml/FileView.fxml")
                        );
                    view = fileView;
                    break;

                case "Settings":
                    if (settingsView == null)
                        settingsView = FXMLLoader.load(
                                getClass().getResource("/ui/fxml/SettingsView.fxml")
                        );
                    view = settingsView;
                    break;
            }

            if (view != null) {

                // Set initial animation state
                Node target = view;
                target.setOpacity(0);
                target.setTranslateY(20);

                // Replace content area
                contentArea.getChildren().setAll(target);

                // Fade animation
                FadeTransition ft = new FadeTransition(Duration.millis(300), target);
                ft.setFromValue(0);
                ft.setToValue(1);

                // Slide animation
                TranslateTransition tt = new TranslateTransition(Duration.millis(300), target);
                tt.setFromY(20);
                tt.setToY(0);

                // Play animations together
                new ParallelTransition(ft, tt).play();
            }

        } catch (IOException e) {

            // Handle view loading errors
            e.printStackTrace();
            contentArea.getChildren().setAll(
                    new Label("Error loading view: " + e.getMessage())
            );
        }
    }

    // Toggle menu overlay
    @FXML
    private void onToggleMenu() {
        if (menuOverlay.isVisible()) closeMenuOverlay();
        else openMenuOverlay();
    }

    // Show menu overlay
    private void openMenuOverlay() {
        contactOverlay.setVisible(false);
        overlayContainer.setVisible(true);
        overlayContainer.setMouseTransparent(false);
        menuOverlay.setVisible(true);
        menuOverlay.setOpacity(0);

        // Fade-in animation
        FadeTransition ft = new FadeTransition(Duration.millis(250), menuOverlay);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    // Hide menu overlay
    private void closeMenuOverlay() {
        FadeTransition ft = new FadeTransition(Duration.millis(200), menuOverlay);
        ft.setFromValue(1);
        ft.setToValue(0);

        // Reset visibility after animation
        ft.setOnFinished(e -> {
            menuOverlay.setVisible(false);
            overlayContainer.setVisible(false);
            overlayContainer.setMouseTransparent(true);
        });

        ft.play();
    }

    // Toggle contact overlay
    @FXML
    private void onToggleContact() {
        if (contactOverlay.isVisible()) closeContactOverlay();
        else openContactOverlay();
    }

    // Show contact overlay
    private void openContactOverlay() {
        menuOverlay.setVisible(false);
        overlayContainer.setVisible(true);
        overlayContainer.setMouseTransparent(false);
        contactOverlay.setVisible(true);
        contactOverlay.setOpacity(0);

        FadeTransition ft = new FadeTransition(Duration.millis(250), contactOverlay);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    // Hide contact overlay
    private void closeContactOverlay() {
        FadeTransition ft = new FadeTransition(Duration.millis(200), contactOverlay);
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.setOnFinished(e -> {
            contactOverlay.setVisible(false);
            overlayContainer.setVisible(false);
            overlayContainer.setMouseTransparent(true);
        });

        ft.play();
    }

    // Navigation from menu
    @FXML private void onMenuHome()     { closeMenuOverlay(); navigateTo("Home"); }
    @FXML private void onMenuEncrypt()  { closeMenuOverlay(); navigateTo("Encryption"); }
    @FXML private void onMenuFiles()    { closeMenuOverlay(); navigateTo("File"); }
    @FXML private void onMenuSettings() { closeMenuOverlay(); navigateTo("Settings"); }

    // Close overlays manually
    @FXML private void onCloseMenu()    { closeMenuOverlay(); }
    @FXML private void onCloseContact() { closeContactOverlay(); }

    // Toggle dark mode using CSS class
    public void toggleDarkMode() {

        isDarkMode = !isDarkMode;

        var root = contentArea.getScene().getRoot();

        if (isDarkMode) {
            if (!root.getStyleClass().contains("dark-mode")) {
                root.getStyleClass().add("dark-mode");
            }
        } else {
            root.getStyleClass().remove("dark-mode");
        }
    }
}
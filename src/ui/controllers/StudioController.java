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

/**
 * Root controller for the 9to5 Studio layout.
 * Manages view switching, menu/contact overlay visibility, and animations.
 */
public class StudioController {

    @FXML private StackPane contentArea;
    @FXML private StackPane overlayContainer;
    @FXML private VBox menuOverlay;
    @FXML private VBox contactOverlay;
    @FXML private Label appTitle;

    private Node homeView;
    private Node encryptionView;
    private Node fileView;
    private Node settingsView;
    private String currentView = "";
    private boolean isDarkMode = false;

    @FXML
    public void initialize() {
        // Start hidden
        overlayContainer.setVisible(false);
        overlayContainer.setMouseTransparent(true);
        menuOverlay.setVisible(false);
        contactOverlay.setVisible(false);

        // Load home view on startup
        navigateTo("Home");
    }

    // ── Navigation (called from overlay or cards) ─────────────────

    public void navigateTo(String viewName) {
        if (viewName.equals(currentView)) return;
        currentView = viewName;

        try {
            Node view = null;
            switch (viewName) {
                case "Home":
                    if (homeView == null) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/HomeView.fxml"));
                        homeView = loader.load();
                        HomeController hc = loader.getController();
                        hc.setStudioController(this);
                    }
                    view = homeView;
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
                Node target = view;
                target.setOpacity(0);
                target.setTranslateY(20);
                contentArea.getChildren().setAll(target);

                // Fade + slide in
                FadeTransition ft = new FadeTransition(Duration.millis(300), target);
                ft.setFromValue(0);
                ft.setToValue(1);

                TranslateTransition tt = new TranslateTransition(Duration.millis(300), target);
                tt.setFromY(20);
                tt.setToY(0);

                ParallelTransition pt = new ParallelTransition(ft, tt);
                pt.play();
            }
        } catch (IOException e) {
            e.printStackTrace();
            contentArea.getChildren().setAll(new Label("Error loading view: " + e.getMessage()));
        }
    }

    // ── Menu Overlay ──────────────────────────────────────────────

    @FXML
    private void onToggleMenu() {
        if (menuOverlay.isVisible()) {
            closeMenuOverlay();
        } else {
            openMenuOverlay();
        }
    }

    private void openMenuOverlay() {
        contactOverlay.setVisible(false);
        overlayContainer.setVisible(true);
        overlayContainer.setMouseTransparent(false);
        menuOverlay.setVisible(true);
        menuOverlay.setOpacity(0);

        FadeTransition ft = new FadeTransition(Duration.millis(250), menuOverlay);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private void closeMenuOverlay() {
        FadeTransition ft = new FadeTransition(Duration.millis(200), menuOverlay);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(e -> {
            menuOverlay.setVisible(false);
            overlayContainer.setVisible(false);
            overlayContainer.setMouseTransparent(true);
        });
        ft.play();
    }

    // ── Contact Overlay ───────────────────────────────────────────

    @FXML
    private void onToggleContact() {
        if (contactOverlay.isVisible()) {
            closeContactOverlay();
        } else {
            openContactOverlay();
        }
    }

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

    // ── Menu navigation items ─────────────────────────────────────

    @FXML private void onMenuHome()     { closeMenuOverlay(); navigateTo("Home"); }
    @FXML private void onMenuEncrypt()  { closeMenuOverlay(); navigateTo("Encryption"); }
    @FXML private void onMenuFiles()    { closeMenuOverlay(); navigateTo("File"); }
    @FXML private void onMenuSettings() { closeMenuOverlay(); navigateTo("Settings"); }

    @FXML
    private void onCloseMenu() {
        closeMenuOverlay();
    }

    @FXML
    private void onCloseContact() {
        closeContactOverlay();
    }

    // ── Theme ─────────────────────────────────────────────────────

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

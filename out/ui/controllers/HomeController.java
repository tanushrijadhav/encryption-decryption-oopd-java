package ui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.util.Duration;

public class HomeController {

    // UI elements from FXML
    @FXML private Label heroTitle;
    @FXML private Label heroSubtitle;
    @FXML private Label scrollPrompt;
    @FXML private HBox cardContainer;

    // Reference to main navigation controller
    private StudioController studioController;

    // Setter for injecting StudioController
    public void setStudioController(StudioController sc) {
        this.studioController = sc;
    }

    @FXML
    public void initialize() {
        // Trigger entrance animations on load
        animateEntrance();
    }

    private void animateEntrance() {

        // Initial state for hero title (hidden + shifted down)
        heroTitle.setOpacity(0);
        heroTitle.setTranslateY(30);

        // Fade + slide animation for title
        FadeTransition ft1 = new FadeTransition(Duration.millis(600), heroTitle);
        ft1.setFromValue(0); ft1.setToValue(1);

        TranslateTransition tt1 = new TranslateTransition(Duration.millis(600), heroTitle);
        tt1.setFromY(30); tt1.setToY(0);

        // Initial state for subtitle
        heroSubtitle.setOpacity(0);
        heroSubtitle.setTranslateY(20);

        // Fade + slide animation for subtitle
        FadeTransition ft2 = new FadeTransition(Duration.millis(500), heroSubtitle);
        ft2.setFromValue(0); ft2.setToValue(1);

        TranslateTransition tt2 = new TranslateTransition(Duration.millis(500), heroSubtitle);
        tt2.setFromY(20); tt2.setToY(0);

        // Initial state for scroll prompt
        scrollPrompt.setOpacity(0);

        // Fade animation for scroll prompt
        FadeTransition ft3 = new FadeTransition(Duration.millis(400), scrollPrompt);
        ft3.setFromValue(0); ft3.setToValue(1);

        // Set initial hidden state for all cards
        for (Node card : cardContainer.getChildren()) {
            card.setOpacity(0);
            card.setTranslateY(40);
        }

        // Pauses between animation steps
        PauseTransition pause1 = new PauseTransition(Duration.millis(100));
        PauseTransition pause2 = new PauseTransition(Duration.millis(200));
        PauseTransition pause3 = new PauseTransition(Duration.millis(100));

        // Sequential animation for title → subtitle → scroll prompt
        SequentialTransition seq = new SequentialTransition(
            new ParallelTransition(ft1, tt1), // Title animation
            pause1,
            new ParallelTransition(ft2, tt2), // Subtitle animation
            pause2,
            ft3,                              // Scroll prompt
            pause3
        );

        // Animate each card one by one
        for (Node card : cardContainer.getChildren()) {

            // Delay before each card animation
            PauseTransition cardDelay = new PauseTransition(Duration.millis(80));

            // Fade animation for card
            FadeTransition cft = new FadeTransition(Duration.millis(400), card);
            cft.setFromValue(0); cft.setToValue(1);

            // Slide-up animation for card
            TranslateTransition ctt = new TranslateTransition(Duration.millis(400), card);
            ctt.setFromY(40); ctt.setToY(0);

            // Add delay + animation to sequence
            seq.getChildren().add(cardDelay);
            seq.getChildren().add(new ParallelTransition(cft, ctt));
        }

        // Start animation sequence
        seq.play();
    }

    @FXML
    private void onCardEncrypt() {
        // Navigate to Encryption screen
        if (studioController != null)
            studioController.navigateTo("Encryption");
    }

    @FXML
    private void onCardFiles() {
        // Navigate to File screen
        if (studioController != null)
            studioController.navigateTo("File");
    }

    @FXML
    private void onCardSettings() {
        // Navigate to Settings screen
        if (studioController != null)
            studioController.navigateTo("Settings");
    }
}
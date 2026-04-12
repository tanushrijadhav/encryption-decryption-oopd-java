package ui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Controls the Home / Landing view with hero text and project cards.
 */
public class HomeController {

    @FXML private Label heroTitle;
    @FXML private Label heroSubtitle;
    @FXML private Label scrollPrompt;
    @FXML private HBox cardContainer;

    private StudioController studioController;

    public void setStudioController(StudioController sc) {
        this.studioController = sc;
    }

    @FXML
    public void initialize() {
        // Entrance animation for hero text
        animateEntrance();
    }

    private void animateEntrance() {
        // Hero title
        heroTitle.setOpacity(0);
        heroTitle.setTranslateY(30);

        FadeTransition ft1 = new FadeTransition(Duration.millis(600), heroTitle);
        ft1.setFromValue(0); ft1.setToValue(1);
        TranslateTransition tt1 = new TranslateTransition(Duration.millis(600), heroTitle);
        tt1.setFromY(30); tt1.setToY(0);

        // Subtitle
        heroSubtitle.setOpacity(0);
        heroSubtitle.setTranslateY(20);

        FadeTransition ft2 = new FadeTransition(Duration.millis(500), heroSubtitle);
        ft2.setFromValue(0); ft2.setToValue(1);
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(500), heroSubtitle);
        tt2.setFromY(20); tt2.setToY(0);

        // Scroll prompt
        scrollPrompt.setOpacity(0);
        FadeTransition ft3 = new FadeTransition(Duration.millis(400), scrollPrompt);
        ft3.setFromValue(0); ft3.setToValue(1);

        // Cards
        for (Node card : cardContainer.getChildren()) {
            card.setOpacity(0);
            card.setTranslateY(40);
        }

        PauseTransition pause1 = new PauseTransition(Duration.millis(100));
        PauseTransition pause2 = new PauseTransition(Duration.millis(200));
        PauseTransition pause3 = new PauseTransition(Duration.millis(100));

        SequentialTransition seq = new SequentialTransition(
            new ParallelTransition(ft1, tt1),
            pause1,
            new ParallelTransition(ft2, tt2),
            pause2,
            ft3,
            pause3
        );

        // Animate cards sequentially
        int i = 0;
        for (Node card : cardContainer.getChildren()) {
            PauseTransition cardDelay = new PauseTransition(Duration.millis(80));
            FadeTransition cft = new FadeTransition(Duration.millis(400), card);
            cft.setFromValue(0); cft.setToValue(1);
            TranslateTransition ctt = new TranslateTransition(Duration.millis(400), card);
            ctt.setFromY(40); ctt.setToY(0);
            seq.getChildren().add(cardDelay);
            seq.getChildren().add(new ParallelTransition(cft, ctt));
            i++;
        }

        seq.play();
    }

    // ── Card click handlers ───────────────────────────────────────

    @FXML
    private void onCardEncrypt() {
        if (studioController != null) studioController.navigateTo("Encryption");
    }

    @FXML
    private void onCardFiles() {
        if (studioController != null) studioController.navigateTo("File");
    }

    @FXML
    private void onCardSettings() {
        if (studioController != null) studioController.navigateTo("Settings");
    }
}

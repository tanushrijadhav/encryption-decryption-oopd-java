package ui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DashboardController {

    @FXML private Label totalOpsValue;
    @FXML private Label successValue;
    @FXML private Label activeAlgoValue;
    @FXML private Label failedValue;
    @FXML private VBox activityList;

    @FXML
    public void initialize() {
        populateActivityList();
    }

    private void populateActivityList() {
        String[][] activities = {
            {"AES Encryption",     "document.txt",  "2 min ago",  "ok"},
            {"Caesar Decryption",  "message.txt",   "15 min ago", "ok"},
            {"AES Encryption",     "secrets.dat",   "1 hr ago",   "ok"},
            {"Caesar Encryption",  "notes.txt",     "3 hrs ago",  "ok"},
            {"AES Decryption",     "backup.enc",    "5 hrs ago",  "fail"},
        };

        for (String[] a : activities) {
            boolean success = a[3].equals("ok");

            HBox item = new HBox(12);
            item.getStyleClass().add("activity-item");
            item.setAlignment(Pos.CENTER_LEFT);
            item.setPadding(new Insets(12, 16, 12, 16));

            Label dot = new Label("\u25CF");
            dot.getStyleClass().add(success ? "activity-success" : "activity-fail");

            VBox details = new VBox(2);
            HBox.setHgrow(details, Priority.ALWAYS);
            Label title = new Label(a[0]);
            title.getStyleClass().add("activity-title");
            Label file = new Label(a[1]);
            file.getStyleClass().add("activity-file");
            details.getChildren().addAll(title, file);

            Label time = new Label(a[2]);
            time.getStyleClass().add("activity-time");

            item.getChildren().addAll(dot, details, time);
            activityList.getChildren().add(item);
        }
    }
}

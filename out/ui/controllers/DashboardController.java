package ui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DashboardController {

    // Labels bound to FXML for displaying stats
    @FXML private Label totalOpsValue;
    @FXML private Label successValue;
    @FXML private Label activeAlgoValue;
    @FXML private Label failedValue;

    // Container for dynamically added activity items
    @FXML private VBox activityList;

    @FXML
    public void initialize() {
        // Called automatically after FXML is loaded
        populateActivityList();
    }

    private void populateActivityList() {

        // Sample activity data (operation, file, time, status)
        String[][] activities = {
            {"AES Encryption",      "document.txt",  "2 min ago",  "ok"},
            {"Blowfish Encryption", "credentials.dat","10 min ago", "ok"},
            {"Caesar Decryption",   "message.txt",   "15 min ago", "ok"},
            {"AES Encryption",      "secrets.dat",   "1 hr ago",   "ok"},
            {"Blowfish Decryption", "archive.enc",   "2 hrs ago",  "ok"},
            {"Caesar Encryption",   "notes.txt",     "3 hrs ago",  "ok"},
            {"AES Decryption",      "backup.enc",    "5 hrs ago",  "fail"},
        };

        // Loop through each activity entry
        for (String[] a : activities) {

            // Check if operation was successful
            boolean success = a[3].equals("ok");

            // Create horizontal container for activity item
            HBox item = new HBox(12);
            item.getStyleClass().add("activity-item"); // Apply CSS class
            item.setAlignment(Pos.CENTER_LEFT);        // Align content
            item.setPadding(new Insets(12, 16, 12, 16)); // Set spacing

            // Create status indicator (dot)
            Label dot = new Label("\u25CF");
            dot.getStyleClass().add(success ? "activity-success" : "activity-fail");

            // Create container for activity details
            VBox details = new VBox(2);
            HBox.setHgrow(details, Priority.ALWAYS); // Allow expansion

            // Activity title (algorithm used)
            Label title = new Label(a[0]);
            title.getStyleClass().add("activity-title");

            // File name label
            Label file = new Label(a[1]);
            file.getStyleClass().add("activity-file");

            // Add title and file to details container
            details.getChildren().addAll(title, file);

            // Time label (when operation occurred)
            Label time = new Label(a[2]);
            time.getStyleClass().add("activity-time");

            // Add all components to activity item
            item.getChildren().addAll(dot, details, time);

            // Add item to main activity list (VBox)
            activityList.getChildren().add(item);
        }
    }
}